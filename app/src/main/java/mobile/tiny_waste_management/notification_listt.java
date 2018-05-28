package mobile.tiny_waste_management;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import mobile.tiny_waste_management.app.Config;

public class notification_listt extends AppCompatActivity {
    ListView Lv_tabs;
    ArrayList<String> arrPackage = new ArrayList<>();
    ImageView img_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_lv_layout);
        Lv_tabs = (ListView) findViewById(R.id.listView_notification);
        img_back = (ImageView) findViewById(R.id.imageView9);
        Set<String> set = new HashSet<String>();
        SharedPreferences pref = getSharedPreferences(Config.SHARED_PREF, 0);
        set = pref.getStringSet("Notification_LIST", null);
        try {
            if (set.size() != 0) {
                arrPackage.addAll(set);
                ArrayAdapter<String> arrayAdapter =
                        new ArrayAdapter<String>(notification_listt.this, android.R.layout.simple_list_item_1, arrPackage);
                // Set The Adapter
                Lv_tabs.setAdapter(arrayAdapter);
            } else {
                Toast.makeText(notification_listt.this, "No notification found", Toast.LENGTH_LONG).show();
                Intent i1 = new Intent(notification_listt.this, Home_screen.class);

                startActivity(i1);

                finish();
            }
        } catch (java.lang.NullPointerException e) {
            e.printStackTrace();

        }



        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(notification_listt.this, Home_screen.class);

                startActivity(i1);

                finish();
            }
        });
    }
}
