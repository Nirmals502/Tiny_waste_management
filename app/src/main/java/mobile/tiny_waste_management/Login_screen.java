package mobile.tiny_waste_management;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import Service_handler.SERVER;
import Service_handler.ServiceHandler;
import mobile.tiny_waste_management.app.Config;

public class Login_screen extends AppCompatActivity {
    Button Btn_login;
    private ProgressDialog pDialog;
    String Device_id = "";
    String status = "", Message;
    String Acess_tocken;
    TextView Txt_show, Register_now;
    EditText Edt_txt_username, Edt_password;
    String str_email = "", Str_password = "", Driver_id = "", String_device_tocken = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Btn_login = (Button) findViewById(R.id.button);
        Edt_txt_username = (EditText) findViewById(R.id.editText);
        Edt_password = (EditText) findViewById(R.id.editText2);
        Txt_show = (TextView) findViewById(R.id.textView4);
        Device_id = getDeviceId(Login_screen.this);

        final SharedPreferences shared = getSharedPreferences("Tidy_waste_management", MODE_PRIVATE);
        Acess_tocken = (shared.getString("Acess_tocken", "nodata"));
//
        if (getIntent() != null) {
//            for (String key : getIntent().getExtras().keySet()) {
            try {
                String value = getIntent().getExtras().getString("message");
//                if (value != null) {
//                    ArrayList<String> arrPackage = new ArrayList<>();
//                    Set<String> set = new HashSet<String>();
//                    SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
//                    set = pref.getStringSet("Notification_LIST", null);
////
////
//                    try {
//                        if (set== null) {
//                            set = new HashSet<String>();
//                            set.add(value);
//                        } else {
//                            set.add(value);
//                        }
//                        //set.add(value);
//                    } catch (java.lang.NullPointerException e) {
//                        e.printStackTrace();
//                        //arrPackage.add(value);
//                    }
//                    SharedPreferences.Editor editor = pref.edit();
//
//                    //set.addAll(arrPackage);
//                    editor.putStringSet("Notification_LIST", set);
//                    editor.apply();
//                }

                //System.out.println(".............." + value);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
//
//                if (key.equals("chirag")) {
//                    //Intent intent = new Intent(this, AnotherActivity.class);
//                    //intent.putExtra("value", value);
//                    //startActivity(intent);
//                    //finish();
//                    //txtMsg.setText(value+" :- this is notification .");
//
//                }
//
//            }
        } else

        {


        }


        if (!Acess_tocken.contentEquals("nodata"))

        {
//            SharedPreferences.Editor editor = shared.edit();
//
//            editor.putString("Check_screen", "Login");
//            editor.commit();
//                    // do some thing
            Intent i1 = new Intent(Login_screen.this, Home_screen.class);

            startActivity(i1);

            finish();
        }
//
//                }else{
//                    Intent i = new Intent(Screen_01.this, Main.class);
//                    startActivity(i);
//
//                    finish();
//                }
        Txt_show.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                if (Txt_show.getText().toString().contentEquals("Show")) {
                    Txt_show.setText("Hide");
                    Edt_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    Edt_password.setSelection(Edt_password.getText().length());
                } else if (Txt_show.getText().toString().contentEquals("Hide")) {
                    Txt_show.setText("Show");
                    Edt_password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                    Edt_password.setSelection(Edt_password.getText().length());

                }

            }
        });
        Btn_login.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                //  Intent i1 = new Intent(Login_screen.this, Home_screen.class);
                //  startActivity(i1);
                // String_device_tocken = FirebaseInstanceId.getInstance().getToken();
                SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
                String_device_tocken = pref.getString("regId", "");
                if (!String_device_tocken.contentEquals("")) {
                    if (Edt_txt_username.getText().toString().contentEquals("")) {
                        Animation anm = Shake_Animation();
                        Edt_txt_username.startAnimation(anm);
                    } else if (Edt_password.getText().toString().contentEquals("")) {
                        Animation anm = Shake_Animation();
                        Edt_password.startAnimation(anm);
                    } else {


                        str_email = Edt_txt_username.getText().toString();
                        Str_password = Edt_password.getText().toString();
                        new Login().execute();
                    }
                } else {
                    String_device_tocken = FirebaseInstanceId.getInstance().getToken();
                    if (Edt_txt_username.getText().toString().contentEquals("")) {
                        Animation anm = Shake_Animation();
                        Edt_txt_username.startAnimation(anm);
                    } else if (Edt_password.getText().toString().contentEquals("")) {
                        Animation anm = Shake_Animation();
                        Edt_password.startAnimation(anm);
                    } else {


                        str_email = Edt_txt_username.getText().toString();
                        Str_password = Edt_password.getText().toString();
                        new Login().execute();
                    }
                }
                //new Login().execute();
            }
        });
    }

    public Animation Shake_Animation() {
        Animation shake = new TranslateAnimation(0, 5, 0, 0);
        shake.setInterpolator(new CycleInterpolator(5));
        shake.setDuration(300);


        return shake;
    }


    private class Login extends AsyncTask<Void, String, Void> implements DialogInterface.OnCancelListener {


        JSONObject jsonnode, json_User;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // mProgressHUD = ProgressHUD.show(Login_Screen.this, "Connecting", true, true, this);
            // Showing progress dialog
            pDialog = new ProgressDialog(Login_screen.this);
            pDialog.setMessage(getString(R.string.please_wait));
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
                Intent i1 = new Intent(Login_screen.this, Home_screen.class);
                startActivity(i1);
                finish();
            } else if (status.contentEquals("")) {
                Toast.makeText(Login_screen.this, R.string.invalid_username_password, Toast.LENGTH_LONG).show();
            }

        }

        @Override
        public void onCancel(DialogInterface dialog) {

        }

    }

    public String getDeviceId(Context context) {
        // returns 64-bit unique string
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);

    }
}
