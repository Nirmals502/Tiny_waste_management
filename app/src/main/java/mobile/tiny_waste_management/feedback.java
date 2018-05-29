package mobile.tiny_waste_management;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kyanogen.signatureview.SignatureView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import Multipart_enttity.AndroidMultiPartEntity;
import Service_handler.APIInterface;
import Service_handler.Tidy_feedback;
import Service_handler.ApiClient;
import Service_handler.Base64;
import Service_handler.ConnectionManager;
import Service_handler.HttpHandler;
import Service_handler.ServiceHandler;
import adapter.adittional_charge_adapter;
import retrofit2.Call;
import retrofit2.Callback;

public class feedback extends AppCompatActivity {
    ImageView img_back;
    String Status = "", Job_number = "", Job_additional_charge = "", Project_site_id = "", Job_id = "";
    TextView Txt_job_number;
    CheckBox checkbox1, checkbox2, checkbox3, checkbox4;
    private ProgressDialog pDialog, pDialog2, pDialog3, pdioalog__;
    SharedPreferences shared;
    String Access_tocken = "";
    Spinner wastetype, collection_method, Spnr_driver_note;
    ArrayList<waste_value> bin_waste = new ArrayList<>();
    ArrayList<waste_value> get_driver_notes = new ArrayList<>();
    String jsonStr_for_collection_method = "";
    String[] options, array_additional;
    RadioButton radio_yes, radio_no;
    private static final int CAMERA_PHOTO = 111;
    long totalSize = 0;
    private Uri imageToUploadUri;
    String Str_img_path = "";
    ImageView Img_image, img_click_photo;
    String img_base64 = "", Strng_signedby = "", Str_signature_base_64 = "";
    Button Btn_submit;
    EditText Signedby, Edt_txt_amount_collected, Edttxt_bin;
    RatingBar rtingbar;
    String Str_rating = "", Strng_bin_number = "";
    String str_projectsite_chargeid = "", Payment_collected_method = "true", Str_collection_method = "", Str_amount_collected = "", Str_driver_notes = "", Driver_id = "", Job_stepid = "", Next_status = "", current_status = "", Str_bin = "";
    SignatureView signatureView;
    ScrollView sv;
    String str_prefix = "";
    ListView Lv_aditional_charges;
    String Str_collect_payment = "", Payment_term = "", project_site_array = "";
    TextView txt_additional_charges, Text_rating__;
    String additional_charges_string_to_send = "";
    ArrayList<String> alstring_to_send = new ArrayList<String>();
    final ArrayList<HashMap<String, String>> listt = new ArrayList<HashMap<String, String>>();
    ImageView img_signature_clear;
    TextView txt_collectionmethod;
    String str_chec_step = "";
    ConstraintLayout cs_lv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feed_back_layout);
        img_back = (ImageView) findViewById(R.id.imageView9);
        Txt_job_number = (TextView) findViewById(R.id.textView7);
        checkbox1 = (CheckBox) findViewById(R.id.checkBox2);
        checkbox2 = (CheckBox) findViewById(R.id.checkBox3);
        checkbox3 = (CheckBox) findViewById(R.id.checkBox4);
        checkbox4 = (CheckBox) findViewById(R.id.checkBox5);
        wastetype = (Spinner) findViewById(R.id.editText10);
        collection_method = (Spinner) findViewById(R.id.editText7);
        Spnr_driver_note = (Spinner) findViewById(R.id.editText5);
        radio_yes = (RadioButton) findViewById(R.id.sound);
        radio_no = (RadioButton) findViewById(R.id.vibration);
        Img_image = (ImageView) findViewById(R.id.imageView8);
        img_click_photo = (ImageView) findViewById(R.id.imageView7);
        img_signature_clear = (ImageView) findViewById(R.id.imageView10);
        Btn_submit = (Button) findViewById(R.id.button2);
        Signedby = (EditText) findViewById(R.id.editText6);
        Edt_txt_amount_collected = (EditText) findViewById(R.id.editText8);
        Edttxt_bin = (EditText) findViewById(R.id.editText11);
        rtingbar = (RatingBar) findViewById(R.id.ratingBar);
        signatureView = (SignatureView) findViewById(R.id.textView29);
        sv = (ScrollView) findViewById(R.id.sv);
        txt_additional_charges = (TextView) findViewById(R.id.textView15);
        Text_rating__ = (TextView) findViewById(R.id.textView31);
        txt_collectionmethod = (TextView) findViewById(R.id.textView33);
        Lv_aditional_charges = (ListView) findViewById(R.id.lv_aditional_charges);
        cs_lv = (ConstraintLayout) findViewById(R.id.contraint);
        shared = getSharedPreferences("Tidy_waste_management", MODE_PRIVATE);
        Access_tocken = (shared.getString("Acess_tocken", "nologin"));
        Driver_id = (shared.getString("Driver_id", "nologin"));
        rtingbar.setVisibility(View.GONE);
        Text_rating__.setVisibility(View.GONE);
        if (Build.VERSION.SDK_INT >= 24) {
            try {
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                Job_number = null;
                Job_additional_charge = null;
                Project_site_id = null;
                Job_id = null;
                Job_stepid = null;
                Next_status = null;
                current_status = null;
                Strng_bin_number = null;
                Str_collect_payment = null;
                Payment_term = null;
                project_site_array = null;
                str_chec_step = null;
            } else {
                Job_number = extras.getString("Job_number");
                Job_additional_charge = extras.getString("Job_additional_charge");
                Project_site_id = extras.getString("Project_site_id");
                Job_id = extras.getString("Jobid");
                Job_stepid = extras.getString("Job_stepid");
                Next_status = extras.getString("Next_status");
                current_status = extras.getString("current_status");
                Strng_bin_number = extras.getString("Bin_number");
                Str_collect_payment = extras.getString("Collect_payment");
                Payment_term = extras.getString("payment_term");
                project_site_array = extras.getString("Project_site");
                str_chec_step = extras.getString("Check_step");
            }
        } else {
            Job_number = (String) savedInstanceState.getSerializable("Job_number");
            Job_additional_charge = (String) savedInstanceState.getSerializable("Job_additional_charge");
            Project_site_id = (String) savedInstanceState.getSerializable("Project_site_id");
            Job_id = (String) savedInstanceState.getSerializable("Jobid");
            Job_stepid = (String) savedInstanceState.getSerializable("Job_stepid");
            Next_status = (String) savedInstanceState.getSerializable("Next_status");
            current_status = (String) savedInstanceState.getSerializable("current_status");
            Strng_bin_number = (String) savedInstanceState.getSerializable("Bin_number");
            Str_collect_payment = (String) savedInstanceState.getSerializable("Collect_payment");
            Payment_term = (String) savedInstanceState.getSerializable("payment_term");
            project_site_array = (String) savedInstanceState.getSerializable("Project_site");
            str_chec_step = (String) savedInstanceState.getSerializable("Check_step");
        }
        if (Strng_bin_number.contentEquals("null")) {
            Strng_bin_number = "";
        } else if (Strng_bin_number.contentEquals(" ")) {
            Strng_bin_number = "";
        }
        if (str_chec_step.contentEquals("2")) {
            cs_lv.setVisibility(View.GONE);
        } else {
            cs_lv.setVisibility(View.VISIBLE);
        }
        Txt_job_number.setText(Job_number);
        Edttxt_bin.setText(Strng_bin_number);
        Edttxt_bin.setSelection(Strng_bin_number.length());
        JSONArray jArr_jobs_steps = null;
        JSONArray jArr_aditional = null;
        if (Str_collect_payment.contentEquals("true")) {
            radio_yes.setChecked(true);
            radio_no.setChecked(false);
            collection_method.setVisibility(View.VISIBLE);
            txt_collectionmethod.setVisibility(View.VISIBLE);
        } else {
            collection_method.setVisibility(View.GONE);
            txt_collectionmethod.setVisibility(View.GONE);
            radio_yes.setChecked(false);
            radio_no.setChecked(true);
            //collection_method.setEnabled(false);
            Edt_txt_amount_collected.setText("0");
            Edt_txt_amount_collected.setEnabled(false);
            List<String> list = new ArrayList<String>();
            list.add("Select Collection method");


            options = list.toArray(new String[list.size()]);
            ArrayAdapter aa = new ArrayAdapter(feedback.this, android.R.layout.simple_spinner_item, options) {
                @NonNull
                @Override
                public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                    convertView = View.inflate(feedback.this, R.layout.support_simple_spinner_dropdown_item, null);
                    // get view
                    TextView tvText1 = (TextView) convertView.findViewById(android.R.id.text1);
                    tvText1.setTextColor(Color.parseColor("#0b813f"));

                    // set content
                    return super.getView(position, convertView, parent);
                }
            };
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            collection_method.setAdapter(aa);
            Payment_collected_method = "false";
        }
        try {
            //String Str_response = jsonObj.getString("data");
//                    Json_category = new JSONObject(Str_response);
//                    Str_response = Json_category.getString("categories");
            jArr_aditional = new JSONArray(Job_additional_charge);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONArray jArr_project_site_additional = null;
        JSONArray jArr_project_site_ = null;
        ArrayList<HashMap<String, String>> listt_check = new ArrayList<HashMap<String, String>>();
        try {
            //  List<String> listt = new ArrayList<String>();
            //ArrayList<HashMap<String, String>> listt = new ArrayList<HashMap<String, String>>();
            for (int count = 0; count < jArr_aditional.length(); count++) {
                JSONObject jsonObj_steps = null;

                try {
                    jsonObj_steps = jArr_aditional.getJSONObject(count);
                    String Job_adtional_charge = jsonObj_steps.getString("AdditionalCharge");
                    jsonObj_steps = new JSONObject(Job_adtional_charge);
                    Job_adtional_charge = jsonObj_steps.getString("AdditionalChargeName");
                    String Job_adtional_charge_id = jsonObj_steps.getString("AdditionalChargeId");
                    //additional_charges_string_to_send =
                    alstring_to_send.add(Job_adtional_charge_id);
                    HashMap<String, String> charges = new HashMap<String, String>();

                    // adding each child node to HashMap key => value
                    charges.put("charges_name", Job_adtional_charge);
                    listt_check.add(charges);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            additional_charges_string_to_send = TextUtils.join("| ", alstring_to_send);
        } catch (java.lang.NullPointerException e) {
            e.printStackTrace();
            Log.e("ServiceHandler", "Couldn't get any data from the url");
        }


        try {
            //String Str_response = jsonObj.getString("data");
//                    Json_category = new JSONObject(Str_response);
//                    Str_response = Json_category.getString("categories");
            JSONObject jsonObj_adittional = null;
            jsonObj_adittional = new JSONObject(project_site_array);
            String Str_project_site = jsonObj_adittional.getString("ProjectSiteAdditionalCharges");


            jArr_project_site_additional = new JSONArray(Str_project_site);
            for (int count = 0; count < jArr_project_site_additional.length(); count++) {
                JSONObject jsonObj_steps = null;
                jsonObj_steps = jArr_project_site_additional.getJSONObject(count);
                String project_additional_charges = jsonObj_steps.getString("AdditionalCharge");
                //  if (!project_additional_charges.contentEquals("[]")) {
//                    jArr_project_site_ = new JSONArray(project_additional_charges);
//                    jsonObj_steps = jArr_project_site_.getJSONObject(count);
//                    String additional_charges = jsonObj_steps.getString("AdditionalCharge");
                jsonObj_steps = new JSONObject(project_additional_charges);
                String strad_name = jsonObj_steps.getString("AdditionalChargeName");
                String AdditionalChargeId = jsonObj_steps.getString("AdditionalChargeId");
                HashMap<String, String> charges = new HashMap<String, String>();

                // adding each child node to HashMap key => value
                charges.put("charges_name", strad_name);
                charges.put("ad_id", AdditionalChargeId);
                listt.add(charges);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //   }
        adittional_charge_adapter adapter = new adittional_charge_adapter(feedback.this,
                listt, listt_check
        );
        Lv_aditional_charges.setAdapter(adapter);
        img_signature_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signatureView.clearCanvas();
                Signedby.setEnabled(false);
                rtingbar.setEnabled(false);
                rtingbar.setRating(0.0f);
                Signedby.setText("");
                rtingbar.setVisibility(View.GONE);
                Text_rating__.setVisibility(View.GONE);
                Signedby.setFocusable(false);
                Str_signature_base_64 = "";
            }
        });

        Lv_aditional_charges.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                //  long viewId = view.getId();
                final CheckBox ch = (CheckBox) view.findViewById(R.id.checkBox);
                if (ch.isChecked()) {
                    String str_id = listt.get(position).get("ad_id");
                    alstring_to_send.add(str_id);
//                                    String listString = alstring_to_send.stream().map(Object::toString)
//                                            .collect(Collectors.joining(", "));
                    //ch.getText();
                    additional_charges_string_to_send = TextUtils.join("| ", alstring_to_send);
                    // Toast.makeText(feedback.this, additional_charges_string_to_send, Toast.LENGTH_LONG).show();
                } else {
                    String str_id = listt.get(position).get("ad_id");
                    alstring_to_send.remove(str_id);
                    additional_charges_string_to_send = TextUtils.join("| ", alstring_to_send);
                    //    Toast.makeText(feedback.this, additional_charges_string_to_send, Toast.LENGTH_LONG).show();
                    // Toast.makeText(feedback.this,joined,Toast.LENGTH_LONG).show();
                }
//                ch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                        if (isChecked) {
//                            String str_id = listt.get(position).get("ad_id");
//                            alstring_to_send.add(str_id);
////                                    String listString = alstring_to_send.stream().map(Object::toString)
////                                            .collect(Collectors.joining(", "));
//                            //ch.getText();
//                            additional_charges_string_to_send = TextUtils.join("| ", alstring_to_send);
//                            //Toast.makeText(feedback.this,joined,Toast.LENGTH_LONG).show();
//                        } else {
//                            String str_id = listt.get(position).get("ad_id");
//                            alstring_to_send.remove(str_id);
//                            additional_charges_string_to_send = TextUtils.join("| ", alstring_to_send);
//                            // Toast.makeText(feedback.this,joined,Toast.LENGTH_LONG).show();
//                        }
//                    }
//                });
//                        if (viewId == R.id.checkBox) {
//                            Toast.makeText(feedback.this,"Clicked",Toast.LENGTH_LONG).show();
//
//                        }
            }
        });
        if (adapter.getCount() == 0) {
            txt_additional_charges.setVisibility(View.GONE);
        }
        if (adapter.getCount() == 1) {
            View item = adapter.getView(0, null, Lv_aditional_charges);
            item.measure(0, 0);
            ViewGroup.LayoutParams params = Lv_aditional_charges.getLayoutParams();
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params.height = 1 * item.getMeasuredHeight();
            Lv_aditional_charges.setLayoutParams(params);


        } else if (adapter.getCount() == 2) {
            View item = adapter.getView(0, null, Lv_aditional_charges);
            item.measure(0, 0);
            ViewGroup.LayoutParams params = Lv_aditional_charges.getLayoutParams();
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params.height = 2 * item.getMeasuredHeight();
            Lv_aditional_charges.setLayoutParams(params);


        } else if (adapter.getCount() == 3) {
            View item = adapter.getView(0, null, Lv_aditional_charges);
            item.measure(0, 0);
            ViewGroup.LayoutParams params = Lv_aditional_charges.getLayoutParams();
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params.height = 3 * item.getMeasuredHeight();
            Lv_aditional_charges.setLayoutParams(params);


        } else if (adapter.getCount() > 3) {
            View item = adapter.getView(0, null, Lv_aditional_charges);
            item.measure(0, 0);
            ViewGroup.LayoutParams params = Lv_aditional_charges.getLayoutParams();
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params.height = 3 * item.getMeasuredHeight();
            Lv_aditional_charges.setLayoutParams(params);


        }


//


        try {
            jArr_jobs_steps = new JSONArray(Job_additional_charge);
            if (jArr_jobs_steps.length() == 1) {
                checkbox1.setChecked(true);
                checkbox2.setChecked(false);
                checkbox3.setChecked(false);
                checkbox4.setChecked(false);
            } else if (jArr_jobs_steps.length() == 2) {
                checkbox1.setChecked(true);
                checkbox2.setChecked(true);
                checkbox3.setChecked(false);
                checkbox4.setChecked(false);
            } else if (jArr_jobs_steps.length() == 3) {
                checkbox1.setChecked(true);
                checkbox2.setChecked(true);
                checkbox3.setChecked(true);
                checkbox4.setChecked(false);
            } else if (jArr_jobs_steps.length() == 4) {
                checkbox1.setChecked(true);
                checkbox2.setChecked(true);
                checkbox3.setChecked(true);
                checkbox4.setChecked(true);
            }
            {

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        rtingbar.setRating(0.0f);
        // Text_rating__
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(feedback.this, Home_screen.class);

                startActivity(i1);

                finish();
            }
        });
        Signedby.setEnabled(false);
        rtingbar.setEnabled(false);
        signatureView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                // Signedby.setFocusable(false);
                Signedby.setEnabled(true);
                // Signedby.setFocusable(true);
                rtingbar.setEnabled(true);
                rtingbar.setRating(0.0f);
                if (!str_chec_step.contentEquals("2")) {
                    rtingbar.setVisibility(View.VISIBLE);
                    Text_rating__.setVisibility(View.VISIBLE);
                }

                Signedby.setFocusableInTouchMode(true);
                Signedby.setFocusable(true);

                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disable the scroll view to intercept the touch event
                        sv.requestDisallowInterceptTouchEvent(true);
                        return false;
                    case MotionEvent.ACTION_UP:
                        // Allow scroll View to interceot the touch event
                        sv.requestDisallowInterceptTouchEvent(false);
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        sv.requestDisallowInterceptTouchEvent(true);
                        return false;
                    default:
                        return true;
                }
            }
        });
        Lv_aditional_charges.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disable the scroll view to intercept the touch event
                        sv.requestDisallowInterceptTouchEvent(true);
                        return false;
                    case MotionEvent.ACTION_UP:
                        // Allow scroll View to interceot the touch event
                        sv.requestDisallowInterceptTouchEvent(false);
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        sv.requestDisallowInterceptTouchEvent(true);
                        return false;
                    default:
                        return true;
                }
            }
        });
        img_click_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean result = Utility.checkPermission(feedback.this);

                if (result = true) {
                    selectImage_new();

                    //galleryIntent();
                }

            }
        });
//String str= "abc"+"|"+"dfdf";
//        wastetype.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                str_projectsite_chargeid = bin_waste.get(position).getContact_id();
//            }
//        });
        wastetype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                str_projectsite_chargeid = bin_waste.get(position).getContact_id();
                char first = bin_waste.get(position).getContact_name().charAt(0);
                String str = String.valueOf(first);

                //if (Strng_bin_number.contentEquals("")) {
                str_prefix = str;
                //  Edttxt_bin.setText(str);
                //  Edttxt_bin.setSelection(str.length());
//                } else {
//                    str_prefix = Strng_bin_number;
//                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Edttxt_bin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //str_prefix
//                if (!s.toString().startsWith(str_prefix)) {
//                    String cleanString;
//                    String deletedPrefix = str_prefix.substring(0, str_prefix.length() - 1);
//                    if (s.toString().startsWith(deletedPrefix)) {
//                        cleanString = s.toString().replaceAll(deletedPrefix, "");
//                    } else {
//                        cleanString = s.toString().replaceAll(str_prefix, "");
//                    }
//                    Edttxt_bin.setText(str_prefix + cleanString);
//                    Edttxt_bin.setSelection(str_prefix.length());
//                }
            }
        });
        collection_method.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (options[position].contentEquals("Select Collection method")) {
                    Str_collection_method = "";
                } else {
                    Str_collection_method = options[position];
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Spnr_driver_note.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Str_driver_notes = get_driver_notes.get(position).getContact_id();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        new Bin_wate_type().execute();
        new Collection_Method().execute();
        new driver_notes().execute();
        rtingbar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Str_rating = String.valueOf(rating);
                Str_rating = Str_rating.substring(0, Str_rating.indexOf("."));
            }
        });


        radio_yes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    collection_method.setVisibility(View.VISIBLE);
                    txt_collectionmethod.setVisibility(View.VISIBLE);
                    new Collection_Method().execute();
                    Payment_collected_method = "true";
                    //collection_method.setEnabled(true);
                    Edt_txt_amount_collected.setEnabled(true);
                    Edt_txt_amount_collected.setText("");
                } else {
                    collection_method.setVisibility(View.GONE);
                    txt_collectionmethod.setVisibility(View.GONE);
                    Edt_txt_amount_collected.setText("0");
                    Edt_txt_amount_collected.setEnabled(false);
                    List<String> list = new ArrayList<String>();
                    list.add("Select Collection method");
                    options = list.toArray(new String[list.size()]);
                    ArrayAdapter aa = new ArrayAdapter(feedback.this, android.R.layout.simple_spinner_item, options) {
                        @NonNull
                        @Override
                        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                            convertView = View.inflate(feedback.this, R.layout.support_simple_spinner_dropdown_item, null);
                            // get view
                            TextView tvText1 = (TextView) convertView.findViewById(android.R.id.text1);
                            tvText1.setTextColor(Color.parseColor("#0b813f"));

                            // set content
                            return super.getView(position, convertView, parent);
                        }
                    };
                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    collection_method.setAdapter(aa);
                    Payment_collected_method = "false";
                }
            }
        });
        if (Str_collect_payment.contentEquals("true")) {
            selectValue(collection_method, Payment_term);
        }
//        Signedby.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (signatureView.isBitmapEmpty()) {
//                    Signedby.setFocusable(false);
//                   // Signedby.setEnabled(false);
//                    //Toast.makeText(feedback.this, "Signature cannot be empty", Toast.LENGTH_LONG).show();
//                    //new SUBMIT_INFO().execute();
//                    // img_base64
//                } else {
//                    Signedby.setFocusable(true);
//                   // Signedby.setEnabled(true);
//                }
//                return false;
//            }
//        });
//        rtingbar.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (signatureView.isBitmapEmpty()) {
//                    rtingbar.setEnabled(false);
//                    Str_rating = "";
//                    //Toast.makeText(feedback.this, "Signature cannot be empty", Toast.LENGTH_LONG).show();
//                    //new SUBMIT_INFO().execute();
//                    // img_base64
//                } else {
//                    rtingbar.setEnabled(true);
//                }
//                return false;
//            }
//        });
        Btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //additional_charges_string_to_send = "1|10|13";
                if (str_chec_step.contentEquals("2")) {
                    Payment_collected_method = "false";
                }

                if (Payment_collected_method.contentEquals("true")) {
                    Strng_signedby = Signedby.getText().toString();
                    Str_amount_collected = Edt_txt_amount_collected.getText().toString();
                    Str_bin = Edttxt_bin.getText().toString();
                    String Str_check = "";
                    if (!Str_bin.contentEquals("")) {
                        // String test = "StackOverflow";
                        char first = Str_bin.charAt(0);
                        Str_check = String.valueOf(first);
                    }
                    //Edttxt_bin
                    if (Str_bin.contentEquals("")) {
                        Toast.makeText(feedback.this, "Bin number cannot be empty", Toast.LENGTH_LONG).show();
                        //new SUBMIT_INFO().execute();
                    }
                    if (!str_prefix.contentEquals(Str_check)) {
                        Toast.makeText(feedback.this, "First Character of Bin number should start from " + str_prefix, Toast.LENGTH_LONG).show();
                        //new SUBMIT_INFO().execute();
                        // Str_collection_method
                    } else if (Str_collection_method.contentEquals("Select Collection method")) {
                        Toast.makeText(feedback.this, "Select Collection method", Toast.LENGTH_LONG).show();
                        //new SUBMIT_INFO().execute();
                    } else if (Str_collection_method.contentEquals("")) {
                        Toast.makeText(feedback.this, "Select Collection method", Toast.LENGTH_LONG).show();
                        //new SUBMIT_INFO().execute();
                    } else if (Str_amount_collected.contentEquals("")) {
                        Toast.makeText(feedback.this, "Amount collected cannot be empty", Toast.LENGTH_LONG).show();
                        //new SUBMIT_INFO().execute();
                    } else if (img_base64.contentEquals("")) {
                        Toast.makeText(feedback.this, "Picture cannot be empty", Toast.LENGTH_LONG).show();
                        //new SUBMIT_INFO().execute();
                        // img_base64
                    } else if (!signatureView.isBitmapEmpty()) {
                        if (Strng_signedby.contentEquals("")) {
                            Toast.makeText(feedback.this, "Signed By cannot be empty", Toast.LENGTH_LONG).show();
                            //new SUBMIT_INFO().execute();
                            // img_base64
                        } else {
                            Bitmap bitmap = signatureView.getSignatureBitmap();
                            //Str_signature_base_64 = ImageUtil.convert(bitmap);
                            Str_signature_base_64 = convertBitmapToString(bitmap);
                            if (!Str_signature_base_64.contentEquals("")) {
                                //new SUBMIT_INFO().execute();
                                ///Submit_Update();
                                //new Submit_Task().execute();
                                UPDATE_FEEDBACK();
                            }
                        }
                        // Toast.makeText(feedback.this, "Signature cannot be empty", Toast.LENGTH_LONG).show();
                        //new SUBMIT_INFO().execute();
                        // img_base64
                    } else if (signatureView.isBitmapEmpty()) {
                        if (Str_driver_notes.contentEquals("noid")) {
                            Toast.makeText(feedback.this, "Driver notes cannot be empty", Toast.LENGTH_LONG).show();
                        } else {
                            UPDATE_FEEDBACK();
                        }
                        // Toast.makeText(feedback.this, "Signature cannot be empty", Toast.LENGTH_LONG).show();
                        //new SUBMIT_INFO().execute();
                        // img_base64
                    } else {
                        // Bitmap bitmap = signatureView.getSignatureBitmap();
                        //Str_signature_base_64 = ImageUtil.convert(bitmap);
                        // Str_signature_base_64 = convertBitmapToString(bitmap);
                        // if (!Str_signature_base_64.contentEquals("")) {
                        //new SUBMIT_INFO().execute();
                        ///Submit_Update();
                        //new Submit_Task().execute();
                        UPDATE_FEEDBACK();
                        // }

                    }

                } else if (Payment_collected_method.contentEquals("false")) {
                    Strng_signedby = Signedby.getText().toString();
                    Str_amount_collected = Edt_txt_amount_collected.getText().toString();
                    Str_bin = Edttxt_bin.getText().toString();
                    String Str_check = "";
                    if (!Str_bin.contentEquals("")) {
                        // String test = "StackOverflow";
                        char first = Str_bin.charAt(0);
                        Str_check = String.valueOf(first);
                    }
                    //Edttxt_bin
                    if (Str_bin.contentEquals("")) {
                        Toast.makeText(feedback.this, "Bin number cannot be empty", Toast.LENGTH_LONG).show();
                        //new SUBMIT_INFO().execute();
                    }
                    if (!str_prefix.contentEquals(Str_check)) {
                        Toast.makeText(feedback.this, "First Chracter of Bin number should start from " + str_prefix, Toast.LENGTH_LONG).show();
                        //new SUBMIT_INFO().execute();
                    } else if (img_base64.contentEquals("")) {
                        Toast.makeText(feedback.this, "Picture cannot be empty", Toast.LENGTH_LONG).show();
                        //new SUBMIT_INFO().execute();
                        // img_base64
                    } else if (!signatureView.isBitmapEmpty()) {
                        if (Strng_signedby.contentEquals("")) {
                            Toast.makeText(feedback.this, "Signed By cannot be empty", Toast.LENGTH_LONG).show();
                            //new SUBMIT_INFO().execute();
                            // img_base64

                        } else if (str_chec_step.contentEquals("1")) {
                            // Payment_collected_method = "false";
                            if (Str_driver_notes.contentEquals("noid")) {
                                Toast.makeText(feedback.this, "Driver notes cannot be empty", Toast.LENGTH_LONG).show();
                            } else {
                                Bitmap bitmap = signatureView.getSignatureBitmap();
                                //Str_signature_base_64 = ImageUtil.convert(bitmap);
                                Str_signature_base_64 = convertBitmapToString(bitmap);
                                if (!Str_signature_base_64.contentEquals("")) {
                                    // new SUBMIT_INFO().execute();
                                    //Submit_Update();
                                    //new Submit_Task().execute();
                                    UPDATE_FEEDBACK();

                                }
                            }
                        } else {
                            Bitmap bitmap = signatureView.getSignatureBitmap();
                            //Str_signature_base_64 = ImageUtil.convert(bitmap);
                            Str_signature_base_64 = convertBitmapToString(bitmap);
                            if (!Str_signature_base_64.contentEquals("")) {
                                // new SUBMIT_INFO().execute();
                                //Submit_Update();
                                //new Submit_Task().execute();
                                UPDATE_FEEDBACK();

                            }
                        }
                        // Toast.makeText(feedback.this, "Signature cannot be empty", Toast.LENGTH_LONG).show();
                        //new SUBMIT_INFO().execute();
                        // img_base64
                    } else if (str_chec_step.contentEquals("1")) {
                        if (Str_driver_notes.contentEquals("noid")) {
                            Toast.makeText(feedback.this, "Driver notes cannot be empty", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        //  Bitmap bitmap = signatureView.getSignatureBitmap();
                        //Str_signature_base_64 = ImageUtil.convert(bitmap);
                        //  Str_signature_base_64 = convertBitmapToString(bitmap);
                        // if (!Str_signature_base_64.contentEquals("")) {
                        // new SUBMIT_INFO().execute();
                        //Submit_Update();
                        //new Submit_Task().execute();
                        UPDATE_FEEDBACK();

                        //}
                    }
                }

            }
        });
    }

    private void selectValue(Spinner spinner, String value) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).equals(value)) {
                spinner.setSelection(i);
                break;
            }
        }
    }

    private class Bin_wate_type extends AsyncTask<Void, String, Void> implements DialogInterface.OnCancelListener {


        JSONObject jsonnode, json_User;
        String jsonStr="";


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // mProgressHUD = ProgressHUD.show(Login_Screen.this, "Connecting", true, true, this);
            // Showing progress dialog
            pDialog = new ProgressDialog(feedback.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();


        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            publishProgress("Please wait...");


            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            //String url = "http://api.androidhive.info/contacts/";
             jsonStr = sh.makeServiceCall("http://112.196.3.42:8298/v1/ProjectSiteCharge/GetProjectSiteChargeIDBinWateType/" + Project_site_id, Access_tocken);

            Log.d("Response: ", "> " + jsonStr);

            if (jsonStr != null) {
                JSONObject jsonObj = null;
                JSONObject Json_category = null;

//                try {
//                    jsonObj = new JSONObject(jsonStr);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
                JSONArray jArr = null;
                try {
                    //String Str_response = jsonObj.getString("data");
//                    Json_category = new JSONObject(Str_response);
//                    Str_response = Json_category.getString("categories");
                    jArr = new JSONArray(jsonStr);
                    for (int count = 0; count < jArr.length(); count++) {
                        JSONObject jsonObjj = null;

                        jsonObjj = jArr.getJSONObject(count);
                        String Text = jsonObjj.getString("Text");
                        String Value = jsonObjj.getString("Value");
                        bin_waste.add(new waste_value(Text, Value));


                        // bin_waste.
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//

                // Getting JSON Array node
                // JSONArray array1 = null;

            }


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            pDialog.dismiss();
            if (jsonStr == null) {
                open_loginWindow();
            } else if (jsonStr.contentEquals("Invalid_Token")) {
                open_loginWindow();
            } else {
                try {
                    str_projectsite_chargeid = bin_waste.get(0).getContact_id();
                } catch (java.lang.IndexOutOfBoundsException e) {
                    e.printStackTrace();
                }

                ArrayAdapter<waste_value> adapter =
                        new ArrayAdapter<waste_value>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, bin_waste) {
                            @NonNull
                            @Override
                            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                                convertView = View.inflate(feedback.this, R.layout.support_simple_spinner_dropdown_item, null);
                                // get view
                                TextView tvText1 = (TextView) convertView.findViewById(android.R.id.text1);
                                tvText1.setTextColor(Color.parseColor("#0b813f"));

                                // set content
                                return super.getView(position, convertView, parent);
                            }
                        };
                adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

                wastetype.setAdapter(adapter);
                try {
                    char first = bin_waste.get(0).getContact_name().charAt(0);
                    String str = String.valueOf(first);

                    if (Strng_bin_number.contentEquals("")) {
                        Edttxt_bin.setText(str);
                        str_prefix = str;
                    } else {
                        //Edttxt_bin.setText(Strng_bin_number);
                        str_prefix = Strng_bin_number;
                    }
                } catch (java.lang.IndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
            }

        }

        @Override
        public void onCancel(DialogInterface dialog) {

        }
    }

    private class Collection_Method extends AsyncTask<Void, String, Void> implements DialogInterface.OnCancelListener {


        JSONObject jsonnode, json_User;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // mProgressHUD = ProgressHUD.show(Login_Screen.this, "Connecting", true, true, this);
            // Showing progress dialog
            pDialog2 = new ProgressDialog(feedback.this);
            pDialog2.setMessage("Please wait...");
            pDialog2.setCancelable(false);
            pDialog2.show();


        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            publishProgress("Please wait...");


            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            //String url = "http://api.androidhive.info/contacts/";
            jsonStr_for_collection_method = sh.makeServiceCall("http://112.196.3.42:8298/v1/Job/GetPaymentterms", Access_tocken);
            //String[] options=

            Log.d("Response: ", "> " + jsonStr_for_collection_method);

            if (jsonStr_for_collection_method != null) {
                JSONObject jsonObj = null;
                JSONObject Json_category = null;

//                try {
//                    jsonObj = new JSONObject(jsonStr);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }

                JSONArray jsonArray = null;
                try {
                    List<String> list = new ArrayList<String>();
                    //List<String> list = new ArrayList<String>();
                    list.add("Select Collection method");
                    jsonArray = new JSONArray(jsonStr_for_collection_method);
                    for (int count = 0; count < jsonArray.length(); count++) {
                        //String value = jsonArray.getString(count);
                        list.add(jsonArray.getString(count));
                        //options.
                    }
                    options = list.toArray(new String[list.size()]);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // String[] strArr = new String[jsonArray.length()];
//

                // Getting JSON Array node
                // JSONArray array1 = null;

            }


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            pDialog2.dismiss();
            //String[] str =toStringArray(jsonArray);
            if (jsonStr_for_collection_method == null) {
                open_loginWindow();
            } else if (jsonStr_for_collection_method.contentEquals("Invalid_Token")) {
                open_loginWindow();
            } else {

                ArrayAdapter aa = new ArrayAdapter(feedback.this, android.R.layout.simple_spinner_item, options) {
                    @NonNull
                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                        convertView = View.inflate(feedback.this, R.layout.support_simple_spinner_dropdown_item, null);
                        // get view
                        TextView tvText1 = (TextView) convertView.findViewById(android.R.id.text1);
                        tvText1.setTextColor(Color.parseColor("#0b813f"));

                        // set content
                        return super.getView(position, convertView, parent);
                    }
                };
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                collection_method.setAdapter(aa);
            }
            //Spinner_.setOnItemSelectedListener(this);


        }

        @Override
        public void onCancel(DialogInterface dialog) {

        }
    }

    private class driver_notes extends AsyncTask<Void, String, Void> implements DialogInterface.OnCancelListener {


        JSONObject jsonnode, json_User;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // mProgressHUD = ProgressHUD.show(Login_Screen.this, "Connecting", true, true, this);
            // Showing progress dialog
            pDialog3 = new ProgressDialog(feedback.this);
            pDialog3.setMessage("Please wait...");
            pDialog3.setCancelable(false);
            pDialog3.show();


        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            publishProgress("Please wait...");


            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            //String url = "http://api.androidhive.info/contacts/";
            jsonStr_for_collection_method = sh.makeServiceCall("http://112.196.3.42:8298/v1/DriverNote/GetAllDriverNote", Access_tocken);
            //String[] options=

            Log.d("Response: ", "> " + jsonStr_for_collection_method);

            if (jsonStr_for_collection_method != null) {
                JSONObject jsonObj = null;
                JSONObject Json_category = null;

//                try {
//                    jsonObj = new JSONObject(jsonStr);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }

                JSONArray jsonArray = null;
                try {
                    List<String> list = new ArrayList<String>();
                    jsonArray = new JSONArray(jsonStr_for_collection_method);
                    get_driver_notes.add(new waste_value("none", "noid"));
                    for (int count = 0; count < jsonArray.length(); count++) {
                        //String value = jsonArray.getString(count);
                        JSONObject jsonObjj = null;
                        String amount_tocllect = "";
                        try {
                            jsonObjj = jsonArray.getJSONObject(count);
                            String NoteID = jsonObjj.getString("NoteID");
                            String DriverNotes = jsonObjj.getString("DriverNotes");
                            get_driver_notes.add(new waste_value(DriverNotes, NoteID));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //options.
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // String[] strArr = new String[jsonArray.length()];
//

                // Getting JSON Array node
                // JSONArray array1 = null;

            }


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            pDialog3.dismiss();
            //String[] str =toStringArray(jsonArray);
            // jsonStr_for_collection_method
            if (jsonStr_for_collection_method == null) {
                open_loginWindow();
            } else if (jsonStr_for_collection_method.contentEquals("Invalid_Token")) {
                open_loginWindow();
            } else {

                ArrayAdapter aa = new ArrayAdapter(feedback.this, android.R.layout.simple_spinner_item, get_driver_notes) {
                    @NonNull
                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                        convertView = View.inflate(feedback.this, R.layout.support_simple_spinner_dropdown_item, null);
                        // get view
                        TextView tvText1 = (TextView) convertView.findViewById(android.R.id.text1);
                        tvText1.setTextColor(Color.parseColor("#0b813f"));

                        // set content
                        return super.getView(position, convertView, parent);
                    }
                };
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                Spnr_driver_note.setAdapter(aa);
            }
            //Spinner_.setOnItemSelectedListener(this);


        }

        @Override
        public void onCancel(DialogInterface dialog) {

        }
    }

    private class waste_value {
        private String contact_name;
        private String contact_id;

        public waste_value() {
        }

        public waste_value(String contact_name, String contact_id) {
            this.contact_name = contact_name;
            this.contact_id = contact_id;
        }

        public String getContact_name() {
            return contact_name;
        }

        public void setContact_name(String contact_name) {
            this.contact_name = contact_name;
        }

        public String getContact_id() {
            return contact_id;
        }

        public void setContact_id(String contact_id) {
            this.contact_id = contact_id;
        }

        /**
         * Pay attention here, you have to override the toString method as the
         * ArrayAdapter will reads the toString of the given object for the name
         *
         * @return contact_name
         */
        @Override
        public String toString() {
            return contact_name;
        }
    }

    public static String[] toStringArray(JSONArray array) {
        if (array == null)
            return null;

        String[] arr = new String[array.length()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = array.optString(i);
        }
        return arr;
    }

    private void selectImage_new() {

        final CharSequence[] options = {"Take Photo", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(feedback.this);

        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    Intent chooserIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(Environment.getExternalStorageDirectory(), "tidy_signature.jpg");
                    chooserIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    imageToUploadUri = Uri.fromFile(f);
                    startActivityForResult(chooserIntent, CAMERA_PHOTO);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
                //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.YELLOW));
            }
        });
        builder.show();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_PHOTO && resultCode == RESULT_OK) {
            if (imageToUploadUri != null) {
                try {
                    Uri selectedImage = imageToUploadUri;
                    getContentResolver().notifyChange(selectedImage, null);
                    Str_img_path = imageToUploadUri.getPath();
                    // img_base64 = getFileToByte(Str_img_path);
                    Bitmap reducedSizeBitmap = getBitmap(imageToUploadUri.getPath());

                    // Bitmap rotated_bitmap = rotateBitmap(reducedSizeBitmap, 90);
                    if (reducedSizeBitmap != null) {

                        Img_image.setImageBitmap(reducedSizeBitmap);
                        img_base64 = convertBitmapToString(reducedSizeBitmap);

                    } else {
                        Toast.makeText(this, "Error while capturing Image", Toast.LENGTH_LONG).show();
                    }
                } catch (OutOfMemoryError e) {
                    e.printStackTrace();
                    img_base64 = "";
                    Toast.makeText(this, "Memory Error while capturing Image", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "Error while capturing Image", Toast.LENGTH_LONG).show();
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private Bitmap getBitmap(String path) {

        Uri uri = Uri.fromFile(new File(path));
        InputStream in = null;
        try {
            final int IMAGE_MAX_SIZE = 1200000; // 1.2MP
            in = getContentResolver().openInputStream(uri);

            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(in, null, o);
            in.close();


            int scale = 1;
            while ((o.outWidth * o.outHeight) * (1 / Math.pow(scale, 2)) >
                    IMAGE_MAX_SIZE) {
                scale++;
            }
            Log.d("", "scale = " + scale + ", orig-width: " + o.outWidth + ", orig-height: " + o.outHeight);

            Bitmap b = null;
            in = getContentResolver().openInputStream(uri);
            if (scale > 1) {
                scale--;
                // scale to max possible inSampleSize that still yields an image
                // larger than target
                o = new BitmapFactory.Options();
                o.inSampleSize = scale;
                b = BitmapFactory.decodeStream(in, null, o);

                // resize to desired dimensions
                int height = b.getHeight();
                int width = b.getWidth();
                Log.d("", "1th scale operation dimenions - width: " + width + ", height: " + height);

                double y = Math.sqrt(IMAGE_MAX_SIZE
                        / (((double) width) / height));
                double x = (y / height) * width;

                Bitmap scaledBitmap = Bitmap.createScaledBitmap(b, (int) x,
                        (int) y, true);
                b.recycle();
                b = scaledBitmap;

                System.gc();
            } else {
                b = BitmapFactory.decodeStream(in);
            }
            in.close();

            Log.d("", "bitmap size - width: " + b.getWidth() + ", height: " +
                    b.getHeight());
            return b;
        } catch (IOException e) {
            Log.e("", e.getMessage(), e);
            return null;
        }
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, int orientation) {

        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_NORMAL:
                return bitmap;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                matrix.setScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                matrix.setRotate(180);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                matrix.setRotate(90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                matrix.setRotate(-90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(-90);
                break;
            default:
                return bitmap;
        }
        try {

            Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

//            try {
//                bitmap.recycle();
//            }catch (java.lang.RuntimeException e){
//                e.printStackTrace();
//            }
            return bmRotated;

        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }

    }

    public static String getFileToByte(String filePath) {
        Bitmap bmp = null;
        ByteArrayOutputStream bos = null;
        byte[] bt = null;
        String encodeString = null;
        try {
            bmp = BitmapFactory.decodeFile(filePath);
            bos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bt = bos.toByteArray();
            // encodeString = Base64.encodeToString(bt, Base64.DEFAULT);

            //System.out.println("Base 64....." + encodeString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encodeString;
    }

    private class SUBMIT_INFO extends AsyncTask<Void, String, Void> implements DialogInterface.OnCancelListener {


        JSONObject jsonnode, json_User;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // mProgressHUD = ProgressHUD.show(Login_Screen.this, "Connecting", true, true, this);
            // Showing progress dialog
            pDialog = new ProgressDialog(feedback.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();


        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            publishProgress("Please wait...");


            ServiceHandler sh = new ServiceHandler();
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(16);
            //nameValuePairs.add(new BasicNameValuePair("email", email_fb));
            nameValuePairs.add(new BasicNameValuePair("JobID", Job_id));
            nameValuePairs.add(new BasicNameValuePair("CustomerSignature", Str_signature_base_64));
            nameValuePairs.add(new BasicNameValuePair("PhotoFile", img_base64));
            nameValuePairs.add(new BasicNameValuePair("ProjectSiteId", Project_site_id));
            nameValuePairs.add(new BasicNameValuePair("ProjectSiteChargeID", str_projectsite_chargeid));
            nameValuePairs.add(new BasicNameValuePair("JobAdditionalCharges", Job_additional_charge));
            nameValuePairs.add(new BasicNameValuePair("SignedBy", Strng_signedby));
            nameValuePairs.add(new BasicNameValuePair("Rating", Str_rating));
            nameValuePairs.add(new BasicNameValuePair("PaymentCollected", Payment_collected_method));
            nameValuePairs.add(new BasicNameValuePair("CollectionMethod", Str_collection_method));
            nameValuePairs.add(new BasicNameValuePair("AmountCollected", Str_amount_collected));
            nameValuePairs.add(new BasicNameValuePair("DriverNotes", Str_driver_notes));
            nameValuePairs.add(new BasicNameValuePair("driverId", Driver_id));
            nameValuePairs.add(new BasicNameValuePair("JobStepId", Job_stepid));
            nameValuePairs.add(new BasicNameValuePair("JobNumber", Job_number));
            nameValuePairs.add(new BasicNameValuePair("Completed", "2018-05-10T14:02:23.3530000+0530"));
            nameValuePairs.add(new BasicNameValuePair("BinNumber", "56565"));
            String str = nameValuePairs.toString();
            str = str.replace("=", ":");
            //BinNumber
            //nameValuePairs = new ArrayList<NameValuePair>(Arrays.asList(str.split(",")));

            String jsonStr = sh.makeServiceCall_withHeader("http://112.196.3.42:8298/v1/Job/UpdateJobStepMobile",
                    ServiceHandler.POST, nameValuePairs, Access_tocken);

            Log.d("Response", "> " + jsonStr);

            if (jsonStr != null) {
                JSONObject jsonObj = null;
                try {
                    jsonObj = new JSONObject(jsonStr);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // Getting JSON Array node
                // JSONArray array1 = null;
//                try {
//                    status = jsonObj.getString("status");
//
//                    Message = jsonObj.getString("message");
//                    if (status.contentEquals("true")) {
//                        JSONObject jsonObj_data = null;
//                        jsonObj_data = jsonObj.getJSONObject("data");
//
//
//                        Device_id = jsonObj_data.getString("device_id");
//                        Acess_tocken = jsonObj_data.getString("access_token");
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }


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
            // new Status_update().execute();

        }

        @Override
        public void onCancel(DialogInterface dialog) {

        }
    }

    private class Status_update extends AsyncTask<Void, String, Void> implements DialogInterface.OnCancelListener {


        JSONObject jsonnode, json_User;

        String jsonStr = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // mProgressHUD = ProgressHUD.show(Login_Screen.this, "Connecting", true, true, this);
            // Showing progress dialog
            pDialog = new ProgressDialog(feedback.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();


        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            publishProgress("Please wait...");


            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            //String url = "http://api.androidhive.info/contacts/";
            String str_url = "http://112.196.3.42:8298/v1/Job/UpdateJobStatus?JobId=" + Job_id + "&nextstatus=" + Next_status + "&driverid=" + Driver_id + "&currentstatus=" + current_status;
            String newurl = str_url.replaceAll(" ", "%20");
            jsonStr = sh.makeServiceCall_post(newurl, Access_tocken);

            Log.d("Response: ", "> " + jsonStr);

            if (jsonStr != null) {
                JSONObject jsonObj = null;
                JSONObject Json_category = null;

//                try {
//                    jsonObj = new JSONObject(jsonStr);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }


//

                // Getting JSON Array node
                // JSONArray array1 = null;

            }


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            pDialog.dismiss();
            if (jsonStr == null) {
                open_loginWindow();
            } else if (jsonStr.contentEquals("Invalid_Token")) {
                open_loginWindow();
            } else {
                SharedPreferences shared = getSharedPreferences("Tidy_waste_management", MODE_PRIVATE);
                SharedPreferences.Editor editor = shared.edit();

                editor.putString("Check_screen", "Adapter");
                editor.putString("Next_status", Next_status);
                editor.commit();
                Intent i1 = new Intent(feedback.this, Home_screen.class);
                startActivity(i1);
                finish();
            }

            //context.f


        }

        @Override
        public void onCancel(DialogInterface dialog) {

        }
    }

    private void Submit_Update() {

        pdioalog__ = new ProgressDialog(feedback.this);
        pdioalog__.setMessage("Uploading, please wait...");
        pdioalog__.show();
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://112.196.3.42:8298/v1/Job/UpdateJobStepMobile",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pdioalog__.dismiss();
                        Log.d("uploade", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pdioalog__.dismiss();
                        Toast.makeText(feedback.this, "No internet connection", Toast.LENGTH_LONG).show();

                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new Hashtable<String, String>();
                params.put("Token", Access_tocken);
                params.put("Content-Type", "application/json");

                return params;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                //Map<String, String> params = new Hashtable<String, String>();

                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("JobID", Job_id);
                parameters.put("CustomerSignature", Str_signature_base_64);
                parameters.put("PhotoFile", img_base64);
                parameters.put("ProjectSiteId", Project_site_id);
                parameters.put("ProjectSiteChargeID", str_projectsite_chargeid);
                parameters.put("JobAdditionalCharges", additional_charges_string_to_send);
                parameters.put("SignedBy", Strng_signedby);
                parameters.put("Rating", Str_rating);
                parameters.put("PaymentCollected", Payment_collected_method);
                parameters.put("CollectionMethod", Str_collection_method);
                parameters.put("AmountCollected", Str_amount_collected);
                parameters.put("DriverNotes", Str_driver_notes);
                parameters.put("driverId", Driver_id);
                parameters.put("JobStepId", Job_stepid);
                parameters.put("JobNumber", Job_number);
                parameters.put("Completed", "2018-05-07T12:56:24.4176763+08:00");
                parameters.put("BinNumber", "56565");
                // nameValuePairs.add(new BasicNameValuePair("BinNumber", "56565"));
                //  parameters.put("image", imageString);

                // params.put("image", image);
                return parameters;
            }
        };
        {
            int socketTimeout = 310000;
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            stringRequest.setRetryPolicy(policy);
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
    }

    public String convertBitmapToString(Bitmap bmp) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 90, stream); //compress to which format you want.
        byte[] byte_arr = stream.toByteArray();
        String imageStr = Base64.encodeBytes(byte_arr);
        return imageStr;
    }


    private class Submit_Task extends AsyncTask<Void, Integer, String> {
        @Override
        protected void onPreExecute() {
            // setting progress bar to zero
            pDialog = new ProgressDialog(feedback.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.setIndeterminate(false);
            pDialog.setMax(100);
            pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pDialog.show();
//            progressBar.setVisibility(View.VISIBLE);
//            progressBar.setProgress(0);
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            // Making progress bar visible


            // updating progress bar value
            pDialog.setProgress(progress[0]);

            // updating percentage value
            //txtPercentage.setText(String.valueOf(progress[0]) + "%");
        }

        @Override
        protected String doInBackground(Void... params) {
            return uploadFile();
        }

        @SuppressWarnings("deprecation")
        private String uploadFile() {
            String responseString = null;

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://112.196.3.42:8298/v1/Job/UpdateJobStepMobile");
            httppost.addHeader("Token", Access_tocken);

            try {
                AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
                        new AndroidMultiPartEntity.ProgressListener() {

                            @Override
                            public void transferred(long num) {
                                publishProgress((int) ((num / (float) totalSize) * 100));
                            }
                        });

                entity.addPart("JobID", new StringBody(Job_id));
                entity.addPart("CustomerSignature", new StringBody(Str_signature_base_64));
                entity.addPart("PhotoFile", new StringBody(img_base64));
                entity.addPart("ProjectSiteId", new StringBody(Project_site_id));
                entity.addPart("ProjectSiteChargeID", new StringBody(str_projectsite_chargeid));
                entity.addPart("JobAdditionalCharges", new StringBody(Job_additional_charge));
                entity.addPart("SignedBy", new StringBody(Strng_signedby));
                entity.addPart("Rating", new StringBody(Str_rating));
                entity.addPart("PaymentCollected", new StringBody(Payment_collected_method));
                entity.addPart("CollectionMethod", new StringBody(Str_collection_method));
                entity.addPart("AmountCollected", new StringBody(Str_amount_collected));
                entity.addPart("DriverNotes", new StringBody(Str_driver_notes));
                entity.addPart("driverId", new StringBody(Driver_id));
                entity.addPart("JobStepId", new StringBody(Job_stepid));
                entity.addPart("JobNumber", new StringBody(Job_number));
                entity.addPart("Completed", new StringBody("2018-05-07T12:56:24.4176763+08:00"));
                entity.addPart("BinNumber", new StringBody(Str_bin));


                totalSize = entity.getContentLength();
                httppost.setEntity(entity);
//
                // Making server call
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity r_entity = response.getEntity();

                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    // Server response
                    responseString = EntityUtils.toString(r_entity);
                } else {
                    responseString = "Error occurred! Http Status Code: "
                            + statusCode;
                }

            } catch (ClientProtocolException e) {
                responseString = e.toString();
            } catch (IOException e) {
                responseString = e.toString();
            }
            if (responseString != null) {
                JSONObject jsonObj = null;
                try {
                    jsonObj = new JSONObject(responseString);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // Getting JSON Array node
                // JSONArray array1 = null;


                // entity.addPart("status", new StringBody("1"));


                // entity.addPart("status", new StringBody("1"));


                //  entity.addPart("status", new StringBody("1"));
            }


            return responseString;

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //Log.e(TAG, "Response from server: " + result);

            // showing the server response in an alert dialog
            //  showAlert(result);

            // new User_profile().execute();

            pDialog.dismiss();

        }

    }

    public void UPDATE_FEEDBACK() {
        String pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSSZ";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, new Locale("fr", "FR"));
        String date = simpleDateFormat.format(new Date());
        pdioalog__ = new ProgressDialog(feedback.this);
        pdioalog__.setMessage("Please wait...");
        pdioalog__.setCancelable(false);
        pdioalog__.show();
        Tidy_feedback tidyfeedback = new Tidy_feedback();
        tidyfeedback.setJobID(Job_id);
        tidyfeedback.setCustomerSignature(Str_signature_base_64);
        tidyfeedback.setPhotoFile(img_base64);
        tidyfeedback.setProjectSiteId(Project_site_id);
        tidyfeedback.setProjectSiteChargeID(str_projectsite_chargeid);
        if (!additional_charges_string_to_send.contentEquals("")) {
            tidyfeedback.setJobAdditionalCharges(additional_charges_string_to_send);
        }
        tidyfeedback.setSignedBy(Strng_signedby);
        if (!str_chec_step.contentEquals("2")) {
            tidyfeedback.setRating(Str_rating);
        }
        tidyfeedback.setPaymentCollected(Payment_collected_method);
        tidyfeedback.setCollectionMethod(Str_collection_method);
        tidyfeedback.setAmountCollected(Str_amount_collected);
        tidyfeedback.setDriverNotes(Str_driver_notes);
        tidyfeedback.setDriverId(Driver_id);
        tidyfeedback.setJobStepId(Job_stepid);
        tidyfeedback.setJobNumber(Job_number);
        tidyfeedback.setCompleted(date);
        tidyfeedback.setBinNumber(Str_bin);
        if (!ConnectionManager.isConnected(feedback.this)) {
            pdioalog__.dismiss();
            Toast.makeText(feedback.this, "No Internet Connection", Toast.LENGTH_LONG).show();
            //  Constants.showAlertDialog(getActivity(), "Please Enable Your Internet Connection", "Alert!", "Ok").show();
        } else {
            APIInterface mAPIInterface = ApiClient.getClient(Access_tocken).create(APIInterface.class);
            mAPIInterface.CallApi(tidyfeedback).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                    pdioalog__.dismiss();
                    try {
                        if (response.isSuccessful()) {
                            new Status_update().execute();
                            // String respose = response.toString();
                            // Toast.makeText(feedback.this,respose,Toast.LENGTH_LONG).show();
                        }else{
                            //Toast.makeText(feedback.this,response.toString(),Toast.LENGTH_LONG).show();
                            open_loginWindow();
                        }
                    } catch (Exception e) {
                        // Toast.makeText(feedback.this, "Server error", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                        open_loginWindow();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    pdioalog__.dismiss();
                    try {
                        // Toast.makeText(feedback.this, "Server error", Toast.LENGTH_LONG).show();
                        open_loginWindow();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public void open_loginWindow() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Your session has expired. Please log in again");
        alertDialogBuilder.setPositiveButton("ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        SharedPreferences settings = getSharedPreferences("Tidy_waste_management", Context.MODE_PRIVATE);
                        settings.edit().clear().commit();
                        Intent i1 = new Intent(feedback.this, Login_screen.class);
                        startActivity(i1);
                        finish();
                    }
                });


        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}


