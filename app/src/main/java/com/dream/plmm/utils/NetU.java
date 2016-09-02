package com.dream.plmm.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

/**
 * Created by likun on 15/12/2.
 */
public class NetU {


    /**
     * Is network available
     *
     * @return true or false
     */
    public static boolean isNetworkAvailable(Context context) {
        try {
                ConnectivityManager cManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo info = cManager.getActiveNetworkInfo();
                if (info != null && info.isAvailable()) {
                    return true;
                } else {

                    return false;
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Determine the current network type
     *
     * @return boolean
     */
    public static boolean isNetworkTypeWifi(Context context) {
        try {
                ConnectivityManager cManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo info = cManager.getActiveNetworkInfo();

                if (info != null && info.isAvailable() && info.getTypeName().equals("WIFI")) {
                    return true;
                } else {
                    return false;
                }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * get current network type, ps:3G 2G
     *
     * @return String
     */
    public static String getNetworkType(Context context) {

        if (context == null) return "";
        ConnectivityManager conn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conn == null) {
            return "none";
        }
        try {

                NetworkInfo network = conn.getActiveNetworkInfo();
                if (network == null || !network.isAvailable()) {
                    return "none";
                }
                int type = network.getType();
                if (ConnectivityManager.TYPE_WIFI == type) {
                    return "wifi";
                }
                if (ConnectivityManager.TYPE_MOBILE == type) {
                    String wap = "";
                    return (isFastMobileNetwork(context) ? "3G" : "2G") + wap;
                }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * Get the current networking
     *
     * @return WIFI or MOBILE
     */
    private static boolean isFastMobileNetwork(Context context) {
        try {
            TelephonyManager phone = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (phone == null) {
                return false;
            }

            switch (phone.getNetworkType()) {
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                    return false; // ~ 50-100 kbps
                case TelephonyManager.NETWORK_TYPE_CDMA:
                    return false; // ~ 14-64 kbps
                case TelephonyManager.NETWORK_TYPE_EDGE:
                    return false; // ~ 50-100 kbps
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    return true; // ~ 400-1000 kbps
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    return true; // ~ 600-1400 kbps
                case TelephonyManager.NETWORK_TYPE_GPRS:
                    return false; // ~ 100 kbps
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                    return true; // ~ 2-14 Mbps
                case TelephonyManager.NETWORK_TYPE_HSPA:
                    return true; // ~ 700-1700 kbps
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                    return true; // ~ 1-23 Mbps
                case TelephonyManager.NETWORK_TYPE_UMTS:
                    return true; // ~ 400-7000 kbps
                case 14: // TelephonyManager.NETWORK_TYPE_EHRPD:
                    return true; // ~ 1-2 Mbps
                case 12: // TelephonyManager.NETWORK_TYPE_EVDO_B:
                    return true; // ~ 5 Mbps
                case 15: // TelephonyManager.NETWORK_TYPE_HSPAP:
                    return true; // ~ 10-20 Mbps
                case TelephonyManager.NETWORK_TYPE_IDEN:
                    return false; // ~25 kbps
                case 13: // TelephonyManager.NETWORK_TYPE_LTE:
                    return true; // ~ 10+ Mbps
                case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                    return false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }


}
