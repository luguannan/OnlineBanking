package com.upgrade.wallet.api;

import com.upgrade.wallet.AccountImpl;
import com.upgrade.wallet.exception.NoSuchAccountException;

import java.util.List;

/**
 * Created by luguannan on 2018/11/7.
 */
public interface Wallet {

    /**
     * Create a new account.
     * @return a new account.
     */
    Account createAccount();

    /**
     * Get the account by id.
     * @param id the account id
     * @return the account
     * @throws NoSuchAccountException When no account is associated with the given id.
     */
    Account getAccount(String id) throws NoSuchAccountException;

    /**
     * Delete an account for user.
     * @param account
     * @throws NoSuchAccountException
     */
    void destroyAccount(Account account) throws NoSuchAccountException;

    /**
     * Get all accounts of the wallet.
     * @return a list of accounts
     */
    List<AccountImpl> listAllAccount();

}
