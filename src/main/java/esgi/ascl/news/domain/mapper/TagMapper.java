package esgi.ascl.news.domain.mapper;

import esgi.ascl.news.domain.entities.TagEntity;
import esgi.ascl.news.infrastructure.web.responses.TagResponse;

public class TagMapper {
    public static TagResponse entityToResponse(TagEntity tagEntity){
        return new TagResponse()
                .setName(tagEntity.getName())
                .setPostId(tagEntity.getNews().getId());
    }

}
