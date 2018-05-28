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
public class aditional_charges_for_completed extends BaseAdapter {

    ArrayList<HashMap<String, String>> subscriptionarray = new ArrayList<HashMap<String, String>>();
 //   ArrayList<HashMap<String, String>> Check__ = new ArrayList<HashMap<String, String>>();
    LayoutInflater inflater;
    Context context;


    public aditional_charges_for_completed(Context context, ArrayList<HashMap<String, String>> Array_subscription) {
        this.subscriptionarray = Array_subscription;
     //   this.Check__ = Check_value;
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
            convertView = inflater.inflate(R.layout.lv_aditional_charges_completed, parent, false);
            mViewHolder = new MyViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (MyViewHolder) convertView.getTag();
        }

        try {
            String str_real_value = (subscriptionarray.get(position).get("charges_name"));
            mViewHolder.Check_box.setText(str_real_value);

        } catch (java.lang.IndexOutOfBoundsException e) {
            e.printStackTrace();

        }




        return convertView;
    }

    private class MyViewHolder {
        TextView Check_box;

        public MyViewHolder(View item) {
            Check_box = (TextView) item.findViewById(R.id.checkBox);

//            tvDesc = (TextView) item.findViewById(R.id.tvDesc);

        }
    }
}