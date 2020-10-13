package tools;

import code.Word;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Scanner;

public class Database {

    private static Connection connect = null;
    private static Statement statement = null;
    private static PreparedStatement preparedStatement = null;
    private static ResultSet resultSet = null;
    private static String url = "jdbc:mysql://localhost:3307/mysql";
    private static String user = "root", pass = "";

    public static void readDataBase() {
        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.jdbc.Driver");
            // Setup the connection with the DB
            connect = DriverManager.getConnection(url, user, pass);

            // Statements allow to issue SQL queries to the database
            statement = connect.createStatement();
            // Result set get the result of the SQL query
            resultSet = statement.executeQuery("select * from entries.entries limit 10;");
            writeResultSet(resultSet);
        } catch (Exception e) {

        } finally {
            close();
        }
    }

    public static void writeResultSet(ResultSet resultSet) throws SQLException {
        // ResultSet is initially before the first data set
        while (resultSet.next()) {
            // It is possible to get the columns via name
            // also possible to get the columns via the column number
            // which starts at 1
            // e.g. resultSet.getSTring(2);
            int id = resultSet.getInt("id");
            String word = resultSet.getString("word");
            String wordtype = resultSet.getString("wordtype");
            String explain = resultSet.getString("definition");

            System.out.println("ID: " + id);
            System.out.println("Word: " + word);
//            System.out.println("Wordtype: " + wordtype);
            System.out.println("Explain: " + wordtype + "\n" + explain);

        }
    }

    public static void addIntoDatabase(Word newWord) {
        String insert = "insert into entries.entries values (default, ?, ?, ?);";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection(url, user, pass);
            connect.setAutoCommit(false);
            if (connect != null) {
                System.out.println("Connect to Database successfully");
            }

            preparedStatement = connect.prepareStatement(insert);

            if (connect != null) {
                preparedStatement.setString(1, newWord.getWordTarget());
                preparedStatement.setString(2, "");
                preparedStatement.setString(3, newWord.getWordExplain());
                preparedStatement.execute();
                System.out.println("Add to Database successfully");
            }

        } catch (Exception e) {

        } finally {
            close();
        }
    }

    public static void deleteFromDatabase(String deleteWord) {
        String delete = "delete from entries.entries where word = ?;";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection(url, user, pass);
            connect.setAutoCommit(false);
            if (connect != null) {
                System.out.println("Connect to Database successfully");
            }

            preparedStatement = connect.prepareStatement(delete);
            if (connect != null) {
                preparedStatement.setString(1, deleteWord);
                preparedStatement.execute();
            }
        } catch (Exception e) {

        } finally {
            close();
        }
    }

    public static void updateInDatabase(Word editWord) {
        String update = "update entries.entries set definition = ? where word = ?";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection(url, user, pass);
            connect.setAutoCommit(false);
            if (connect != null) {
                System.out.println("Connect to Database successfully");
            }

            preparedStatement = connect.prepareStatement(update);
            if (connect != null) {
                preparedStatement.setString(1, editWord.getWordExplain());
                preparedStatement.setString(2, editWord.getWordTarget());
                preparedStatement.execute();
            }
        } catch (Exception e) {

        } finally {
            close();
        }
    }

    private static void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (connect != null) {
                connect.close();
            }
        } catch (Exception e) {

        }
    }

    public static void main(String[] args) throws Exception {
        Word newWord = new Word();
        newWord.setWordTarget("asdefgh");
        newWord.setWordExplain("dc pls du me");
        addIntoDatabase(newWord);

//        deleteFromDatabase(newWord.getWordTarget());
//        Class.forName("com.mysql.jdbc.Driver");
//        // Setup the connection with the DB
//        connect = DriverManager.getConnection(url, user, pass);
//
//        // Statements allow to issue SQL queries to the database
//        statement = connect.createStatement();
//        // Result set get the result of the SQL query
//        resultSet = statement.executeQuery("select * from entries.entries where word = 'tra oi anh yeu em';");
//        writeResultSet(resultSet);
    }
}
