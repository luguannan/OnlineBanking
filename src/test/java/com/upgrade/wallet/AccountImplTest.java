package com.upgrade.wallet;

import com.upgrade.wallet.api.Transaction;
import com.upgrade.wallet.exception.IllegalAmountException;
import com.upgrade.wallet.exception.InsufficientFundException;
import com.upgrade.wallet.exception.NoSuchAccountException;
import com.upgrade.wallet.exception.UserNameAlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class AccountImplTest {
    AccountImpl account;
    ISBankImpl bank = new ISBankImpl();
    WalletImpl wallet ;
    AccountImpl acc;

    @BeforeEach
    public void setup() throws UserNameAlreadyExistsException {
        wallet = bank.createWallet("username");
        account = bank.createAccount(wallet);
        acc = bank.createAccount(wallet);
        acc.balance = 100.0;
    }

    @Test
    public void getIdTest(){
        account.id = "newId";
        assertEquals("newId",account.getId());
    }

    @Test
    public void depositTest() throws IllegalAmountException {
        account.deposit(50);
        assertEquals(50, account.getBalance());
        assertEquals(1,account.transactionLog.size());
        assertEquals(Transaction.TransactionType.DEPOSIT, account.transactionLog.get(0).getType());
        assertEquals(50, account.transactionLog.get(0).getAmount());
        assertEquals(account.getId(), account.transactionLog.get(0).getToAccount());
        assertNull(account.transactionLog.get(0).getFromAccount());
    }

    @Test
    public void withdrawTest() throws InsufficientFundException, IllegalAmountException{
        account.balance = 50.0;
        account.withdraw(20);
        assertEquals(30, account.getBalance());
        assertEquals(1,account.transactionLog.size());
        assertEquals(Transaction.TransactionType.WITHDRAW, account.transactionLog.get(0).getType());
        assertEquals(20, account.transactionLog.get(0).getAmount());
        assertEquals(account.getId(), account.transactionLog.get(0).getFromAccount());
        assertNull(account.transactionLog.get(0).getToAccount());
    }

    @Test
    public void transferTest() throws InsufficientFundException, IllegalAmountException, NoSuchAccountException {
        acc.transfer(account.id, 30);
        assertEquals(30, account.getBalance());
        assertEquals(70, acc.getBalance());
        assertEquals(account.getId(), account.transactionLog.get(0).getToAccount());
        assertEquals(1,account.transactionLog.size());
        assertEquals(Transaction.TransactionType.TRANSFER, account.transactionLog.get(0).getType());
        assertEquals(30, account.transactionLog.get(0).getAmount());
        assertEquals(acc.id, account.transactionLog.get(0).getFromAccount());
        assertEquals(acc.getId(), acc.transactionLog.get(0).getFromAccount());
        assertEquals(1,acc.transactionLog.size());
        assertEquals(Transaction.TransactionType.TRANSFER, acc.transactionLog.get(0).getType());
        assertEquals(30, acc.transactionLog.get(0).getAmount());
        assertEquals(account.id, acc.transactionLog.get(0).getToAccount());

    }

    @Test
    public void makeTransferTest() throws IllegalAmountException, InsufficientFundException {
        String tid = "transactionId";
        Date time = new Date();
        acc.makeTransfer(account.id, 30, tid, time);
        assertEquals(70, acc.getBalance());
        assertEquals(acc.getId(), acc.transactionLog.get(0).getFromAccount());
        assertEquals(1,acc.transactionLog.size());
        assertEquals(Transaction.TransactionType.TRANSFER, acc.transactionLog.get(0).getType());
        assertEquals(30, acc.transactionLog.get(0).getAmount());
        assertEquals(account.id, acc.transactionLog.get(0).getToAccount());
    }

    @Test
    public void receiveTransfer() throws IllegalAmountException {
        String tid = "transactionId";
        Date time = new Date();
        account.receiveTransfer(acc.id, 30, tid, time);
        assertEquals(30, account.getBalance());
        assertEquals(account.getId(), account.transactionLog.get(0).getToAccount());
        assertEquals(1,account.transactionLog.size());
        assertEquals(Transaction.TransactionType.TRANSFER, account.transactionLog.get(0).getType());
        assertEquals(30, account.transactionLog.get(0).getAmount());
        assertEquals(acc.id, account.transactionLog.get(0).getFromAccount());
    }
    @Test
    public void getBalanceTest(){
        assertEquals(0, account.getBalance());
    }

    @Test
    public void getLastNTransactionsTest() throws InsufficientFundException, IllegalAmountException{
        account.deposit(50);
        account.withdraw(20);
        account.deposit(100.50);
        assertEquals(3, account.transactionLog.size());
        assertEquals(2, account.getLastNTransactions(2).size());
        List<Transaction> res = account.getLastNTransactions(2);
        assertEquals(Transaction.TransactionType.WITHDRAW, res.get(0).getType());
        assertEquals(20 ,res.get(0).getAmount());
        assertEquals(account.id,res.get(0).getFromAccount());
        assertNull(res.get(0).getToAccount());

    }

}
