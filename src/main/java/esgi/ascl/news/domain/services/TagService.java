package esgi.ascl.news.domain.services;

import esgi.ascl.news.domain.entities.NewsEntity;
import esgi.ascl.news.domain.entities.TagEntity;
import esgi.ascl.news.infrastructure.repositories.TagRepository;

import java.util.List;

public class TagService {

    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public TagEntity create(String name, NewsEntity newsEntity){
        var tagEntity = new TagEntity()
                .setName(name)
                .setNews(newsEntity);
        return tagRepository.save(tagEntity);
    }

    public List<TagEntity> getAll(){
        return tagRepository.findAll();
    }

    public TagEntity getById(Long id){
        return tagRepository.findById(id).orElse(null);
    }

    public List<TagEntity> getAllByName(String name){
        return tagRepository.findAllByName(name);
    }

    public List<TagEntity> getAllByNewsId(Long newsId){
        return tagRepository.findAllByNewsId(newsId);
    }

    public void deleteAllByNewsId(Long newsId){
        tagRepository.deleteAllByNewsId(newsId);
    }
}
