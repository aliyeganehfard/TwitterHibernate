package controller.service;

import model.entity.Comment;
import model.entity.Tweet;
import model.repository.CommentRepository;
import model.repository.impl.CommentRepositoryImpl;

import java.util.List;
import java.util.stream.Collectors;

public class CommentServiceImpl extends ServiceImpl<CommentRepositoryImpl, Comment,Integer> implements CommentRepository {
    private CommentRepositoryImpl commentRepository;
    private TweetServiceImpl tweetService;
    public CommentServiceImpl() {
        super(new CommentRepositoryImpl());
        this.commentRepository = new CommentRepositoryImpl();
    }

    public List<Comment> findByTweetId(Integer id) {
        return commentRepository.findAll(Comment.class)
                .stream()
                .filter(comment -> comment.getTweet().getId().equals(id))
                .collect(Collectors.toList());
    }
}
