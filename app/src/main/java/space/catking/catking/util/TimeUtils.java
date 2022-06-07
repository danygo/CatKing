package space.catking.catking.util;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TimeUtils {
    @SuppressLint("SimpleDateFormat")
    public static final SimpleDateFormat DATE_FORMAT_DD_MM_YYYY_HH_MM_SS = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.US);

    @SuppressLint("SimpleDateFormat")
    public static final SimpleDateFormat DATE_FORMAT_D_MMM_YYYY_HH_MM = new SimpleDateFormat("d MMM yyyy HH:mm", Locale.US);

    public static String convertDate(String date) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(DATE_FORMAT_DD_MM_YYYY_HH_MM_SS.parse(date));
            return DATE_FORMAT_D_MMM_YYYY_HH_MM.format(calendar.getTimeInMillis());
        } catch (ParseException e) {
            return StringUtils.EMPTY;
        }
    }
}
