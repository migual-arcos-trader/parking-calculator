package com.paymeter.parking_calculator.application.helpers;

public class FormatsHelper {

    public static final int FORMAT_PRICE_BASE = 100;

    public static String formatPrice(double price) {
        int amountInCents = (int) Math.round(price * FORMAT_PRICE_BASE);
        int euros = amountInCents / FORMAT_PRICE_BASE;
        int cents = amountInCents % FORMAT_PRICE_BASE;
        return String.format("%d.%02d EUR", euros, cents);
    }

}
