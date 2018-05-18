package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.HashMap;

import mobile.tiny_waste_management.R;


/**
 * Created by Nirmal on 6/24/2016.
 */
public class adittional_charge_adapter extends BaseAdapter {

    ArrayList<HashMap<String, String>> subscriptionarray = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> Check__ = new ArrayList<HashMap<String, String>>();
    LayoutInflater inflater;
    Context context;


    public adittional_charge_adapter(Context context, ArrayList<HashMap<String, String>> Array_subscription, ArrayList<HashMap<String, String>> Check_value) {
        this.subscriptionarray = Array_subscription;
        this.Check__ = Check_value;
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
    public View getView(final int position, View convertView, final ViewGroup parent) {
        MyViewHolder mViewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.adittional_charges_checkbox, parent, false);
            mViewHolder = new MyViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (MyViewHolder) convertView.getTag();
        }

        try {
            String str_real_value = (subscriptionarray.get(position).get("charges_name"));
            mViewHolder.Check_box.setText(str_real_value);
            String value_determined = "false";
            for (int count = 0; count < Check__.size(); count++) {
                String value_analysed = Check__.get(count).get("charges_name");
                if (value_analysed.contentEquals(str_real_value)) {
                    value_determined = "true";
                }
            }

            if (value_determined.contentEquals("true")) {
                mViewHolder.Check_box.setChecked(true);
                mViewHolder.Check_box.setEnabled(false);
            } else {
                mViewHolder.Check_box.setChecked(false);
                mViewHolder.Check_box.setEnabled(true);
            }
        } catch (java.lang.IndexOutOfBoundsException e) {
            e.printStackTrace();

        }
        mViewHolder.Check_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ((ListView) parent).performItemClick(buttonView, position, 0); // Let the event be handled in onItemClick()
            }
        });
        //if()


        return convertView;
    }

    private class MyViewHolder {
        CheckBox Check_box;

        public MyViewHolder(View item) {
            Check_box = (CheckBox) item.findViewById(R.id.checkBox);

//            tvDesc = (TextView) item.findViewById(R.id.tvDesc);

        }
    }
}