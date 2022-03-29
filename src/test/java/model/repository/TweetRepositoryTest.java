package model.repository;

import controller.service.AccountServiceImpl;
import controller.service.TweetServiceImpl;
import model.entity.Account;
import model.entity.Tweet;
import model.repository.impl.AccountRepositoryImpl;
import model.repository.impl.TweetRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Date;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class TweetRepositoryTest {
    private TweetServiceImpl tweetRepositoryImpl;
    private static Connection connection;
    private AccountServiceImpl accountRepositoryImpl;

//    @BeforeAll
//    public static void beforeAll() {
//        connection = PostgresConnection.connection;
//    }

    @BeforeEach
    public void beforeEach() {
        tweetRepositoryImpl = new TweetServiceImpl();
        accountRepositoryImpl = new AccountServiceImpl();
    }

    @Test
    void save() {
//        arrange
        Tweet tweet = new Tweet(
                null,
                Date.valueOf("2000-02-02"),
                "title87",
                "tt",
                0L,
                0L,
                null
        );
//        act
        tweetRepositoryImpl.save(tweet);
        Tweet load = tweetRepositoryImpl.findById(Tweet.class, tweet.getId());
//        assert
        assertAll(
                () -> assertEquals(tweet.getId(), load.getId())
        );
    }

    @Test
    void update() {
//        arrange
        Tweet tweet = new Tweet(
                null,
                Date.valueOf("2000-02-02"),
                "title87",
                "tt",
               0L,
                0L,
                null
        );
//        act
        tweetRepositoryImpl.save(tweet);
        String title = "title";
        tweet.setTitle(title);
        tweetRepositoryImpl.update(tweet);
        Tweet load = tweetRepositoryImpl.findById(Tweet.class, tweet.getId());
//        assert
        assertEquals(title, load.getTitle());
    }

    @Test
    void delete() {
        //        arrange
        Tweet tweet = new Tweet(
                null,
                Date.valueOf("2000-02-02"),
                "title87",
                "tt",
                0L,
                0L,
                null
        );
//        act
        tweetRepositoryImpl.save(tweet);
        tweetRepositoryImpl.delete(tweetRepositoryImpl.findById(Tweet.class,  tweet.getId()));
//        assert
        assertNull(tweetRepositoryImpl.findById(Tweet.class, tweet.getId()));
    }

    @Test
    void findById() {
//        arrange
        Tweet tweet = new Tweet(
                null,
                Date.valueOf("2000-02-02"),
                "title87",
                "tt",
                0L,
                0L,
                null
        );
//        act
        tweetRepositoryImpl.save(tweet);
//        assert
        assertNotNull(tweetRepositoryImpl.findById(Tweet.class , tweet.getId()));
    }

    @Test
    void findAll() {
//        arrange
        List<Tweet> tweetList = Arrays.asList(
                new Tweet(
                        null,
                        Date.valueOf("2000-02-02"),
                        "title87",
                        "tt",
                        0L,
                        0L,
                        null
                ),
                new Tweet(
                        null,
                        Date.valueOf("2000-02-02"),
                        "title87",
                        "tt",
                        0L,
                        0L,
                        null
                )
        );
        int size = tweetRepositoryImpl.findAll(Tweet.class).size();
        for (Tweet t : tweetList) {
            tweetRepositoryImpl.save(t);
        }
        size += tweetList.size();
//        assert
        assertEquals(size, tweetRepositoryImpl.findAll(Tweet.class).size());
    }

}