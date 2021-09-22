package banking;


import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        String command = null;
        String argument = null;
        try {
            command = args[0];
            argument = args[1];
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        if (command.equals("-fileName")) {
            String url = "jdbc:sqlite:" + argument;

            SQLiteDataSource dataSource = new SQLiteDataSource();
            dataSource.setUrl(url);

            try (Connection conn = dataSource.getConnection()) {
                Bank bank = new Bank(conn);
                bank.startMenu();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Invalid command.");
        }
    }
}