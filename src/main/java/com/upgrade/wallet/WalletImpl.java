package com.upgrade.wallet;

import com.upgrade.wallet.api.Account;
import com.upgrade.wallet.api.Bank;
import com.upgrade.wallet.api.Wallet;
import com.upgrade.wallet.exception.NoSuchAccountException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by luguannan on 2018/11/7.
 */
public class WalletImpl implements Wallet {

    String id;
    ISBankImpl bank;

    protected WalletImpl(String id, ISBankImpl bank) {
        this.id = id;
        this.bank = bank;
    }

    public String getId() {
        return id;
    }

    /**
     * Create an account under this bank and wallet
     * @return an account
     */
    public Account createAccount() {
        return bank.createAccount(this);
    }

    /**
     * Get access to an account.
     * @param id the account id
     * @return an account
     * @throws NoSuchAccountException throws when the account is not existed
     */
    public Account getAccount(String id) throws NoSuchAccountException {
        return bank.getAccount(id);
    }

    /**
     * Destroy an account from the wallet.
     * @param account the account
     * @throws NoSuchAccountException throws when the account is not existed
     */
    public void destroyAccount(Account account) throws NoSuchAccountException {
        bank.destroyAccount(this, account);
    }

    /**
     * Get a list of all accounts with the wallet.
     * @return a list of accounts
     */
    @Override
    public List<AccountImpl> listAllAccount() {
        Map<String, AccountImpl> map = bank.walletAccountsMap.get(id);
        List<AccountImpl> list = new ArrayList<>();
        for(String s : map.keySet()){
            list.add(map.get(s));
        }
        return list;
    }

    protected static WalletImpl newWallet(ISBankImpl bank) {
        return new WalletImpl(Utils.generateId(), bank);
    }
}
