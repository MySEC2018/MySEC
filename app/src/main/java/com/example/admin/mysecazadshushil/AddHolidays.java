package com.example.admin.mysecazadshushil;

public class AddHolidays {
    String holidays_date;
    String holidays_month;
    String holidyas_tittle;
    String holidyas_dur_text;
    String holidyas_dur_day;
    public AddHolidays()
    {

    }
    public AddHolidays(String holidays_date, String holidays_month, String holidyas_tittle, String holidyas_dur_text, String holidyas_dur_day)
    {
        this.holidays_date=holidays_date;
        this.holidays_month=holidays_month;
        this.holidyas_tittle=holidyas_tittle;
        this.holidyas_dur_text=holidyas_dur_text;
        this.holidyas_dur_day=holidyas_dur_day;
    }
    public String getHolidays_date() {
        return holidays_date;
    }

    public void setHolidays_date(String holidays_date) {
        this.holidays_date = holidays_date;
    }

    public String getHolidays_month() {
        return holidays_month;
    }

    public void setHolidays_month(String holidays_month) {
        this.holidays_month = holidays_month;
    }

    public String getHolidyas_tittle() {
        return holidyas_tittle;
    }

    public void setHolidyas_tittle(String holidyas_tittle) {
        this.holidyas_tittle = holidyas_tittle;
    }

    public String getHolidyas_dur_text() {
        return holidyas_dur_text;
    }

    public void setHolidyas_dur_text(String holidyas_dur_text) {
        this.holidyas_dur_text = holidyas_dur_text;
    }

    public String getHolidyas_dur_day() {
        return holidyas_dur_day;
    }

    public void setHolidyas_dur_day(String holidyas_dur_day) {
        this.holidyas_dur_day = holidyas_dur_day;
    }
}
