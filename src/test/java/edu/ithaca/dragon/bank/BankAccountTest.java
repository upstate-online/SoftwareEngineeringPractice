package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {

    @Test
    void getBalanceTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals(200, bankAccount.getBalance());
    }

    @Test
        //Lei//Is a border case
        //Lei//equivalence:test different number of starting and withdraw
        //Lei//Not present:test withdraw higher than starting balance
        //Lei//Not present:test withdraw negative number
    void withdrawTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(100);

        assertEquals(100, bankAccount.getBalance());
    }

    @Test
        //Lei//Not a border case
        //Lei//equivalence:test double valid symbol and invalid symbol
        //Lei//equivalence:test @ and . position
        //Lei//equivalence: equivalent to test other double valid symbols and invalid symbols
        //Lei//Not present:position of valid symbol (- and _)
    void isEmailValidTest(){

        assertTrue(BankAccount.isEmailValid( "a@b.com"));
        assertFalse( BankAccount.isEmailValid(""));

        //CE Added
        assertFalse(BankAccount.isEmailValid("a@@b.com"));
        assertFalse(BankAccount.isEmailValid("a@c*"));

    }

    @Test
    void constructorTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance());
        //check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", 100));
    }

}