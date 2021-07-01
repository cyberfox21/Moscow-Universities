package com.example.registration;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;
public class DescriptionActivity extends AppCompatActivity {

    private ImageView logo_d;
    private ImageView imageview;
    private TextView descriptionTextView;

    private CollapsingToolbarLayout collapsingToolbar;
    private AppBarLayout appBarLayout;
    private FloatingActionButton location_button;

    private Menu collapsedMenu;
    private boolean appBarExpanded = true;

    private Card model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT < 19) {
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else {
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }

        setContentView(R.layout.activity_description);

        logo_d = findViewById(R.id.logo_d);
        imageview = findViewById(R.id.image_d);
        descriptionTextView = findViewById(R.id.text_d);
        location_button = findViewById(R.id.location_button);

        Intent fromMenuActivity = getIntent();
        model = fromMenuActivity.getParcelableExtra("model");

        Picasso.with(DescriptionActivity.this).load(model.getImage()).into(imageview);
        Picasso.with(DescriptionActivity.this).load(model.getLogo()).into(logo_d);
        descriptionTextView.setText(model.getDescr());

        final Toolbar toolbar = findViewById(R.id.anim_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        appBarLayout = findViewById(R.id.appbar);

        collapsingToolbar = findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(model.getTitle());

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                Log.d(DescriptionActivity.class.getSimpleName(), "onOffsetChanged: verticalOffset: " + verticalOffset);

                if (Math.abs(verticalOffset) > 200) {
                    appBarExpanded = false;
                    invalidateOptionsMenu();
                } else {
                    appBarExpanded = true;
                    invalidateOptionsMenu();
                }
            }
        });
        location_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLocation(v);
            }
        });
    }
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (collapsedMenu != null
                && (!appBarExpanded || collapsedMenu.size() != 1)) {
            collapsedMenu.add("Add")
                    .setIcon(R.drawable.ic_message_black_24dp)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        } else {
        }
        return super.onPrepareOptionsMenu(collapsedMenu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                goBack();
                return true;

        }
        if (item.getTitle() == "Add") {
            openChat();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        collapsedMenu = menu;
        return true;
    }
    public void openChat() {
        Intent toChatActivity = new Intent(this, ChatActivity.class);
        toChatActivity.putExtra("model", model);
        startActivity(toChatActivity);
    }
    public void showBalls(View view) {
        Intent toBrowser = new Intent(Intent.ACTION_VIEW, Uri.parse(model.getSite()));
        toBrowser.putExtra("model", model);
        startActivity(Intent.createChooser(toBrowser, "Browser"));
    }
    public void showLocation(View view) {
        Intent toMapActivity = new Intent(this, MapActivity.class);
        toMapActivity.putExtra("model", model);
        startActivity(toMapActivity);
    }
    public void goBack() {
        Intent toMenuActivity = new Intent(this, MenuActivity.class);
        startActivity(toMenuActivity);
    }
}