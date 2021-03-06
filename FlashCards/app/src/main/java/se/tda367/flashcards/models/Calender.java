package se.tda367.flashcards.models;

/**
 * Created by Emilia on 2016-04-26.
 */

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.util.TimeZone;

public class Calender extends Calendar{

    private static int SECONDSDAY = 24 * 60 * 60;
    private Calendar today = Calendar.getInstance();
    private Calendar startDay = Calendar.getInstance();
    private long diff =  startDay.getTimeInMillis() - today.getTimeInMillis();
    private long diffSec = diff / 1000;
    private long days = diffSec / SECONDSDAY;
    private long secondsDay = diffSec % SECONDSDAY;
    private long seconds = secondsDay % 60;
    private long minutes = (secondsDay / 60) % 60;
    private long hours = (secondsDay / 3600); // % 24 not needed
    private long unixSec;

    public Calender(){

    }

    @Override
    public void add(int i, int i1) {

    }

    @Override
    protected void computeFields() {

    }

    @Override
    protected void computeTime() {

    }

    @Override
    public int getGreatestMinimum(int i) {
        return 0;
    }

    @Override
    public int getLeastMaximum(int i) {
        return 0;
    }

    @Override
    public int getMaximum(int i) {
        return 0;
    }

    @Override
    public int getMinimum(int i) {
        return 0;
    }

    @Override
    public void roll(int i, boolean b) {

    }
    public static String convertUnix(long unix){

        long unixSec = unix;
        Date date = new Date(unixSec*1000L);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT-1"));
        String stringSimpleDate = simpleDateFormat.format(date);
        return stringSimpleDate;
    }

    public Calender startDay() {

        startDay.setTime(today.getTime()); /* reset */
        startDay.set(Calendar.DAY_OF_MONTH, today.DAY_OF_MONTH);
        startDay.set(Calendar.MONTH, today.MONTH); // 0-11 so 1 less
        startDay.set(Calendar.YEAR, today.YEAR);

        Calender startDay = (Calender) startDay();
        return startDay;
    }
    public long daysSince() {
        return days;
    }
    public long minutesSince() {
        return minutes;
    }
    public long secondsSince() {
        return seconds;
    }
    public long hoursSince() {
        return hours;
    }
    public String sinceMade() {
        return "" + days + " dagar " + hours + " timmar " + minutes + " minuter " + seconds +" since made";
    }

    public String sincePlayed() {
        return "" + days + " dagar " + hours + " timmar " + minutes + " minuter " + seconds +" since played";
}   }