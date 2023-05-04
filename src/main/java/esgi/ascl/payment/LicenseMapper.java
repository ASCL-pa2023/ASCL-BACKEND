package esgi.ascl.payment;

import esgi.ascl.User.domain.entities.License;
import esgi.ascl.User.domain.mapper.UserMapper;
import esgi.ascl.User.infrastructure.web.response.LicenseResponse;

public class LicenseMapper {


    public static LicenseResponse toResponse(License license) {
        return new LicenseResponse()
                .setId(license.getId())
                .setUser(UserMapper.entityToResponse(license.getUser()))
                .setNumber(license.getNumber())
                .setExpirationDate(license.getExpirationDate())
                .setCreationDate(license.getCreatedAt());
    }
}
