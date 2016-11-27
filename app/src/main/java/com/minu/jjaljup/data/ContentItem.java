package com.minu.jjaljup.data;

public class ContentItem {
    private boolean check;
    private String no;
    private String date;
    private String writer;
    private String subject;

    public ContentItem(String no, String date, String writer, String subject) {
        this.check = false;
        this.no = no;
        this.date = date;
        this.writer = writer;
        this.subject = subject;
    }

    public boolean isCheck() {
        return check;
    }

    public String getNo() {
        return no;
    }

    public String getDate() {
        return date;
    }

    public String getWriter() {
        return writer;
    }

    public String getSubject() {
        return subject;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}
