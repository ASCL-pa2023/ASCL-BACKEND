package esgi.ascl.news.domain.services;

import esgi.ascl.news.domain.entities.NewsEntity;
import esgi.ascl.news.domain.entities.TagEntity;
import esgi.ascl.news.infrastructure.repositories.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TagServiceTest {
    TagRepository tagRepository;
    TagService tagService;
    NewsEntity newsEntity = new NewsEntity();


    TagEntity tagEntity1 = new TagEntity();
    TagEntity tagEntity2 = new TagEntity();
    TagEntity tagEntity3 = new TagEntity();


    @BeforeEach
    void setUp(){
        tagRepository = Mockito.mock(TagRepository.class);
        tagService = new TagService(tagRepository);


        newsEntity
                .setId(Long.parseLong("1"))
                .setTitle("title")
                .setContent("content")
                .setCreationDate(new Date());


        tagEntity1
                .setId(Long.parseLong("1"))
                .setName("tag")
                .setNews(newsEntity);

        tagEntity2
                .setId(Long.parseLong("2"))
                .setName("tag")
                .setNews(newsEntity);

        tagEntity3
                .setId(Long.parseLong("3"))
                .setName("tag")
                .setNews(newsEntity);
    }
/*
    @Test
    void should_create_tag() {
        var tagEntity = new TagEntity()
                .setId(Long.parseLong("1"))
                .setName("tag")
                .setNews(newsEntity);

        lenient().when(tagRepository.save(tagEntity)).thenReturn(tagEntity);

        var res = this.tagService.create("tag", newsEntity);
        assertEquals(tagEntity, res);
    }

 */

    @Test
    void should_get_all_tags() {
        var tagList = List.of(tagEntity1, tagEntity2);

        when(tagRepository.findAll()).thenReturn(tagList);

        var res = this.tagService.getAll();
        assertEquals(tagList, res);
    }

    @Test
    void should_get_tag_by_id() {
        when(tagRepository.findById(Long.parseLong("2")))
                .thenReturn(Optional.of(tagEntity1));

        var res = this.tagService.getById(Long.parseLong("2"));
        assertEquals(tagEntity1, res);
    }

    @Test
    void should_get_all_tag_by_name() {
        var tagList = List.of(tagEntity1, tagEntity2);

        when(tagRepository.findAllByName("tag")).thenReturn(tagList);

        var res = this.tagService.getAllByName("tag");
        assertEquals(tagList, res);
    }

    @Test
    void should_get_all_tag_by_news_id() {
        var tagList = List.of(tagEntity1, tagEntity2);

        when(tagRepository.findAllByNewsId(Long.parseLong("1"))).thenReturn(tagList);

        var res = this.tagService.getAllByNewsId(Long.parseLong("1"));
        assertEquals(tagList, res);
    }
/*
    @Test
    void should_delete_tag_by_id() {
        when(tagRepository.findById(Long.parseLong("1")))
                .thenReturn(Optional.of(tagEntity1));
        this.tagService.deleteAllByNewsId(Long.parseLong("1"));
        Mockito.verify(tagRepository).deleteById(Long.parseLong("1"));
    }
    
 */

}