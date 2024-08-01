package com.netshiftdigital.dhhpodcast.config;

import lombok.ToString;

@ToString
public class BankAccount {
    private String accountHolderName;
    private double balance;
    private String accountNumber;

    // Constructor
    public BankAccount(String accountHolderName, String accountNumber, double initialBalance) {
        this.accountHolderName = accountHolderName;
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
    }

    // Method to deposit money
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
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

    // Getters for account holder name and account number
    public String getAccountHolderName() {
        return accountHolderName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    // Main method for testing
    public static void main(String[] args) {
        // Creating a new bank account
        BankAccount account = new BankAccount("John Doe", "123456789", 500.00);
        System.out.println(account);

        account.deposit(3000);
        account.deposit(5000);
        account.withdraw(-457);
        account.checkBalance();
        System.out.println(account.checkBalance());

        System.out.println(account);


        // Displaying account details
//        System.out.println("Account Holder: " + account.getAccountHolderName());
//        System.out.println("Account Number: " + account.getAccountNumber());
//        System.out.println("Initial Balance: " + account.checkBalance());
//
//        // Performing operations
//        account.deposit(200.00);
//        account.withdraw(100.00);
//        System.out.println("Final Balance: " + account.checkBalance());
    }
}
