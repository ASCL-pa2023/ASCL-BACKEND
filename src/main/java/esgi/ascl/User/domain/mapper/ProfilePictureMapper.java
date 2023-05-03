package esgi.ascl.User.domain.mapper;

import esgi.ascl.User.domain.entities.ProfilePicture;
import esgi.ascl.User.infrastructure.web.response.ProfilePictureResponse;

public class ProfilePictureMapper {

    public static ProfilePictureResponse toResponse(ProfilePicture profilePicture){
        return new ProfilePictureResponse()
                .setId(profilePicture.getId())
                .setFilename(profilePicture.getFilename())
                .setCreationDate(profilePicture.getCreationDate());
    }
}
