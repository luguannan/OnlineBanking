package com.upgrade.wallet.exception;

/**
 * Check if there are still accounts remained when destroying a wallet.
 */
public class AccountsStillExistException extends AccountException{
    public AccountsStillExistException() {
    }

    public AccountsStillExistException(String message) {
        super(message);
    }

    public AccountsStillExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccountsStillExistException(Throwable cause) {
        super(cause);
    }
}
