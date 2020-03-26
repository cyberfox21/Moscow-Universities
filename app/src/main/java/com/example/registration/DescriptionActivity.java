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

    String[] titles;
    String[] descriptions;
    String[] sites;
    String[] xes;
    String[] yes;

    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description_2);

        image = findViewById(R.id.image_d);
        descriptionTextView = findViewById(R.id.text_d);
        textViewUniversityTitle = findViewById(R.id.textViewUniversityTitle);


        Intent fromMenuActivity = getIntent();
        position = fromMenuActivity.getIntExtra("position", 0);

        titles = getResources().getStringArray(R.array.title_logos);
        descriptions = getResources().getStringArray(R.array.texts);
        sites = getResources().getStringArray(R.array.sites);
        xes = getResources().getStringArray(R.array.xes);
        yes = getResources().getStringArray(R.array.yes);
        int[] images = {R.drawable.hse_image, R.drawable.mfti_image, R.drawable.mgu_image, R.drawable.rhtu_image, R.drawable.mpgu_image, R.drawable.politech_image, R.drawable.mirea_image, R.drawable.misis_image, R.drawable.mtusi_image};

        image.setImageResource(images[position]);
        descriptionTextView.setText(descriptions[position]);
        textViewUniversityTitle.setText(titles[position]);
    }

    public void openChat(View view) {
        Intent toChatActivity = new Intent(this, ChatActivity.class);
        toChatActivity.putExtra("title", titles[position]);
        startActivity(toChatActivity);
    }
    public void showBalls(View view) {
        Intent toBrowser = new Intent(Intent.ACTION_VIEW, Uri.parse(sites[position]));
        toBrowser.putExtra("url", sites[position]);
        startActivity(Intent.createChooser(toBrowser, "Browser"));
    }

    public void showPanorama(View view) {
        Intent toPanoramaActivity = new Intent(this, PanoramaActivity.class);
        toPanoramaActivity.putExtra("x", xes[position]);
        toPanoramaActivity.putExtra("y", yes[position]);
        startActivity(toPanoramaActivity);
    }
    public void showLocation(View view) {
        Intent toSearchActivity = new Intent(this, SearchActivity.class);
        toSearchActivity.putExtra("title", titles[position]);
        toSearchActivity.putExtra("x", xes[position]);
        toSearchActivity.putExtra("y", yes[position]);
        startActivity(toSearchActivity);
    }
    public void showRoute(View view) {
        Intent toDrivingActivity = new Intent(this, DrivingActivity.class);
        toDrivingActivity.putExtra("x", xes[position]);
        toDrivingActivity.putExtra("y", yes[position]);
        startActivity(toDrivingActivity);
    }

    public void goBack(View view) {
        Intent toMenuActivity = new Intent(this, MenuActivity.class);
        startActivity(toMenuActivity);
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
