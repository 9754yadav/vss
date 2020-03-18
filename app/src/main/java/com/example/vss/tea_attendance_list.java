package com.example.vss;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

import static com.example.vss.Data_activity.SOAP_ACTION;

public class tea_attendance_list extends AppCompatActivity {
    String msg,resp1;
    String Error_Result;
    int i;
    int j;

    ListView list;
    String[][] list1;
    public static String webmethod="getst_att_list";
    public static String webmethod1="save";
    ArrayList<att_checkbox_Myitem> myitems=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tea_attendance_list);
        list=(ListView) findViewById(R.id.lvst);
      CallWebservice c=new CallWebservice();
     c.execute();
      //  Toast.makeText(getApplicationContext(), Data_activity.deptatt, Toast.LENGTH_LONG).show();

    }
    public String getst_att() {

        SoapObject request = new SoapObject(Data_activity.NAMESPACE, webmethod);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(110);
        envelope.dotNet = true;
        // request.addProperty("prod_type", datafile.Food_Type);

        request.addProperty("dept",Data_activity.deptatt);
        request.addProperty("sem",Data_activity.sematt);
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(Data_activity.URL);

        try {
            androidHttpTransport.call(SOAP_ACTION + webmethod, envelope);
            SoapObject result = (SoapObject)envelope.bodyIn;
            if (result.getPropertyCount() > 0) {
                SoapObject Rows = (SoapObject)result.getProperty(0);
                this.i = Rows.getPropertyCount();
                this.list1 = new String[this.i][1];
                Data_activity.attlist=new String[this.i][1];
                for(int nRow = 0; nRow < this.i; ++nRow) {
                    SoapObject Cols = (SoapObject)Rows.getProperty(nRow);
                    this.j = Cols.getPropertyCount();

                    for(int nCol = 0; nCol < this.j; ++nCol) {
                        this.list1[nRow][nCol] = Cols.getProperty(nCol).toString();
                        Data_activity.attlist[nRow][nCol] = Cols.getProperty(nCol).toString();
                    }
                }
            }

            this.Error_Result = result.toString();
            Log.i("Response", "" + result);
        } catch (Exception var9) {
            Log.i(" error" ,"" + var9.getMessage().toString());
            return var9.getMessage().toString();
        }

        return this.Error_Result;

    }
    public class CallWebservice extends AsyncTask<String, Void, Void> {
        public CallWebservice() {
        }

        protected Void doInBackground(String... arg0) {
            getst_att();
            return null;
        }

        protected void onPostExecute(Void result) {
            String[] values = new String[i];
            if (i > 0) {


              /* // values[0]= "        Roll No";
                int at=Integer.parseInt(list1[0][0]);

                for(int p = 0; p < at; ++p) {
                    myitems.add(new att_checkbox_Myitem(""+(p+1),false));

                }
                att_checkbox_adapter adapter=new att_checkbox_adapter(myitems,tea_attendance_list.this);
                list.setAdapter(adapter);
*/              Data_activity.NOS = i;
                for(int p = 0; p < i; ++p) {
                    myitems.add(new att_checkbox_Myitem(""+list1[p][0],false));
                   StringBuilder sb = new StringBuilder();
                    sb.append("ROll");
                    Toast.makeText(getApplicationContext(),""+i, Toast.LENGTH_LONG).show();

                    sb.append(list1[p][0]);
                    values[p] = sb.toString();
                }

                att_checkbox_adapter adapter=new att_checkbox_adapter(myitems,tea_attendance_list.this);
                list.setAdapter(adapter);
            } else  {
                Toast.makeText(getApplicationContext(), "No Data Found", Toast.LENGTH_LONG).show();
            }

        }
        protected void onPreExecute() {
        }
    }

    public void save()
    {

        for(int i=0;i<Data_activity.NOS;i++) {

            SoapObject request = new SoapObject(Data_activity.NAMESPACE, webmethod1);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(110);
            envelope.dotNet = true;
            // request.addProperty("prod_type", datafile.Food_Type);

            request.addProperty("dept", Data_activity.deptatt);
            request.addProperty("sem", Data_activity.sematt);
            request.addProperty("teacher", Data_activity.tea_name);
            request.addProperty("course", Data_activity.deptcorse);
            request.addProperty("date",Data_activity.dcal);
            request.addProperty("roll", Data_activity.attlist[i][0]);
            request.addProperty("status", Data_activity.status[i]);
            //request.addProperty("start_time",utype);
            //request.addProperty("end_time",Data_activity.deptatt);

            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(Data_activity.URL);

            try {
                androidHttpTransport.call(SOAP_ACTION + webmethod1, envelope);
                SoapObject result = (SoapObject) envelope.bodyIn;
                SoapPrimitive response =  (SoapPrimitive)envelope.getResponse();

                resp1=response.toString();
               // return  resp1;



            } catch (Exception var9) {
                Log.i(" error" ,"" + var9.getMessage().toString());
               // return var9.getMessage().toString();
            }
        }
            //return this.Error_Result;

    }
    public class CallWebservice1 extends AsyncTask<String, Void, Void> {
        public CallWebservice1() {
        }

        protected Void doInBackground(String... arg0) {
           save();
            return null;
        }

        protected void onPostExecute(Void result)
        {
Toast.makeText(getApplicationContext(),resp1+" ",Toast.LENGTH_LONG).show();
        }
        protected void onPreExecute() {
        }

    }
    public void save_att(View view)
    {
        CallWebservice1 c1=new CallWebservice1();
        c1.execute();
    }


}
