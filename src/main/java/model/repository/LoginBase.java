package model.repository;

import model.utility.SingletonConnection;

import javax.persistence.NoResultException;

public interface LoginBase<T> {
    default T login(Class<T>  tClass, String userName , String password){
        var sessionFactory = SingletonConnection.getINSTANCE();
        try(var session = sessionFactory.openSession()){
            var transaction = session.getTransaction();
            try{
                var criteriaBuilder = session.getCriteriaBuilder();
                var criteriaQuery = criteriaBuilder.createQuery(tClass);
                var root = criteriaQuery.from(tClass);

                var userNameEqual = criteriaBuilder.equal(root.get("userName"),userName);
                var passwordEqual = criteriaBuilder.equal(root.get("password"),password);

                var query = criteriaQuery
                        .select(root)
                        .where(criteriaBuilder.and(userNameEqual,passwordEqual));
                return session.createQuery(query).getSingleResult();
            }catch (NoResultException e){
//                 transaction.rollback();
//                 e.printStackTrace();
            }
        }
        return null;
    }
}
