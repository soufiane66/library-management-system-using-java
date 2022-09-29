package com.gama.library.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

//singleton
public class ConnectionManger {

   private static volatile ConnectionManger connectionManger;
    private Connection connection;
    private Statement statement;


   private ConnectionManger(){
       // jdbc:mysql://localhost:3306/jdbc_database;
       try {
           Properties props = new Properties();
           props.load(new FileInputStream("src/main/resources/dbConfig.properties"));
            String HOST = props.getProperty("HOST");
            String PORT = props.getProperty("PORT");
            String DB_NAME = props.getProperty("DB_NAME");
            String USERNAME = props.getProperty("USERNAME");
            String PASSWORD = props.getProperty("PASSWORD");
           String url = String.format("jdbc:mysql://%s:%s/%s",HOST,PORT,DB_NAME);
           connection = DriverManager.getConnection(url, USERNAME, PASSWORD);
           statement = connection.createStatement();


       } catch (SQLException sqlException ) {
           System.out.println("ConnectionManager : There is not connection with server");
       }catch (IOException e){
           System.out.println(e.getMessage());
       }
   }

    public Connection getConnection() {
        return connection;
    }

    public Statement getStatement() {
        return statement;
    }

    public static ConnectionManger getConnectionManger()  {
        try {
            if(connectionManger != null && connectionManger.getConnection().isClosed()){
                return connectionManger = new ConnectionManger();
            }else
            if(connectionManger == null ){
                synchronized (ConnectionManger.class){
                    if(connectionManger == null ){
                        return connectionManger = new ConnectionManger();
                    }
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connectionManger;
    }

    public void closeConnection()  {
       if(this.connection != null ){
           try {
               this.connection.close();
               this.statement.cancel();
           } catch (SQLException e) {
               e.printStackTrace();
           }
       }
    }
}
