package mobile.tiny_waste_management.service;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Service_handler.SERVER;
import Service_handler.ServiceHandler;
import mobile.tiny_waste_management.Home_screen;
import mobile.tiny_waste_management.Login_screen;
import mobile.tiny_waste_management.app.Config;


/**
 * Created by Ravi Tamada on 08/08/16.
 * www.androidhive.info
 */
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = MyFirebaseInstanceIDService.class.getSimpleName();
    private ProgressDialog pDialog;
    String Device_id = "";
    String status = "", Message;
    String Acess_tocken;
    TextView Txt_show, Register_now;
    EditText Edt_txt_username, Edt_password;
    String str_email = "", Str_password = "", Driver_id = "", String_device_tocken = "";

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        // Saving reg id to shared preferences
        storeRegIdInPref(refreshedToken);

        // sending reg id to your server


        sendRegistrationToServer(refreshedToken);

        // Notify UI that registration has completed, so the progress indicator can be hidden.
        Intent registrationComplete = new Intent(Config.REGISTRATION_COMPLETE);
        registrationComplete.putExtra("token", refreshedToken);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }

    private void sendRegistrationToServer(final String token) {
        // sending gcm token to server
        String_device_tocken = token;
        final SharedPreferences shared = getSharedPreferences("Tidy_waste_management", MODE_PRIVATE);
        str_email = (shared.getString("UserName", "nodata"));
        Str_password = (shared.getString("Str_password", "nodata"));
        Device_id = (shared.getString("DeviceId", "nodata"));
        new Login().execute();

        Log.e(TAG, "sendRegistrationToServer: " + token);
    }

    private void storeRegIdInPref(String token) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("regId", token);
        editor.commit();
    }

    private class Login extends AsyncTask<Void, String, Void> implements DialogInterface.OnCancelListener {


        JSONObject jsonnode, json_User;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // mProgressHUD = ProgressHUD.show(Login_Screen.this, "Connecting", true, true, this);
            // Showing progress dialog
            pDialog = new ProgressDialog(MyFirebaseInstanceIDService.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();


        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            publishProgress("Please wait...");


            ServiceHandler sh = new ServiceHandler();
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(7);
            //nameValuePairs.add(new BasicNameValuePair("email", email_fb));
            nameValuePairs.add(new BasicNameValuePair("UserName", str_email));
            nameValuePairs.add(new BasicNameValuePair("Password", Str_password));
            nameValuePairs.add(new BasicNameValuePair("DeviceId", Device_id));
            nameValuePairs.add(new BasicNameValuePair("DeviceToken", String_device_tocken));
            // String_device_tocken
            nameValuePairs.add(new BasicNameValuePair("DeviceType", "Android"));


            String jsonStr = sh.makeServiceCall(SERVER.LOGIN,
                    ServiceHandler.POST, nameValuePairs);

            Log.d("Response: ", "> " + jsonStr);

            if (jsonStr != null) {
                JSONObject jsonObj = null;
                try {
                    jsonObj = new JSONObject(jsonStr);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // Getting JSON Array node
                // JSONArray array1 = null;
                try {
                    status = jsonObj.getString("Status");
                    Acess_tocken = jsonObj.getString("Token");
                    String Data = jsonObj.getString("Data");
                    JSONObject jsonObj_data = null;
                    try {
                        jsonObj_data = new JSONObject(Data);
                        String Driver = jsonObj_data.getString("Driver");
                        JSONObject jsonObj_driver = null;
                        try {
                            jsonObj_driver = new JSONObject(Driver);
                            Driver_id = jsonObj_driver.getString("DriverID");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            pDialog.dismiss();
            if (!status.contentEquals("")) {
                SharedPreferences shared = getSharedPreferences("Tidy_waste_management", MODE_PRIVATE);
                SharedPreferences.Editor editor = shared.edit();
                editor.putString("Acess_tocken", Acess_tocken);
                editor.putString("login_status", "loged_in");
                editor.putString("Driver_id", Driver_id);
                editor.putString("Check_screen", "Login");
                editor.putString("UserName", str_email);
                editor.putString("Password", Str_password);
                editor.putString("DeviceId", Device_id);

                editor.commit();
                Intent i1 = new Intent(MyFirebaseInstanceIDService.this, Home_screen.class);
                startActivity(i1);
                //finish();
            } else if (status.contentEquals("")) {
                Toast.makeText(MyFirebaseInstanceIDService.this, "Invalid Username or Password", Toast.LENGTH_LONG).show();
            }

        }

        @Override
        public void onCancel(DialogInterface dialog) {

        }
    }
}

