package com.gama.library.entities;

public class BookEntity {

    private int id;
    private String title;
    private String author;
    private String publisher;
    private Double price;
    private boolean isAvailable;


    public BookEntity(int id, String title, String author, String publisher, Double price, boolean isAvailable) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.price = price;
        this.isAvailable = isAvailable;
    }

    public BookEntity(String title, String author, String publisher, Double price, boolean isAvailable) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.price = price;
        this.isAvailable = isAvailable;
    }


    @Override
    public String toString() {
        return "BookEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", publisher='" + publisher + '\'' +
                ", price=" + price +
                ", isAvailable=" + isAvailable +
                '}';
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public BookEntity() {

    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public static BookEntityBuilder builder(){
        return  new BookEntityBuilder();
    }
}
