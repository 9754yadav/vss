package com.example.vss;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

public class login_main extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
EditText txtemail,txtpasswd;
Button btn_login;
Spinner spinner;
String utype;
    String usertype="Select-user-type";
    public static String webmethod="login";
     String email,password;
    String resp1;
    List<String> Type=new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);


        txtemail=(EditText)findViewById(R.id.txt_email);
         txtpasswd=(EditText)findViewById(R.id.txt_passwd);
         btn_login=(Button)findViewById(R.id.btn_login);
         spinner=(Spinner)findViewById(R.id.spinner1);
        spinner.setOnItemSelectedListener(this);
        Type.add("Select-user-type");
        Type.add("Teacher");
        Type.add("Student");
        ArrayAdapter<String> dataAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,Type);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);



         btn_login.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String item=spinner.getSelectedItem().toString();
                email=txtemail.getText().toString().trim();
                  password=txtpasswd.getText().toString().trim();
                  if(email.equals("")||password.equals(""))
                  {
                      Toast.makeText(getApplicationContext(), "Password and username can not  blank", Toast.LENGTH_SHORT).show();
                  }
                  else if(utype.equals("Select-user-type"))
                  {
                      Toast.makeText(getApplicationContext(), "Select the user type", Toast.LENGTH_SHORT).show();
                  }
                  else {
                      CallWebservice c = new CallWebservice();
                      c.execute();
                  }




             }
         });


    }

    public String validatelogin()
    {
        SoapObject request = new SoapObject(Data_activity.NAMESPACE, webmethod);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);

        envelope.dotNet = true;
        request.addProperty("username",email);
        request.addProperty("passwd",password);
        request.addProperty("utype",utype);

        envelope.setOutputSoapObject(request);
        Log.d("my request",String.valueOf(request));
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(Data_activity.URL);

        try
        {
            androidHttpTransport.call(Data_activity.NAMESPACE+webmethod, envelope);
            SoapPrimitive response =  (SoapPrimitive)envelope.getResponse();

            resp1=response.toString();
            return  resp1;
        }
        catch (Exception e)
        {
            resp1=e.getMessage().toString();
            return e.getMessage().toString();
        }
    }



    class  CallWebservice extends AsyncTask<String, Void, Void>
    {
        @Override
        protected Void doInBackground(String... arg0)
        {
            validatelogin();
            return null;
        }
        protected void onPostExecute(Void result)
        {

            Toast.makeText(getApplicationContext(), resp1, Toast.LENGTH_SHORT).show();
            if(resp1.equals("1")){
            if(utype.equals("Teacher")){
                Intent i = new Intent(login_main.this, Teacher_Dashboard.class);
                startActivity(i);
                Data_activity.tea_id=email;
                Toast.makeText(getApplicationContext(), "Welcome Teacher", Toast.LENGTH_SHORT).show();
            }else {
                if(utype.equals("Student")){
                        Intent i = new Intent(login_main.this, Student_dashboard.class);
                        startActivity(i);
                    Data_activity.pnr=email;
                    Toast.makeText(getApplicationContext(), "Welcome Student", Toast.LENGTH_SHORT).show();
                }
            }
            }
            else
            {
                Toast.makeText(getApplicationContext(), "enter a valid username and password", Toast.LENGTH_SHORT).show();
            }

        }
        protected void onPreExecute()
        {
            Toast.makeText(getApplicationContext(), "connecting to sever", Toast.LENGTH_SHORT).show();


        }
    }
    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                               long arg3)
    {
        utype=spinner.getItemAtPosition(arg2).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
