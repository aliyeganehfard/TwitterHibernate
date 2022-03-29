package controller.service;

import model.entity.Comment;
import model.entity.Tweet;
import model.repository.TweetRepository;
import model.repository.impl.TweetRepositoryImpl;

import java.util.List;

public class TweetServiceImpl extends ServiceImpl<TweetRepositoryImpl, Tweet,Integer> implements TweetRepository {
    private TweetRepositoryImpl tweetRepository;
    public TweetServiceImpl() {
        super(new TweetRepositoryImpl());
        this.tweetRepository = new TweetRepositoryImpl();
    }

//    public List<Comment> findByTweetId(Integer tweetId){
//        return commentRepositoryImpl.findByTweetId(tweetId);
//    }
}
