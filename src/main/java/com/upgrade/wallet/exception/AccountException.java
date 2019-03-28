package com.upgrade.wallet.exception;

/**
 * Created by luguannan on 2018/11/7.
 * Exception with Account, inherits from BankException
 */
public class AccountException extends BankException {

    public AccountException() {
    }

    public AccountException(String message) {
        super(message);
    }

    public AccountException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccountException(Throwable cause) {
        super(cause);
    }

}
