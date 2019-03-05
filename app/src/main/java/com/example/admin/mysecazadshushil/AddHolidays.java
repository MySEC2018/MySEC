package com.example.admin.mysecazadshushil;

public class AddHolidays {
    public String h_month;
    public String h_date;
    public String h_tittle;
    public String  h_dur_day;
    public String h_dur_text;
    public AddHolidays()
    {

    }
    public String getH_month() {
        return h_month;
    }

    public void setH_month(String h_month) {
        this.h_month = h_month;
    }

    public String getH_date() {
        return h_date;
    }

    public void setH_date(String h_date) {
        this.h_date = h_date;
    }

    public String getH_tittle() {
        return h_tittle;
    }

    public void setH_tittle(String h_tittle) {
        this.h_tittle = h_tittle;
    }

    public String getH_dur_day() {
        return h_dur_day;
    }

    public void setH_dur_day(String h_dur_day) {
        this.h_dur_day = h_dur_day;
    }

    public String getH_dur_text() {
        return h_dur_text;
    }

    public void setH_dur_text(String h_dur_text) {
        this.h_dur_text = h_dur_text;
    }
}
//setter/getter field name should exactly same with database field name