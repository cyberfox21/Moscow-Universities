package com.example.registration;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
public class DescriptionActivity extends AppCompatActivity {

    private ImageView imageview;
    private TextView descriptionTextView, textViewUniversityTitle;

    String title;
    String title_descr;
    String descr;
    String site;
    String logo;
    String image;
    Double x;
    Double y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description_2);

        imageview = findViewById(R.id.image_d);
        descriptionTextView = findViewById(R.id.text_d);
        textViewUniversityTitle = findViewById(R.id.textViewUniversityTitle);

        Intent fromMenuActivity = getIntent();
        title = fromMenuActivity.getStringExtra("title");
        title_descr = fromMenuActivity.getStringExtra("title_descr");
        descr = fromMenuActivity.getStringExtra("descr");
        logo = fromMenuActivity.getStringExtra("logo");
        image = fromMenuActivity.getStringExtra("image");
        site = fromMenuActivity.getStringExtra("site");
        x = fromMenuActivity.getDoubleExtra("x", 0);
        y = fromMenuActivity.getDoubleExtra("y", 0);

        Picasso.with(DescriptionActivity.this).load(image).into(imageview);
        descriptionTextView.setText(descr);
        textViewUniversityTitle.setText(title);
    }
    public void openChat(View view) {
        Intent toChatActivity = new Intent(this, ChatActivity.class);
        toChatActivity.putExtra("title", title);
        startActivity(toChatActivity);
    }
    public void showBalls(View view) {
        Intent toBrowser = new Intent(Intent.ACTION_VIEW, Uri.parse(site));
        toBrowser.putExtra("url", site);
        startActivity(Intent.createChooser(toBrowser, "Browser"));
    }
    public void showPanorama(View view) {
        Intent toPanoramaActivity = new Intent(this, PanoramaActivity.class);
        toPanoramaActivity.putExtra("x", x);
        toPanoramaActivity.putExtra("y", y);
        startActivity(toPanoramaActivity);
    }
    public void showLocation(View view) {
        Intent toSearchActivity = new Intent(this, SearchActivity.class);
        toSearchActivity.putExtra("title", title);
        toSearchActivity.putExtra("x", x);
        toSearchActivity.putExtra("y", y);
        startActivity(toSearchActivity);
    }
    public void showRoute(View view) {
        Intent toDrivingActivity = new Intent(this, DrivingActivity.class);
        toDrivingActivity.putExtra("x", x);
        toDrivingActivity.putExtra("y", y);
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