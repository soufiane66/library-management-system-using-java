package com.gama.library.database;

import com.gama.library.util.ConnectionManger;

import java.sql.*;
import java.util.Objects;

public class LibraryDB {

    private static final ConnectionManger connectionManger = ConnectionManger.getConnectionManger();
    private final static Connection connection = connectionManger.getConnection();
    private static final Statement statement = connectionManger.getStatement();
    public static final String DATABASE_NAME = "library";
    public static final String TABLE_BOOKS_NAME = "books";
    public static final String TABLE_Member_NAME = "members";
    public static final String TABLE_ISSUE_NAME = "issue";
    private static LibraryDB instance;

    public LibraryDB(){

    }

    public static LibraryDB getInstance(){
        return Objects.requireNonNullElseGet(instance, () -> instance = new LibraryDB());
    }

    public void initializeDb(){

        createDatabase();
        createBooksTable();
        createMembersTable();
        createIssueTable();


    }

    private boolean databaseExists(String name){
        String query = String.format("SHOW DATABASES LIKE '%s'",name);
        try {

            ResultSet resultSet = statement.executeQuery(query);
            if(resultSet.next()){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    private void createDatabase() {
        if (connection != null) {

            if(databaseExists(DATABASE_NAME)){
                System.out.println("Database "+DATABASE_NAME+" is already exists");
            }else {
                try {
                    //Create database if not exists
                    String spl = String.format("CREATE DATABASE %s", DATABASE_NAME);
                    int rowsAffected = statement.executeUpdate(spl);
                    if(rowsAffected == 1){
                        System.out.println("Database "+DATABASE_NAME+" Created");
                    }
                    System.out.println(rowsAffected);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }


        }
    }


    private static void createBooksTable() {
        if (connection != null) {

            //com/gama/library/hello-view.fxml

            try {
                //Check if database already exists
                DatabaseMetaData metaData = connection.getMetaData();
                ResultSet tables = metaData.getTables(null,null, TABLE_BOOKS_NAME.toUpperCase(),null );
                if(tables.next()){
                    System.out.println("table "+ TABLE_BOOKS_NAME +" already exists");
                }else {

                    //Create table if not exists
                    String spl = String.format("CREATE TABLE %s (" +
                            "id INT PRIMARY KEY AUTO_INCREMENT," +
                            "title VARCHAR (100)," +
                            "author VARCHAR (100)," +
                            "publisher VARCHAR (100)," +
                            "intCode INT," +
                            "isAvailable BOOLEAN" +
                            ")", TABLE_BOOKS_NAME);

                    statement.execute(spl);

                    System.out.println("table "+ TABLE_BOOKS_NAME +" created");


                }
            }catch (SQLException e){
                e.printStackTrace();
            }

        }
    }

    private static void createMembersTable() {
        if (connection != null) {
            try {
                //Check if table already exists
                DatabaseMetaData metaData = connection.getMetaData();
                ResultSet tables = metaData.getTables(null,null,TABLE_Member_NAME.toUpperCase(),null );
                if(tables.next()){
                    System.out.println("table "+ TABLE_Member_NAME +" already exists");
                }else {

                    //Create table if not exists
                    String spl = String.format("CREATE TABLE %s (" +
                            "id INT PRIMARY KEY AUTO_INCREMENT," +
                            "name VARCHAR (100)," +
                            "mobile VARCHAR (100)," +
                            "email VARCHAR (100)" +
                            ")", TABLE_Member_NAME);
                    statement.execute(spl);

                    System.out.println("table "+ TABLE_Member_NAME +" created");


                }
            }catch (SQLException e){
                e.printStackTrace();
            }

        }
    }

    public static ResultSet executeQuery(String sql){
        try  {
            return statement.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Boolean executeAction(String sql){
        try {
            return statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void createIssueTable(){

        if (connection != null) {
            try {
                //Check if table already exists
                DatabaseMetaData metaData = connection.getMetaData();
                ResultSet tables = metaData.getTables(null,null,TABLE_ISSUE_NAME.toUpperCase(),null );
                if(tables.next()){
                    System.out.println("table "+ TABLE_ISSUE_NAME +" already exists");
                }else {

                    //Create table if not exists
                    String spl = String.format("CREATE TABLE %s (" +
                            "bookID INT PRIMARY KEY," +
                            "memberID INT ," +
                            "issueTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                            "renew_count INT DEFAULT 0," +
                            "FOREIGN KEY (bookID) REFERENCES books(id)," +
                            "FOREIGN KEY (memberID) REFERENCES members(id)" +
                            ")", TABLE_ISSUE_NAME);
                    statement.execute(spl);

                    System.out.println("table "+ TABLE_ISSUE_NAME +" created");


                }
            }catch (SQLException e){
                e.printStackTrace();
            }

        }
    }




}
