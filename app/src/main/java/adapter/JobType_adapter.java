package adapter;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import Service_handler.ServiceHandler;
import mobile.tiny_waste_management.Home_screen;
import mobile.tiny_waste_management.MapsActivity;
import mobile.tiny_waste_management.R;
import mobile.tiny_waste_management.feedback;


/**
 * Created by Nirmal on 6/24/2016.
 */
public class JobType_adapter extends BaseAdapter {

    ArrayList<HashMap<String, String>> subscriptionarray = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> jobs_steps = new ArrayList<HashMap<String, String>>();
    LayoutInflater inflater;
    private ProgressDialog pDialog;
    Context context;
    String Str_phone1 = "", StrPhone2 = "";
    String jsonStr = "";
    SharedPreferences shared;
    String Access_tocken = "", Driver_id = "", Search_withjob_number = "", Jobidd = "", Next_statuss = "", current_statuss = "";
    String Status = "";

    public JobType_adapter(Context context, ArrayList<HashMap<String, String>> Array_subscription, ArrayList<HashMap<String, String>> list_jobs_steps) {
        this.subscriptionarray = Array_subscription;
        this.jobs_steps = list_jobs_steps;
        this.context = context;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return subscriptionarray.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }


    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final MyViewHolder mViewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.one_fragmentlayout, parent, false);
            mViewHolder = new MyViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (MyViewHolder) convertView.getTag();
        }
        try {
            //if(Str_profile_image!=null) {
            //img_loader.DisplayImage(fl,vh.Img_profilepic);


        } catch (IllegalArgumentException e) {
            e.printStackTrace();

        }
        // String Str_Pic_image = subscriptionarray.get(position).get("image");

        try {
            mViewHolder.job_number.setText(subscriptionarray.get(position).get("Jobnumber"));


            mViewHolder.txt_datetime.setText(subscriptionarray.get(position).get("JobDate"));
            mViewHolder.txt_jobtype.setText(subscriptionarray.get(position).get("jobtype"));

            mViewHolder.customername.setText(subscriptionarray.get(position).get("customer_name"));
            String additional_remarks = subscriptionarray.get(position).get("DriverRemark");
            if (additional_remarks.contentEquals("null")) {
                additional_remarks = "";
            }
            String remarks = "";
            remarks = "Additional Remarks :" + additional_remarks;
            //additional_remarks.replace(null,"");
            mViewHolder.Additionalremarks.setText(remarks);

            mViewHolder.Jobid = subscriptionarray.get(position).get("id");


            //mViewHolder.siteadess.setText(subscriptionarray.get(position).get("Address"));
            mViewHolder.img_location.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String Adress1 = jobs_steps.get(position).get("adress");
                    String adrss = "";
                    try {
                        String[] separated = Adress1.split(",,");
                        adrss = separated[0];

                    } catch (java.lang.IndexOutOfBoundsException e) {
                        e.printStackTrace();
                        mViewHolder.rlv_step2.setVisibility(View.GONE);
                    }
                    SharedPreferences shared = context.getSharedPreferences("Tidy_waste_management", context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = shared.edit();
                    editor.putString("Location", adrss);
                    editor.commit();
                    Intent i1 = new Intent(context, MapsActivity.class);
                    context.startActivity(i1);
                    // finish();
                }
            });
            mViewHolder.img_location_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String Adress1 = jobs_steps.get(position).get("adress");
                    String adrss = "";
                    try {
                        String[] separated = Adress1.split(",,");
                        adrss = separated[1];

                    } catch (java.lang.IndexOutOfBoundsException e) {
                        e.printStackTrace();
                        mViewHolder.rlv_step2.setVisibility(View.GONE);
                    }
                    SharedPreferences shared = context.getSharedPreferences("Tidy_waste_management", context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = shared.edit();
                    editor.putString("Location", adrss);
                    editor.commit();
                    Intent i1 = new Intent(context, MapsActivity.class);
                    context.startActivity(i1);
                    // finish();
                }
            });

            mViewHolder.Img_phone1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + Str_phone1));
                    context.startActivity(intent);
                }
            });
            mViewHolder.ImgPhone2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + StrPhone2));
                    context.startActivity(intent);
                }
            });
            shared = context.getSharedPreferences("Tidy_waste_management", context.MODE_PRIVATE);
            Access_tocken = (shared.getString("Acess_tocken", "nologin"));
            Driver_id = (shared.getString("Driver_id", "nologin"));


            Status = subscriptionarray.get(position).get("Check_fragment");
            String Job_type = subscriptionarray.get(position).get("jobtype");
            if (Status.contentEquals("Assigned")) {
                mViewHolder.rlv_step1.setBackgroundColor(Color.parseColor("#E4F8EB"));
                mViewHolder.rlv_step2.setBackgroundColor(Color.parseColor("#fefffe"));
                mViewHolder.btn_acknowledge.setText("Acknowledge");
                mViewHolder.btn_acknowledge.setVisibility(View.VISIBLE);
                if (Job_type.contentEquals("Exchange")) {

                    mViewHolder.personincharge2.setVisibility(View.GONE);
                    mViewHolder.txt_person_in___.setVisibility(View.GONE);
                    mViewHolder.Txt_contperson.setVisibility(View.INVISIBLE);
                    mViewHolder.contactnumber2.setVisibility(View.INVISIBLE);
                    mViewHolder.ImgPhone2.setVisibility(View.INVISIBLE);

                } else if (Job_type.contentEquals("Pull")) {

                    mViewHolder.personincharge2.setVisibility(View.GONE);
                    mViewHolder.txt_person_in___.setVisibility(View.GONE);
                    mViewHolder.Txt_contperson.setVisibility(View.INVISIBLE);
                    mViewHolder.contactnumber2.setVisibility(View.INVISIBLE);
                    mViewHolder.ImgPhone2.setVisibility(View.INVISIBLE);

                } else {
                    mViewHolder.personincharge2.setVisibility(View.VISIBLE);
                    mViewHolder.txt_person_in___.setVisibility(View.VISIBLE);
                    mViewHolder.Txt_contperson.setVisibility(View.VISIBLE);
                    mViewHolder.contactnumber2.setVisibility(View.VISIBLE);
                    mViewHolder.ImgPhone2.setVisibility(View.VISIBLE);
                }
                mViewHolder.rlv_holder1.setVisibility(View.GONE);
                mViewHolder.Rlvholder2.setVisibility(View.GONE);
            } else if (Status.contentEquals("Acknowledge")) {
                mViewHolder.rlv_step1.setBackgroundColor(Color.parseColor("#E4F8EB"));
                mViewHolder.rlv_step2.setBackgroundColor(Color.parseColor("#fefffe"));
                mViewHolder.btn_acknowledge.setText("Start");
                mViewHolder.btn_acknowledge.setVisibility(View.VISIBLE);
                mViewHolder.personincharge2.setVisibility(View.VISIBLE);
                mViewHolder.txt_person_in___.setVisibility(View.VISIBLE);
                if (Job_type.contentEquals("Exchange")) {

                    mViewHolder.personincharge2.setVisibility(View.GONE);
                    mViewHolder.txt_person_in___.setVisibility(View.GONE);
                    mViewHolder.Txt_contperson.setVisibility(View.INVISIBLE);
                    mViewHolder.contactnumber2.setVisibility(View.INVISIBLE);
                    mViewHolder.ImgPhone2.setVisibility(View.INVISIBLE);


                } else if (Job_type.contentEquals("Pull")) {

                    mViewHolder.personincharge2.setVisibility(View.GONE);
                    mViewHolder.txt_person_in___.setVisibility(View.GONE);
                    mViewHolder.Txt_contperson.setVisibility(View.INVISIBLE);
                    mViewHolder.contactnumber2.setVisibility(View.INVISIBLE);
                    mViewHolder.ImgPhone2.setVisibility(View.INVISIBLE);

                } else {
                    mViewHolder.personincharge2.setVisibility(View.VISIBLE);
                    mViewHolder.txt_person_in___.setVisibility(View.VISIBLE);
                    mViewHolder.Txt_contperson.setVisibility(View.VISIBLE);
                    mViewHolder.contactnumber2.setVisibility(View.VISIBLE);
                    mViewHolder.ImgPhone2.setVisibility(View.VISIBLE);
                }
                mViewHolder.rlv_holder1.setVisibility(View.GONE);
                mViewHolder.Rlvholder2.setVisibility(View.GONE);
            } else if (Status.contentEquals("In Progress")) {
                mViewHolder.rlv_step1.setBackgroundColor(Color.parseColor("#E4F8EB"));
                mViewHolder.rlv_step2.setBackgroundColor(Color.parseColor("#fefffe"));
                mViewHolder.btn_acknowledge.setVisibility(View.VISIBLE);
                if (Job_type.contentEquals("Exchange")) {
                    mViewHolder.btn_acknowledge.setText("Changed");
                    mViewHolder.personincharge2.setVisibility(View.GONE);
                    mViewHolder.txt_person_in___.setVisibility(View.GONE);
                    mViewHolder.Txt_contperson.setVisibility(View.INVISIBLE);
                    mViewHolder.contactnumber2.setVisibility(View.INVISIBLE);
                    mViewHolder.ImgPhone2.setVisibility(View.INVISIBLE);
                } else if (Job_type.contentEquals("Pull")) {
                    mViewHolder.btn_acknowledge.setText("Pulled");
                    mViewHolder.personincharge2.setVisibility(View.GONE);
                    mViewHolder.txt_person_in___.setVisibility(View.GONE);
                    mViewHolder.Txt_contperson.setVisibility(View.INVISIBLE);
                    mViewHolder.contactnumber2.setVisibility(View.INVISIBLE);
                    mViewHolder.ImgPhone2.setVisibility(View.INVISIBLE);

                } else if (Job_type.contentEquals("Put")) {
                    mViewHolder.btn_acknowledge.setText("Completed");
                    mViewHolder.personincharge2.setVisibility(View.VISIBLE);
                    mViewHolder.txt_person_in___.setVisibility(View.VISIBLE);
                    mViewHolder.Txt_contperson.setVisibility(View.VISIBLE);
                    mViewHolder.contactnumber2.setVisibility(View.VISIBLE);
                    mViewHolder.ImgPhone2.setVisibility(View.VISIBLE);
                } else if (Job_type.contentEquals("Shift")) {
                    mViewHolder.btn_acknowledge.setText("Shifted");
                    mViewHolder.personincharge2.setVisibility(View.VISIBLE);
                    mViewHolder.txt_person_in___.setVisibility(View.VISIBLE);
                    mViewHolder.Txt_contperson.setVisibility(View.VISIBLE);
                    mViewHolder.contactnumber2.setVisibility(View.VISIBLE);
                    mViewHolder.ImgPhone2.setVisibility(View.VISIBLE);
                } else if (Job_type.contentEquals("Out")) {
                    mViewHolder.btn_acknowledge.setText("Completed");
                    mViewHolder.personincharge2.setVisibility(View.VISIBLE);
                    mViewHolder.txt_person_in___.setVisibility(View.VISIBLE);
                    mViewHolder.Txt_contperson.setVisibility(View.VISIBLE);
                    mViewHolder.contactnumber2.setVisibility(View.VISIBLE);
                    mViewHolder.ImgPhone2.setVisibility(View.VISIBLE);
                } else if (Job_type.contentEquals("Throw At Customer Site")) {
                    mViewHolder.btn_acknowledge.setText("Shifted");
                    mViewHolder.personincharge2.setVisibility(View.VISIBLE);
                    mViewHolder.txt_person_in___.setVisibility(View.VISIBLE);
                    mViewHolder.Txt_contperson.setVisibility(View.VISIBLE);
                    mViewHolder.contactnumber2.setVisibility(View.VISIBLE);
                    mViewHolder.ImgPhone2.setVisibility(View.VISIBLE);
                } else if (Job_type.contentEquals("Pull Empty Bin")) {
                    mViewHolder.btn_acknowledge.setText("Completed");
                    mViewHolder.personincharge2.setVisibility(View.VISIBLE);
                    mViewHolder.txt_person_in___.setVisibility(View.VISIBLE);
                    mViewHolder.Txt_contperson.setVisibility(View.VISIBLE);
                    mViewHolder.contactnumber2.setVisibility(View.VISIBLE);
                    mViewHolder.ImgPhone2.setVisibility(View.VISIBLE);
                }
                mViewHolder.rlv_holder1.setVisibility(View.GONE);
                mViewHolder.Rlvholder2.setVisibility(View.GONE);
            } else if (Status.contentEquals("cancelled")) {
                if (Job_type.contentEquals("Exchange")) {

                    mViewHolder.personincharge2.setVisibility(View.GONE);
                    mViewHolder.txt_person_in___.setVisibility(View.GONE);
                    mViewHolder.Txt_contperson.setVisibility(View.INVISIBLE);
                    mViewHolder.contactnumber2.setVisibility(View.INVISIBLE);
                    mViewHolder.ImgPhone2.setVisibility(View.INVISIBLE);


                } else if (Job_type.contentEquals("Pull")) {

                    mViewHolder.personincharge2.setVisibility(View.GONE);
                    mViewHolder.txt_person_in___.setVisibility(View.GONE);
                    mViewHolder.Txt_contperson.setVisibility(View.INVISIBLE);
                    mViewHolder.contactnumber2.setVisibility(View.INVISIBLE);
                    mViewHolder.ImgPhone2.setVisibility(View.INVISIBLE);

                } else {
                    mViewHolder.personincharge2.setVisibility(View.VISIBLE);
                    mViewHolder.txt_person_in___.setVisibility(View.VISIBLE);
                    mViewHolder.Txt_contperson.setVisibility(View.VISIBLE);
                    mViewHolder.contactnumber2.setVisibility(View.VISIBLE);
                    mViewHolder.ImgPhone2.setVisibility(View.VISIBLE);
                }
                mViewHolder.rlv_step1.setBackgroundColor(Color.parseColor("#E4F8EB"));
                mViewHolder.rlv_step2.setBackgroundColor(Color.parseColor("#fefffe"));
                mViewHolder.btn_acknowledge.setText("Completed");
                mViewHolder.btn_acknowledge.setVisibility(View.INVISIBLE);
                mViewHolder.rlv_holder1.setVisibility(View.GONE);
                mViewHolder.Rlvholder2.setVisibility(View.GONE);
            } else if (Status.contentEquals("Changed")) {
                if (Job_type.contentEquals("Exchange")) {

                    mViewHolder.personincharge2.setVisibility(View.GONE);
                    mViewHolder.txt_person_in___.setVisibility(View.GONE);
                    mViewHolder.Txt_contperson.setVisibility(View.INVISIBLE);
                    mViewHolder.contactnumber2.setVisibility(View.INVISIBLE);
                    mViewHolder.ImgPhone2.setVisibility(View.INVISIBLE);

                } else if (Job_type.contentEquals("Pull")) {

                    mViewHolder.personincharge2.setVisibility(View.GONE);
                    mViewHolder.txt_person_in___.setVisibility(View.GONE);
                    mViewHolder.Txt_contperson.setVisibility(View.INVISIBLE);
                    mViewHolder.contactnumber2.setVisibility(View.INVISIBLE);
                    mViewHolder.ImgPhone2.setVisibility(View.INVISIBLE);

                } else {
                    mViewHolder.personincharge2.setVisibility(View.VISIBLE);
                    mViewHolder.txt_person_in___.setVisibility(View.VISIBLE);
                    mViewHolder.Txt_contperson.setVisibility(View.VISIBLE);
                    mViewHolder.contactnumber2.setVisibility(View.VISIBLE);
                    mViewHolder.ImgPhone2.setVisibility(View.VISIBLE);
                }
                mViewHolder.rlv_step1.setBackgroundColor(Color.parseColor("#fefffe"));
                mViewHolder.rlv_step2.setBackgroundColor(Color.parseColor("#E4F8EB"));
                mViewHolder.btn_acknowledge.setText("Completed");
                mViewHolder.btn_acknowledge.setVisibility(View.VISIBLE);
                mViewHolder.rlv_holder1.setVisibility(View.GONE);
                mViewHolder.Rlvholder2.setVisibility(View.GONE);
            } else if (Status.contentEquals("Completed")) {
                if (Job_type.contentEquals("Exchange")) {

                    mViewHolder.personincharge2.setVisibility(View.GONE);
                    mViewHolder.txt_person_in___.setVisibility(View.GONE);
                    mViewHolder.Txt_contperson.setVisibility(View.INVISIBLE);
                    mViewHolder.contactnumber2.setVisibility(View.INVISIBLE);
                    mViewHolder.ImgPhone2.setVisibility(View.INVISIBLE);


                } else if (Job_type.contentEquals("Pull")) {

                    mViewHolder.personincharge2.setVisibility(View.GONE);
                    mViewHolder.txt_person_in___.setVisibility(View.GONE);
                    mViewHolder.Txt_contperson.setVisibility(View.INVISIBLE);
                    mViewHolder.contactnumber2.setVisibility(View.INVISIBLE);
                    mViewHolder.ImgPhone2.setVisibility(View.INVISIBLE);

                } else {
                    mViewHolder.personincharge2.setVisibility(View.VISIBLE);
                    mViewHolder.txt_person_in___.setVisibility(View.VISIBLE);
                    mViewHolder.Txt_contperson.setVisibility(View.VISIBLE);
                    mViewHolder.contactnumber2.setVisibility(View.VISIBLE);
                    mViewHolder.ImgPhone2.setVisibility(View.VISIBLE);
                }
                mViewHolder.rlv_step1.setBackgroundColor(Color.parseColor("#E4F8EB"));
                mViewHolder.rlv_step2.setBackgroundColor(Color.parseColor("#E4F8EB"));
                //mViewHolder.btn_acknowledge.setText("Proof of Delivery");
                mViewHolder.btn_acknowledge.setVisibility(View.INVISIBLE);
                mViewHolder.rlv_holder1.setVisibility(View.VISIBLE);
                mViewHolder.Rlvholder2.setVisibility(View.VISIBLE);
                String Collection_method1 = "";
                String AmountCollected1 = "";
                String DriverNotes1 = "";
                String CompletedTime1 = "";
                String CustomerSignature1 = "";
                String PhotoFile1 = "";
                String Signedby1 = "";

//                Signedby1= (TextView) item.findViewById(R.id.textView36);
//                signedby2= (TextView) item.findViewById(R.id.textView51);
                Collection_method1 = jobs_steps.get(position).get("Collection_method1");
                AmountCollected1 = jobs_steps.get(position).get("AmountCollected1");
                DriverNotes1 = jobs_steps.get(position).get("DriverNotes1");
                CompletedTime1 = jobs_steps.get(position).get("CompletedTime1");
                CustomerSignature1 = jobs_steps.get(position).get("CustomerSignature1");
                PhotoFile1 = jobs_steps.get(position).get("PhotoFile1");
                Signedby1 = jobs_steps.get(position).get("signedby1");
                try {
                    String[] separated = Collection_method1.split(",,");
                    mViewHolder.collection_method1.setText(separated[0]);
                    mViewHolder.collection_method2.setText(separated[1]);

                } catch (java.lang.IndexOutOfBoundsException e) {
                    e.printStackTrace();

                }
                try {
                    String[] separated = AmountCollected1.split(",,");
                    mViewHolder.amountcollected1.setText(separated[0]);
                    mViewHolder.amountcollected2.setText(separated[1]);

                } catch (java.lang.IndexOutOfBoundsException e) {
                    e.printStackTrace();

                }
                try {
                    String[] separated = DriverNotes1.split(",,");
                    mViewHolder.drivernotes1.setText(separated[0]);
                    mViewHolder.drivernotes2.setText(separated[1]);

                } catch (java.lang.IndexOutOfBoundsException e) {
                    e.printStackTrace();

                }
                try {
                    String[] separated = CompletedTime1.split(",,");
                    mViewHolder.completedtime1.setText(separated[0]);
                    mViewHolder.completedtime2.setText(separated[1]);

                } catch (java.lang.IndexOutOfBoundsException e) {
                    e.printStackTrace();

                }
                try {
                    String[] separated = Signedby1.split(",,");
                    mViewHolder.Signedby1.setText(separated[0]);
                    mViewHolder.signedby2.setText(separated[1]);

                } catch (java.lang.IndexOutOfBoundsException e) {
                    e.printStackTrace();

                }

                try {
                    String[] separated = CustomerSignature1.split(",,");
                    String image1 = "";
                    image1 = separated[0];
                    try {

                        Picasso.with(context).load(image1).error(R.drawable.noimage).memoryPolicy(MemoryPolicy.NO_CACHE).into(mViewHolder.img_signature1);
                        final String finalImage = image1;
                        mViewHolder.img_signature1.setEnabled(true);
                        mViewHolder.img_signature1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final Dialog nagDialog = new Dialog(context, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
                                nagDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                nagDialog.setCancelable(false);
                                nagDialog.setContentView(R.layout.big_image_click);
                                Button btnClose = (Button) nagDialog.findViewById(R.id.btnIvClose);
                                ImageView ivPreview = (ImageView) nagDialog.findViewById(R.id.iv_preview_image);
                                try {
                                    Picasso.with(context).load(finalImage).error(R.drawable.noimage).into(ivPreview);
                                } catch (java.lang.IllegalArgumentException e) {
                                    e.printStackTrace();
                                }
                                //ivPreview.setBackgroundDrawable(dd);

                                btnClose.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View arg0) {

                                        nagDialog.dismiss();
                                    }
                                });
                                nagDialog.show();
                            }
                        });

                    } catch (java.lang.IllegalArgumentException e) {
                        e.printStackTrace();
                        Picasso.with(context).load(R.drawable.noimage).error(R.drawable.noimage).memoryPolicy(MemoryPolicy.NO_CACHE).into(mViewHolder.img_signature1);
                        mViewHolder.img_signature1.setEnabled(false);
                    }

                    String image2 = "";
                    image2 = separated[1];
                    mViewHolder.img_signature2.setEnabled(true);
                    try {
                        Picasso.with(context).load(image2).error(R.drawable.noimage).memoryPolicy(MemoryPolicy.NO_CACHE).into(mViewHolder.img_signature2);
                        final String finalImage = image2;
                        mViewHolder.img_signature2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final Dialog nagDialog = new Dialog(context, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
                                nagDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                nagDialog.setCancelable(false);
                                nagDialog.setContentView(R.layout.big_image_click);
                                Button btnClose = (Button) nagDialog.findViewById(R.id.btnIvClose);
                                ImageView ivPreview = (ImageView) nagDialog.findViewById(R.id.iv_preview_image);
                                try {
                                    Picasso.with(context).load(finalImage).error(R.drawable.noimage).into(ivPreview);
                                } catch (java.lang.IllegalArgumentException e) {
                                    e.printStackTrace();
                                }
                                //ivPreview.setBackgroundDrawable(dd);

                                btnClose.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View arg0) {

                                        nagDialog.dismiss();
                                    }
                                });
                                nagDialog.show();
                            }
                        });
                    } catch (java.lang.IllegalArgumentException e) {
                        e.printStackTrace();
                        mViewHolder.img_signature2.setEnabled(false);
                        Picasso.with(context).load(R.drawable.noimage).error(R.drawable.noimage).memoryPolicy(MemoryPolicy.NO_CACHE).into(mViewHolder.img_signature2);
                    }

//                    mViewHolder.completedtime1.setText(separated[0]);
//                    mViewHolder.completedtime2.setText(separated[1]);

                } catch (java.lang.IndexOutOfBoundsException e) {
                    e.printStackTrace();

                }
                try {
                    String[] separated = PhotoFile1.split(",,");
                    String image1 = "";
                    image1 = separated[0];
                    mViewHolder.photo1.setEnabled(true);
                    try {
                        Picasso.with(context).load(image1).error(R.drawable.noimage).memoryPolicy(MemoryPolicy.NO_CACHE).into(mViewHolder.photo1);
                        final String finalImage = image1;
                        mViewHolder.photo1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final Dialog nagDialog = new Dialog(context, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
                                nagDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                nagDialog.setCancelable(false);
                                nagDialog.setContentView(R.layout.big_image_click);
                                Button btnClose = (Button) nagDialog.findViewById(R.id.btnIvClose);
                                ImageView ivPreview = (ImageView) nagDialog.findViewById(R.id.iv_preview_image);
                                try {
                                    Picasso.with(context).load(finalImage).error(R.drawable.noimage).into(ivPreview);
                                } catch (java.lang.IllegalArgumentException e) {
                                    e.printStackTrace();
                                }
                                //ivPreview.setBackgroundDrawable(dd);

                                btnClose.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View arg0) {

                                        nagDialog.dismiss();
                                    }
                                });
                                nagDialog.show();
                            }
                        });
                    } catch (java.lang.IllegalArgumentException e) {
                        e.printStackTrace();
                        mViewHolder.photo1.setEnabled(false);
                        Picasso.with(context).load(R.drawable.noimage).error(R.drawable.noimage).memoryPolicy(MemoryPolicy.NO_CACHE).into(mViewHolder.photo1);
                    }

                    String image2 = "";
                    image2 = separated[1];
                    mViewHolder.photo2.setEnabled(true);
                    try {
                        Picasso.with(context).load(image2).error(R.drawable.noimage).memoryPolicy(MemoryPolicy.NO_CACHE).into(mViewHolder.photo2);
                        final String finalImage = image2;
                        mViewHolder.photo2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final Dialog nagDialog = new Dialog(context, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
                                nagDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                nagDialog.setCancelable(false);
                                nagDialog.setContentView(R.layout.big_image_click);
                                Button btnClose = (Button) nagDialog.findViewById(R.id.btnIvClose);
                                ImageView ivPreview = (ImageView) nagDialog.findViewById(R.id.iv_preview_image);
                                try {
                                    Picasso.with(context).load(finalImage).error(R.drawable.noimage).into(ivPreview);
                                } catch (java.lang.IllegalArgumentException e) {
                                    e.printStackTrace();
                                }
                                //ivPreview.setBackgroundDrawable(dd);

                                btnClose.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View arg0) {

                                        nagDialog.dismiss();
                                    }
                                });
                                nagDialog.show();
                            }
                        });

                    } catch (java.lang.IllegalArgumentException e) {
                        e.printStackTrace();
                        mViewHolder.photo2.setEnabled(false);
                        Picasso.with(context).load(R.drawable.noimage).error(R.drawable.noimage).memoryPolicy(MemoryPolicy.NO_CACHE).into(mViewHolder.photo2);
                    }

//                    mViewHolder.completedtime1.setText(separated[0]);
//                    mViewHolder.completedtime2.setText(separated[1]);

                } catch (java.lang.IndexOutOfBoundsException e) {
                    e.printStackTrace();

                }


//                rlv_holder1 = (RelativeLayout) item.findViewById(R.id.rlv_holder);
//                Rlvholder2 = (RelativeLayout) item.findViewById(R.id.rlv_holder2);
//                collection_method1= (TextView) item.findViewById(R.id.textView41);
//                amountcollected1= (TextView) item.findViewById(R.id.textView43);
//                drivernotes1= (TextView) item.findViewById(R.id.textView45);
//                completedtime1= (TextView) item.findViewById(R.id.textView47);
//                collection_method2= (TextView) item.findViewById(R.id.textView411);
//                amountcollected2= (TextView) item.findViewById(R.id.textView433);
//                drivernotes2= (TextView) item.findViewById(R.id.textView455);
//                completedtime2= (TextView) item.findViewById(R.id.textView477);
//                img_signature1 = (ImageView)item.findViewById(R.id.imageView6);
//                photo1 = (ImageView)item.findViewById(R.id.imageView11);
//                img_signature2 = (ImageView)item.findViewById(R.id.imageView66);
//                photo2 = (ImageView)item.findViewById(R.id.imageView111);
            } else if (Status.contentEquals("pulled")) {
                if (Job_type.contentEquals("Exchange")) {

                    mViewHolder.personincharge2.setVisibility(View.GONE);
                    mViewHolder.txt_person_in___.setVisibility(View.GONE);
                    mViewHolder.Txt_contperson.setVisibility(View.INVISIBLE);
                    mViewHolder.contactnumber2.setVisibility(View.INVISIBLE);
                    mViewHolder.ImgPhone2.setVisibility(View.INVISIBLE);


                } else if (Job_type.contentEquals("Pull")) {

                    mViewHolder.personincharge2.setVisibility(View.GONE);
                    mViewHolder.txt_person_in___.setVisibility(View.GONE);
                    mViewHolder.Txt_contperson.setVisibility(View.INVISIBLE);
                    mViewHolder.contactnumber2.setVisibility(View.INVISIBLE);
                    mViewHolder.ImgPhone2.setVisibility(View.INVISIBLE);

                } else {
                    mViewHolder.personincharge2.setVisibility(View.VISIBLE);
                    mViewHolder.txt_person_in___.setVisibility(View.VISIBLE);
                    mViewHolder.Txt_contperson.setVisibility(View.VISIBLE);
                    mViewHolder.contactnumber2.setVisibility(View.VISIBLE);
                    mViewHolder.ImgPhone2.setVisibility(View.VISIBLE);
                }
                mViewHolder.rlv_step1.setBackgroundColor(Color.parseColor("#fefffe"));
                mViewHolder.rlv_step2.setBackgroundColor(Color.parseColor("#E4F8EB"));
                mViewHolder.btn_acknowledge.setText("Completed");
                mViewHolder.btn_acknowledge.setVisibility(View.VISIBLE);
                mViewHolder.rlv_holder1.setVisibility(View.GONE);
                mViewHolder.Rlvholder2.setVisibility(View.GONE);
            } else if (Status.contentEquals("Shifted")) {
                if (Job_type.contentEquals("Exchange")) {

                    mViewHolder.personincharge2.setVisibility(View.GONE);
                    mViewHolder.txt_person_in___.setVisibility(View.GONE);
                    mViewHolder.Txt_contperson.setVisibility(View.INVISIBLE);
                    mViewHolder.contactnumber2.setVisibility(View.INVISIBLE);
                    mViewHolder.ImgPhone2.setVisibility(View.INVISIBLE);


                } else if (Job_type.contentEquals("Pull")) {

                    mViewHolder.personincharge2.setVisibility(View.GONE);
                    mViewHolder.txt_person_in___.setVisibility(View.GONE);
                    mViewHolder.Txt_contperson.setVisibility(View.INVISIBLE);
                    mViewHolder.contactnumber2.setVisibility(View.INVISIBLE);
                    mViewHolder.ImgPhone2.setVisibility(View.INVISIBLE);

                } else {
                    mViewHolder.personincharge2.setVisibility(View.VISIBLE);
                    mViewHolder.txt_person_in___.setVisibility(View.VISIBLE);
                    mViewHolder.Txt_contperson.setVisibility(View.VISIBLE);
                    mViewHolder.contactnumber2.setVisibility(View.VISIBLE);
                    mViewHolder.ImgPhone2.setVisibility(View.VISIBLE);
                }
                mViewHolder.rlv_step1.setBackgroundColor(Color.parseColor("#fefffe"));
                mViewHolder.rlv_step2.setBackgroundColor(Color.parseColor("#E4F8EB"));
                mViewHolder.btn_acknowledge.setText("Completed");
                mViewHolder.btn_acknowledge.setVisibility(View.VISIBLE);
                mViewHolder.rlv_holder1.setVisibility(View.GONE);
                mViewHolder.Rlvholder2.setVisibility(View.GONE);
            }


            ////////////////////////////////////////////////////
            String PersonInCharge1 = "";
            String ContactNo1 = "";
            String BinType1 = "";
            String WasteType1 = "";
            String Remarks1 = "";
            String Adress1 = "";
            String job_step_id1 = "";

            PersonInCharge1 = jobs_steps.get(position).get("PersonInCharge");
            ContactNo1 = jobs_steps.get(position).get("ContactNo");
            BinType1 = jobs_steps.get(position).get("bin_type_name");
            WasteType1 = jobs_steps.get(position).get("wastetype_name");
            Remarks1 = jobs_steps.get(position).get("Remarks");
            Adress1 = jobs_steps.get(position).get("adress");
            job_step_id1 = jobs_steps.get(position).get("job_step_id1");
            Remarks1.replace("null", " ");
            try {
                String[] separated = PersonInCharge1.split(",,");
                mViewHolder.personincharge.setText(separated[0]);
                mViewHolder.personincharge2.setText(separated[1]);
                mViewHolder.rlv_step2.setVisibility(View.VISIBLE);
            } catch (java.lang.IndexOutOfBoundsException e) {
                e.printStackTrace();
                mViewHolder.rlv_step2.setVisibility(View.GONE);
            }
            try {
                String[] separated = job_step_id1.split(",,");
                mViewHolder.Job_stepid = separated[0];

            } catch (java.lang.IndexOutOfBoundsException e) {
                e.printStackTrace();

            }
            try {
                String[] separated = Adress1.split(",,");
                mViewHolder.siteadess.setText(separated[0]);
                if (separated[0].contentEquals("")) {
                    mViewHolder.img_location.setVisibility(View.INVISIBLE);
                } else if (separated[0].contentEquals(" ")) {
                    mViewHolder.img_location.setVisibility(View.INVISIBLE);
                } else if (separated[0].contentEquals("  ")) {
                    mViewHolder.img_location.setVisibility(View.INVISIBLE);
                } else {
                    mViewHolder.img_location.setVisibility(View.VISIBLE);
                }
                mViewHolder.siteadess2.setText(separated[1]);
                String locationn = separated[1];
                if (locationn.contentEquals("")) {
                    mViewHolder.img_location_2.setVisibility(View.INVISIBLE);
                } else if (locationn.contentEquals(" ")) {
                    mViewHolder.img_location_2.setVisibility(View.INVISIBLE);
                } else if (locationn.contentEquals("   ")) {
                    mViewHolder.img_location_2.setVisibility(View.INVISIBLE);
                } else {
                    mViewHolder.img_location_2.setVisibility(View.VISIBLE);
                }
                mViewHolder.rlv_step2.setVisibility(View.VISIBLE);
            } catch (java.lang.IndexOutOfBoundsException e) {
                e.printStackTrace();
                mViewHolder.rlv_step2.setVisibility(View.GONE);
            }
            try {
                String[] separated2 = ContactNo1.split(",,");
                mViewHolder.contactnumber.setText(separated2[0]);
                Str_phone1 = separated2[0];
                if (Str_phone1.contentEquals("")) {
                    mViewHolder.Img_phone1.setVisibility(View.INVISIBLE);
                } else if (Str_phone1.contentEquals(" ")) {
                    mViewHolder.Img_phone1.setVisibility(View.INVISIBLE);
                } else {
                    if (Job_type.contentEquals("Exchange")) {
                        mViewHolder.ImgPhone2.setVisibility(View.INVISIBLE);


                    } else if (Job_type.contentEquals("Pull")) {
                        mViewHolder.ImgPhone2.setVisibility(View.INVISIBLE);
                    } else {
                        mViewHolder.ImgPhone2.setVisibility(View.VISIBLE);
                    }

                }

                mViewHolder.contactnumber2.setText(separated2[1]);
                StrPhone2 = separated2[1];
                if (StrPhone2.contentEquals("")) {
                    mViewHolder.ImgPhone2.setVisibility(View.INVISIBLE);
                } else if (StrPhone2.contentEquals(" ")) {
                    mViewHolder.ImgPhone2.setVisibility(View.INVISIBLE);
                } else {
                    if (Job_type.contentEquals("Exchange")) {
                        mViewHolder.ImgPhone2.setVisibility(View.INVISIBLE);


                    } else if (Job_type.contentEquals("Pull")) {
                        mViewHolder.ImgPhone2.setVisibility(View.INVISIBLE);
                    } else {
                        mViewHolder.ImgPhone2.setVisibility(View.VISIBLE);
                    }
                }
                mViewHolder.rlv_step2.setVisibility(View.VISIBLE);

            } catch (java.lang.IndexOutOfBoundsException e) {
                e.printStackTrace();
                mViewHolder.rlv_step2.setVisibility(View.GONE);
            }

            try {
                String[] separated3 = BinType1.split(",,");
                mViewHolder.bintype.setText(separated3[0]);
                mViewHolder.bintype2.setText(separated3[1]);
                mViewHolder.rlv_step2.setVisibility(View.VISIBLE);
            } catch (java.lang.IndexOutOfBoundsException e) {
                e.printStackTrace();
                mViewHolder.rlv_step2.setVisibility(View.GONE);
            }
            try {
                String[] separated4 = WasteType1.split(",,");
                mViewHolder.wastetype.setText(separated4[0]);
                mViewHolder.wastetype2.setText(separated4[1]);
                mViewHolder.rlv_step2.setVisibility(View.VISIBLE);
            } catch (java.lang.IndexOutOfBoundsException e) {
                e.printStackTrace();
                mViewHolder.rlv_step2.setVisibility(View.GONE);
            }
            try {
                String[] separated5 = Remarks1.split(",,");
                String strrmks1 = separated5[0];
                if (strrmks1.contentEquals("null")) {
                    strrmks1 = "";
                }
                mViewHolder.remarksforstep.setText(strrmks1);
                String strrmks12 = separated5[1];
                if (strrmks12.contentEquals("null")) {
                    strrmks12 = "";
                }
                mViewHolder.remarksforstep2.setText(strrmks12);
                mViewHolder.rlv_step2.setVisibility(View.VISIBLE);
            } catch (java.lang.IndexOutOfBoundsException e) {
                e.printStackTrace();
                mViewHolder.rlv_step2.setVisibility(View.GONE);
            }
            String collect_payment = jobs_steps.get(position).get("Collect_payment");
            String collect_paymenttt = "";
            try {
                String[] separated = collect_payment.split(",,");
                collect_paymenttt = separated[0];
                if (collect_paymenttt.contentEquals("true")) {
                    mViewHolder.amounttocollected.setText("Amount to be collected $" + subscriptionarray.get(position).get("Price"));
                    mViewHolder.amounttocollected.setVisibility(View.VISIBLE);
                } else {
                    mViewHolder.amounttocollected.setText("");
                    mViewHolder.amounttocollected.setVisibility(View.INVISIBLE);
                }

            } catch (java.lang.IndexOutOfBoundsException e) {
                e.printStackTrace();

            }

        } catch (java.lang.IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        mViewHolder.btn_acknowledge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Project_site = subscriptionarray.get(position).get("Project_site");
                String payment_term = subscriptionarray.get(position).get("Payment_term");
                mViewHolder.Job_number = subscriptionarray.get(position).get("Jobnumber");
                mViewHolder.Job_additional_charge = subscriptionarray.get(position).get("Job_additional_charges");
                mViewHolder.Project_site_id = subscriptionarray.get(position).get("Project_site_id");
                mViewHolder.Jobid = subscriptionarray.get(position).get("id");
                mViewHolder.Next_status = subscriptionarray.get(position).get("Nextstatus");
                Next_statuss = mViewHolder.Next_status;
                Jobidd = mViewHolder.Jobid;
                mViewHolder.current_status = subscriptionarray.get(position).get("jobstatus");
                current_statuss = mViewHolder.current_status;
                String job_step_id1 = jobs_steps.get(position).get("job_step_id1");
                try {
                    String[] separated = job_step_id1.split(",,");
                    mViewHolder.Job_stepid = separated[0];

                } catch (java.lang.IndexOutOfBoundsException e) {
                    e.printStackTrace();

                }
                String bin_number = jobs_steps.get(position).get("bin_number");
                String bin_numberr = "";
                try {
                    String[] separated = bin_number.split(",,");
                    bin_numberr = separated[0];

                } catch (java.lang.IndexOutOfBoundsException e) {
                    e.printStackTrace();

                }
                String collect_payment = jobs_steps.get(position).get("Collect_payment");
                String collect_paymenttt = "";
                try {
                    String[] separated = collect_payment.split(",,");
                    collect_paymenttt = separated[0];

                } catch (java.lang.IndexOutOfBoundsException e) {
                    e.printStackTrace();

                }

                if (Status.contentEquals("In Progress")) {

                    Intent i1 = new Intent(context, feedback.class);
                    i1.putExtra("Job_number", mViewHolder.Job_number);
                    i1.putExtra("Job_additional_charge", mViewHolder.Job_additional_charge);
                    i1.putExtra("Project_site_id", mViewHolder.Project_site_id);
                    i1.putExtra("Jobid", mViewHolder.Jobid);
                    i1.putExtra("Job_stepid", mViewHolder.Job_stepid);
                    i1.putExtra("Next_status", mViewHolder.Next_status);
                    i1.putExtra("current_status", mViewHolder.current_status);
                    i1.putExtra("Bin_number", bin_numberr);
                    i1.putExtra("payment_term", payment_term);
                    i1.putExtra("Project_site", Project_site);

                    i1.putExtra("Collect_payment", collect_paymenttt);

                    context.startActivity(i1);
                    ((Activity) context).finish();
                } else if (Status.contentEquals("Changed")) {
                    //RelativeLayout rlv_step1, rlv_step2;

                    job_step_id1 = jobs_steps.get(position).get("job_step_id1");
                    try {
                        String[] separated = job_step_id1.split(",,");
                        mViewHolder.Job_stepid = separated[1];

                    } catch (java.lang.IndexOutOfBoundsException e) {
                        e.printStackTrace();

                    }
                    bin_number = jobs_steps.get(position).get("bin_number");
                    bin_numberr = "";
                    try {
                        String[] separated = bin_number.split(",,");
                        bin_numberr = separated[1];

                    } catch (java.lang.IndexOutOfBoundsException e) {
                        e.printStackTrace();

                    }

                    Intent i1 = new Intent(context, feedback.class);
                    i1.putExtra("Job_number", mViewHolder.Job_number);
                    i1.putExtra("Job_additional_charge", mViewHolder.Job_additional_charge);
                    i1.putExtra("Project_site_id", mViewHolder.Project_site_id);
                    i1.putExtra("Jobid", mViewHolder.Jobid);
                    i1.putExtra("Job_stepid", mViewHolder.Job_stepid);
                    i1.putExtra("Next_status", mViewHolder.Next_status);
                    i1.putExtra("current_status", mViewHolder.current_status);
                    i1.putExtra("Bin_number", bin_numberr);
                    i1.putExtra("payment_term", payment_term);
                    i1.putExtra("Project_site", Project_site);
                    i1.putExtra("Collect_payment", collect_paymenttt);

                    context.startActivity(i1);
                    ((Activity) context).finish();
                } else if (Status.contentEquals("Shifted")) {

                    job_step_id1 = jobs_steps.get(position).get("job_step_id1");
                    try {
                        String[] separated = job_step_id1.split(",,");
                        mViewHolder.Job_stepid = separated[1];

                    } catch (java.lang.IndexOutOfBoundsException e) {
                        e.printStackTrace();

                    }
                    bin_number = jobs_steps.get(position).get("bin_number");
                    bin_numberr = "";
                    try {
                        String[] separated = bin_number.split(",,");
                        bin_numberr = separated[1];

                    } catch (java.lang.IndexOutOfBoundsException e) {
                        e.printStackTrace();

                    }

                    Intent i1 = new Intent(context, feedback.class);
                    i1.putExtra("Job_number", mViewHolder.Job_number);
                    i1.putExtra("Job_additional_charge", mViewHolder.Job_additional_charge);
                    i1.putExtra("Project_site_id", mViewHolder.Project_site_id);
                    i1.putExtra("Jobid", mViewHolder.Jobid);
                    i1.putExtra("Job_stepid", mViewHolder.Job_stepid);
                    i1.putExtra("Next_status", mViewHolder.Next_status);
                    i1.putExtra("current_status", mViewHolder.current_status);
                    i1.putExtra("Bin_number", bin_numberr);
                    i1.putExtra("payment_term", payment_term);
                    i1.putExtra("Project_site", Project_site);
                    i1.putExtra("Collect_payment", collect_paymenttt);

                    context.startActivity(i1);
                    ((Activity) context).finish();
                } else if (Status.contentEquals("pulled")) {

                    job_step_id1 = jobs_steps.get(position).get("job_step_id1");
                    try {
                        String[] separated = job_step_id1.split(",,");
                        mViewHolder.Job_stepid = separated[1];

                    } catch (java.lang.IndexOutOfBoundsException e) {
                        e.printStackTrace();

                    }
                    bin_number = jobs_steps.get(position).get("bin_number");
                    bin_numberr = "";
                    try {
                        String[] separated = bin_number.split(",,");
                        bin_numberr = separated[1];

                    } catch (java.lang.IndexOutOfBoundsException e) {
                        e.printStackTrace();

                    }

                    Intent i1 = new Intent(context, feedback.class);
                    i1.putExtra("Job_number", mViewHolder.Job_number);
                    i1.putExtra("Job_additional_charge", mViewHolder.Job_additional_charge);
                    i1.putExtra("Project_site_id", mViewHolder.Project_site_id);
                    i1.putExtra("Jobid", mViewHolder.Jobid);
                    i1.putExtra("Job_stepid", mViewHolder.Job_stepid);
                    i1.putExtra("Next_status", mViewHolder.Next_status);
                    i1.putExtra("current_status", mViewHolder.current_status);
                    i1.putExtra("Bin_number", bin_numberr);
                    i1.putExtra("payment_term", payment_term);
                    i1.putExtra("Project_site", Project_site);
                    i1.putExtra("Collect_payment", collect_paymenttt);

                    context.startActivity(i1);
                    ((Activity) context).finish();
                } else {

                    new Status_update().execute();
                    //Submit_Update();
                }

            }
        });

        return convertView;
    }

    private class MyViewHolder {
        TextView Additionalremarks, job_number, txt_datetime, txt_jobtype, amounttocollected, customername, personincharge, contactnumber, siteadess, bintype, wastetype, remarksforstep, personincharge2, txt_person_in___, contactnumber2, siteadess2, bintype2, wastetype2, remarksforstep2, Txt_contperson;
        ImageView img_signature1, photo1, img_signature2, photo2;
        ;
        TextView collection_method1, amountcollected1, drivernotes1, completedtime1, collection_method2, amountcollected2, drivernotes2, completedtime2, Signedby1, signedby2;
        RelativeLayout rlv_step1, rlv_step2, rlv_holder1, Rlvholder2;
        Button btn_acknowledge;
        ImageView img_location, img_location_2, Img_phone1, ImgPhone2;
        String Job_number = "", Job_additional_charge = "", Project_site_id = "", Jobid = "", Job_stepid = "", Next_status = "", current_status = "";

        public MyViewHolder(View item) {

            job_number = (TextView) item.findViewById(R.id.text_date_time);
            Additionalremarks = (TextView) item.findViewById(R.id.textView15);
            txt_datetime = (TextView) item.findViewById(R.id.textView7);
            txt_jobtype = (TextView) item.findViewById(R.id.textView10);
            amounttocollected = (TextView) item.findViewById(R.id.textView11);
            customername = (TextView) item.findViewById(R.id.textView14);
            //personincharge = (TextView) item.findViewById(R.id.textView14);
            //customername = (TextView) item.findViewById(R.id.textView14);
            personincharge = (TextView) item.findViewById(R.id.textView18);
            txt_person_in___ = (TextView) item.findViewById(R.id.textView17p);
            contactnumber = (TextView) item.findViewById(R.id.textView200);
            siteadess = (TextView) item.findViewById(R.id.textView22);
            bintype = (TextView) item.findViewById(R.id.textView23);
            wastetype = (TextView) item.findViewById(R.id.textView25);
            remarksforstep = (TextView) item.findViewById(R.id.textView27);
            personincharge2 = (TextView) item.findViewById(R.id.textView188);
            contactnumber2 = (TextView) item.findViewById(R.id.textView20);
            siteadess2 = (TextView) item.findViewById(R.id.textView222);
            bintype2 = (TextView) item.findViewById(R.id.textView233);
            wastetype2 = (TextView) item.findViewById(R.id.textView255);
            Txt_contperson = (TextView) item.findViewById(R.id.textView19p);
            remarksforstep2 = (TextView) item.findViewById(R.id.textView277);
            rlv_step1 = (RelativeLayout) item.findViewById(R.id.rlv_step1);
            rlv_step2 = (RelativeLayout) item.findViewById(R.id.rlv_step2);
            btn_acknowledge = (Button) item.findViewById(R.id.button4);
            img_location = (ImageView) item.findViewById(R.id.imageView66);
            img_location_2 = (ImageView) item.findViewById(R.id.imageView664);
            Img_phone1 = (ImageView) item.findViewById(R.id.imageView4);
            ImgPhone2 = (ImageView) item.findViewById(R.id.imageView5_phone);


            //////////////////////////////////////////////////////////////////////////
            rlv_holder1 = (RelativeLayout) item.findViewById(R.id.rlv_holder);
            Rlvholder2 = (RelativeLayout) item.findViewById(R.id.rlv_holder2);
            collection_method1 = (TextView) item.findViewById(R.id.textView41);
            amountcollected1 = (TextView) item.findViewById(R.id.textView43);
            drivernotes1 = (TextView) item.findViewById(R.id.textView45);
            completedtime1 = (TextView) item.findViewById(R.id.textView47);
            collection_method2 = (TextView) item.findViewById(R.id.textView411);
            amountcollected2 = (TextView) item.findViewById(R.id.textView433);
            drivernotes2 = (TextView) item.findViewById(R.id.textView455);
            completedtime2 = (TextView) item.findViewById(R.id.textView477);
            img_signature1 = (ImageView) item.findViewById(R.id.imageView6);
            photo1 = (ImageView) item.findViewById(R.id.imageView11);
            img_signature2 = (ImageView) item.findViewById(R.id.imageView6662);
            photo2 = (ImageView) item.findViewById(R.id.imageView111);
            Signedby1 = (TextView) item.findViewById(R.id.textView36);
            signedby2 = (TextView) item.findViewById(R.id.textView51);
            //  collection_method1 = ()

//            tvDesc = (TextView) item.findViewById(R.id.tvDesc);

        }
    }

    private class Status_update extends AsyncTask<Void, String, Void> implements DialogInterface.OnCancelListener {


        JSONObject jsonnode, json_User;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // mProgressHUD = ProgressHUD.show(Login_Screen.this, "Connecting", true, true, this);
            // Showing progress dialog
            pDialog = new ProgressDialog(context);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();


        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            publishProgress("Please wait...");


            ServiceHandler sh = new ServiceHandler();

            List<NameValuePair> params = new ArrayList<NameValuePair>();

            // Making a request to url and getting response
            //String url = "http://api.androidhive.info/contacts/";
            String str_url = "http://112.196.3.42:8298/v1/Job/UpdateJobStatus?JobId=" + Jobidd + "&nextstatus=" + Next_statuss + "&driverid=" + Driver_id + "&currentstatus=" + current_statuss;
            String newurl = str_url.replaceAll(" ", "%20");
            jsonStr = sh.makeServiceCall_withHeader(newurl, 2, params, Access_tocken);

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
            SharedPreferences shared = context.getSharedPreferences("Tidy_waste_management", context.MODE_PRIVATE);
            SharedPreferences.Editor editor = shared.edit();

            editor.putString("Check_screen", "Adapter");
            editor.putString("Next_status", Next_statuss);
            editor.commit();
            Intent i1 = new Intent(context, Home_screen.class);
            context.startActivity(i1);
            ((Activity) context).finish();
            //context.f


        }

        @Override
        public void onCancel(DialogInterface dialog) {

        }
    }

    private void Submit_Update() {

        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Uploading, please wait...");
        pDialog.show();
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://112.196.3.42:8298/v1/Job/UpdateJobStatus?JobId=" + Jobidd + "&nextstatus=" + Next_statuss + "&driverid=" + Driver_id + "&currentstatus=" + current_statuss,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pDialog.dismiss();
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
                        pDialog.dismiss();
                        Toast.makeText(context, "No internet connection", Toast.LENGTH_LONG).show();

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
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(stringRequest);
        }
    }


}