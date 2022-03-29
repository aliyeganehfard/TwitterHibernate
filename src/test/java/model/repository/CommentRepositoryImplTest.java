package model.repository;

import controller.service.AccountServiceImpl;
import controller.service.CommentServiceImpl;
import controller.service.TweetServiceImpl;
import model.entity.Account;
import model.entity.Comment;
import model.entity.Tweet;
import model.repository.impl.AccountRepositoryImpl;
import model.repository.impl.CommentRepositoryImpl;
import model.repository.impl.TweetRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CommentRepositoryImplTest {
    private CommentServiceImpl commentRepositoryImpl;
    private AccountServiceImpl accountRepositoryImpl;
    private TweetServiceImpl tweetRepositoryImpl;
    private static Connection connection;

//    @BeforeAll
//    public static void beforeAll() {
//        connection = PostgresConnection.connection;
//    }

    @BeforeEach
    public void beforeEach() {
        commentRepositoryImpl = new CommentServiceImpl();
        accountRepositoryImpl = new AccountServiceImpl();
        tweetRepositoryImpl = new TweetServiceImpl();
    }


    @Test
    void save() {
//        arrange
        Comment comment = new Comment(
                null,
                "hi",
                null,
                null
        );
//        act
        commentRepositoryImpl.save(comment);
        Comment load = commentRepositoryImpl.findById(Comment.class,comment.getId());
//        assert
        assertAll(
                () -> assertEquals(comment.getId(), load.getId())
        );

    }

    @Test
    void update() {
        //        arrange
        Comment comment = new Comment(
                null,
                "hdfdi",
                null,
                null
        );
//        act
        commentRepositoryImpl.save(comment);
        String cmt = "new comment";
        comment.setComment(cmt);
        commentRepositoryImpl.update(comment);
        Comment load = commentRepositoryImpl.findById(Comment.class,comment.getId());
//        assert
        assertEquals(cmt, load.getComment());
    }

    @Test
    void delete() {
        //        arrange
        Comment comment = new Comment(
                null,
                "hi",
                null,
                null
        );
//        act
        commentRepositoryImpl.save(comment);
        commentRepositoryImpl.delete(commentRepositoryImpl.findById(Comment.class,comment.getId()));
//        assert
        assertNull(commentRepositoryImpl.findById(Comment.class,comment.getId()));
    }

    @Test
    void findById() {
        //        arrange
        Comment comment = new Comment(
                null,
                "hi",
                null,
                null
        );
//        act
        commentRepositoryImpl.save(comment);
//        assert
        assertNotNull(commentRepositoryImpl.findById(Comment.class , comment.getId()));
    }

    @Test
    void findAll() {
//        arrange
        List<Comment> commentList = Arrays.asList(
                new Comment(
                        null,
                        "hi",
                       null,
                        null
                ),
                new Comment(
                        null,
                        "hi",
                        null,
null
                )
        );
        int size = commentRepositoryImpl.findAll(Comment.class).size();
        for (Comment cmt : commentList) {
            commentRepositoryImpl.save(cmt);
        }
        size += commentList.size();
//        assert
        assertEquals(size, commentRepositoryImpl.findAll(Comment.class).size());
    }
}