package com.example.autentificare.model;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class Common {
    public static User currentUser;

    public static final String INTENT_FOOD_ID = "FoodId";

    public static String convertCodeToStatus(String status) {

        if (status.equals("0"))
            return "Comanda este plasata";
        else if (status.equals("1"))
            return "Comanda este pe drum";
        else
            return "Comanda este finalizata!";
    }

    public static final String DELETE = "Delete";
    public static final String USER_KEY = "User";
    public static final String PWD_KEY = "Password";


    public static boolean isConnectedToInterner(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            return true;
        }
        else
            return false;
    }

}
