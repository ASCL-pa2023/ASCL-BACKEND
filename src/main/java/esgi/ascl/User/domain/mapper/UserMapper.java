package esgi.ascl.User.domain.mapper;

import esgi.ascl.User.domain.entities.DashboardUser;
import esgi.ascl.User.domain.entities.DashboardUserResponse;
import esgi.ascl.User.domain.entities.User;
import esgi.ascl.User.infrastructure.web.response.UserResponse;
import esgi.ascl.license.domain.mapper.LicenseMapper;

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

    public static DashboardUserResponse dashboardUserResponse(DashboardUser dashboardUser) {
        return new DashboardUserResponse()
                .setId(dashboardUser.getId())
                .setFirstname(dashboardUser.getFirstname())
                .setLastname(dashboardUser.getLastname())
                .setRole(dashboardUser.getRole())
                .setNbFollowers(dashboardUser.getNbFollowers())
                .setLicense(
                        dashboardUser.getLicense() == null ?
                                null:
                                LicenseMapper.toResponse(dashboardUser.getLicense())
                );
    }

}
