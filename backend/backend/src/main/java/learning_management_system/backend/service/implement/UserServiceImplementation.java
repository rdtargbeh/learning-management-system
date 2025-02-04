package learning_management_system.backend.service.implement;


import jakarta.persistence.EntityNotFoundException;
import learning_management_system.backend.dto.UserDto;
import learning_management_system.backend.entity.*;
import learning_management_system.backend.enums.PermissionAction;
import learning_management_system.backend.mapper.UserMapper;
import learning_management_system.backend.repository.*;
import learning_management_system.backend.service.UserService;
import learning_management_system.backend.utility.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private StaffRepository staffRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private TenantRepository tenantRepository;
    @Autowired
    private AdminRepository adminRepository;



    // Create new User
    @Override
    public UserDto createUser(UserDto userDto) {
        // Check if username already exists
        if (userRepository.findByUserName(userDto.getUserName()).isPresent()) {
            throw new RuntimeException("Username already exists: " + userDto.getUserName());
        }

        // Check if email already exists
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists: " + userDto.getEmail());
        }

        User user = UserMapper.toEntity(userDto);

        // Check if tenant exists and assign it to the user
        if (userDto.getTenantId() != null) {
            Tenant tenant = tenantRepository.findById(userDto.getTenantId())
                    .orElseThrow(() -> new RuntimeException("Tenant not found with ID: " + userDto.getTenantId()));
            user.setTenant(tenant);
        }

        // Assign roles to the user if provided
        if (userDto.getRoleIds() != null && !userDto.getRoleIds().isEmpty()) {
            List<Role> roles = roleRepository.findAllById(userDto.getRoleIds());
            if (roles.size() != userDto.getRoleIds().size()) {
                throw new RuntimeException("Some roles do not exist");
            }
            user.setRoles(new HashSet<>(roles));
        }

        return UserMapper.toDto(userRepository.save(user));
    }


    // Update User Method
    @Override
    public UserDto updateUser(Long userId, UserDto userDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

//        // Check if updated email already exists
        if (!user.getEmail().equals(userDto.getEmail())
                && userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists: " + userDto.getEmail());
        }

        // Check if updated username already exists
        if (!user.getUserName().equals(userDto.getUserName())
                && userRepository.findByUserName(userDto.getUserName()).isPresent()) {
            throw new RuntimeException("Username already exists: " + userDto.getUserName());
        }

        // Update user fields
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setUserName(userDto.getUserName());
        user.setPreferredLanguage(userDto.getPreferredLanguage());
        user.setNotificationPreference(userDto.getNotificationPreference());
        user.setTimezone(userDto.getTimezone());
        user.setProfilePictureUrl(userDto.getProfilePictureUrl());
        user.setActive(userDto.isActive());

        // Update tenant if provided
        if (userDto.getTenantId() != null) {
            Tenant tenant = tenantRepository.findById(userDto.getTenantId())
                    .orElseThrow(() -> new RuntimeException("Tenant not found with ID: " + userDto.getTenantId()));
            user.setTenant(tenant);
        }

        // Update roles if provided
        if (userDto.getRoleIds() != null && !userDto.getRoleIds().isEmpty()) {
            List<Role> roles = roleRepository.findAllById(userDto.getRoleIds());
            if (roles.size() != userDto.getRoleIds().size()) {
                throw new RuntimeException("Some roles do not exist");
            }
            user.setRoles(new HashSet<>(roles));
        }

        // or  Fetch roles by IDs
//        Set<Role> roles = userDto.getRoleIds().stream()
//                .map(roleId -> roleRepository.findById(roleId)
//                        .orElseThrow(() -> new IllegalArgumentException("Role not found with id: " + roleId)))
//                .collect(Collectors.toSet());

        return UserMapper.toDto(userRepository.save(user));
    }

    // Get All Users Method
    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }

    // Find User By ID
    @Override
    public UserDto getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
        return UserMapper.toDto(user);
    }


    @Override
    public UserDto findByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + email));
        return UserMapper.toDto(user);
    }

    // Get User by Role ID
    @Override
    public List<UserDto> getUsersByRoleId(Long roleId) {
        // Validate that the role exists
        boolean roleExists = roleRepository.existsById(roleId);
        if (!roleExists) {
            throw new IllegalArgumentException("Role not found with ID: " + roleId);
        }

        // Fetch users by role
        List<User> users = userRepository.findUsersByRoleId(roleId);
        return users.stream().map(UserMapper::toDto).collect(Collectors.toList());
    }

    // Find User By Role Name Method
    @Override
    public List<UserDto> getUsersByRoleName(String roleName) {
        List<User> users = userRepository.findUsersByRoleName(roleName);
        if (users.isEmpty()) {
            throw new IllegalArgumentException("No users found with role name: " + roleName);
        }
        return users.stream().map(UserMapper::toDto).collect(Collectors.toList());
    }



    // Assigned User a role
    @Override
    public void assignRoleToUser(Long userId, String roleName) {
        // Ensure the user doesn't already belong to another specific entity
        if (studentRepository.existsById(userId) || staffRepository.existsById(userId) ||
                tenantRepository.existsById(userId) || adminRepository.existsById(userId)) {
            throw new IllegalArgumentException("User with ID: " + userId + " is already assigned a specific role.");
        }

        // Assign the role
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        Role role = roleRepository.findByRoleName(roleName)
                .orElseThrow(() -> new IllegalArgumentException("Role not found: " + roleName));

        // Ensure the role is not already assigned
        if (!user.getRoles().contains(role)) {
            user.getRoles().add(role);
        }

        userRepository.save(user);
    }


    // Deletes both User and the associated Staff due to cascade
    @Override
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        // Check if the user is linked to a Staff record
        Optional<Staff> staff = staffRepository.findById(userId);
        staff.ifPresent(staffRepository::delete); // Deletes both Staff and User

        // Check if the user is linked to a Student record
        Optional<Student> student = studentRepository.findById(userId);
        student.ifPresent(studentRepository::delete); // Deletes the Student record if present

        // Check if the user linked to a Admin record
        Optional<Admin> admin = adminRepository.findById(userId);
        admin.ifPresent(adminRepository::delete);

        // If the user is not linked to either Staff or Student, delete the User directly
        if (!staff.isPresent() && !student.isPresent()) {
            userRepository.delete(user);
        }
    }


    @Override
    public void removeRoleFromUser(Long userId, String roleName) {
        // Fetch the user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Find the role to be removed
        Role role = roleRepository.findByRoleName(roleName)
                .orElseThrow(() -> new IllegalArgumentException("Role not found"));

        // Remove the role from the user's roles
        boolean removed = user.getRoles().remove(role);
        if (!removed) {
            throw new IllegalArgumentException("Role not assigned to the user");
        }

        // Determine the new primary role (if any)
        Role primaryRole = user.getRoles().stream().findFirst().orElse(null);

        // Log or update based on the primary role if needed
        if (primaryRole != null) {
            System.out.println("New primary role: " + primaryRole.getRoleName());
        } else {
            System.out.println("No roles left for the user.");
        }

        // Save the updated user
        userRepository.save(user);
    }



    @Override
    public String getRoleNameByUserId(Long userId) {
        return userRepository.findRoleNameByUserId(userId);
    }

    public boolean hasPermission(User user, String resource, PermissionAction action) {
        return user.getRoles().stream()
                .flatMap(role -> role.getPermissions().stream())
                .anyMatch(permission -> permission.getResource().equals(resource) &&
                        permission.getAction().equals(action));
    }


    /**
     * Finds a user by their ID.
     *
     * @param userId the ID of the user.
     * @return an Optional containing the User if found.
     */
    public Optional<User> findById(Long userId) {
        return userRepository.findById(userId);
    }

    // Assuming your UserDetails class has a method to get user ID
    public Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("No authenticated user found.");
        }
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        return principal.getId(); // This now refers to the `getId` method in UserPrincipal
    }
    //  Fetch the User Entity for Create Question usage
    public User getUserEntityById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
    }






    // Delete user from user unity  only
//        @Override
//    public void deleteUser(Long id) {
//        if (!userRepository.existsById(id)) {
//            throw new IllegalArgumentException("User not found with id: " + id);
//        }
//        userRepository.deleteById(id);
//    }

    //    @Override
//    public UserDto createUser(UserDto userDto) {
//        // Check if username or email already exists
//        if (userRepository.existsByUserName(userDto.getUserName())) {
//            throw new IllegalArgumentException("Username already exists.");
//        }
//        if (userRepository.existsByEmail(userDto.getEmail())) {
//            throw new IllegalArgumentException("Email already exists.");
//        }
//
//        // Fetch roles by IDs
//        Set<Role> roles = userDto.getRoleIds().stream()
//                .map(roleId -> roleRepository.findById(roleId)
//                        .orElseThrow(() -> new IllegalArgumentException("Role not found with id: " + roleId)))
//                .collect(Collectors.toSet());
//
//        // Map DTO to Entity
//        User user = UserMapper.toEntity(userDto, roles);
//
//        // Assign roles and set the primary role name if applicable
//        if (!roles.isEmpty()) {
//            user.setRoles(roles);
//            user.setRoleName(roles.iterator().next().getRoleName()); // Set the first role as the primary role
//        }
//
//        // Save user
//        User savedUser = userRepository.save(user);
//        return UserMapper.toDto(savedUser);
//    }

//    @Override
//    public void assignRoleToUser(Long userId, Long roleId) {
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
//
//        Role role = roleRepository.findById(roleId)
//                .orElseThrow(() -> new RuntimeException("Role not found with ID: " + roleId));
//
//        if (user.getRoles().contains(role)) {
//            throw new RuntimeException("Role is already assigned to the user.");
//        }
//
//        user.getRoles().add(role);
//        userRepository.save(user);
//    }


}
