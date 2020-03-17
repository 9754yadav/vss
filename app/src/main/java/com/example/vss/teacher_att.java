package com.example.vss;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

import static com.example.vss.Data_activity.SOAP_ACTION;
import static com.example.vss.Data_activity.tea_id;

public class teacher_att extends AppCompatActivity{

    Spinner spinner,spinc,spint;
    Spinner spin;
    String msg;
    String Error_Result;
    TextView tv;
    int i1;
    int j,i;
    ListView list;
    String[][] list1;
    List<String> Type=new ArrayList<String>();
    List<String> T=new ArrayList<String>();
    public static String webmethod="attendence_course";
    public static String webmethod1="att_tea_name";
    ArrayList<String> spinnerArray = new ArrayList<String>();
    ArrayList<String> spinnerArray1 = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_att);
        spinc=(Spinner)findViewById(R.id.spinner5);
        tv = (TextView) findViewById(R.id.textv);
        CallWebservice1 c1=new CallWebservice1();
        c1.execute();

        spinc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                // do something upon option selection
                Data_activity.deptcorse =parent.getItemAtPosition(position).toString();
                // Toast.makeText(getApplicationContext(), Data_activity.deptcorse, Toast.LENGTH_LONG).show();

            }

// is me hai sir wo dropdown
            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                // can leave this empty
                //Data_activity.sematt =parent.getSelectedItem().toString();
            }
        });


        spin=(Spinner)findViewById(R.id.spin2);
       //spin.setOnItemSelectedListener(this);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                // do something upon option selection
                Data_activity.deptatt =parent.getItemAtPosition(position).toString();
                if(Data_activity.deptatt.equals("Select Department"))
                {
                   // Toast.makeText(getApplicationContext(), "Select department", Toast.LENGTH_LONG).show();

                }
                else {
                    CallWebservice c = new CallWebservice();
                    c.execute();
                 //   Toast.makeText(getApplicationContext(), Data_activity.deptatt, Toast.LENGTH_LONG).show();
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                // can leave this empty
                //Data_activity.sematt =parent.getSelectedItem().toString();
            }
        });
        T.add("Select Department");
        T.add("B.SC(I.T)");
        T.add("B.B.I");
        T.add("B.M.M");
        T.add("B.M.S");
        T.add("B.A.F");
        T.add("B.F.M");
        T.add("B.COM");
        ArrayAdapter<String> d=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,T);
        d.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(d);


        spinner=(Spinner)findViewById(R.id.spinner4);
        //spinner.setOnItemSelectedListener(this);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                // do something upon option selection
                Data_activity.sematt =parent.getItemAtPosition(position).toString();
                if(Data_activity.sematt.equals("Select semester"))
                {
                   // Toast.makeText(getApplicationContext(), "Select semester", Toast.LENGTH_LONG).show();

                }
                else {
                    CallWebservice c = new CallWebservice();
                    c.execute();
                   // Toast.makeText(getApplicationContext(), Data_activity.sematt, Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                // can leave this empty

                Data_activity.deptatt =parent.getSelectedItem().toString();
            }
        });
        Type.add("Select Semester");
        Type.add("Sem-1");
        Type.add("Sem-2");
        Type.add("Sem-3");
        Type.add("Sem-4");
        Type.add("Sem-5");
        Type.add("Sem-6");
        ArrayAdapter<String> dataAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,Type);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);


    }



    public String att_tea_name() {
        SoapObject request = new SoapObject(Data_activity.NAMESPACE, webmethod1);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(110);
        envelope.dotNet = true;
       request.addProperty("tea_id",Data_activity.tea_id);
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(Data_activity.URL);

        try {
            androidHttpTransport.call(SOAP_ACTION + webmethod1, envelope);
            SoapObject result = (SoapObject)envelope.bodyIn;
            if (result.getPropertyCount() > 0) {
                SoapObject Rows = (SoapObject)result.getProperty(0);
                this.i = Rows.getPropertyCount();
                this.list1 = new String[this.i][1];

                for(int nRow = 0; nRow < this.i; ++nRow) {
                    SoapObject Cols = (SoapObject)Rows.getProperty(nRow);
                    this.j = Cols.getPropertyCount();

                    for(int nCol = 0; nCol < this.j; ++nCol) {
                        this.list1[nRow][nCol] = Cols.getProperty(nCol).toString();
                    }
                }
            }

            this.Error_Result = result.toString();
            Log.i("Response", "" + result);
        } catch (Exception var9) {
            this.msg = var9.getMessage().toString();
            return var9.getMessage().toString();
        }

        return this.Error_Result;
    }

    public String attendence_course() {
        SoapObject request = new SoapObject(Data_activity.NAMESPACE, webmethod);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(110);
        envelope.dotNet = true;
        request.addProperty("dept",Data_activity.deptatt);
        request.addProperty("sem",Data_activity.sematt);
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(Data_activity.URL);

        try {
            androidHttpTransport.call(SOAP_ACTION + webmethod, envelope);
            SoapObject result = (SoapObject)envelope.bodyIn;
            if (result.getPropertyCount() > 0) {
                SoapObject Rows = (SoapObject)result.getProperty(0);
                this.i1 = Rows.getPropertyCount();
                this.list1 = new String[this.i1][1];

                for(int nRow = 0; nRow < this.i1; ++nRow) {
                    SoapObject Cols = (SoapObject)Rows.getProperty(nRow);
                    this.j = Cols.getPropertyCount();

                    for(int nCol = 0; nCol < this.j; ++nCol) {
                        this.list1[nRow][nCol] = Cols.getProperty(nCol).toString();
                        spinnerArray.add(this.list1[nRow][nCol]);
                    }
                }
            }

            this.Error_Result = result.toString();
            Log.i("Response", "" + result);
        } catch (Exception var9) {
            this.msg = var9.getMessage().toString();
            return var9.getMessage().toString();
        }

        return this.Error_Result;
    }
    public class CallWebservice extends AsyncTask<String, Void, Void> {
        public CallWebservice() {
        }

        protected Void doInBackground(String... arg0) {
            attendence_course();
            return null;
        }

        protected void onPostExecute(Void result) {

            String[] values = new String[1];
            values[0]="";
         if (i1 > 0)
         {
             ArrayAdapter spinnerArrayAdapter1 = new ArrayAdapter(getApplicationContext(),
                     android.R.layout.simple_spinner_dropdown_item,
                     values);
             spinc.setAdapter(spinnerArrayAdapter1);
             ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(getApplicationContext(),
                        android.R.layout.simple_spinner_dropdown_item,
                        spinnerArray);
                spinc.setAdapter(spinnerArrayAdapter);


            } else {
                Toast.makeText(getApplicationContext(), "No Data Found  "+i1+"  "+j, Toast.LENGTH_LONG).show();
            }

        }
        protected void onPreExecute() {
        }
    }
    public class CallWebservice1 extends AsyncTask<String, Void, Void> {
        public CallWebservice1() {
        }

        protected Void doInBackground(String... arg0) {
            att_tea_name();
            return null;
        }

        protected void onPostExecute(Void result) {
            String[] values = new String[i];
            if (i > 0) {

                    tv.setText(list1[0][0]);
                    Data_activity.tea_name = list1[0][0];

                } else {
                    Toast.makeText(getApplicationContext(), "No Data Found", Toast.LENGTH_LONG).show();
                }

            }
        }
    public void next(View view)
    {
        Intent it= new Intent(this,tea_attendance_list.class);
        startActivity(it);
    }
    }