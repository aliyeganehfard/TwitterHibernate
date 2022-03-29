package model.repository.impl;

import model.entity.Account;
import model.repository.AccountRepository;
import model.utility.SingletonConnection;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class AccountRepositoryImpl extends RepositoryImpl<Account, Integer> implements AccountRepository {
    SessionFactory sessionFactory = SingletonConnection.getINSTANCE();

    @Override
    public Account findByName(String name) {
        var session = getConnection().getCurrentSession();
        var hql = "FROM model.entity.Account account " +
                "WHERE account.name = :name ";
        var query = session.createQuery(hql, Account.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }

    @Override
    public List<Account> getFollowers(Account account) {
        var session = getConnection().getCurrentSession();
        var hql = "FROM model.entity.Account followers " +
                "WHERE account.id = :accountId";
        var query = session.createQuery(hql,Account.class);
        query.setParameter("accountId",account.getId());
        return query.getResultList();
    }

    public void show() {

        var hgl = "from model.entity.Account ";
        var session = sessionFactory.openSession();
        Transaction t = session.beginTransaction();
        var a = session.createQuery(hgl, Account.class)
                .getResultList();
        a.get(0).getFollowers().forEach(System.out::println);
//                    System.out.println(account));
        t.commit();
        System.out.println("99999999999999999999999999999999999999999999999999999999");
        a.get(1).getFollowing().forEach(System.out::println);
        a.get(1).getFollowers().forEach(System.out::println);
        /*var hql = "from ir.maktab.hibernate.relations.onetomany.Person";
            session
                    .createQuery(hql, Person.class)
                    .getResultStream()
                    .forEach(o -> {
                        *//*System.out.println(o.getAddresses());*//*
                        System.out.println(o);
                    });*/

    }
}
