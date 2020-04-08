package com.example.registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;

public class MenuActivity extends AppCompatActivity{

    private EditText search_panel;
    private ImageButton searchBtn;

    private FirebaseRecyclerAdapter<Card, CardViewHolder> firebaseRecyclerAdapter;
    private DatabaseReference universitiesDatabase;
    private Query firebaseSearchQuery;

    private String title;
    private String logo;
    private String title_descr;
    private String descr;
    private String image;
    private String site;
    private String x;
    private String y;

    private RecyclerView listOfCards;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        universitiesDatabase = FirebaseDatabase.getInstance().getReference("Universities");

        search_panel = findViewById(R.id.search_panel);
        searchBtn = findViewById(R.id.searchBtn);

        listOfCards = findViewById(R.id.list_of_cards);
        listOfCards.setHasFixedSize(true);
        listOfCards.setLayoutManager(new LinearLayoutManager(this));
        search_panel.setOnClickListener(new View.OnClickListener() {
             @Override
              public void onClick(View v) {
                    enableSearchPanel(search_panel);
              }
        });
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText = (search_panel.getText().toString()).toUpperCase();
                firebaseUniversitySearch(searchText);
            }
        });
        firebaseUniversitySearch("");
    }

    private void firebaseUniversitySearch(String searchText) {
        Toast.makeText(MenuActivity.this, "Started Search", Toast.LENGTH_LONG).show();
        firebaseSearchQuery = universitiesDatabase.orderByChild("title").startAt(searchText).endAt(searchText + "\uf8ff");

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Card, CardViewHolder>(
                Card.class, R.layout.card, CardViewHolder.class, firebaseSearchQuery
        ) {
            @Override
            protected void populateViewHolder(CardViewHolder viewHolder, Card model, int position) {
                viewHolder.setDetails(getApplicationContext(), model.getTitle(), model.getTitle_descr(),
                        model.getLogo(), model.getImage(), model.getDescr(),  model.getSite(), model.getX(), model.getY());
            }
        };

        listOfCards.setAdapter(firebaseRecyclerAdapter);
        listOfCards.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            GestureDetector gestureDetector = new GestureDetector(MenuActivity.this, new GestureDetector.SimpleOnGestureListener() {
                @Override public boolean onSingleTapUp(MotionEvent motionEvent) {
                    return true;
                }
            });
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                View view = rv.findChildViewUnder(e.getX(), e.getY());
                if(view != null && gestureDetector.onTouchEvent(e)) {
                    title = ((TextView)(view.findViewById(R.id.card_title))).getText().toString();
                    title_descr = ((TextView)(view.findViewById(R.id.card_descr))).getText().toString();
                    descr = ((TextView)(view.findViewById(R.id.card_inv_descr))).getText().toString();
                    image = ((TextView)(view.findViewById(R.id.card_inv_image))).getText().toString();
                    logo = ((TextView)(view.findViewById(R.id.card_inv_logo))).getText().toString();
                    site = ((TextView)(view.findViewById(R.id.card_inv_site))).getText().toString();
                    x = ((TextView)(view.findViewById(R.id.card_inv_x))).getText().toString();
                    y = ((TextView)(view.findViewById(R.id.card_inv_y))).getText().toString();

                    Intent toDescriptionActivity = new Intent(MenuActivity.this, DescriptionActivity.class);
                    toDescriptionActivity.putExtra("title", title);
                    toDescriptionActivity.putExtra("logo", logo);
                    toDescriptionActivity.putExtra("title_descr", title_descr);
                    toDescriptionActivity.putExtra("descr", descr);
                    toDescriptionActivity.putExtra("image", image);
                    toDescriptionActivity.putExtra("site", site);
                    toDescriptionActivity.putExtra("x", Double.parseDouble(x));
                    toDescriptionActivity.putExtra("y", Double.parseDouble(y));
                    startActivity(toDescriptionActivity);
                }
                return false;
            }
            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
            }
            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
    }
    public void enableSearchPanel(View v) {
        v.setFocusable(true);
        v.setEnabled(true);
        v.setFocusableInTouchMode(true);
        v.setClickable(true);
    }
    public static class CardViewHolder extends RecyclerView.ViewHolder {
        View view;
        public CardViewHolder(View itemView) {
            super(itemView);
            view = itemView;
        }
        public void setDetails(Context ctx, String card_title, String card_text, String card_image, String image, String descr, String card_site, Double card_x, Double card_y){

            ((TextView)view.findViewById(R.id.card_title)).setText(card_title);
            ((TextView)view.findViewById(R.id.card_descr)).setText(card_text);

            Picasso.with(ctx).load(card_image).into((ImageView)view.findViewById(R.id.card_image));

            ((TextView)(view.findViewById(R.id.card_inv_descr))).setText(descr);
            ((TextView)(view.findViewById(R.id.card_inv_image))).setText(image);
            ((TextView)(view.findViewById(R.id.card_inv_site))).setText(card_site);
            ((TextView)(view.findViewById(R.id.card_inv_x))).setText(card_x.toString());
            ((TextView)(view.findViewById(R.id.card_inv_y))).setText(card_y.toString());
        }
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