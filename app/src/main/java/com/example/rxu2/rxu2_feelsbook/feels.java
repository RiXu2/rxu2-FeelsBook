package com.example.rxu2.rxu2_feelsbook;

import java.text.SimpleDateFormat;
import java.util.Date;

public class feels {

    protected String feeling;
    protected Date date;

    public feels(){
        this.feeling = "Affection";
        this.date = new Date();
    }

    public static String check_current_date() {
        Date date = new Date();
        final SimpleDateFormat ft = new SimpleDateFormat ("dd '-' hh:mm");
        String str = ft.format(date);
        return str;
    }
    public static String check_defaultFeeling(){
        feels f = new feels();
        return f.feeling;
    }

}
