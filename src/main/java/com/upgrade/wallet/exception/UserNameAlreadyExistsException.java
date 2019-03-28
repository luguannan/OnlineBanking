package com.upgrade.wallet.exception;

/**
 * Throws when creating a wallet with a repeated username.
 */
public class UserNameAlreadyExistsException extends BankException {
    public UserNameAlreadyExistsException() {
    }

    public UserNameAlreadyExistsException(String message) {
        super(message);
    }

    public UserNameAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNameAlreadyExistsException(Throwable cause) {
        super(cause);
    }
}
