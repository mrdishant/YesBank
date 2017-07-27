package com.nearur.yesbank;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText edate,etime,e500,e2000;
    TextView total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edate=(EditText)findViewById(R.id.editTextdate);
        edate.setFocusable(false);
        etime=(EditText)findViewById(R.id.editTexttime);
        etime.setFocusable(false);
        e500=(EditText)findViewById(R.id.editText500);
        e2000=(EditText)findViewById(R.id.editText2000);
        total=(TextView)findViewById(R.id.textViewtotal);

        edate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog.OnDateSetListener d=new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        edate.setText(i2+"/"+(i1+1)+"/"+i);
                    }
                };
                Calendar c=Calendar.getInstance();
                int yy=c.get(Calendar.YEAR);
                int mm=c.get(Calendar.MONTH);
                int dd=c.get(Calendar.DATE);
                DatePickerDialog dialog=new DatePickerDialog(MainActivity.this,d,yy,mm,dd);
                dialog.show();
            }
        });

        etime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog.OnTimeSetListener t=new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        etime.setText(i+":"+i1);
                    }
                };
                Calendar c=Calendar.getInstance();
                int hh=c.get(Calendar.HOUR);
                int mm=c.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog=new TimePickerDialog(MainActivity.this,t,hh,mm,true);
                timePickerDialog.show();
            }
        });

        e2000.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(e500.getText().toString().length()>0){
                if (charSequence.toString().length()> 0) {
                    total.setText("Total :" +( (Integer.parseInt(charSequence.toString()) * 2000)+(Integer.parseInt(e500.getText().toString()))*500));
                }
                else{
                    Toast.makeText(MainActivity.this,"Please Enter 2000 notes",Toast.LENGTH_LONG).show();
                }
                }
                else {
                    Toast.makeText(MainActivity.this,"Please Enter 500 notes",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



    }
   public void cl(View v){
        Intent i=new Intent(this,Download.class);
        i.putExtra("date",edate.getText().toString());
        i.putExtra("time",etime.getText().toString());
        i.putExtra("500",e500.getText().toString());
        i.putExtra("2000",e2000.getText().toString());
        String x=total.getText().toString();
        String y=x.substring(x.indexOf(":")+1).trim();
        i.putExtra("total",y);
        startActivity(i);
    }
}
