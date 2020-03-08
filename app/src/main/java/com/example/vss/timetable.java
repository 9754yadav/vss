package com.example.vss;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import static com.example.vss.Data_activity.SOAP_ACTION;

public class timetable extends AppCompatActivity {
    String msg;
    String Error_Result;
    int i;
    int j;
    ListView list;
    String[][] list1;
    public static String webmethod="Dept_tt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);
        CallWebservice c=new CallWebservice();
        c.execute();
    }
    public String Dept_tt() {
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
    public class CallWebservice extends AsyncTask<String, Void, Void> {
        public CallWebservice() {
        }

        protected Void doInBackground(String... arg0) {
            Dept_tt();
            return null;
        }

        protected void onPostExecute(Void result) {


        }
        protected void onPreExecute() {
        }
    }
    public void first_year(View view){
        String a=list1[0][0];
        Toast.makeText(getApplicationContext(),a+" ", Toast.LENGTH_LONG).show();


        switch (a)
        {

            case "bbi":
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com"));
                startActivity(intent);
                break;
            case "bscit":
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com"));
                startActivity(i);
                break;

        }
    }
}


