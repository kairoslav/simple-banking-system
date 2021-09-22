package banking;

import java.sql.*;
import java.util.Scanner;

public class Service {
    public static void createTableIfNotExists(Connection conn) {
        String query = "CREATE TABLE IF NOT EXISTS card " +
                "(id INTEGER PRIMARY KEY NOT NULL, number TEXT, pin TEXT, balance INTEGER DEFAULT 0)";
        try (Statement statement = conn.createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addNewCard(Card card, Connection conn) {
        String query = "INSERT INTO card (number, pin, balance) VALUES (?, ?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, card.getCardNumber());
            statement.setString(2, card.getPin());
            statement.setInt(3, card.getBalance());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean cardNumberIsExist(String card, Connection conn) {
        String query = "SELECT COUNT(balance) FROM card WHERE number = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, card);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    return resultSet.getInt(1) == 1;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Card login(String cardNumber, String pinCode, Connection conn) {
        try (PreparedStatement statement = conn.prepareStatement("SELECT number, pin, balance FROM card WHERE number = ? AND pin = ?")){
            statement.setString(1, cardNumber);
            statement.setString(2 ,pinCode);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    String cardNum = result.getString(1);
                    String pin = result.getString(2);
                    int balance = result.getInt(3);
                    Card card = new Card();
                    card.setCardNumber(cardNum);
                    card.setBalance(balance);
                    card.setPin(pin);
                    return card;
                }
                return null;
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("\nWrong card number or PIN!\n");
        return null;
    }

    public static int getBalance(Card card, Connection conn) {
        String query = "SELECT balance FROM card WHERE number = ? AND pin = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, card.getCardNumber());
            statement.setString(2,card.getPin());
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("balance");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int getBalance(String cardNumber, Connection conn) {
        String query = "SELECT balance FROM card WHERE number = " + cardNumber;
        try (ResultSet resultSet = conn.createStatement().executeQuery(query)) {
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static void addIncome(Card card, int income, Connection conn) {
        int balance = getBalance(card, conn);
        String query = "UPDATE card SET balance = ?  WHERE number = ? AND pin = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, balance + income);
            statement.setString(2, card.getCardNumber());
            statement.setString(3, card.getPin());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void doTransfer(Card card, String receiverCard, int amount, Connection conn) {
        String query = "UPDATE card SET balance = ?  WHERE number = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, getBalance(card, conn) - amount);
            statement.setString(2, card.getCardNumber());
            statement.executeUpdate();

            statement.setInt(1, getBalance(receiverCard, conn) + amount);
            statement.setString(2, receiverCard);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void closeAccount(Card card, Connection conn) {
        String query = "DELETE FROM card WHERE number = ? and pin = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, card.getCardNumber());
            statement.setString(2, card.getPin());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
