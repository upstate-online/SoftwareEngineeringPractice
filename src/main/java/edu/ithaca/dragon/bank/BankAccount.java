package edu.ithaca.dragon.bank;

public class BankAccount {

    private String email;
    private double balance;

    /**
     * @throws IllegalArgumentException if email is invalid
     */
    public BankAccount(String email, double startingBalance){
        if (isEmailValid(email)){
            this.email = email;

            if(isAmountValid(startingBalance)){
            this.balance = startingBalance;
                }
            else{
                throw new IllegalArgumentException("Invalid Balance: " + startingBalance );
            }
        }

        else {
            throw new IllegalArgumentException("Email address: " + email + " is invalid, cannot create account");
        }
    }

    public double getBalance(){
        return balance;
    }

    public String getEmail(){
        return email;
    }

    /**
     * @post reduces the balance by amount if amount is non-negative and smaller than balance
     */
    public void withdraw (double amount) throws InsufficientFundsException {

        if(isAmountValid(amount) != true){
            throw new IllegalArgumentException("Invalid Balance: " + amount );
        }

        else if(amount > balance){
            throw new InsufficientFundsException("Not enough money");

        }
        else{
            balance -= amount;
        }

    }


    public static boolean isEmailValid(String email){
        if(email==""){
            return false;
        }
        String [ ] symbol = {"@@","--","__","..","-@","@-","@_","_@","@.",".@","`","~","!","#","$","%","^","&","*","(",")","=","+","[","{","}","]",";",": ","<",">",",","?","/","'","\"","\\","|"};
        char [ ] symbol2 = {'-','@','_','.'};

        //check if there's wrong symbol
        for(int i=0;i<symbol.length;i++){
            if(email.contains(symbol[i])) {
                return false;
            }
        }


        //check valid location
        int index=0;
        for(int i=0;i<symbol2.length;i++){
            if(email.indexOf(symbol2[i]) != 0 && email.lastIndexOf(symbol2[i])!=email.length()-1 & email.lastIndexOf(symbol2[i])!=email.length()-2) {
                index = index +0;
            }
            else {
                index = index +1;
            }
        }
        if(index>0){
            return false;
        }

        //check if @ is in front of one. and both not in position -1

        if(email.indexOf('@')>email.lastIndexOf('.')||email.indexOf('@')==-1||email.indexOf('.')==-1){
            return false;
        }
        else if(email.indexOf('@')>email.indexOf('.') && email.indexOf('.')>email.lastIndexOf('.')){
            return false;
        }

        //Nothing wrong
        return true;

    }

    /**
     * @return  true if amount is neither negative nor beyond two decimal places**/

    public static boolean isAmountValid(double amount){
        if((amount * -1) > 0 ){
            return false;
        }

        double newAmt = amount * 100;

        if ((newAmt%1)>0){
            return false;
        }

        else{

            return true;}
    }

    /** deposits valid amount into account**/
    public static void deposit(double amount){


    }

    /** transfers valid amount of money from one account to another **/
    public static void transfer(double amount){


    }

}
