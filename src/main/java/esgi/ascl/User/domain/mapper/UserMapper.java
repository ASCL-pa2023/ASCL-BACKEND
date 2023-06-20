package esgi.ascl.User.domain.mapper;

import esgi.ascl.User.domain.entities.User;
import esgi.ascl.User.infrastructure.web.response.UserResponse;

public class UserMapper {

    public static UserResponse entityToResponse(User user) {
        return new UserResponse()
                .setId(user.getId())
                .setEmail(user.getEmail())
                .setPhone(user.getPhone())
                .setFirstname(user.getFirstname())
                .setLastname(user.getLastname())
                .setBirthday(user.getBirthday())
                .setRole(user.getRole());
    }

}
