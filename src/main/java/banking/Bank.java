package banking;
import java.sql.*;
import java.util.Scanner;

import static banking.Card.cardNumberIsValid;
import static banking.Service.*;


public class Bank {
    private final Connection conn;

    public Bank(Connection conn) {
        this.conn = conn;
        createTableIfNotExists(conn);
    }

    public void exit() {
        System.out.println("Bye!");
    }

    public void startMenu() {
        switch (mainMenuInput()) {
            case 0:
                exit();
                break;
            case 1:
                createCard();
                startMenu();
                break;
            case 2:
                Scanner scanner = new Scanner(System.in);
                System.out.println("Enter your card number:");
                String cardNumber = scanner.nextLine();
                System.out.println("Enter your PIN:");
                String pinCode = scanner.nextLine();

                Card card = login(cardNumber, pinCode, conn);
                if (card != null) {
                    System.out.println("You have successfully logged in!\n");
                    cardMenu(card);
                } else {
                    System.out.println("Wrong card number or PIN!\n");
                    startMenu();
                }
                break;
        }
    }

    private void cardMenu(Card card) {
        switch (userMenuInput()) {
            case 0:
                exit();
                break;
            case 1:
                int balance = getBalance(card, conn);
                System.out.println("Balance: " + balance + "\n");
                cardMenu(card);
                break;
            case 2:
                System.out.println("Enter income:");
                Scanner scanner = new Scanner(System.in);
                try {
                    int income = scanner.nextInt();
                    if (income < 0) {
                        System.out.println();
                        System.out.println("Invalid input!\n");
                    } else {
                        addIncome(card, income, conn);
                        System.out.println("Income was added!\n");
                        cardMenu(card);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 3:
                System.out.println("Transfer");
                System.out.println("Enter card number:");
                Scanner scanner1 = new Scanner(System.in);
                try {
                    String cardNumberToTransfer = scanner1.nextLine();
                    if (cardNumberIsValid(cardNumberToTransfer)) {
                        if (cardNumberIsExist(cardNumberToTransfer, conn)) {
                            if (!card.getCardNumber().equals(cardNumberToTransfer)) {
                                System.out.println("Enter how much money you want to transfer:");
                                int amountOfMoneyToTransfer = scanner1.nextInt();
                                if (amountOfMoneyToTransfer <= getBalance(card, conn)) {
                                    doTransfer(card, cardNumberToTransfer, amountOfMoneyToTransfer, conn);
                                    System.out.println("Success!\n");
                                } else {
                                    System.out.println("Not enough money!\n");
                                }
                            } else {
                                System.out.println("You can't transfer money to the same account!\n");
                            }
                        } else {
                            System.out.println("Such a card does not exist.\n");
                        }
                    } else {
                        System.out.println("Probably you made a mistake in the card number. Please try again!\n");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                cardMenu(card);
                break;
            case 4:
                closeAccount(card, conn);
                System.out.println("The account has been closed!\n");
                startMenu();
                break;
            case 5:
                System.out.println("You have successfully logged out!\n");
                startMenu();
                break;
            case -1:
                System.out.println("Invalid input.\n");
                cardMenu(card);
                break;
        }
    }

    private void createCard() {
        Card card = new Card();
        addNewCard(card, conn);
        System.out.println("Your card has been created");
        System.out.println("Your card number:");
        System.out.println(card.getCardNumber());
        System.out.println("Your card PIN:");
        System.out.println(card.getPin());
        System.out.println();
    }


    private int userMenuInput() {
        System.out.println("1. Balance");
        System.out.println("2. Add income");
        System.out.println("3. Do transfer");
        System.out.println("4. Close account");
        System.out.println("5. Log out");
        System.out.println("0. Exit");
        Scanner scanner = new Scanner(System.in);
        try {
            int number = Integer.parseInt(scanner.nextLine());
            if (number < 0 || number > 5) {
                throw new NumberFormatException();
            }
            System.out.println();
            return number;
        }
        catch (NumberFormatException e) {
            return -1;
        }
    }


    private int mainMenuInput() {
        System.out.println("1. Create an account");
        System.out.println("2. Log into account");
        System.out.println("0. Exit");

        Scanner scanner = new Scanner(System.in);
        try {
            int number = Integer.parseInt(scanner.nextLine());
            if (number < 0 || number > 2) {
                throw new NumberFormatException();
            }
            System.out.println();
            return number;
        }
        catch (NumberFormatException e) {
            System.out.println("Invalid input.\n");
            startMenu();
        }
        return 0;
    }
}
