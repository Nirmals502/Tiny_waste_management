//package Fragments;
//
///**
// * Created by Mr singh on 3/8/2018.
// */
//
//import android.annotation.SuppressLint;
//import android.app.Activity;
//import android.app.ProgressDialog;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.ListView;
//import android.widget.TextView;
//
//import org.apache.http.NameValuePair;
//import org.apache.http.message.BasicNameValuePair;
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//import Service_handler.SERVER;
//import Service_handler.ServiceHandler;
//import adapter.JobType_adapter;
//import mobile.tiny_waste_management.Home_screen;
//import mobile.tiny_waste_management.R;
//import mobile.tiny_waste_management.app.Config;
//import mobile.tiny_waste_management.feedback;
//
//
//@SuppressLint("ValidFragment")
//public class Notification_list extends Activity {
//
//    ListView Lv_tabs;
//    ArrayList<String> arrPackage = new ArrayList<>();
//    ImageView img_back;
//
//    @SuppressLint("ValidFragment")
//    public Notification_list(ArrayList<HashMap<String, String>> ackno) {
//
//
//        // Required empty public constructor
//        // System.out.println("value................................"+str);
////        Toast.makeText(getActivity(),str,Toast.LENGTH_LONG ).show();
//
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.notification_lv_layout, container, false);
//    }
//
//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        Lv_tabs = (ListView) view.findViewById(R.id.listView_notification);
//        img_back = (ImageView) view.findViewById(R.id.imageView9);
//        Set<String> set = new HashSet<String>();
//        SharedPreferences pref = getActivity().getSharedPreferences(Config.SHARED_PREF, 0);
//        set = pref.getStringSet("Notification_LIST", null);
//        arrPackage.addAll(set);
//        ArrayAdapter<String> arrayAdapter =
//                new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, arrPackage);
//        // Set The Adapter
//        Lv_tabs.setAdapter(arrayAdapter);
//
//        img_back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i1 = new Intent(getActivity(), Home_screen.class);
//
//                startActivity(i1);
//
//                //finish();
//            }
//        });
//        // if (Statuss.contentEquals("Search")) {
//
//
//    }
//
//
//}
