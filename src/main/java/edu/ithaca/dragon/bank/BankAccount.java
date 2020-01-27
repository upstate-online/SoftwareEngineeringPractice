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
            this.balance = startingBalance;
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
     *
     * Throws Ins.Fund. Exception if withdraw amount is greater than balance
     */
    public void withdraw (double amount) throws InsufficientFundsException{
        if (amount <= balance){
            balance -= amount;
        }
        else {
            throw new InsufficientFundsException("Not enough money");
        }
    }


    public static boolean isEmailValid(String email){
        if(email==""){
            return false;
        }
        String [ ] symbol = {"@@","--","__","..","`","~","!","#","$","%","^","&","*","(",")","=","+","[","{\",\"}\",\"]\",\";\",\":\",\"<\",\">\",\",\",\"?\",\"/\",\"\'\",\"\"","\\","|"};
        char [ ] symbol2 = {'-','@','_','.'};



        //check if there's double or wrong symbol
        for(int i=0;i<symbol.length;i++){
            if(email.contains(symbol[i])) {
                return false;
            }
        }

        //check if it's located at beginning or last two
        for(int i=0;i<symbol2.length;i++){
            if(email.indexOf(symbol2[i])==0&&email.indexOf(symbol2[i])==email.length()-1&&email.indexOf(symbol2[i])==email.length()-2) {
                return false;
            }
        }

        //check if @ is in front of . and both not in position -1
        if(email.indexOf('@')>email.indexOf('.')||email.indexOf('@')==-1||email.indexOf('.')==-1){
            return false;
        }else{
            return true;}

    }
}
