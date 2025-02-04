package learning_management_system.backend.mapper;

import learning_management_system.backend.dto.UserDto;
import learning_management_system.backend.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public static User toEntity(UserDto userDto) {
        if (userDto == null) {
            return null;
        }

        User user = new User();
        user.setUserName(userDto.getUserName());
        user.setEmail(userDto.getEmail());
//        user.setPassword(userDto.getPassword());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setProfilePictureUrl(userDto.getProfilePictureUrl());
        user.setEmailVerified(userDto.getEmailVerified());
        user.setActive(userDto.isActive());
        user.setPreferredLanguage(userDto.getPreferredLanguage());
        user.setNotificationPreference(userDto.getNotificationPreference());
        user.setTimezone(userDto.getTimezone());
        user.setMetadata(userDto.getMetadata());
        user.setPreferences(userDto.getPreferences());
        return user;
    }

    public static UserDto toDto(User user) {
        if (user == null) {
            return null;
        }

        UserDto userDto = new UserDto();
        userDto.setUserId(user.getUserId());
        userDto.setUserName(user.getUserName());
        userDto.setEmail(user.getEmail());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmailVerified(user.getEmailVerified());
        userDto.setActive(user.getActive());
        userDto.setPreferredLanguage(user.getPreferredLanguage());
        userDto.setNotificationPreference(user.getNotificationPreference());
        userDto.setTimezone(user.getTimezone());
        userDto.setMetadata(user.getMetadata());
        userDto.setPreferences(user.getPreferences());
        return userDto;
    }
}
