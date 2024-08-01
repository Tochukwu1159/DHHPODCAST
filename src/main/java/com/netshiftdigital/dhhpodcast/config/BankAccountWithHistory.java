package com.netshiftdigital.dhhpodcast.config;

import java.util.ArrayList;
import java.util.List;

public class BankAccountWithHistory {
    private String accountHolderName;
    private String accountNumber;
    private double balance;
    private List<String> transactionHistory;

    // Constructor
    public BankAccountWithHistory(String accountHolderName, String accountNumber, double initialBalance ) {
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

    // Method to transfer money to another account
    public void transfer(BankAccountWithHistory targetAccount, double amount) {
        if (amount > 0 && amount <= balance) {
            this.withdraw(amount);
            targetAccount.deposit(amount);

        transactionHistory.add("Transferred: " + amount + " to Account: " + targetAccount.getAccountNumber());
            targetAccount.addTransaction("Received: " + amount + " from Account: " + this.accountNumber);
            System.out.println("Transferred: " + amount + " to Account: " + targetAccount.getAccountNumber());
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

        BankAccountWithHistory account1 = new BankAccountWithHistory("Diamond", "3064427864", 100000);
        BankAccountWithHistory account2 = new BankAccountWithHistory("Christian", "3976654362", 1000000);

        account1.deposit(100000);
        account1.withdraw(2000);

        account1.transfer(account2, 40000);

        account2.printTransactionHistory();



        // Creating two bank accounts
//        BankAccountWithHistory account1 = new BankAccountWithHistory("John Doe", "123456789", 500.00);
//        BankAccountWithHistory account2 = new BankAccountWithHistory("Jane Smith", "987654321", 300.00);
//
//        // Displaying initial balances
//        System.out.println("Initial Balance of Account 1: " + account1.checkBalance());
//        System.out.println("Initial Balance of Account 2: " + account2.checkBalance());
//
//        // Performing operations on account1
//        account1.deposit(200.00);
//        account1.withdraw(100.00);
//        account1.printTransactionHistory();
//
//        // Transferring money from account1 to account2
//        account1.transfer(account2, 150.00);
//
//        // Displaying final balances
//        System.out.println("Final Balance of Account 1: " + account1.checkBalance());
//        System.out.println("Final Balance of Account 2: " + account2.checkBalance());
//
//        // Printing transaction histories
//        account1.printTransactionHistory();
//        account2.printTransactionHistory();
    }
}
