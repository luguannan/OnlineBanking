package com.upgrade.wallet;

import com.upgrade.wallet.api.Account;
import com.upgrade.wallet.api.Wallet;
import com.upgrade.wallet.exception.AccountsStillExistException;
import com.upgrade.wallet.exception.NoSuchAccountException;
import com.upgrade.wallet.exception.UserNameAlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;
public class ISBankImplTest {
    ISBankImpl bank;
    String username;
    WalletImpl wallet;

    @BeforeEach
    public void setup() throws UserNameAlreadyExistsException {
        bank = new ISBankImpl();
        username = "username";
        wallet = bank.createWallet(username);

    }

    @Test
    //pass...
    public void createWalletTest() {
        assertTrue(bank.walletMap.containsKey(username));
        assertTrue(wallet.getId().equals( bank.walletMap.get(username).id));
        assertThrows(
                UserNameAlreadyExistsException.class,
                ()-> bank.createWallet(username)
        );
    }

    @Test
    //pass...
    public void destroyWalletTest() throws NoSuchAccountException, AccountsStillExistException {
        AccountImpl account = bank.createAccount(wallet);
        assertTrue(bank.allAccountIndex.containsKey(account.id));
        assertThrows(
                AccountsStillExistException.class,
                ()->bank.destroyWallet(username, wallet)
        );
        bank.destroyAccount(wallet, account);
        assertTrue(bank.walletAccountsMap.containsKey(wallet.getId()));
        assertTrue(bank.walletMap.containsKey(username));
        assertFalse(bank.allAccountIndex.containsKey(account.id));
        bank.destroyWallet(username, wallet);
        assertFalse(bank.walletMap.containsKey(username));
        assertFalse(bank.walletAccountsMap.containsKey(wallet.getId()));

    }

    @Test
    //pass...
    public void createAccountTest() {
        AccountImpl acc = bank.createAccount(wallet);
        assertTrue(bank.allAccountIndex.get(acc.id) == acc);
        AccountImpl acc2 = bank.createAccount(wallet);
        assertTrue(bank.walletAccountsMap.get(wallet.id).containsKey(acc2.id));
    }

    @Test
    //pass...
    public void getAccountTest() throws NoSuchAccountException {
        AccountImpl account = bank.createAccount(wallet);
        assertTrue(bank.allAccountIndex.containsKey(account.id));
        AccountImpl acc2 = new AccountImpl("otherid", 0.0, bank);
        assertThrows(
                NoSuchAccountException.class,
                ()-> bank.getAccount(acc2.id)
        );
    }

    @Test
    //pass...
    public void destroyAccountTest() throws NoSuchAccountException {
        AccountImpl acc = bank.createAccount(wallet);
        bank.destroyAccount(wallet, acc);
        assertFalse(bank.allAccountIndex.containsKey(acc.id));
        AccountImpl acc2 = new AccountImpl("otherid", 0.0, bank);
        assertFalse(bank.walletAccountsMap.get(wallet.id).containsKey(acc.id));
        assertThrows(
                NoSuchAccountException.class,
                ()->bank.destroyAccount(wallet,acc2)
        );
    }
}