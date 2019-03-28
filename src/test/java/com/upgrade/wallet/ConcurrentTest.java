package com.upgrade.wallet;

import com.upgrade.wallet.api.Bank;
import com.upgrade.wallet.exception.IllegalAmountException;
import com.upgrade.wallet.exception.InsufficientFundException;
import com.upgrade.wallet.exception.UserNameAlreadyExistsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ConcurrentTest {

    AccountImpl account;
    ISBankImpl bank;

    @BeforeEach
    public void setup() throws UserNameAlreadyExistsException {
        bank = new ISBankImpl();
        WalletImpl wallet = bank.createWallet("test");
        account = (AccountImpl) wallet.createAccount();
    }

    @Test
    public void testTwoWithdraws() throws InterruptedException {
        int failedCount[] = new int[1];
        account.balance = 20;
        Thread t1 = new Thread() {
            @Override
            public void run() {
                try {
                    account.withdraw(15);
                } catch (InsufficientFundException e) {
                    failedCount[0] ++;
                } catch (IllegalAmountException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread t2 = new Thread(){
            public void run() {
                try {
                    account.withdraw(10);
                } catch (InsufficientFundException e) {
                    failedCount[0] ++;
                } catch (IllegalAmountException e) {
                    e.printStackTrace();
                }
            }
        };
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        Assertions.assertEquals(1, failedCount[0]);
        Assertions.assertTrue(account.balance == 10 || account.balance == 5);
    }
}
