package model.repository.impl;


import model.entity.Tweet;
import model.repository.TweetRepository;
import model.utility.SingletonConnection;
import org.hibernate.SessionFactory;

public class TweetRepositoryImpl extends RepositoryImpl<Tweet, Integer> implements TweetRepository {
    SessionFactory sessionFactory = SingletonConnection.getINSTANCE();
    public void show(){
//        try (var session = sessionFactory.openSession()) {
//            var t = session.beginTransaction();
//            var hql = "from model.entity.Tweet tweet ";
//            session
//                    .createQuery(hql,Tweet.class)
//                    .getResultStream()
//                    .forEach(tweet -> {
//                        System.out.println(tweet.getId());
//                        System.out.println(tweet.getAccount());
////                        System.out.println(tweet.getLike().size());
//                        System.out.println(tweet.getDislike());
//                    });
//            t.commit();

          /*var hql = "from ir.maktab.hibernate.relations.onetomany.Person";
            session
                    .createQuery(hql, Person.class)
                    .getResultStream()
                    .forEach(o -> {
                        *//*System.out.println(o.getAddresses());*//*
                        System.out.println(o);
                    });*/
        }
//        try (var session = sessionFactory.openSession()) {
//            session.beginTransaction();

//            var loadedPerson = session.find(Tweet.class,1);
//            System.out.println(loadedPerson.getLike().size());
//            session.getTransaction().commit();
//        }
//    }
}
