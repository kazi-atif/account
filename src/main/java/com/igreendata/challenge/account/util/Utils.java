package com.igreendata.challenge.account.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Utils {

    public static String convertCalendarToString(Calendar cal){
        Date date = cal.getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
        String strDate = dateFormat.format(date);
        return strDate;
    }

    public static Calendar convertStringToCalendar(String strDate) throws ParseException {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy", Locale.ENGLISH);
        cal.setTime(sdf.parse(strDate));
        return cal;
    }

}
