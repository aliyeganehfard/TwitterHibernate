import controller.exception.DigitException;
import controller.exception.ExceptionHandling;
import controller.service.AccountService;
import controller.service.CommentService;
import controller.service.TweetService;
import model.entity.Account;
import model.entity.Comment;
import model.entity.Tweet;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
//        creat model.sql file
        var accountService = new AccountService();
        var tweetService = new TweetService();
        var commentService = new CommentService();
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
                                        commend[1],
                                        commend[2],
                                        commend[3]
                                )
                        );
                    } catch (Exception e) {
                        System.out.println("wrong input!");
                    }
                    break;
                case "login":
                    try {
                        account = accountService.login(
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

            while (state){
                System.out.print("commend : ");
                commendLine = scanner.nextLine();
                commend = commendLine.trim().split(" ");
                switch (commend[0]){
                    case "showProfile":
                        try {
                            System.out.println(account);
                        }catch (Exception e){
                            System.out.println();
                        }
                        break;
                    case "showTweets":
                        try {
                            List<Tweet> tweetList = tweetService.findAll();
                            List<Comment> cmt =null;
                            for (Tweet twt : tweetList) {
                                System.out.println(twt + "\t");
                                cmt = commentService.findByTweetId(
                                        twt.getId()
                                );
                                if (cmt!=null){
                                    for (Comment com : cmt) {
                                        String name = accountService.findById(com.getAccount().getId()).getName();
                                        System.out.println("name "+ name+
                                                "\t comment : "+com.getComment());
                                    }
                                }
                                System.out.println("-----------------");
                            }
                        }catch (Exception e){
                            System.out.println();
                        }
                        break;
                    case "showTweet":
                        try {
                            ExceptionHandling.isDigit(commend[1]);
                            Tweet twt = tweetService.findById(Integer.valueOf(commend[1]));
                            List<Comment> cmt = commentService.findByTweetId(Integer.valueOf(commend[1]));
                            System.out.println(twt);
                            for (Comment com : cmt) {
                                System.out.println("account id "+ com.getAccount().getId()+
                                        "\t comment : "+com.getComment());
                            }
                        }catch (DigitException digitException){
                            System.out.println("enter digit!");
                        }
                        catch (Exception e){
                            System.out.println("wrong input!");
                        }
                        break;
                    case "showComment":
                        try {
                            ExceptionHandling.isDigit(commend[1]);
                            Comment cmt1 = commentService.findById(Integer.valueOf(commend[1]));
                            System.out.println(cmt1);
                        }catch (DigitException digitException){
                            System.out.println("enter digit!");
                        }
                        catch (Exception e){
                            System.out.println("wrong input!");
                        }
                        break;
                    case "editAccount":
                        try {
                            account.setUserName(commend[1]);
                            account.setPassword(commend[2]);
                            account.setName(commend[3]);
                            accountService.update(account);
                        }catch (Exception e){
                            System.out.println("wrong input!");
                        }
                        break;
                    case "deleteAccount":
                        try {
                            accountService.delete(account.getId());
                            state=false;
                            showMenu();
                        }catch (Exception e){
                            System.out.println();
                        }
                        break;
                    case "addTweet":
                        try {
                            tweetService.save(
                                    new Tweet(
                                            Date.valueOf(LocalDate.now()),
                                            account,
                                            commend[1],
                                            commend[2]
                                    )
                            );
                        }catch (Exception e){
                            System.out.println("wrong input!");
                        }
                        break;
                    case "editTweet":
                        try {
                            ExceptionHandling.isDigit(commend[1]);
                            Tweet twt = tweetService.findById(Integer.valueOf(commend[1]));
                            twt.setTitle(commend[2]);
                            twt.setDescription(commend[3]);
                            tweetService.update(twt);
                        }catch (DigitException digitException){
                            System.out.println("enter digit!");
                        }
                        catch (Exception e){
                            System.out.println("wrong input!");
                        }
                        break;
                    case "deleteTweet":
                        try {
                            ExceptionHandling.isDigit(commend[1]);
                            tweetService.delete(Integer.valueOf(commend[1]));
                        }catch (DigitException digitException){
                            System.out.println("enter digit!");
                        }
                        catch (Exception e){
                            System.out.println("wrong input!");
                        }
                        break;
                    case "addComment":
                        try {
                            ExceptionHandling.isDigit(commend[2]);
                            Tweet twt = tweetService.findById(Integer.valueOf(commend[2]));
                            commentService.save(
                                    new Comment(
                                            commend[1],
                                            account,
                                            twt
                                    )
                            );
                        }catch (DigitException digitException){
                            System.out.println("enter digit!");
                        }
                        catch (Exception e){
                            System.out.println("wrong input!");
                        }
                        break;
                    case "editComment":
                        try {
                            ExceptionHandling.isDigit(commend[2]);
                            Comment cmt = commentService.findById(Integer.valueOf(commend[2]));
                            cmt.setComment(commend[1]);
                            commentService.update(cmt);
                        }catch (DigitException digitException){
                            System.out.println("enter digit!");
                        }
                        catch (Exception e){
                            System.out.println("wrong input!");
                        }
                        break;
                    case "deleteComment":
                        try {
                            ExceptionHandling.isDigit(commend[1]);
                            commentService.delete(Integer.valueOf(commend[1]));
                        }catch (DigitException digitException){
                            System.out.println("enter digit!");
                        }catch (Exception e){
                            System.out.println("wrong input!");
                        }
                        break;
                    case "findAccountByName":
                        try {
                            Account act = accountService.findByName(commend[1]);
                            if (act == null){
                                System.out.println("not found!" );
                                break;
                            }
                            System.out.println(act);
                        }catch (Exception e){
                            System.out.println("wrong input!");
                        }
                        break;
                    case "findAccountById":
                        try {
                            ExceptionHandling.isDigit(commend[1]);
                            Account act = accountService.findById(Integer.valueOf(commend[1]));
                            if (act == null){
                                System.out.println("not found!" );
                                break;
                            }
                            System.out.println(act);
                        }catch (DigitException digitException){
                            System.out.println("enter digit!");
                        } catch (Exception e){
                            System.out.println("wrong input!");
                        }
                        break;
                    case "showComments":
                        List<Comment> commentList = commentService.findAll();
                        for (Comment com : commentList) {
                            System.out.println(com);
                        }
                        break;
                    case "findAllAccount":
                        List<Account> accountList = accountService.findAll();
                        for (Account acc : accountList) {
                            System.out.println(acc);
                        }
                        break;
                    case "help":
                        showAccountMenu();
                        break;
                    case "logout":
                        showMenu();
                        state=false;
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
        System.out.println("findAccountById");
        System.out.println("findAllAccount");
        System.out.println("help");
        System.out.println("logout");
        System.out.println("exit");
    }
}
