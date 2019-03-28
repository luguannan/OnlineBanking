package com.upgrade.wallet;

import com.upgrade.wallet.api.Account;
import com.upgrade.wallet.api.Bank;
import com.upgrade.wallet.api.Wallet;
import com.upgrade.wallet.exception.AccountsStillExistException;
import com.upgrade.wallet.exception.NoSuchAccountException;
import com.upgrade.wallet.exception.UserNameAlreadyExistsException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ISBankImpl implements Bank {

    Map<String, WalletImpl> walletMap = new ConcurrentHashMap<>();

    Map<String, Map<String, AccountImpl>> walletAccountsMap = new ConcurrentHashMap<>();

    Map<String, AccountImpl> allAccountIndex = new ConcurrentHashMap<>();

    /**
     * Create a wallet for a user
     * @param username for a user
     * @return an implementation of a wallet
     * @throws UserNameAlreadyExistsException throws when the username is already used
     */
    @Override
    public WalletImpl createWallet(String username) throws UserNameAlreadyExistsException {
        // check if username is being taken.
        if (walletMap.containsKey(username)) {
            throw new UserNameAlreadyExistsException();
        }
        WalletImpl wallet = WalletImpl.newWallet(this);
        walletMap.put(username, wallet);
        return wallet;
    }

    /**
     * Destroy a wallet for this user
     * @param username for a user
     * @param wallet connects to the user
     * @throws NoSuchAccountException throws when the wallet is not existed
     * @throws AccountsStillExistException throws then the wallet still has remaining accounts.
     */
    @Override
    public void destroyWallet(String username, WalletImpl wallet) throws NoSuchAccountException, AccountsStillExistException {
        if(!walletMap.containsKey(username)){
            throw new NoSuchAccountException("The wallet does not exist!");
        }
        if(walletMap.containsKey(username) && walletAccountsMap.get(wallet.getId()).size() == 0){
            walletMap.remove(username);
            walletAccountsMap.remove(wallet.getId());
        }
        else{
            throw new AccountsStillExistException("You must destroy all connected accounts first!");
        }
    }

    /**
     * create an account associated with a wallet
     * @param wallet the wallet
     * @return the new account
     */
    protected AccountImpl createAccount(WalletImpl wallet) {
        AccountImpl account = AccountImpl.newAccount(this);
        Map<String, AccountImpl> accountMap =  walletAccountsMap.get(wallet.getId());
        if (accountMap == null) {
            accountMap = new ConcurrentHashMap<>();
            walletAccountsMap.put(wallet.getId(), accountMap);
        }
        accountMap.put(account.getId(), account);
        allAccountIndex.put(account.getId(), account);
        return account;
    }

    /**
     * Get an account using an account id.
     * @param accountId the account id
     * @return the implementation of an account
     * @throws NoSuchAccountException throws when the account is not existed
     */
    protected AccountImpl getAccount(String accountId) throws NoSuchAccountException {
        if (!allAccountIndex.containsKey(accountId)) {
            throw new NoSuchAccountException();
        }
        return allAccountIndex.get(accountId);
    }

    /**
     * Destroy an account under a wallet
     * @param wallet the wallet which has the account
     * @param account the account to be destroyed
     * @throws NoSuchAccountException throws when the wallet is not existed or the account is not existed
     */
    protected void destroyAccount(WalletImpl wallet, Account account) throws NoSuchAccountException {
        if(!walletAccountsMap.containsKey(wallet.getId())){
            throw new NoSuchAccountException("The wallet does not exist!");
        }
        if(!allAccountIndex.containsKey(account.getId())){
            throw new NoSuchAccountException();
        }
        Map<String, AccountImpl> walletAccount = walletAccountsMap.get(wallet.getId());
        if(!walletAccount.containsKey(account.getId())){
            throw new NoSuchAccountException();
        }
        allAccountIndex.remove(account.getId());
        walletAccount.remove(account.getId());
        walletAccountsMap.put(wallet.getId(), walletAccount);

    }

}
