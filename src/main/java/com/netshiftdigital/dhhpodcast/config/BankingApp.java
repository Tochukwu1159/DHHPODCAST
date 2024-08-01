package com.netshiftdigital.dhhpodcast.config;

import java.util.Scanner;

public class BankingApp {
    private double balance;

    public BankingApp() {
        balance = 0.0;
    }

    public void deposit(double amount) {
        balance += amount;
        System.out.println("Deposit Successful! New Balance: " + balance);
    }

    public void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            System.out.println("Withdrawal Successful! New Balance: " + balance);
        } else {
            System.out.println("Insufficient Funds!");
        }
    }

    public static void main(String[] args) {
        BankingApp app = new BankingApp();
        Scanner scanner = new Scanner(System.in);

        while (true) {
//            System.out.println("1. Deposit");
//            System.out.println("2. Withdraw");
//            System.out.println("3. Check Balance");
//            System.out.println("4. Exit");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter amount to deposit: ");
                    double depositAmount = scanner.nextDouble();
                    app.deposit(depositAmount);
                    break;
                case 2:
                    System.out.print("Enter amount to withdraw: ");
                    double withdrawAmount = scanner.nextDouble();
                    app.withdraw(withdrawAmount);
                    break;
                case 3:
                    System.out.println("Current Balance: " + app.balance);
                    break;
                case 4:
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid Choice!");
            }
        }
    }
}