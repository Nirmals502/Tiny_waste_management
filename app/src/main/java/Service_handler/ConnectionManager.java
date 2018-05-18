package Service_handler;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectionManager {
    /**
     * Detecting whether device connected to internet or not     *     * @return in boolean value the internet connection status of device
     */
    public static boolean isConnected(Context context) {        /* Getting system service for getting the device internet connection information */
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {            /**             * Detecting all types of internet connections             */
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)                /**                 * checking if any network type is in connected state                 */
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;                                            /* return true if device found connected to internet */
                    }
        }
        return false;                                                           /* Return false if device is not connected to any type of internet */
    }
}