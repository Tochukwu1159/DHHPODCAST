package com.netshiftdigital.dhhpodcast.config;
import java.util.List;
        import java.util.ArrayList;

public class BankAccuntsWithHistory {
    private String accountHolderName;
    private String accountNumber;
    private double balance;
    private List<String> transactionHistory;

    // Constructor
    public BankAccuntsWithHistory(String accountHolderName, String accountNumber, double initialBalance) {
        this.accountHolderName = accountHolderName;
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
        this.transactionHistory = new ArrayList<>();
        this.transactionHistory.add("Account created with initial balance: " + initialBalance);
    }

    // Method to deposit money
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            transactionHistory.add("Deposited: " + amount + " | New Balance: " + balance);
            System.out.println("Deposited: " + amount);
            System.out.println("New Balance: " + balance);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    // Method to withdraw money
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            transactionHistory.add("Withdrawn: " + amount + " | New Balance: " + balance);
            System.out.println("Withdrawn: " + amount);
            System.out.println("New Balance: " + balance);
        } else {
            System.out.println("Invalid withdrawal amount or insufficient balance.");
        }
    }

    // Method to check balance
    public double checkBalance() {
        return balance;
    }

    // Method to check account history
    public void printTransactionHistory() {
        System.out.println("Transaction History for Account: " + accountNumber);
        for (String transaction : transactionHistory) {
            System.out.println(transaction);
        }
    }

    public void transfer(BankAccuntsWithHistory targetAccount, BankAccuntsWithHistory targetAccount1, BankAccuntsWithHistory targetAccount2, double amount, double amount1, double amount2) {


        if (amount > 0 && amount <= balance) {
            this.withdraw(amount);
            targetAccount.deposit(amount);
            transactionHistory.add("Transferred: " + amount + " to Account: " + targetAccount.getAccountNumber() + " from " + targetAccount.getAccountHolderName());
            targetAccount.addTransaction("Received: " + amount + " from Account: " + this.accountNumber);
            System.out.println("Transferred: " + amount + " to Account: " + targetAccount.getAccountNumber() + " from " + targetAccount.getAccountHolderName());
        } else {
            System.out.println("Invalid transfer amount or insufficient balance.");
        }
        if (amount1 > 0 && amount1 <= balance) {
            this.withdraw(amount1);
            targetAccount1.deposit(amount1);
            transactionHistory.add("Transferred: " + amount1 + " to Account: " + targetAccount1.getAccountNumber() + " from " + targetAccount1.getAccountHolderName());
            targetAccount1.addTransaction("Received: " + amount1 + " from Account: " + this.accountNumber);
            System.out.println("Transferred: " + amount1 + " to Account: " + targetAccount1.getAccountNumber() + " from " + targetAccount1.getAccountHolderName());
        } else {
            System.out.println("Invalid transfer amount or insufficient balance.");
        }
        if (amount2 > 0 && amount2 <= balance) {
            this.withdraw(amount2);
            targetAccount2.deposit(amount2);
            transactionHistory.add("Transferred: " + amount2 + "to Account:" + targetAccount2.getAccountNumber() + " from " + targetAccount2.getAccountHolderName());
            targetAccount2.addTransaction("Received:" + amount2 + " from Account " + this.accountNumber);
            System.out.println("Transferred: " + amount2 + " to Account: " + targetAccount2.getAccountNumber() + " from " + targetAccount2.getAccountHolderName());
        } else {
            System.out.println("Invalid transfer amount or insufficient balance.");
        }
    }

    // Helper method to add transaction to history
    private void addTransaction(String transaction) {
        transactionHistory.add(transaction);
    }

    // Getters for account holder name and account number
    public String getAccountHolderName() {
        return accountHolderName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    // Main method for testing
    public static void main(String[] args) {

        BankAccuntsWithHistory account1 = new BankAccuntsWithHistory("Diamond", "3064427864", 100000);
        BankAccuntsWithHistory account2 = new BankAccuntsWithHistory("Christian", "3976654362", 1000000);
        BankAccuntsWithHistory account3 = new BankAccuntsWithHistory("Grace", "3446754882", 1000000);
        BankAccuntsWithHistory account4 = new BankAccuntsWithHistory("Chioma", "3316654769", 1000000);

        account1.deposit(100000);
        account1.withdraw(2000);

        account1.transfer(account2, account3, account4, 8000, 5000, 6000);


        //Displaying final balances
        System.out.println("Final Balance of Account 1: " + account1.checkBalance());
        System.out.println("Final Balance of Account 2: " + account2.checkBalance());
        System.out.println("Final Balance of Account 3: " + account3.checkBalance());
        System.out.println("Final Balance of Account 4: " + account4.checkBalance());

        //Printing transaction histories
        account1.printTransactionHistory();
        account2.printTransactionHistory();
        account3.printTransactionHistory();
        account4.printTransactionHistory();
    }
}