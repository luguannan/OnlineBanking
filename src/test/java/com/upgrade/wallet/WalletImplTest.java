package com.upgrade.wallet;

import com.upgrade.wallet.api.Account;
import com.upgrade.wallet.exception.NoSuchAccountException;
import com.upgrade.wallet.exception.UserNameAlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by luguannan on 2018/11/7.
 */
public class WalletImplTest {

    //WalletImpl wallet;
    ISBankImpl bank = new ISBankImpl();
    String id = "username";
    WalletImpl wallet;


    @BeforeEach
    public void setup() throws UserNameAlreadyExistsException {
        wallet = bank.createWallet(id);
    }

    @Test
    //pass...
    public void createAccountTest() throws NoSuchAccountException {
        Account acc = wallet.createAccount();
        assertEquals(acc, bank.getAccount(acc.getId()));
        assertEquals(acc, wallet.getAccount(acc.getId()));

    }

    @Test
    //pass...
    public void getAccountTest() throws NoSuchAccountException {
        assertThrows(
                NoSuchAccountException.class,
                () -> wallet.getAccount("wrongid")
        );

        Account acc = wallet.createAccount();
        Account acc2 = wallet.getAccount(acc.getId());
        assertEquals(acc, acc2);
    }

    @Test
    //pass...
    public void destroyAccountTest() throws NoSuchAccountException {
        Account acc = wallet.createAccount();
        wallet.destroyAccount(acc);
        assertThrows(
                NoSuchAccountException.class,
                () -> wallet.getAccount(acc.getId())
        );
    }

    @Test
    public void listAllAccountTest(){
        Account acc = wallet.createAccount();
        assertEquals(bank.walletAccountsMap.get(wallet.getId()).size(), wallet.listAllAccount().size());
    }
}
