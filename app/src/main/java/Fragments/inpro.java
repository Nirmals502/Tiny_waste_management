package Fragments;

/**
 * Created by Mr singh on 3/8/2018.
 */

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Service_handler.SERVER;
import Service_handler.ServiceHandler;
import adapter.JobType_adapter;
import mobile.tiny_waste_management.R;


@SuppressLint("ValidFragment")
public class inpro extends Fragment {
    Button acknoldege;
    SharedPreferences shared;
    ListView Lv_tabs;
    private ProgressDialog pDialog;
    String Access_tocken = "",Driver_id="";
    ArrayList<HashMap<String, String>> LIST_JOBS = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> JOBS_STEPS = new ArrayList<HashMap<String, String>>();
    String Statuss = "";
    TextView Txt_No_job_found;

    @SuppressLint("ValidFragment")
    public inpro(ArrayList<HashMap<String, String>> ackno, ArrayList<HashMap<String, String>> ackno2,String Status) {
        JOBS_STEPS = ackno;
        LIST_JOBS = ackno2;
        Statuss = Status;

        // Required empty public constructor
        // System.out.println("value................................"+str);
//        Toast.makeText(getActivity(),str,Toast.LENGTH_LONG ).show();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.listview_for_tabs, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        shared = getActivity().getSharedPreferences("Tidy_waste_management", getActivity().MODE_PRIVATE);
        Access_tocken = (shared.getString("Acess_tocken", "nologin"));
        Driver_id= (shared.getString("Driver_id", "nologin"));
        Lv_tabs = (ListView) view.findViewById(R.id.Lv_for_tabs);
        Txt_No_job_found = (TextView)view.findViewById(R.id.textJobs);
        // if (Statuss.contentEquals("Search")) {
        if (LIST_JOBS.size() == 0) {
            Txt_No_job_found.setVisibility(View.VISIBLE);
        }
        //      if (Statuss.contentEquals("Search")) {
//            if (LIST_JOBS.size() > 0) {
//
//            }
//        } else {
//            new JOBS_DATA().execute();
//        }
        JobType_adapter adapter = new JobType_adapter(getActivity(),
                LIST_JOBS, JOBS_STEPS);
        Lv_tabs.setAdapter(adapter);


//        acknoldege = (Button)view.findViewById(R.id.button4);
//        acknoldege.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i1 = new Intent(getActivity(),feedback.class);
//                startActivity(i1);
//            }
//        });

    }

    private class JOBS_DATA extends AsyncTask<Void, String, Void> implements DialogInterface.OnCancelListener {


        JSONObject jsonnode, json_User;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // mProgressHUD = ProgressHUD.show(Login_Screen.this, "Connecting", true, true, this);
            // Showing progress dialog
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
            LIST_JOBS.clear();
            JOBS_STEPS.clear();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            publishProgress("Please wait...");


            ServiceHandler sh = new ServiceHandler();
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(0);
            //nameValuePairs.add(new BasicNameValuePair("category", ID));


//            String jsonStr = sh.makeServiceCall("http://112.196.3.42:8298/v1/Job/GetAllJobByDriverid/149/Assigned",
//                    ServiceHandler.GET, nameValuePairs);
            String jsonStr = sh.makeServiceCall_withHeader("http://112.196.3.42:8298/v1/Job/GetAllJobByDriverid/"+Driver_id+"/Inprogress",
                    ServiceHandler.GET, nameValuePairs, Access_tocken);

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
                try {


                    for (int count = 0; count < jArr.length(); count++) {
                        JSONObject jsonObjj = null;
                        try {
                            jsonObjj = jArr.getJSONObject(count);
                            String jobtype = jsonObjj.getString("JobType");
                            String Price = jsonObjj.getString("Price");
                            String JobDate = jsonObjj.getString("JobDate");
                            String JobNumber = jsonObjj.getString("JobNumber");

                            String id = jsonObjj.getString("JobId");
                            String Customerr = jsonObjj.getString("Customer");
                            String JOBSteps = jsonObjj.getString("JobSteps");
                            JSONArray jArr_jobs_steps = null;
                            try {
                                jArr_jobs_steps = new JSONArray(JOBSteps);
                                String PersonInCharge1 = "";
                                String ContactNo1="";
                                String BinType1="";
                                String WasteType1="";
                                String Remarks1="";
                                String wastetype_name1 = "";
                                String bin_type_name1 = "";
                                String PersonInCharge2="";
                                String ContactNo2="";
                                String BinType2="";
                                String WasteType2="";
                                String Remarks2="";
                                String wastetype_name2 = "";
                                String bin_type_name2 = "";
                                //////////////////////////////////////////////////////
                                for (int count_step = 0; count_step < jArr_jobs_steps.length(); count_step++) {
                                    JSONObject jsonObj_steps = null;
                                    try {
                                        jsonObj_steps = jArr_jobs_steps.getJSONObject(count_step);
                                        String PersonInCharge = jsonObj_steps.getString("PersonInCharge");
                                        String ContactNo = jsonObj_steps.getString("ContactNo");
                                        String BinType = jsonObj_steps.getString("BinType");
                                        String WasteType = jsonObj_steps.getString("WasteType");
                                        String Remarks = jsonObj_steps.getString("Remarks");
                                        String wastetype_name = "";
                                        String bin_type_name = "";
                                        try {
                                            JSONObject Json_object = null;
                                            Json_object = new JSONObject(BinType);
                                            bin_type_name = Json_object.getString("BinTypeName");

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        try {
                                            JSONObject Json_object = null;
                                            Json_object = new JSONObject(WasteType);
                                            wastetype_name = Json_object.getString("WasteTypeName");

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        if(count_step==0) {
                                            PersonInCharge1 = PersonInCharge;
                                            ContactNo1 = ContactNo;
                                            BinType1 = bin_type_name;
                                            WasteType1 = wastetype_name;
                                            Remarks1 = Remarks;
                                        }
                                        if(count_step==1){
                                            PersonInCharge1= PersonInCharge1+","+PersonInCharge;
                                            ContactNo1 = ContactNo1+","+ContactNo;
                                            BinType1 = BinType1+","+bin_type_name;
                                            WasteType1=WasteType1+","+wastetype_name;
                                            Remarks1 = Remarks1+","+Remarks;
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                                HashMap<String, String> jobs_steps = new HashMap<String, String>();
                                jobs_steps.put("PersonInCharge", PersonInCharge1);
                                jobs_steps.put("ContactNo", ContactNo1);
                                jobs_steps.put("Remarks", Remarks1);
                                //  jobs_steps.put("Remarks", Remarks);
                                jobs_steps.put("wastetype_name", WasteType1);
                                jobs_steps.put("bin_type_name", BinType1);

                                JOBS_STEPS.add(jobs_steps);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            JSONObject Json_Cutomer = null;
                            String customer_name = "";
                            String Address = "";


                            try {
                                Json_Cutomer = new JSONObject(Customerr);
                                customer_name = Json_Cutomer.getString("CustomerName");
                                Address = Json_Cutomer.getString("AddressLine1");


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            HashMap<String, String> Search_result = new HashMap<String, String>();

                            // adding each child node to HashMap key => value
                            Search_result.put("jobtype", jobtype);
                            Search_result.put("Price", Price);
                            Search_result.put("customer_name", customer_name);
                            Search_result.put("JobDate", JobDate);
                            Search_result.put("Jobnumber", JobNumber);
                            Search_result.put("id", id);
                            Search_result.put("Address", Address);
                            Search_result.put("Check_fragment", "inprogress");




                            // adding hashmap to arraylist
                            LIST_JOBS.add(Search_result);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (java.lang.NullPointerException e) {
                    e.printStackTrace();
                    Log.e("ServiceHandler", "Couldn't get any data from the url");
                }
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
            JobType_adapter adapter = new JobType_adapter(getActivity(),
                    LIST_JOBS, JOBS_STEPS);
            Lv_tabs.setAdapter(adapter);
        }

        @Override
        public void onCancel(DialogInterface dialog) {

        }
    }
}
