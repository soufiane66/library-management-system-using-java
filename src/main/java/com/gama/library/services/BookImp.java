package com.gama.library.services;



import com.gama.library.entities.BookEntity;
import com.gama.library.models.Book;
import com.gama.library.persistence.daos.BookDaoImp;
import com.gama.library.util.ConnectionManger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class BookImp implements Service<Book,BookEntity>{

    private final BookDaoImp daoImp = new BookDaoImp();

    private static BookImp instance;


    public static BookImp getInstance() {
        if(instance == null){
            return instance = new BookImp();
        }
        return instance;
    }

    @Override
    public boolean save(Book book) {
        BookEntity bookEntity = BookEntity.builder()
                .setTitle(book.getTitle())
                .setAuthor(book.getAuthor())
                .setPublisher(book.getPublisher())
                .setPrice(book.getPrice())
                .Build();
        return daoImp.save(bookEntity);
    }

    @Override
    public boolean isExists(String title) {
        return daoImp.isExistsByName(title);
    }

    @Override
    public Optional<Book> findById(int id) {
        Optional<BookEntity> bookEntityOptional = daoImp.findById(id);
        if(bookEntityOptional.isPresent()){
            BookEntity bookEntity = bookEntityOptional.get();

            return Optional.of(new Book(bookEntity.getId(),bookEntity.getTitle(), bookEntity.getAuthor(), bookEntity.getPublisher(),
                    bookEntity.getPrice(),bookEntity.isAvailable()));

        }

        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        List<BookEntity> list = daoImp.findAll();
        List<Book> books = new ArrayList<>();
        if(list != null){
            for (BookEntity bookEntity : list) {
                books.add(new Book(bookEntity.getId(),bookEntity.getTitle(),bookEntity.getAuthor(),bookEntity.getPublisher(), bookEntity.getPrice(),bookEntity.isAvailable()));
            }
            return books;
        }
        return null;
    }

    @Override
    public boolean update(Book book, int id) {
        BookEntity bookEntity = BookEntity.builder()
                .setTitle(book.getTitle())
                .setPrice(book.getPrice())
                .Build();
       return daoImp.update(bookEntity,id);

    }
    public boolean update(String columnName, String value,int id) {

        String query = String.format("UPDATE %s SET %s = ? WHERE id = ?","books",columnName);
        try {
            ConnectionManger connectionManger = ConnectionManger.getConnectionManger();
            Connection connection = connectionManger.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setBoolean(1,Boolean.parseBoolean(value));
            preparedStatement.setInt(2,id);
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
            ConnectionManger.getConnectionManger().closeConnection();
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        return daoImp.delete(id);
    }



    @Override
    public List<Book> findAllByCondition(Predicate<BookEntity> predicate) {
        List<BookEntity> bookEntityList = daoImp.findAllByCondition(predicate);
        return null;
    }


}
