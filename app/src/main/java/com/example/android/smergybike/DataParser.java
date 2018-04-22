//package com.example.android.smergybike;
//
//import android.util.Log;
//
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
///**
// * Created by Gebruiker on 17/04/2018.
// */
//
//public class DataParser {
//
//    public double speed;
//    public double rpm;
//    public double power;
//
//    public void parseGPX(String data) {
//        speed.clear();
//        rpm.clear();
//        power.clear();
//
//
//        String patternString1 = "(lat=\")(.+?)(\")";
//        String patternString2 = "(lon=\")(.+?)(\")";
//        String patternString3 = "(<ele>)(.+?)(</ele>)";
//
//        Pattern pattern1 = Pattern.compile(patternString1);
//        Pattern pattern2 = Pattern.compile(patternString2);
//        Pattern pattern3 = Pattern.compile(patternString3);
//
//        Matcher matcher1 = pattern1.matcher(data);
//        Matcher matcher2 = pattern2.matcher(data);
//        Matcher matcher3 = pattern3.matcher(data);
//
//        while (matcher1.find()) {
//            speed.add(Double.parseDouble(matcher1.group(2)));
//        }
//        while (matcher2.find()) {
//            rpm.add(Double.parseDouble(matcher2.group(2)));
//        }
//        while (matcher3.find()) {
//            power.add(Double.parseDouble(matcher3.group(2)));
//        }
//
//        //Log.i("Size: ", ""+ lat.size());
//        Log.d("Lat " + routeID, lat.toString());
//        Log.d("Lon " + routeID, lon.toString());
//    }
//
//}
