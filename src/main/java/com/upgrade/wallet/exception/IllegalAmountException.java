package com.upgrade.wallet.exception;

/**
 * Throws when the transaction amount is not valid
 */
public class IllegalAmountException extends AccountException {
    public IllegalAmountException() {
    }

    public IllegalAmountException(String message) {
        super(message);
    }

    public IllegalAmountException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalAmountException(Throwable cause) {
        super(cause);
    }
}
