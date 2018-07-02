package com.cloudvision.utp.quieroentradas.helpers.utils;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Gustavo Ramos M. on 12/11/2017.
 */

public class FormatDate {

    public static SimpleDateFormat getDateFormatAmerican() {
        return new SimpleDateFormat("MM/dd/yyyy");
    }

    /*public static SimpleDateFormat getDateFormatLocale() {
        return new SimpleDateFormat("dd/MM/yyyy");
    }*/

    public static SimpleDateFormat getDateFormatAmericanUntilSeconds() {
        return new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
    }

    public static SimpleDateFormat getDateFormatLocaleUntilSeconds() {
        return new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
    }
}