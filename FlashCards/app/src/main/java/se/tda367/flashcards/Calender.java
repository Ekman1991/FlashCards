package se.tda367.flashcards;

/**
 * Created by Emilia on 2016-04-26.
 */

import java.util.Date;
import java.util.Calendar;
public class Calender {
    public static int SECONDSDAY = 24 * 60 * 60;
    public static void main(String[] args) {
        Calendar startDay = Calendar.getInstance();
        startDay.setTime(new Date(0)); /* reset */
        startDay.set(Calendar.DAY_OF_MONTH,1);
        startDay.set(Calendar.MONTH,0); // 0-11 so 1 less
        startDay.set(Calendar.YEAR, 2014);

        Calendar today = Calendar.getInstance();
        long diff =  startDay.getTimeInMillis() - today.getTimeInMillis();
        long diffSec = diff / 1000;

        long days = diffSec / SECONDSDAY;
        long secondsDay = diffSec % SECONDSDAY;
        long seconds = secondsDay % 60;
        long minutes = (secondsDay / 60) % 60;
        long hours = (secondsDay / 3600); // % 24 not needed

        System.out.printf("%d days, %d hours, %d minutes and %d seconds\n", days, hours, minutes, seconds);
    }
}