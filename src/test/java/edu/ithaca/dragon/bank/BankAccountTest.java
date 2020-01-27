package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {

    @Test
    void getBalanceTest() {

        //Equiv: 200 Balance
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        assertEquals(200, bankAccount.getBalance());

        //CE: Equiv: .90 Balance
        BankAccount bankAccount4 = new BankAccount("a@b.com", 0.90);
        assertEquals(0.90, bankAccount4.getBalance());

        //CE: Border Case: 0 Balance
        BankAccount bankAccount1 = new BankAccount("a@b.com", 0);
        assertEquals(0, bankAccount1.getBalance());

        //CE:Border Case : 1 Million (Arbitrary Max Value)
        BankAccount bankAccount2 = new BankAccount("a@b.com", 1000000);
        assertEquals(1000000, bankAccount2.getBalance());


    }

    @Test
        //Lei//Is a border case
        //Lei//equivalence:test different number of starting and withdraw
        //Lei//Not present:test withdraw higher than starting balance
        //Lei//Not present:test withdraw negative number
    void withdrawTest() throws InsufficientFundsException{
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        //CE: Boarder Case: Positive Amount Withdraw
        bankAccount.withdraw(100);
        assertEquals(100, bankAccount.getBalance());


        //CE: Negative Withdraw Amounts

        //Equivalence Test Neg. Decimals: Withdraw "-0.10" : current balance 100
        bankAccount.withdraw(-0.10);
        assertEquals(100.,bankAccount.getBalance());

        //Equivalence Test: Withdraw "-1.00" : current balance 100
        bankAccount.withdraw(-1.00);
        assertEquals(100,bankAccount.getBalance());

        //Equivalence Test: Withdraw "-100.00" : current balance 100
        bankAccount.withdraw(-100.00);
        assertEquals(100,bankAccount.getBalance());

        //CE: Withdraw Invervals Cents and Dollars

        //Equiv: Cents (Less than $1)
        bankAccount.withdraw(0.99);
        assertEquals(99.01,bankAccount.getBalance());

        //Boarder: Dollar ($1)
        bankAccount.withdraw(1.00);
        assertEquals(98.01,bankAccount.getBalance());

        //Equiv: Dollars and Cents
        bankAccount.withdraw(1.01);
        assertEquals(97.00,bankAccount.getBalance());

        //CE: Overdraw Account

        //Boarder: Withdraw Amount == Balance
        bankAccount.withdraw(97.00);
        assertEquals(0.00,bankAccount.getBalance());

//        //Equiv: 0.01 over balance
//        assertThrows(InsufficientFundsException.class, ()->bankAccount.withdraw(0.01));
//
//        //Equiv: 1.00 over balance
//        assertThrows(InsufficientFundsException.class, ()->bankAccount.withdraw(1.00));
//
//        //Equiv: 300 over balance
//        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(300));


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

        //CE: Invalid Symbol Tests (1.26)

        //Boarder: No Invalid Symbols
        assertTrue(BankAccount.isEmailValid("a@b.com"));

        //Equivalence: One Invalid Symbol
        assertFalse(BankAccount.isEmailValid("a*@b.com"));

        //Equivalence: Double Symbols
        assertFalse(BankAccount.isEmailValid("a..b@c.com"));

        //Boarder: All Invalid Symbols
        assertFalse(BankAccount.isEmailValid("()*&"));


        //CE: Symbol Placement

        //Boarder: Valid Symbol Placement
        assertTrue(BankAccount.isEmailValid("a@b.com"));

        //Equivalence: Begins with Symbol
        assertFalse(BankAccount.isEmailValid(".ab@c.com"));

        //Equivalence: Symbol in penultimate spot
        assertFalse(BankAccount.isEmailValid("ab@c.c.m"));
        //TESTS FAILED

        //Boarder: Ends with Symbol
        assertFalse(BankAccount.isEmailValid("a@b.co."));
        //TESTS FAILED

        //CE: @ and . present in email
        //Boarder: @ in email address
        assertTrue(BankAccount.isEmailValid("a@b.com"));

        //Equivalence: @ not in email address
        assertFalse(BankAccount.isEmailValid("ab.com"));

        //Equivalence: .not in email address
        assertFalse(BankAccount.isEmailValid("a@bcom"));


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