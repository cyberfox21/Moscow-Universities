package com.example.registration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener{

    static String doc;
    static String title;
    static float x, y;
    static int button_value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button hse2 = (findViewById(R.id.hse_button));
        hse2.setOnClickListener(this);
        Button mfti = (findViewById(R.id.mfti_button));
        mfti.setOnClickListener(this);
        Button mgu = (findViewById(R.id.mgu_button));
        mgu.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        Intent i = new Intent(this, LoadingActivity.class);
        //Intent intent = new Intent(this, WebActivity.class);
        //Intent intent_2 = new Intent(this, DescriptionActivity.class);
        button_value = view.getId();

        if(view.getId() == R.id.hse_button) {
            doc = "https://ba.hse.ru/minkrit2020#pagetop";
            title = "ВШЭ";
            x = (float)55.761504;
            y = (float)37.632893;

        }
        if(view.getId() == R.id.mfti_button) {
            doc = "https://studika.ru/moskva/mfti/specialnosti";
            title = "МФТИ";
            x = (float)55.929729;
            y = (float)37.520809;
        }
        if(view.getId() == R.id.mgu_button){
            doc = "https://postupi.info/vuz/mgu-im.-m.v.-lomonosova/spec";
            title = "МГУ";
            x = (float)55.701964;
            y = (float)37.529700;
        }
        if(view.getId() == R.id.rhtu_button){
            doc = "https://postupi.info/vuz/rhtu-im.-d.i.mendeleeva/spec";
            title = "РХТУ";
            x = (float)55.857471;
            y = (float)37.417657;
        }
        i.putExtra("doc", doc);
        i.putExtra("title", title);
        i.putExtra("id", button_value);
        i.putExtra("x", x);
        i.putExtra("y", y);
        startActivity(i);

    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    // Shows the system bars by removing all the flags
// except for the ones that make the content appear under the system bars.
    private void showSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }


}
