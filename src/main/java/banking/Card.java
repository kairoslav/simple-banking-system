package banking;

import java.util.Arrays;
import java.util.Random;

public class Card {
    private String cardNumber;
    private String pin;
    private int balance;

    public Card() {
        this.cardNumber = generateLuhnCardNumber();
        this.pin = getRandomPinCode();
        this.balance = 0;
    }
    // 4000008449433403
    // 400000DDDDDDDDDD
    // 4000001974850721
    private String generateLuhnCardNumber() {
        StringBuilder baseBin = new StringBuilder("400000");
        Random random = new Random();
        int totalSum = 0;
        for (int i = 0; i < 9; i++) {
            int digit = random.nextInt(10);
            baseBin.append(digit);
            if (i % 2 == 0) {
                digit *= 2;
                if (digit > 9) {
                    digit -= 9;
                }
            }
            totalSum += digit;
        }
        totalSum += 8; // для бин номера
        totalSum = (10 - totalSum % 10) % 10;
        return baseBin.toString() + totalSum;
    }

    private String getRandomCardNumber() {
        StringBuilder baseBin = new StringBuilder("400000");
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            baseBin.append(random.nextInt(10));
        }
        return baseBin.toString();
    }

    private String getRandomPinCode() {
        StringBuilder pin = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            pin.append(new Random().nextInt(10));
        }
        return pin.toString();
    }

    public static boolean cardNumberIsValid(String cardNumber) {
        int[] card = new int[16];
        int totalSum = 0;
        for (int i = 0; i < cardNumber.length(); i++) {
            int val = Character.getNumericValue(cardNumber.charAt(i));
            if (val < 0 || val > 9) {
                return false;
            }
            card[i] = val;
        }

        for (int i = 0; i < card.length - 1; i++) {
            int digit = card[i];
            if (i % 2 == 0) {
                digit *= 2;
                if (digit > 9) {
                    digit -= 9;
                }
            }
            totalSum += digit;
        }
        totalSum = (10 - totalSum % 10) % 10;
        return totalSum == card[card.length - 1];

    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getPin() {
        return pin;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}
