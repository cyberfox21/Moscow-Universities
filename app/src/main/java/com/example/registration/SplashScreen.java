package com.example.registration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import static java.lang.Thread.sleep;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideSystemUI();
        try {
            sleep(1000);
        } catch (Exception e) {

        }

            setContentView(R.layout.activity_splash_screen);
        final Animation anim_2;
        anim_2 = AnimationUtils.loadAnimation(this,R.anim.back);
        final TextView tv = (TextView) findViewById(R.id.textView2);
            Thread welcomeThread = new Thread() {

                @Override
                public void run() {
                    try {
                        super.run();
                        sleep(3000);
                    } catch (Exception e) {

                    } finally {

                        try {
                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {

                                    tv.startAnimation(anim_2);
                                    tv.setVisibility(View.INVISIBLE);

                                }
                            });
                            sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }




                        Intent i = new Intent(SplashScreen.this,
                                MenuActivity.class);

                        startActivity(i);

                        finish();

                    }
                }
            };
            welcomeThread.start();

    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }
    private void hideSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }
}
