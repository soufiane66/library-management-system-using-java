package com.gama.library.persistence.daos;

import com.gama.library.entities.BookEntity;
import com.gama.library.util.ConnectionManger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class BookDaoImp implements  BookDao{


    private  final ConnectionManger connectionManger = ConnectionManger.getConnectionManger();


    private final String TABLE_NAME = "books";
    private final String ID = "id";
    private final String TITLE = "title";
    private final String AUTHOR = "author";
    private final String PUBLISHER = "publisher";
    private final String PRICE = "price";
    private final String AVAILABLE = "available";


    //this methode get a list of items by condition (predicate);
    @Override
    public List<BookEntity> findAllByCondition(Predicate<BookEntity> predicate) {
        List<BookEntity> list = findAll();
        list = list.stream().filter(predicate).collect(Collectors.toList());
        return list;
    }

    @Override
    public boolean save(BookEntity entity) {
        Connection connection = ConnectionManger.getConnectionManger().getConnection();
        String query = "insert into "+TABLE_NAME+ " ("+TITLE+","+AUTHOR+","+PUBLISHER+","+PRICE+") values (?,?,?,?);";
        try(connection) {
            PreparedStatement
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,entity.getTitle());
            preparedStatement.setString(2,entity.getAuthor());
            preparedStatement.setString(3,entity.getPublisher());
            preparedStatement.setDouble(4,entity.getPrice());
            int rows = preparedStatement.executeUpdate();
            if(rows != 0){
                System.out.println(rows+"row(s) saved");
                return true;
            }else {
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
          connectionManger.closeConnection();
            System.out.println("Connection closed");
        }
        return false;
    }


    @Override
    public boolean isExistsByName(String title) {
        String query = String.format("SELECT * FROM %s WHERE %s = ?",TABLE_NAME,TITLE );

        try {
            Connection connection = ConnectionManger.getConnectionManger().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,title);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            connectionManger.closeConnection();
        }
        return false;
    }

    @Override
    public Optional<BookEntity> findById(Integer id) {
        String query = "SELECT * FROM "+TABLE_NAME+ " WHERE id = ? ;";
        try {
            Connection connection = ConnectionManger.getConnectionManger().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return Optional.of(new BookEntity(resultSet.getInt(ID), resultSet.getString(TITLE),
                        resultSet.getString(AUTHOR),resultSet.getString(PUBLISHER),resultSet.getDouble(PRICE),resultSet.getBoolean(AVAILABLE)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            connectionManger.closeConnection();
        }
        return Optional.empty();
    }

    @Override
    public List<BookEntity> findAll() {
        String query = "SELECT * FROM "+TABLE_NAME;
        Connection connection = ConnectionManger.getConnectionManger().getConnection();
        if(connection != null) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();
                List<BookEntity> list = new ArrayList<>();
                while (resultSet.next()) {
                    list.add(new BookEntity(resultSet.getInt(ID), resultSet.getString(TITLE), resultSet.getString(AUTHOR),
                            resultSet.getString(PUBLISHER),resultSet.getDouble(PRICE),resultSet.getBoolean(AVAILABLE)));
                }
                return list;
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                connectionManger.closeConnection();
                System.out.println("Connection closed");
            }
        }
        System.out.println("Connection is null");
        return null;
    }

    @Override
    public boolean update(BookEntity entity, Integer id) {

        String query = String.format("UPDATE %s SET %s = ?, %s = ? %s = ?, %s = ?, %s = ? WHERE id = ?",TABLE_NAME,TITLE,AUTHOR,PUBLISHER,PRICE,AVAILABLE);
        try {
            Connection connection = ConnectionManger.getConnectionManger().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,entity.getTitle());
            preparedStatement.setString(2,entity.getAuthor());
            preparedStatement.setString(3,entity.getPublisher());
            preparedStatement.setDouble(4,entity.getPrice());
            preparedStatement.setBoolean(5,entity.isAvailable());
            preparedStatement.setInt(6,id);
            int rows = preparedStatement.executeUpdate();
            if(rows != 0){
                System.out.println(rows+" row(s) updated");
                return true;
            }else {
                System.out.println(rows+" row(s) updated");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            connectionManger.closeConnection();
        }
        return false;
    }


    @Override
    public boolean delete(Integer id) {
        String query = "DELETE FROM "+TABLE_NAME+ " WHERE id = ?";
        try {
            Connection connection = ConnectionManger.getConnectionManger().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            int rows = preparedStatement.executeUpdate();
            if(rows != 0){
                System.out.println(rows+" row(s) deleted");
                return true;
            }else {
                System.out.println(rows+" row(s) deleted");
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            connectionManger.closeConnection();
        }
        return false;


    }
}
