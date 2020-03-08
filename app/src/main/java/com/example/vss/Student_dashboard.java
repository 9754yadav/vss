package com.example.vss;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Student_dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_dashboard);
    }
    public  void profile(View view)
    {
        Intent i=new Intent(this,stu_profile.class);
        startActivity(i);
    }
    public  void result(View view)
    {

    }
    public  void timetable(View view)
    {
        Intent i=new Intent(this,timetable.class);
        startActivity(i);
    }
    public  void attendence(View view)
    {

    }
    public  void course(View view)
    {

    }
    public  void notice(View view)
    {

    }
}
