package esgi.ascl.conversation.domain.services;

import esgi.ascl.conversation.domain.entities.ConversationImage;
import esgi.ascl.conversation.domain.mapper.ConversationImageMapper;
import esgi.ascl.conversation.infrastructure.repositories.ConversationImageRepository;
import esgi.ascl.conversation.infrastructure.web.requests.ConversationImageRequest;
import esgi.ascl.conversation.infrastructure.web.response.ConversationImageResponse;
import esgi.ascl.image.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ConversationImageService {
    private final ConversationImageRepository conversationImageRepository;
    private final ConversationService conversationService;
    private final FileService fileService;

    public ConversationImageService(ConversationImageRepository conversationImageRepository, ConversationService conversationService, FileService fileService) {
        this.conversationImageRepository = conversationImageRepository;
        this.conversationService = conversationService;
        this.fileService = fileService;
    }

    public ConversationImage create(ConversationImageRequest conversationImageRequest) {
        var conversation = conversationService.getById(conversationImageRequest.getConversationId());

        return conversationImageRepository.save(
                ConversationImageMapper.toEntity(conversationImageRequest, conversation)
        );
    }

    public void uploadImage(Long conversationId, MultipartFile file) {
        var old = conversationImageRepository.findByConversationId(conversationId);
        if(old != null) {
            old.setConversation(null);
            conversationImageRepository.delete(old);
        }

        var s3Object = fileService.putFile(file);

        var conversationImageRequest = new ConversationImageRequest()
                .setConversationId(conversationId)
                .setFilename(s3Object.getKey())
                .setUrl(s3Object.getObjectContent().getHttpRequest().getURI().toString());

        create(conversationImageRequest);
    }

    public ConversationImageResponse getByConversationId(Long conversationId) {
        var conversationImageEntity = conversationImageRepository.findByConversationId(conversationId);
        if(conversationImageEntity == null) return null;
        var image = fileService.getFile(conversationImageEntity.getFilename());
        return ConversationImageMapper.toEntity(conversationImageEntity, image);
    }

    public void deleteByConversationId(Long conversationId) {
        var conversationImageEntity = conversationImageRepository.findByConversationId(conversationId);
        conversationImageRepository.delete(conversationImageEntity);
    }
}
