package model.repository;

import controller.service.AccountServiceImpl;
import model.entity.Account;
import model.repository.impl.AccountRepositoryImpl;
import model.utility.PostgresConnection;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AccountImplRepositoryTest {
    private AccountServiceImpl accountRepositoryImpl;
    private static Connection connection;

    @BeforeAll
    public static void beforeAll() {
        connection = PostgresConnection.connection;
    }

    @BeforeEach
    public void beforeEach() {
        accountRepositoryImpl = new AccountServiceImpl();
    }


    @Test
    void save() {
//        arrange
        Account account = new Account(null, "admivn", "admisdfn", "adf",new HashSet<>(),new HashSet<>());
//        act
        accountRepositoryImpl.save(account);
        Account load = accountRepositoryImpl.findById(Account.class , account.getId());
//        assert
        assertAll(
                () -> assertEquals(account.getId(), load.getId()),
                () -> assertEquals(account.getUserName(), load.getUserName())
        );

    }

    @Test
    void update() {
//        arrange
        Account account = new Account(null, "asddfdmin", "adfhmin", "ade",new HashSet<>(),new HashSet<>());
//        act
        accountRepositoryImpl.save(account);
        Account load = account;
        load.setName("omid");
        accountRepositoryImpl.update(load);
        load = accountRepositoryImpl.findById(Account.class,load.getId());
//        assert
        assertEquals("omid",load.getName());
    }

    @Test
    void delete() {
//        arrange
        Account account = new Account(null, "admasdfin", "afghdfdmin", "aed",new HashSet<>(),new HashSet<>());
//        act
        accountRepositoryImpl.save(account);
        var load = accountRepositoryImpl.findById(Account.class , account.getId());
        accountRepositoryImpl.delete(load);
//        assert
        assertNull(accountRepositoryImpl.findById(Account.class,account.getId()));
    }

    @Test
    void findById() {
//        arrange
        Account account = new Account(null, "admfdrtin", "admikjhn", "ahj",new HashSet<>(),new HashSet<>());
//        act
        accountRepositoryImpl.save(account);
//        assert
        assertNotNull(accountRepositoryImpl.findById(Account.class , account.getId()));
    }

    @Test
    void findAll() {
//        arrange
        List<Account> accountList = Arrays.asList(
           new Account(null, "admin", "admin", "a",new HashSet<>(),new HashSet<>()),
           new Account(null, "asdafdmin", "aasdfdmin", "add",new HashSet<>(),new HashSet<>()),
           new Account(null, "adminfsadf", "admisdfn", "ad",new HashSet<>(),new HashSet<>())
        ) ;
//        act
        int size = accountRepositoryImpl.findAll(Account.class).size();
        for (Account ac : accountList) {
            accountRepositoryImpl.save(ac);
        }
        size += accountList.size();
//        assert
        assertEquals(size, accountRepositoryImpl.findAll(Account.class).size());
    }

    @Test
    void findByName(){
//        arrange
        Account account = new Account(null, "aadfd", "admikjhn", "aa",new HashSet<>(),new HashSet<>());
//        act
        accountRepositoryImpl.save(account);
        var load = accountRepositoryImpl.findByName("aa");
        System.out.println("--------------------------------------");
        System.out.println(load);
//        assert
        assertAll(
                ()-> assertNotNull(load),
                ()-> assertEquals(account.getName(),load.getName())
        );
    }

    @Test
    void getFollowers(){
//        arrange
        Account account = new Account(null, "aadasdffd", "adasfmikjhn", "aaasdfazxcv",new HashSet<>(),new HashSet<>());
        Account account1 = new Account(null, "asdf", "qqdfgqzxcv", "qqxzsadfcv",new HashSet<>(),new HashSet<>());
        Account account11 = new Account(null, "asdsdff", "qqdfgsdfqzxcv", "qqsdfxzsadfcv",new HashSet<>(),new HashSet<>());
//        act
        account.getFollowers().add(account1);
        account.getFollowers().add(account11);
        account1.getFollowing().add(account);
        account11.getFollowing().add(account);
        accountRepositoryImpl.save(account);
        accountRepositoryImpl.save(account1);
        var following = accountRepositoryImpl.findById(Account.class,1);
//        assert
        assertEquals(2,following.getFollowers().size());
    }

}