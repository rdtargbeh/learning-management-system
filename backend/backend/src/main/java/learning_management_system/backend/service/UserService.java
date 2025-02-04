package learning_management_system.backend.service;

import learning_management_system.backend.dto.UserDto;
import learning_management_system.backend.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserDto> getAllUsers();

    UserDto getUserById(Long id);

    UserDto createUser(UserDto userDto);

    UserDto updateUser(Long id, UserDto userDto);

    void deleteUser(Long id);

    UserDto findByEmail(String email);

    List<UserDto> getUsersByRoleId(Long roleId);

    List<UserDto> getUsersByRoleName(String roleName);

    void assignRoleToUser(Long userId, String roleName);

    void removeRoleFromUser(Long userId, String roleName);

    String getRoleNameByUserId(Long userId);

    Optional<User> findById(Long userId);

    Long getCurrentUserId();

    User getUserEntityById(Long userId);


}
