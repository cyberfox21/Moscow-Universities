package com.example.registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MenuActivity extends AppCompatActivity{

    private ArrayList resultList = new ArrayList();

    private EditText search_panel;
    private ImageButton searchBtn;

    private MenuRecyclerAdapter adapter;
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
        //hideSystemUI();
        setContentView(R.layout.activity_menu);

        adapter = new MenuRecyclerAdapter();
        adapter.setContext(this);

        universitiesDatabase = FirebaseDatabase.getInstance().getReference("Universities");

        search_panel = findViewById(R.id.search_panel);
        searchBtn = findViewById(R.id.searchBtn);

        listOfCards = findViewById(R.id.list_of_cards);
        listOfCards.setHasFixedSize(true);
        listOfCards.setLayoutManager(new LinearLayoutManager(this));
        listOfCards.setAdapter(adapter);
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
        firebaseSearchQuery = universitiesDatabase.orderByChild("title").startAt(searchText).endAt(searchText + "\uf8ff");

        firebaseSearchQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                resultList.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Card card = postSnapshot.getValue(Card.class);
                    resultList.add(card);
                }
                adapter.updateList(resultList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("CHECKER", "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });

    }
    public void enableSearchPanel(View v) {
        v.setFocusable(true);
        v.setEnabled(true);
        v.setFocusableInTouchMode(true);
        v.setClickable(true);
    }

    /*
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

     */
}