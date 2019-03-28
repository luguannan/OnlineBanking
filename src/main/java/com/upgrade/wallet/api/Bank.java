package com.upgrade.wallet.api;

import com.upgrade.wallet.WalletImpl;
import com.upgrade.wallet.exception.AccountsStillExistException;
import com.upgrade.wallet.exception.NoSuchAccountException;
import com.upgrade.wallet.exception.UserNameAlreadyExistsException;

public interface Bank {

    /**
     * Create a wallet for a user with the given username.
     * @param username for a user
     * @return a wallet that related to the username
     * @throws UserNameAlreadyExistsException when the username is already existed in the bank
     */
    Wallet createWallet(String username) throws UserNameAlreadyExistsException;

    /**
     * Destroy the wallet for the user.
     * @param username for a user
     * @param wallet connects to the user
     * @throws NoSuchAccountException when the wallet is not existed in the bank
     * @throws AccountsStillExistException when the wallet has at least 1 account in it.
     */
    void destroyWallet(String username, WalletImpl wallet) throws NoSuchAccountException, AccountsStillExistException;
}
