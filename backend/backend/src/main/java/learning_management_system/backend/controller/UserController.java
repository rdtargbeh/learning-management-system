package learning_management_system.backend.controller;

import learning_management_system.backend.dto.UserDto;
import learning_management_system.backend.entity.User;
import learning_management_system.backend.mapper.UserMapper;
import learning_management_system.backend.repository.UserRepository;
import learning_management_system.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;


    // Build A Create User REST API
    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        try {
            UserDto createdUser = userService.createUser(userDto);
            return ResponseEntity.ok(createdUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Build A Update User REST API
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        try {
            UserDto updatedUser = userService.updateUser(id, userDto);
            return ResponseEntity.ok(updatedUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Build A Get  All Users REST API
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // Build A Get User By ID REST API
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        try {
            UserDto user = userService.getUserById(id);
            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Build A Get User By Email REST API
    @GetMapping("/email")
    public ResponseEntity<UserDto> getUserByEmail(@RequestParam String email) {
        UserDto userDto = userService.findByEmail(email);
        return ResponseEntity.ok(userDto);
    }

   // Build A Get User By Role ID REST API
    @GetMapping("/role/{roleId}")
    public ResponseEntity<List<UserDto>> getUsersByRoleId(@PathVariable Long roleId) {
        List<UserDto> users = userService.getUsersByRoleId(roleId);
        return ResponseEntity.ok(users);
    }

    // Build A Get User By Role Name REST API
//    @GetMapping("/role-name/{roleName}")
//    public ResponseEntity<List<UserDTO>> getUsersByRoleName(@PathVariable String roleName) {
//        List<UserDTO> users = userService.getUsersByRoleName(roleName);
//        return ResponseEntity.ok(users);
//    }

    @GetMapping("/role/{roleName}")
    public ResponseEntity<List<UserDto>> getUsersByRoleName(@PathVariable String roleName) {
        List<User> users = userRepository.findUsersByRoleName(roleName);
        List<UserDto> userDtos = users.stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDtos);
    }


    // Build Assign User Role REST API
    @PostMapping("/{userId}/assign-role")
    public ResponseEntity<String> assignRoleToUser(
            @PathVariable Long userId,
            @RequestParam String roleName) {
        userService.assignRoleToUser(userId, roleName);
        return ResponseEntity.ok("Role '" + roleName + "' assigned to user with ID " + userId);
    }

    // Build A Get Role User By ID REST API
    @GetMapping("/{userId}/role-name")
    public ResponseEntity<String> getRoleNameByUserId(@PathVariable Long userId) {
        String roleName = userService.getRoleNameByUserId(userId);
        return ResponseEntity.ok(roleName);
    }

    // Build A Remove Role From User REST API
    @DeleteMapping("/{userId}/roles/{roleName}")
    public ResponseEntity<String> removeRoleFromUser(@PathVariable Long userId, @PathVariable String roleName) {
        try {
            userService.removeRoleFromUser(userId, roleName);
            return ResponseEntity.ok("Role '" + roleName + "' removed successfully from user with ID: " + userId);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while removing the role.");
        }
    }

    // Build A Delete User REST API
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok("User and associated Staff deleted successfully, if applicable.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
//        try {
//            userService.deleteUser(id);
//            return ResponseEntity.noContent().build();
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.notFound().build();
//        }
//    }

}
