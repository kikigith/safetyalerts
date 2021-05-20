package com.safetynet.utils;

import java.time.LocalDate;
import java.time.Period;

public class Utils {
    public static int calculateAge(LocalDate bdate) {
        LocalDate today = LocalDate.now();
        LocalDate bDat = LocalDate.of(bdate.getYear(), bdate.getMonth(), bdate.getDayOfMonth());
        Period period = Period.between(bDat, today);
        int age = today.getYear()- bdate.getYear();
        return age;
        //return period.getYears();
    }
}
