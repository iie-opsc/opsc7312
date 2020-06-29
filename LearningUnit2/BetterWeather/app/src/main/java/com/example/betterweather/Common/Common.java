package com.example.betterweather.Common;

import android.location.Location;

import java.text.SimpleDateFormat;
import java.util.Date;

public  class Common
{
    public static final String APP_ID="298604f23af307000d66a147aa6f7071";
    public static Location currentLocation;

    public static String convertTime(int dt)
    {
        Date date = new Date(dt*1000L);
        SimpleDateFormat sdf =  new SimpleDateFormat("HH:mm EEEE MM yyyy");
        String formattedDate = sdf.format(date);
        return formattedDate;

    }

}
