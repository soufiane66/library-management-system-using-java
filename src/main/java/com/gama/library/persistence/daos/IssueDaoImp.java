package com.gama.library.persistence.daos;

import com.gama.library.entities.BookEntity;
import com.gama.library.entities.IssueEntity;
import com.gama.library.util.ConnectionManger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class IssueDaoImp implements  IssueDao{


    private  final ConnectionManger connectionManger = ConnectionManger.getConnectionManger();


    private final String TABLE_NAME = "issue";
    private final String BOOK_ID = "bookID";
    private final String MEMBER_ID = "memberID";
    private final String DATE = "date";
    private final String RENEW_COUNT = "renew_count";


    //this methode get a list of items by condition (predicate);
    @Override
    public List<IssueEntity> findAllByCondition(Predicate<IssueEntity> predicate) {
        List<IssueEntity> list = findAll();
        list = list.stream().filter(predicate).collect(Collectors.toList());
        return list;
    }

    @Override
    public boolean save(IssueEntity entity) {
        Connection connection = ConnectionManger.getConnectionManger().getConnection();
        String query = "insert into "+TABLE_NAME+ " ("+BOOK_ID+","+MEMBER_ID+") values (?,?);";
        try(connection) {
            PreparedStatement
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,entity.getBookID());
            preparedStatement.setInt(2,entity.getMemberID());
//            preparedStatement.setDate(3,new Date(entity.getDate().getTime()));
//            preparedStatement.setInt(4,entity.getRenew_count());
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


    public boolean isExistsByName(String bookId) {
        String query = String.format("SELECT * FROM %s WHERE %s = ?",TABLE_NAME,BOOK_ID );

        try {
            Connection connection = ConnectionManger.getConnectionManger().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,Integer.parseInt(bookId));
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
    public Optional<IssueEntity> findById(Integer id) {
        String query = "SELECT * FROM "+TABLE_NAME+ " WHERE id = ? ;";
        try {
            Connection connection = ConnectionManger.getConnectionManger().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return Optional.of(new IssueEntity(resultSet.getInt(BOOK_ID), resultSet.getInt(MEMBER_ID),
                        resultSet.getDate(DATE),resultSet.getInt(RENEW_COUNT)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            connectionManger.closeConnection();
        }
        return Optional.empty();
    }

    @Override
    public List<IssueEntity> findAll() {
        String query = "SELECT * FROM "+TABLE_NAME;
        Connection connection = ConnectionManger.getConnectionManger().getConnection();
        if(connection != null) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();
                List<IssueEntity> list = new ArrayList<>();
                while (resultSet.next()) {
                    list.add(new IssueEntity(resultSet.getInt(BOOK_ID), resultSet.getInt(MEMBER_ID),
                            resultSet.getDate(DATE),resultSet.getInt(RENEW_COUNT)));

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
    public boolean update(IssueEntity entity, Integer id) {

        String query = String.format("UPDATE %s SET %s = ?, %s = ? %s = ?, %s = ? WHERE id = ?",TABLE_NAME,BOOK_ID,MEMBER_ID,DATE,RENEW_COUNT);
        try {
            Connection connection = ConnectionManger.getConnectionManger().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,entity.getBookID());
            preparedStatement.setInt(2,entity.getMemberID());
            preparedStatement.setDate(3,new Date(entity.getDate().getTime()));
            preparedStatement.setDouble(4,entity.getRenew_count());
            preparedStatement.setInt(5,id);
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
