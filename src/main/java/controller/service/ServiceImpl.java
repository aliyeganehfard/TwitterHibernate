package controller.service;

import model.repository.Repository;
import model.utility.SingletonConnection;
import org.hibernate.SessionFactory;

import java.util.List;

public class ServiceImpl<R extends Repository<T, ID>, T, ID> implements Service<R, T, ID> {
    private R r;

    public ServiceImpl(R r) {
        this.r = r;
    }

    @Override
    public void save(T t) {
        try (var session = getConnection().getCurrentSession()){
            var transaction = session.getTransaction();
            try {
                transaction.begin();
                r.save(t);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void update(T t) {
        try (var session = getConnection().getCurrentSession()){
            var transaction = session.getTransaction();
            try {
                transaction.begin();
                r.update(t);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void delete(T t) {
        try (var session = getConnection().getCurrentSession()){
            var transaction = session.getTransaction();
            try {
                transaction.begin();
                r.delete(t);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void deleteById(ID id) {

    }

    @Override
    public T findById(Class<T> tClass, ID id) {
        return r.findById(tClass, id);
    }

    @Override
    public List<T> findAll(Class<T> tClass) {
        return r.findAll(tClass);
    }
}
