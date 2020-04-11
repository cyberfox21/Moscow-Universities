package com.example.registration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import static java.lang.Thread.sleep;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            sleep(1000);
        } catch (Exception e) {

        }

            setContentView(R.layout.activity_splash_screen);
            Thread welcomeThread = new Thread() {

                @Override
                public void run() {
                    try {
                        super.run();
                        sleep(1500);
                    } catch (Exception e) {

                    } finally {
                        Intent i = new Intent(SplashScreen.this,
                                MenuActivity.class);
                        startActivity(i);
                        finish();
                    }
                }
            };
            welcomeThread.start();
        }
}
