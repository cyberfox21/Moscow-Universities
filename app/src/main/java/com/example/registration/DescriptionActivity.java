package com.example.registration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DescriptionActivity extends AppCompatActivity {

    private ImageView image;
    private TextView descriptionTextView, textViewUniversityTitle;
    private Button hse;
    private Button mfti;
    private Button mgu;
    private Button rhtu;
    private String doc;
    private String title;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        hse = (findViewById(R.id.hse_button));
        mfti = (findViewById(R.id.mfti_button));
        mgu = (findViewById(R.id.mgu_button));
        rhtu = (findViewById(R.id.rhtu_button));


        image = findViewById(R.id.image_d);
        descriptionTextView = findViewById(R.id.text_d);
        textViewUniversityTitle = findViewById(R.id.textViewUniversityTitle);

        String description = getString(R.string.text_mgu);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", R.id.mfti_button);
        doc = intent.getStringExtra("doc");
        title = intent.getStringExtra("title");


        if(id == R.id.mgu_button){
            description = getString(R.string.text_mgu);
            image.setImageResource(R.drawable.mgu_image);
        }
        if(id == R.id.hse_button){
            description = getString(R.string.text_hse);
            image.setImageResource(R.drawable.hse_image);
        }
        if(id == R.id.mfti_button){
            description = getString(R.string.text_mfti);
            image.setImageResource(R.drawable.mfti_image);
        }
        if(id == R.id.rhtu_button){
            description = getString(R.string.text_rhtu);
            image.setImageResource(R.drawable.rhtu_image);
        }
        descriptionTextView.setText(description);
        textViewUniversityTitle.setText(title);
    }
    public void onClick(View view){
        Intent intent = getIntent();
        doc = intent.getStringExtra("doc");
        if(view.getId() == R.id.balls ){
//            Intent i = new Intent(this, WebActivity.class);
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(doc));
            i.putExtra("doc", doc);
            startActivity(Intent.createChooser(i, "Browser"));
        }
        else if(view.getId() == R.id.panorama ){
            Intent i = new Intent(this, PanoramaActivity.class);
            float x = intent.getFloatExtra("x", (float) 55.929729);
            float y = intent.getFloatExtra("y", (float) 37.520809);
            i.putExtra("x", x);
            i.putExtra("y", y);
            startActivity(i);
        }
        else if(view.getId() == R.id.back ){
            Intent i = new Intent(this, MenuActivity.class);
            startActivity(i);
        }

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

    public void showLocation(View view) {
            Intent intent = getIntent();
            float x = intent.getFloatExtra("x", (float) 55.929729);
            float y = intent.getFloatExtra("y", (float) 37.520809);
            Intent i = new Intent(this, SearchActivity.class);
            i.putExtra("title", title);
            i.putExtra("x", x);
            i.putExtra("y", y);
            startActivity(i);
    }

    public void openChat(View view) {
    }
}
