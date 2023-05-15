package esgi.ascl.User.domain.service;

import esgi.ascl.User.domain.entities.ProfilePicture;
import esgi.ascl.User.infrastructure.repositories.ProfilePictureRepository;
import esgi.ascl.User.infrastructure.web.request.ProfilePictureRequest;
import esgi.ascl.User.infrastructure.web.response.ProfilePictureResponse;
import esgi.ascl.image.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Service
public class ProfilePictureService {
    private final ProfilePictureRepository profilePictureRepository;
    private final FileService fileService;
    private final UserService userService;

    public ProfilePictureService(ProfilePictureRepository profilePictureRepository, FileService fileService, UserService userService) {
        this.profilePictureRepository = profilePictureRepository;
        this.fileService = fileService;
        this.userService = userService;
    }

    private ProfilePicture create(ProfilePictureRequest profilePictureRequest) {
        var user = userService.getById(profilePictureRequest.getUserId());
        var profilePicture = new ProfilePicture()
                .setFilename(profilePictureRequest.getFilename())
                .setUser(user)
                .setCreationDate(new Date());
        return profilePictureRepository.save(profilePicture);
    }

    public ProfilePicture getByUserId(Long userId) {
        return profilePictureRepository.findByUserId(userId).orElse(null);
    }


    public void upload(Long userId, MultipartFile file) {
        var s3Object = fileService.putFile(file);

        profilePictureRepository.findByUserId(userId)
                .ifPresent(profilePicture -> delete(userId));

        var profilePictureRequest = new ProfilePictureRequest()
                .setUserId(userId)
                .setFilename(s3Object.getKey());
        create(profilePictureRequest);
    }

    public ProfilePictureResponse download(Long userId) {
        var profilePicture =  profilePictureRepository.findByUserId(userId).orElse(null);
        if(profilePicture == null) return null;

        return new ProfilePictureResponse()
                .setId(profilePicture.getId())
                .setFilename(profilePicture.getFilename())
                .setUserId(profilePicture.getUser().getId())
                .setFile(fileService.getFile(profilePicture.getFilename()))
                .setCreationDate(profilePicture.getCreationDate());
    }


    public void delete(Long userId){
        profilePictureRepository.findByUserId(userId)
                .ifPresent(profilePicture -> {
                    fileService.deleteFile(profilePicture.getFilename());
                    profilePictureRepository.delete(profilePicture.setUser(null));
                }
        );
    }
}
