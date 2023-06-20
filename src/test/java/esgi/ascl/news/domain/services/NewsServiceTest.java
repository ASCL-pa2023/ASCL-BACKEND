package esgi.ascl.news.domain.services;

import esgi.ascl.User.domain.entities.User;
import esgi.ascl.User.domain.service.UserService;
import esgi.ascl.news.domain.entities.NewsEntity;
import esgi.ascl.news.domain.mapper.NewsMapper;
import esgi.ascl.news.infrastructure.repositories.NewsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NewsServiceTest {
    NewsRepository newsRepository;
    NewsMapper newsMapper;
    UserLikeService userLikeService;
    UserService userService;
    TagService tagService;
    NewsService newsService;


    NewsEntity newsEntity1 = new NewsEntity();
    NewsEntity newsEntity2 = new NewsEntity();
    User user = new User();
    
    @BeforeEach
    void setUp(){
        newsRepository = Mockito.mock(NewsRepository.class);
        newsMapper = Mockito.mock(NewsMapper.class);
        userLikeService = Mockito.mock(UserLikeService.class);
        userService = Mockito.mock(UserService.class);
        tagService = Mockito.mock(TagService.class);

        newsService = new NewsService(newsRepository, newsMapper, userLikeService, userService, tagService);

        user
            .setId(Long.parseLong("1"))
            .setEmail("email")
            .setPhone("0693759302")
            .setFirstname("firstName")
            .setLastname("lastName")
            .setPassword("password")
            .setBirthday(new Date())
            .setProfilePicture("profilePicture");

        newsEntity1
                .setId(1L)
                .setUser(user)
                .setTitle("title1")
                .setContent("content1")
                .setCreationDate(new Date());
        newsEntity2
                .setId(2L)
                .setUser(user)
                .setTitle("title2")
                .setContent("content2")
                .setCreationDate(new Date());
    }


    @Test
    void should_get_news_by_id() {
        when(newsRepository.findById(1L)).thenReturn(Optional.ofNullable(newsEntity1));
        var newsFound = newsService.getById(1L);
        assertEquals(newsEntity1, newsFound);
    }

    @Test
    void should_get_all_news() {
        when(newsRepository.findAll()).thenReturn(List.of(newsEntity1, newsEntity2));
        var newsFound = newsService.getAll();
        assertEquals(List.of(newsEntity1, newsEntity2), newsFound);
    }

    @Test
    void should_get_all_news_by_user_id() {
        var newsListExpected = List.of(newsEntity1, newsEntity2);

        when(newsRepository.findAllByUserId(1L)).thenReturn(newsListExpected);
        var newsListFound = newsService.getAllByUserId(1L);
        assertEquals(newsListExpected, newsListFound);
    }

}