package com.gama.library.persistence.daos;

import com.gama.library.entities.MemberEntity;
import com.gama.library.util.ConnectionManger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MemberDaoImp implements  MemberDao{


    private  final ConnectionManger connectionManger = ConnectionManger.getConnectionManger();


    private final String TABLE_NAME = "members";
    private final String ID = "id";
    private final String NAME = "name";
    private final String MOBILE = "mobile";
    private final String EMAIL = "email";



    //this methode get a list of items by condition (predicate);
    @Override
    public List<MemberEntity> findAllByCondition(Predicate<MemberEntity> predicate) {
        List<MemberEntity> list = findAll();
        list = list.stream().filter(predicate).collect(Collectors.toList());
        return list;
    }

    @Override
    public boolean save(MemberEntity entity) {

        String query = "insert into "+TABLE_NAME+ " ("+NAME+","+MOBILE+","+EMAIL+") values (?,?,?);";
        Connection connection = connectionManger.getConnection();
        try(connection) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,entity.getName());
            preparedStatement.setString(2,entity.getMobile());
            preparedStatement.setString(3,entity.getEmail());

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
    public boolean isExistsByName(String name) {
        String query = String.format("SELECT * FROM %s WHERE %s = ?",TABLE_NAME,NAME );

        try {
            Connection connection = connectionManger.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,name);
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
    public Optional<MemberEntity> findById(Integer id) {
        String query = "SELECT * FROM "+TABLE_NAME+ " WHERE id = ? ;";
        try {
            Connection connection = connectionManger.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return Optional.of(new MemberEntity(resultSet.getInt(ID), resultSet.getString(NAME),resultSet.getString(MOBILE),resultSet.getString(EMAIL)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            connectionManger.closeConnection();
        }
        return Optional.empty();
    }

    @Override
    public List<MemberEntity> findAll() {
        String query = "SELECT * FROM "+TABLE_NAME;
        Connection connection = connectionManger.getConnection();
        if(connection != null) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();
                List<MemberEntity> list = new ArrayList<>();
                while (resultSet.next()) {
                    list.add(new MemberEntity(resultSet.getInt(ID), resultSet.getString(NAME), resultSet.getString(MOBILE),resultSet.getString(EMAIL)));
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
    public boolean update(MemberEntity entity, Integer id) {

        String query = String.format("UPDATE %s SET %s = ?, %s = ? %s = ? WHERE id = ?",TABLE_NAME,NAME,MOBILE,EMAIL);
        try {
            Connection connection = connectionManger.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,entity.getName());
            preparedStatement.setString(2,entity.getMobile());
            preparedStatement.setString(3,entity.getEmail());
            preparedStatement.setInt(4,id);
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
            Connection connection = connectionManger.getConnection();
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
