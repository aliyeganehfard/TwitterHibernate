package model.repository.impl;

import model.entity.Account;
import model.repository.Repository;
import model.utility.SingletonConnection;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.security.AccessControlContext;
import java.security.spec.ECField;
import java.util.List;

public class RepositoryImpl<T, ID> implements Repository<T, ID> {
    @Override
    public void save(T t) {
        var session = getConnection().getCurrentSession();
        session.save(t);
    }

    @Override
    public void update(T t) {
        var session = getConnection().getCurrentSession();
        session.update(t);
    }

    @Override
    public void delete(T t) {
        var session = getConnection().getCurrentSession();
        session.delete(t);
    }

    @Override
    public void deleteById(ID id) {
        try (var session = getConnection().openSession()) {
            var transaction = session.beginTransaction();
            try {
//                session.delete();
            } catch (Exception e) {
                e.printStackTrace();
                transaction.rollback();
            }
        }
    }

    @Override
    public T findById(Class<T> tClass, ID id) {
        try(var session = getConnection().openSession()){
            return session.find(tClass,id);
        }
    }

    @Override
    public List<T> findAll(Class<T> tClass) {
        try(var session = getConnection().openSession()){
            var criteriaBuilder = session.getCriteriaBuilder();
            var criteriaQuery = criteriaBuilder.createQuery(tClass);
            var root = criteriaQuery.from(tClass);
            return session.createQuery(criteriaQuery.select(root)).list();
        }
    }
}
