package com.example.admin.mysecazadshushil;

public class AddHolidays {
    private String id;
    String holidays_date;
    String holidays_month;
    String holidyas_tittle;
    String holidyas_dur_text;
    String holidyas_dur_day;
    public AddHolidays()
    {

    }

    public AddHolidays(String id, String holidays_date, String holidays_month, String holidyas_tittle, String holidyas_dur_text, String holidyas_dur_day)
    {
        this.id=id;
        this.holidays_date=holidays_date;
        this.holidays_month=holidays_month;
        this.holidyas_tittle=holidyas_tittle;
        this.holidyas_dur_text=holidyas_dur_text;
        this.holidyas_dur_day=holidyas_dur_day;
    }
}
