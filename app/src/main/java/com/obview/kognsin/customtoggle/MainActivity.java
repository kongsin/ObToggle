package com.obview.kognsin.customtoggle;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.obview.obtoggle.ObToggle;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ObToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToggle = (ObToggle) findViewById(R.id.obToggle);
        mToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mToggle.setEnabled(false);
                new Handler(getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mToggle.setEnabled(true);
                    }
                }, 1000);
            }
        });
    }
}
