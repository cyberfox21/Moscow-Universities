package com.example.registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity{

    private FirebaseListAdapter<Card> adapter;
    private Intent toDescriptionActivity;

    private String title;
    private String descr;
    private String image;
    private String site;
    private Double x;
    private Double y;


    private ListView listOfCards;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        listOfCards = (ListView) findViewById(R.id.list_of_cards);


        toDescriptionActivity = new Intent(this, DescriptionActivity.class);
        listOfCards.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //toDescriptionActivity.putExtra("title", title);
                //toDescriptionActivity.putExtra("descr", descr);
                //toDescriptionActivity.putExtra("image", image);
                //toDescriptionActivity.putExtra("x", x);
                //toDescriptionActivity.putExtra("y", y);
                startActivity(toDescriptionActivity);
            }
        });


        displayAllCards();
    }

    private void displayAllCards() {
        ListView listOfCards = findViewById(R.id.list_of_cards);


        adapter = new FirebaseListAdapter<Card>(this, Card.class, R.layout.card, FirebaseDatabase.getInstance().getReference().child("Universities")) {
            @Override
            protected void populateView(View v, Card model, int position) {
                TextView card_title, card_descr;
                ImageView card_image;

                card_title = v.findViewById(R.id.card_title);
                card_descr = v.findViewById(R.id.card_descr);
                card_image = v.findViewById(R.id.card_image);

                card_title.setText(model.getTitle());
                card_descr.setText(model.getTitle_descr());

                Picasso.with(MenuActivity.this).load(model.getLogo()).into(card_image);

                title = model.getTitle();
                descr = model.getDescr();
                image = model.getImage();
                site = model.getSite();
                x = model.getX();
                y = model.getY();
            }
        };
        listOfCards.setAdapter(adapter);
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