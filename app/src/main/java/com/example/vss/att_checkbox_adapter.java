package com.example.vss;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class att_checkbox_adapter extends BaseAdapter {
    String str[];

    private Context mContext;
    ArrayList<att_checkbox_Myitem> mylist=new ArrayList<>();


    public att_checkbox_adapter(ArrayList<att_checkbox_Myitem> itemArray,Context mContext) {
        super();
        str =new String[Data_activity.NOS];
        Data_activity.status=new String[Data_activity.NOS];
        for(int i=0;i<Data_activity.NOS;i++)
        {
            str[i]="Absent";
        }
        this.mContext = mContext;
        mylist=itemArray;
    }

    @Override
    public int getCount() {
        return mylist.size();

    }

    @Override
    public String getItem(int position) {
        return mylist.get(position).toString();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void onItemSelected(int position) {

    }

    public class ViewHolder {
        public TextView nametext;
        public CheckBox tick;

    }

    @Override
    public View getView(final int position, View convertView,
                        ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder view = null;
        LayoutInflater inflator = ((Activity) mContext).getLayoutInflater();

        if (view == null) {
            view = new ViewHolder();
            convertView = inflator.inflate(  R.layout.myadapter, null);

            view.nametext = (TextView) convertView.findViewById(R.id.adaptertextview);
            view.tick=(CheckBox)convertView.findViewById(R.id.adaptercheckbox);
            view.tick.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView,
                                             boolean isChecked) {
                    int getPosition = (Integer) buttonView.getTag(); // Here
                    // we get  the position that we have set for the checkbox using setTag.
                  mylist.get(getPosition).setChecked(buttonView.isChecked()); // Set the value of checkbox to maintain its state.

                    if (isChecked) {
                        //do sometheing here
                       // Toast.makeText(this, "No Data Found", Toast.LENGTH_LONG).show();

                        str[getPosition]="Present";
                        Data_activity.status[getPosition]="Present";
                        Log.d("Roll no",""+Data_activity.attlist[getPosition][0]+" is "+str[getPosition]);
                        Data_activity.att_status=str[getPosition];
                        Data_activity.att_roll=Data_activity.attlist[getPosition][0];

                    }
                    else
                    {
                        Data_activity.status[getPosition]="Absent";
                        str[getPosition]="Absent";
                        Log.d("Roll no",""+Data_activity.attlist[getPosition][0]+" is "+str[getPosition]);
                        // code here
                        Data_activity.att_status=str[getPosition];
                        Data_activity.att_roll=Data_activity.attlist[getPosition][0];
                    }
                }
            });

            convertView.setTag(view);
        } else {
            view = (ViewHolder) convertView.getTag();
        }

        view.tick.setTag(position);
        view.nametext.setText("" + mylist.get(position).getTitle());
        view.tick.setChecked(mylist.get(position).isChecked());

        return convertView;
    }

}
