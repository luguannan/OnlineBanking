package com.upgrade.wallet.exception;

/**
 * Created by luguannan on 2018/11/7.
 *
 * Throws when getting a non existed account.
 */
public class NoSuchAccountException extends AccountException {
    public NoSuchAccountException() {
    }

    public NoSuchAccountException(String message) {
        super(message);
    }

    public NoSuchAccountException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchAccountException(Throwable cause) {
        super(cause);
    }
}
