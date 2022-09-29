package com.gama.library.models;

import java.util.Date;

public class Issue {

    private int bookID;
    private int memberID;
    private Date date;
    private int renew_count;

    public Issue(int bookID, int memberID, Date date, int renew_count) {
        this.bookID = bookID;
        this.memberID = memberID;
        this.date = date;
        this.renew_count = renew_count;
    }

    public Issue(int bookID, int memberID) {
        this.bookID = bookID;
        this.memberID = memberID;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public int getMemberID() {
        return memberID;
    }

    public void setMemberID(int memberID) {
        this.memberID = memberID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getRenew_count() {
        return renew_count;
    }

    public void setRenew_count(int renew_count) {
        this.renew_count = renew_count;
    }

    @Override
    public String toString() {
        return "Issue{" +
                "bookID=" + bookID +
                ", memberID=" + memberID +
                ", date=" + date +
                ", renew_count=" + renew_count +
                '}';
    }
}
