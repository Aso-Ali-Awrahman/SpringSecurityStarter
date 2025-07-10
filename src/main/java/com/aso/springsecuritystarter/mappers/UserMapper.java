package com.aso.springsecuritystarter.mappers;

import com.aso.springsecuritystarter.dtos.UserDto;
import com.aso.springsecuritystarter.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto toDto(User user) {
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );
    }


}
