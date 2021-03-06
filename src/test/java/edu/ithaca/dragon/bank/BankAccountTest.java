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


//        //CE: Negative Withdraw Amounts (Tests no longer valid with update to method)
//
//        //Equivalence Test Neg. Decimals: Withdraw "-0.10" : current balance 100
//        bankAccount.withdraw(-0.10);
//        assertEquals(100.,bankAccount.getBalance());
//
//        //Equivalence Test: Withdraw "-1.00" : current balance 100
//        bankAccount.withdraw(-1.00);
//        assertEquals(100,bankAccount.getBalance());
//
//        //Equivalence Test: Withdraw "-100.00" : current balance 100
//        bankAccount.withdraw(-100.00);
//        assertEquals(100,bankAccount.getBalance());

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

        //Equiv: 0.01 over balance
        assertThrows(InsufficientFundsException.class, ()->bankAccount.withdraw(0.01));

        //Equiv: 1.00 over balance
        assertThrows(InsufficientFundsException.class, ()->bankAccount.withdraw(1.00));

        //Equiv: 300 over balance
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(300));

        BankAccount bankAccount1 = new BankAccount("a@b.com", 200);

        //Beyond Two Decimal Places
        assertThrows(IllegalArgumentException.class, ()-> bankAccount1.withdraw(1.001));

        //Negative Numbers Exception
        assertThrows(IllegalArgumentException.class, ()-> bankAccount1.withdraw(-1.00));

        //Two Decimal Places and Negative Numbers
        assertThrows(IllegalArgumentException.class, ()-> bankAccount1.withdraw(-1.0001));


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

    void isAmountValidTest(){

        //Negative Amount Tests
        assertFalse(BankAccount.isAmountValid(-10000.0000000000009));//boarder case
        assertFalse(BankAccount.isAmountValid(-12.011)); // equivalence case
        assertFalse(BankAccount.isAmountValid(-0.01)); //equivalence case
        assertTrue(BankAccount.isAmountValid(0.00)); //border case

        //Zero and Cents Tests
        assertTrue(BankAccount.isAmountValid(0.00)); //border case
        assertTrue(BankAccount.isAmountValid(0.01)); //equivalence case
        assertFalse(BankAccount.isAmountValid(0.011)); //equivalence case
        assertFalse(BankAccount.isAmountValid(0.00000000000000001)); //border case


        //Positive Amount Tests
        assertTrue(BankAccount.isAmountValid(1.00)); //boarder case
        assertFalse(BankAccount.isAmountValid(1.00001)); //equivalence case
        assertFalse(BankAccount.isAmountValid(1.001)); //equivalence case
        assertTrue(BankAccount.isAmountValid(100.01)); //boarder case




    }

    @Test
    void constructorTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance());
        //check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", 100));

        //Negative Amount
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("a@b.com", -100));

        //More than two decimal places
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("a@b.com", 100.0001));

        //Negative and more than two decimal places
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("a@b.com", -100.0001));



    }
     @Test
     void depositTest(){
        BankAccount bankAccount1 = new BankAccount("a@b.com", 200);

        //CE: Withdraw Invervals Cents and Dollars

        //Equiv: Cents (Less than $1)
        bankAccount1.deposit(0.99);
        assertEquals(200.99,bankAccount1.getBalance());

        //Boarder: Dollar ($1)
        bankAccount1.deposit(1.00);
        assertEquals(201.99,bankAccount1.getBalance());

        //Equiv: Dollars and Cents
        bankAccount1.deposit(1.01);
        assertEquals(203.00,bankAccount1.getBalance());

        //Invalid Amounts: Exception Throwing
        //Beyond Two Decimal Places
        assertThrows(IllegalArgumentException.class, ()-> bankAccount1.deposit(1.001));

        //Negative Numbers Exception
        assertThrows(IllegalArgumentException.class, ()-> bankAccount1.deposit(-1.00));

        //Two Decimal Places and Negative Numbers
        assertThrows(IllegalArgumentException.class, ()-> bankAccount1.deposit(-1.0001));


    }

    @Test
    void transferTest() throws InsufficientFundsException {
        BankAccount bankAccount1 = new BankAccount("a@b.com", 200);
        BankAccount bankAccount2 = new BankAccount("a@b.com", 0);
        BankAccount bankAccount3 = new BankAccount("a@b.com", 0);

        //valid transfers
        bankAccount1.transfer(1,bankAccount2);
        assertEquals(1, bankAccount2.getBalance()); //boarder
        assertEquals(199, bankAccount1.getBalance()); //boarder

        bankAccount1.transfer(10.50,bankAccount2);
        assertEquals(11.50, bankAccount2.getBalance()); //equivalence
        assertEquals(188.50, bankAccount1.getBalance()); //equivalence

        bankAccount1.transfer(100,bankAccount2);
        assertEquals(111.50, bankAccount2.getBalance());//boarder
        assertEquals(88.50, bankAccount1.getBalance()); //boarder

        //overdrawn transfers (valid amounts)
        assertThrows(InsufficientFundsException.class, ()->bankAccount3.transfer(0.01, bankAccount2)); //boarder
        assertThrows(InsufficientFundsException.class, ()->bankAccount3.transfer(1.01, bankAccount2)); //equivalence
        assertThrows(InsufficientFundsException.class, ()->bankAccount3.transfer(1.00, bankAccount2)); //boarder

        //invalid amounts
        //Invalid Amounts: Exception Throwing
        //Beyond Two Decimal Places
        assertThrows(IllegalArgumentException.class, ()-> bankAccount1.transfer(-1.001,bankAccount3));

        //Negative Numbers Exception
        assertThrows(IllegalArgumentException.class, ()-> bankAccount1.transfer(-1.00, bankAccount3));

        //Two Decimal Places and Negative Numbers
        assertThrows(IllegalArgumentException.class, ()-> bankAccount1.transfer(-1.0001, bankAccount3));

        assertEquals(0, bankAccount3.getBalance());








    }


}