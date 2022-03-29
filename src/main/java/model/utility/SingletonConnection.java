package model.utility;

import model.entity.Account;
import model.entity.Comment;
import model.entity.Tweet;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class SingletonConnection {
    private SingletonConnection(){}

    private static class LazyHolder{
        static SessionFactory INSTANCE;

        static {
            var registry = new StandardServiceRegistryBuilder()
                    .configure()
                    .build();

            INSTANCE = new MetadataSources(registry)
                    .addAnnotatedClass(Account.class)
                    .addAnnotatedClass(Tweet.class)
                    .addAnnotatedClass(Comment.class)
                    .buildMetadata()
                    .buildSessionFactory();
        }
    }

    public static SessionFactory getINSTANCE(){
        return LazyHolder.INSTANCE;
    }
}
