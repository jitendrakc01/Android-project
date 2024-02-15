package com.example.journeyapp;

public class JournalModel {
    String title, description, date, purl;
JournalModel()
{

}
    public JournalModel(String title, String description, String date, String purl) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.purl = purl;
    }

    public JournalModel(String purl) {
        this.purl = purl;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPurl(String purl) {
        this.purl = purl;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public String getPurl() {
        return purl;
    }
}
