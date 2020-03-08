package com.example.vss;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import static com.example.vss.Data_activity.SOAP_ACTION;


public class stu_profile extends AppCompatActivity {
    String msg;
    String Error_Result;
    int i;
    int j;
    ListView list;
    String[][] list1;
    public static String webmethod="getst_profile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stu_profile);
        list=(ListView) findViewById(R.id.lvst);
        CallWebservice c=new CallWebservice();
        c.execute();

    }

    public String getstudent() {
        SoapObject request = new SoapObject(Data_activity.NAMESPACE, webmethod);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(110);
        envelope.dotNet = true;
       // request.addProperty("prod_type", datafile.Food_Type);

        request.addProperty("pnr",Data_activity.pnr);
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(Data_activity.URL);

        try {
            androidHttpTransport.call(SOAP_ACTION + webmethod, envelope);
            SoapObject result = (SoapObject)envelope.bodyIn;
            if (result.getPropertyCount() > 0) {
                SoapObject Rows = (SoapObject)result.getProperty(0);
                this.i = Rows.getPropertyCount();
                this.list1 = new String[this.i][3];

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
    public class CallWebservice extends AsyncTask<String, Void, Void> {
        public CallWebservice() {
        }

        protected Void doInBackground(String... arg0) {
            getstudent();
            return null;
        }

        protected void onPostExecute(Void result) {
            String[] values = new String[i];
            if (i > 0) {
                for(int p = 0; p < i; ++p) {
                    for(int q = 0; q < j; ++q) {
                    }

                    StringBuilder sb = new StringBuilder();
                    sb.append("PNR");

                    sb.append("\t\t\t\t\t"+list1[p][0]);
                    sb.append("\n\n\n");
                    sb.append("Name:");
                    sb.append("\t\t\t\t\t"+list1[p][1]);
                    sb.append("\n\n\n");
                    sb.append("YEAR");
                    sb.append("\t\t\t\t\t"+list1[p][2]);

                    values[p] = sb.toString();
                }

                ArrayAdapter<String> adapter = new ArrayAdapter(getBaseContext(),android.R.layout.simple_list_item_2, android.R.id.text1, values);
                list.setAdapter(adapter);
            } else {
                Toast.makeText(getApplicationContext(), "No Data Found", Toast.LENGTH_LONG).show();
            }

        }
        protected void onPreExecute() {
        }
    }
}
