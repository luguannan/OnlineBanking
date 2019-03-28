package com.upgrade.wallet.exception;

/**
 * The Bank exception
 */
public class BankException extends Exception {
    public BankException() {
    }

    public BankException(String message) {
        super(message);
    }

    public BankException(String message, Throwable cause) {
        super(message, cause);
    }

    public BankException(Throwable cause) {
        super(cause);
    }
}
