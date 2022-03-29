package controller.service;

import model.entity.Account;
import model.repository.AccountRepository;
import model.repository.impl.AccountRepositoryImpl;

import java.util.List;

public class AccountServiceImpl extends ServiceImpl<AccountRepositoryImpl, Account, Integer> implements AccountRepository {
    private AccountRepositoryImpl accountRepository;

    public AccountServiceImpl() {
        super(new AccountRepositoryImpl());
        this.accountRepository = new AccountRepositoryImpl();
    }

    @Override
    public Account findByName(String name) {
        try (var session = getConnection().getCurrentSession()) {
            var transaction = session.getTransaction();
            try {
                transaction.begin();
                return accountRepository.findByName(name);
            } catch (Exception e) {
                e.printStackTrace();
                transaction.rollback();
            }
            return null;
        }

    }

    @Override
    public List<Account> getFollowers(Account account) {
        try (var session = getConnection().getCurrentSession()) {
            var transaction = session.getTransaction();
            try {
                transaction.begin();
                return accountRepository.getFollowers(account);
            } catch (Exception e) {
                e.printStackTrace();
                transaction.rollback();
            }
        }
        return null;
    }
}
