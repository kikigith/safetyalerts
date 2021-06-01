package com.safetynet.utils;

import java.time.LocalDate;
import java.time.Period;

public class Utils {
    public static int calculateAge(LocalDate bdate) {
        LocalDate today = LocalDate.now();
        int age = today.getYear()- bdate.getYear();
        return age;
    }
}
