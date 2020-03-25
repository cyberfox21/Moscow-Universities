package com.example.registration;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LoadingActivity extends Activity {

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        Intent intent = getIntent();
//        if(!intent.hasExtra("id")){
//            setContentView(R.layout.activity_start);
//            Thread welcomeThread = new Thread() {
//
//                @Override
//                public void run() {
//                    try {
//                        super.run();
//                        sleep(1000);
//                    } catch (Exception e) {
//
//                    } finally {
//                        Intent i = new Intent(LoadingActivity.this,
//                                MenuActivity.class);
//                        startActivity(i);
//                        finish();
//                    }
//                }
//            };
//            welcomeThread.start();
//        }
//        else if(intent.hasExtra("id")) {
//            setContentView(R.layout.activity_loading);
//
//            Thread welcomeThread = new Thread() {
//
//                @Override
//                public void run() {
//                    try {
//                        super.run();
//                        sleep(1000);
//                    } catch (Exception e) {
//
//                    } finally {
//                        Intent intent = getIntent();
//                        String doc = intent.getStringExtra("doc");
//                        String title = intent.getStringExtra("title");
//                        float x = intent.getFloatExtra("x", (float) 55.929729);
//                        float y = intent.getFloatExtra("y", (float) 37.520809);
//                        int id = intent.getIntExtra("id", R.id.hse_button);
//                        Intent i = new Intent(LoadingActivity.this,
//                                DescriptionActivity.class);
//                        i.putExtra("id", id);
//                        i.putExtra("doc", doc);
//                        i.putExtra("title", title);
//                        i.putExtra("x", x);
//                        i.putExtra("y", y);
//                        startActivity(i);
//                        finish();
//                    }
//                }
//            };
//            welcomeThread.start();
//        }
//    }
//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//        if (hasFocus) {
//            hideSystemUI();
//        }
//    }
//
//    private void hideSystemUI() {
//        View decorView = getWindow().getDecorView();
//        decorView.setSystemUiVisibility(
//                View.SYSTEM_UI_FLAG_IMMERSIVE
//                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
//    }

}
