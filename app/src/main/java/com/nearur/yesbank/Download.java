package com.nearur.yesbank;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.FileOutputStream;

public class Download extends AppCompatActivity {
    ConstraintLayout constraintLayout;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    TextView date,time,t500,t2000,total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        Intent rcv=getIntent();
        date=(TextView)findViewById(R.id.textViewdate);
        time=(TextView)findViewById(R.id.textViewtime);
        t500=(TextView)findViewById(R.id.textView500);
        t2000=(TextView)findViewById(R.id.textView2000);
        total=(TextView)findViewById(R.id.textViewtotal);

        date.setText(rcv.getStringExtra("date"));
        time.setText(rcv.getStringExtra("time"));
        t500.setText(rcv.getStringExtra("500"));
        t2000.setText(rcv.getStringExtra("2000"));
        total.setText(rcv.getStringExtra("total"));

        constraintLayout=(ConstraintLayout)findViewById(R.id.download);
        preferences=getSharedPreferences("download",MODE_PRIVATE);
        editor=preferences.edit();
        h.sendEmptyMessageDelayed(101,2000);
    }
    Handler h=new Handler(){
        @Override
        public void handleMessage(Message msg) {
           if(msg.what==101){
               int count=preferences.getInt("count",0);
               String path= Environment.getExternalStorageDirectory().getAbsolutePath();
               saveLayout(constraintLayout,path+"/Yesbank"+(count++)+".PNG");
               editor.putInt("count",count);
               editor.commit();
           }
        }
    };

    public void saveLayout(ConstraintLayout frameLayout, String path) {
        frameLayout.setDrawingCacheEnabled(true);
        frameLayout.buildDrawingCache();
        Bitmap cache = frameLayout.getDrawingCache();
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(path);
            cache.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            Toast.makeText(Download.this,"Image Saved",Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(Download.this,"Error "+e.getMessage(),Toast.LENGTH_LONG).show();
        } finally {
            frameLayout.destroyDrawingCache();
        }
    }
}
