package com.fakhrus.bootcamppariwisata;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.fakhrus.bootcamppariwisata.main.MainActivity;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        waitingSplashScreen();
    }



    private void waitingSplashScreen() {

        Thread timerThread = new Thread() {
            public void run() {

                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {

                    Intent toMainActivity = new Intent(SplashScreenActivity.this, MainActivity.class);
                    toMainActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(toMainActivity);

                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                }
            }
        };

        timerThread.start();
    }

}
