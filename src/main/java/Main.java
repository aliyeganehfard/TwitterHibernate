import controller.exception.*;
import controller.service.AccountServiceImpl;
import controller.service.CommentServiceImpl;
import controller.service.TweetServiceImpl;
import model.entity.Account;
import model.entity.Comment;
import model.entity.Tweet;
import model.utility.SingletonConnection;

import java.security.spec.ECField;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
//        creat model.sql file
        var accountService = new AccountServiceImpl();
        var tweetService = new TweetServiceImpl();
        var commentService = new CommentServiceImpl();
        Account account = null;
        Tweet tweet = null;
        Comment comment = null;

        Scanner scanner = new Scanner(System.in);
        String commendLine = "";
        String[] commend;

        boolean state = false;
        boolean app = true;

        showMenu();
        while (app) {
            System.out.print("commend : ");
            commendLine = scanner.nextLine();
            commend = commendLine.trim().split(" ");
            switch (commend[0]) {
                case "register":
                    try {
                        accountService.save(
                                new Account(
                                        null,
                                        commend[1],
                                        commend[2],
                                        commend[3],
                                        new HashSet<>(),
                                        new HashSet<>()
                                )

                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("wrong input!");
                    }
                    break;
                case "login":
                    try {
                        account = accountService.login(
                                Account.class,
                                commend[1],
                                commend[2]
                        );
                        if (account == null) {
                            System.out.println("wrong userName || password");
                            break;
                        }
                        showAccountMenu();
                        state = true;
                    } catch (Exception e) {
                        System.out.println("wrong input!");
                    }
                    break;
                default:
                    System.out.println("wrong commend!");
                    break;
            }

            while (state) {
                System.out.print("commend : ");
                commendLine = scanner.nextLine();
                commend = commendLine.trim().split(" ");
                switch (commend[0]) {
                    case "showProfile":
                        try {
                            System.out.println(account);
                        } catch (Exception e) {
                            System.out.println();
                        }
                        break;
                    case "showTweets":
                        try {
                            List<Tweet> tweetList = tweetService.findAll(Tweet.class);
                            List<Comment> cmt = null;
                            for (Tweet twt : tweetList) {
                                System.out.println(twt + "\t");
                                cmt = commentService.findByTweetId(
                                        twt.getId()
                                );
                                if (cmt != null) {
                                    for (Comment com : cmt) {
                                        String name = accountService.findById(Account.class, com.getAccount().getId()).getName();
                                        System.out.println("name " + name +
                                                "\t comment : " + com.getComment());
                                    }
                                }
                                System.out.println("-----------------");
                            }
                        } catch (Exception e) {
                            System.out.println();
                        }
                        break;
                    case "showTweet":
                        try {
                            ExceptionHandling.isDigit(commend[1]);
                            Tweet twt = tweetService.findById(Tweet.class, Integer.valueOf(commend[1]));
                            List<Comment> cmt = commentService.findByTweetId(Integer.valueOf(commend[1]));
                            System.out.println(twt);
                            for (Comment com : cmt) {
                                System.out.println("account id " + com.getAccount().getId() +
                                        "\t comment : " + com.getComment());
                            }
                        } catch (DigitException digitException) {
                            System.out.println("enter digit!");
                        } catch (Exception e) {
                            System.out.println("wrong input!");
                        }
                        break;
                    case "showComment":
                        try {
                            ExceptionHandling.isDigit(commend[1]);
                            Comment cmt1 = commentService.findById(Comment.class, Integer.valueOf(commend[1]));
                            System.out.println(cmt1);
                        } catch (DigitException digitException) {
                            System.out.println("enter digit!");
                        } catch (Exception e) {
                            System.out.println("wrong input!");
                        }
                        break;
                    case "editAccount":
                        try {
                            account.setUserName(commend[1]);
                            account.setPassword(commend[2]);
                            account.setName(commend[3]);
                            accountService.update(account);
                        } catch (Exception e) {
                            System.out.println("wrong input!");
                        }
                        break;
                    case "deleteAccount":
                        try {
                            accountService.delete(account);
                            state = false;
                            showMenu();
                        } catch (Exception e) {
                            System.out.println();
                        }
                        break;
                    case "addTweet":
                        try {
                            if (commend[2].length() > 280)
                                throw new OutOfBoundLength();
                            tweetService.save(
                                    new Tweet(
                                            null,
                                            Date.valueOf(LocalDate.now()),
                                            commend[1],
                                            commend[2],
                                            0L,
                                            0L,
                                            account
                                    )
                            );
                        } catch (OutOfBoundLength out) {
                            System.out.println("out of bound length");
                        } catch (Exception e) {
                            System.out.println("wrong input!");
                        }
                        break;
                    case "editTweet":
                        try {
                            ExceptionHandling.isDigit(commend[1]);
                            Tweet twt = tweetService.findById(Tweet.class, Integer.valueOf(commend[1]));
                            twt.setTitle(commend[2]);
                            if (commend[3].length() > 280)
                                throw new OutOfBoundLength();
                            twt.setDescription(commend[3]);
                            tweetService.update(twt);
                        } catch (OutOfBoundLength out) {
                            System.out.println("out of bound length");
                        } catch (DigitException digitException) {
                            System.out.println("enter digit!");
                        } catch (Exception e) {
                            System.out.println("wrong input!");
                        }
                        break;
                    case "deleteTweet":
                        try {
                            ExceptionHandling.isDigit(commend[1]);
                            var load = tweetService.findById(Tweet.class, Integer.valueOf(commend[1]));
                            if (load == null)
                                throw new TweetNotFound();
                            tweetService.delete(load);
                        } catch (TweetNotFound tweetNotFound) {
                            System.out.println("tweet not found");
                        } catch (DigitException digitException) {
                            System.out.println("enter digit!");
                        } catch (Exception e) {
                            System.out.println("wrong input!");
                        }
                        break;
                    case "addComment":
                        try {
                            ExceptionHandling.isDigit(commend[2]);

                            var load = tweetService.findById(Tweet.class, Integer.valueOf(commend[2]));
                            if (load == null)
                                throw new TweetNotFound();
                            commentService.save(
                                    new Comment(
                                            null,
                                            commend[1],
                                            account,
                                            load
                                    )
                            );
                        } catch (TweetNotFound tweetNotFound) {
                            System.out.println("tweet not found");
                        } catch (DigitException digitException) {
                            System.out.println("enter digit!");
                        } catch (Exception e) {
                            System.out.println("wrong input!");
                        }
                        break;
                    case "editComment":
                        try {
                            ExceptionHandling.isDigit(commend[2]);
                            var load = commentService.findById(Comment.class, Integer.valueOf(commend[2]));
                            if (load == null)
                                throw new TweetNotFound();
                            load.setComment(commend[1]);
                            commentService.update(load);
                        } catch (CommentNotFound commentNotFound) {
                            System.out.println("comment not found");
                        } catch (DigitException digitException) {
                            System.out.println("enter digit!");
                        } catch (Exception e) {
                            System.out.println("wrong input!");
                        }
                        break;
                    case "deleteComment":
                        try {
                            ExceptionHandling.isDigit(commend[1]);
                            var load = commentService.findById(Comment.class, Integer.valueOf(commend[2]));
                            if (load == null)
                                throw new TweetNotFound();
                            commentService.delete(load);
                        } catch (CommentNotFound commentNotFound) {
                            System.out.println("comment not found");
                        } catch (DigitException digitException) {
                            System.out.println("enter digit!");
                        } catch (Exception e) {
                            System.out.println("wrong input!");
                        }
                        break;
                    case "findAccountByName":
                        try {
                            var load = accountService.findByName(commend[1]);
                            if (load == null)
                                throw new AccountNotFound();
                            System.out.println(load);
                        } catch (AccountNotFound accountNotFound) {
                            System.out.println("account not found");
                        } catch (Exception e) {
                            System.out.println("wrong input!");
                        }
                        break;
                    case "follow":
                        try {
                            ExceptionHandling.isDigit(commend[1]);
                            var load = accountService.findById(Account.class, Integer.valueOf(commend[1]));
                            if (load == null)
                                throw new AccountNotFound();
                            account.getFollowing().add(load);
                            load.getFollowers().add(account);
                            accountService.update(load);
                            accountService.update(account);
                        } catch (AccountNotFound accountNotFound) {
                            System.out.println("account not found");
                        } catch (DigitException digitException) {
                            System.out.println("incorrect id");
                        } catch (Exception e) {
                            System.out.println("wrong input");
                        }
                        break;
                    case "like":
                        try {
                            ExceptionHandling.isDigit(commend[1]);
                            var load = tweetService.findById(Tweet.class, Integer.valueOf(commend[1]));
                            if (load == null)
                                throw new TweetNotFound();

                            load.setDl(load.getDl() + 1);
                            tweetService.update(load);
                        } catch (TweetNotFound tweetNotFound) {
                            System.out.println("tweet not found");
                        } catch (DigitException digitException) {
                            System.out.println("incorrect id");
                        } catch (Exception e) {
                            System.out.println("wrong input");
                        }
                        break;
                    case "dislike":
                        try {
                            ExceptionHandling.isDigit(commend[1]);
                            var load = tweetService.findById(Tweet.class, Integer.valueOf(commend[1]));
                            if (load == null)
                                throw new TweetNotFound();
                            load.setL(load.getL() + 1);
                            tweetService.update(load);
                        } catch (TweetNotFound tweetNotFound) {
                            System.out.println("tweet not found");
                        } catch (DigitException digitException) {
                            System.out.println("incorrect id");
                        } catch (Exception e) {
                            System.out.println("wrong input");
                        }
                        break;
                    case "showComments":
                        List<Comment> commentList = commentService.findAll(Comment.class);
                        for (Comment com : commentList) {
                            System.out.println(com);
                        }
                        break;
                    case "findAllAccount":
                        List<Account> accountList = accountService.findAll(Account.class);
                        for (Account acc : accountList) {
                            System.out.println(acc);
                        }
                        break;
                    case "showFollowers":
                        try {
                            var load = accountService.findById(Account.class, account.getId());
                            load.getFollowers().forEach(System.out::println);
                        } catch (Exception e) {
                            System.out.println("wrong input");
                        }
                        break;
                    case "unFollow":
                        try {
                            ExceptionHandling.isDigit(commend[1]);
                            var load = accountService.findById(Account.class,Integer.valueOf(commend[1]));
                            if (load == null)
                                throw new AccountNotFound();
                            account.getFollowing().remove(load);
                            load.getFollowers().remove(account);
                            accountService.update(account);
                            accountService.update(load);
                        }catch (AccountNotFound accountNotFound){
                            System.out.println("account not found");
                        }catch (DigitException digitException) {
                            System.out.println("incorrect id");
                        } catch (Exception e) {
                            System.out.println("wrong input");
                        }
                        break;
                    case "help":
                        showAccountMenu();
                        break;
                    case "logout":
                        showMenu();
                        state = false;
                        break;
                    case "exit":
                        state = false;
                        app = false;
                        break;
                    default:
                        System.out.println("wrong commend!");
                        break;
                }
            }
        }
    }

    private static void showMenu() {
        System.out.println("register userName password name");
        System.out.println("login userName password");
    }

    private static void showAccountMenu() {
        System.out.println("showProfile");
        System.out.println("showTweets");
        System.out.println("showComments");
        System.out.println("showTweet tweetId");
        System.out.println("showComment commentId");
        System.out.println("editAccount newUserName newPassword newName");
        System.out.println("deleteAccount");
        System.out.println("addTweet title description");
        System.out.println("editTweet tweetId title description");
        System.out.println("deleteTweet tweetId");
        System.out.println("addComment comment tweetId");
        System.out.println("editComment comment commentId");
        System.out.println("deleteComment commentId");
        System.out.println("findAccountByName");
        System.out.println("findAllAccount");
        System.out.println("follow accountID");
        System.out.println("like tweetId");
        System.out.println("dislike tweetId");
        System.out.println("showFollowers");
        System.out.println("unFollow accountId");
        System.out.println("help");
        System.out.println("logout");
        System.out.println("exit");

    }
}
