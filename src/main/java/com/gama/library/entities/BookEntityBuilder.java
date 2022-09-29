package com.gama.library.entities;

public class BookEntityBuilder {

    private int id;
    private String title;
    private String author;
    private String publisher;
    private Double price;
    private boolean isAvailable;



    public BookEntityBuilder setId(int id){
        this.id = id;
        return this;
    }

    public BookEntityBuilder setTitle(String title){
        this.title = title;
        return this;
    }

    public BookEntityBuilder setAuthor(String author) {
        this.author = author;
        return this;
    }

    public BookEntityBuilder setPublisher(String publisher) {
        this.publisher = publisher;
        return this;
    }

    public BookEntityBuilder setPrice(Double price){
        this.price = price;
        return this;
    }

    public BookEntityBuilder setAvailable(boolean available) {
        isAvailable = available;
        return this;
    }

    public  BookEntity Build(){
        return new BookEntity(title,author,publisher,price,isAvailable);
    }
}
