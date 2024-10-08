package com.netshiftdigital.dhhpodcast.utils;

import org.jetbrains.annotations.Contract;

public class Constants {
    public static final String PAYSTACK_TRANSACTION_INITIALIZER ="https://api.paystack.co/transaction/initialize";

    public static final String PAGENO = "0";
    public static final String PAGESIZE = "10";

    @Contract(value = "_, null -> false", pure = true)
    public static boolean validatePassword(String password, String cpassword) {
        return password.equals(cpassword);
    }

}
