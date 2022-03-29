package model.repository;

import model.entity.Account;

import java.util.List;

public interface AccountRepository extends LoginBase<Account>{
    Account findByName(String name);
    List<Account> getFollowers(Account account);
}
