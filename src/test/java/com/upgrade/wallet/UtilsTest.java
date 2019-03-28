package com.upgrade.wallet;

import com.upgrade.wallet.exception.IllegalAmountException;
import com.upgrade.wallet.exception.InsufficientFundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UtilsTest {

    @Test
    public void validateAmountTest(){
        assertThrows(
                IllegalAmountException.class,
                () -> Utils.validateAmount(-1)
        );
        assertThrows(
                IllegalAmountException.class,
                () -> Utils.validateAmount(100.001)
        );
        assertThrows(
                IllegalAmountException.class,
                () -> Utils.validateAmount(0)
        );
    }

    @Test
    public void validateFundTest(){
        assertThrows(
                InsufficientFundException.class,
                () -> Utils.validateFund(0, 10)
        );
        assertDoesNotThrow(()->Utils.validateFund(10,10));
        assertDoesNotThrow(()->Utils.validateFund(10,9.99));
    }

    @Test
    public void addTest(){
        assertEquals(100.0, Utils.add(50,50));
    }

    @Test
    public void subtractTest(){
        assertEquals(0.6, Utils.subtract(2.0, 1.4));
        assertNotEquals(0.6, 2.0-1.4);
    }
}
