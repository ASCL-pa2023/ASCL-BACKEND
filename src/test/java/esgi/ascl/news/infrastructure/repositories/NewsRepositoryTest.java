package esgi.ascl.news.infrastructure.repositories;

import esgi.ascl.news.domain.entities.NewsEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
class NewsRepositoryTest {
    @Autowired
    private NewsRepository newsRepository;


    NewsEntity news1 = new NewsEntity();
    NewsEntity news2 = new NewsEntity();
    NewsEntity news3 = new NewsEntity();

    @BeforeEach
    void setUp() {
        newsRepository.deleteAll();
        news1 = new NewsEntity().setId(1L).setTitle("1");
        news2 = new NewsEntity().setId(2L).setTitle("2");
        news3 = new NewsEntity().setId(3L).setTitle("3");
    }

    @Test
    void should_find_empty() {
        var news = newsRepository.findAll();
        assertTrue(news.isEmpty());
    }

    @Test
    void should_get_all_news() {
        newsRepository.save(news1);
        newsRepository.save(news2);
        newsRepository.save(news3);

        var news = newsRepository.findAll();
        assertEquals(3, news.size());
    }

}