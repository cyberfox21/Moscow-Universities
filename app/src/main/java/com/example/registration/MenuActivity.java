package com.example.registration;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
    //private Boolean flag = false;

    //private FirebaseRecyclerAdapter<Card> adapter;
    private FirebaseRecyclerAdapter<Card, CardViewHolder> firebaseRecyclerAdapter;
    private DatabaseReference universitiesDatabase;
    private Query firebaseSearchQuery;
    //private ;

    private String title;
    private String descr;
    private String image;
    private String site;
    private String x;
    private String y;

    private int p;
    //private String textTosearch;

    private RecyclerView listOfCards;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        universitiesDatabase = FirebaseDatabase.getInstance().getReference("Universities");
        //firebaseSearchQuery = universitiesDatabase.orderByChild("title").startAt("").endAt("" + "\uf8ff");

        search_panel = findViewById(R.id.search_panel);
        searchBtn = findViewById(R.id.searchBtn);

        Intent toDescriptionActivity = new Intent(this, DescriptionActivity.class);

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
                String searchText = search_panel.getText().toString();
                firebaseUniversitySearch(searchText);
            }
        });

//        listOfCards.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                title = ((TextView)(view.findViewById(R.id.card_title))).getText().toString();
//                descr = ((TextView)(view.findViewById(R.id.card_inv_descr))).getText().toString();
//                image = ((TextView)(view.findViewById(R.id.card_inv_image))).getText().toString();
//                site = ((TextView)(view.findViewById(R.id.card_inv_site))).getText().toString();
//                x = ((TextView)(view.findViewById(R.id.card_inv_x))).getText().toString();
//                y = ((TextView)(view.findViewById(R.id.card_inv_y))).getText().toString();
//
//                toDescriptionActivity.putExtra("title", title);
//                toDescriptionActivity.putExtra("descr", descr);
//                toDescriptionActivity.putExtra("image", image);
//                toDescriptionActivity.putExtra("site", site);
//                toDescriptionActivity.putExtra("x", Double.parseDouble(x));
//                toDescriptionActivity.putExtra("y", Double.parseDouble(y));
//                startActivity(toDescriptionActivity);
//            }
//        });
        //displayAllCards();

        firebaseUniversitySearch("");
    }

    private void firebaseUniversitySearch(String searchText) {
        Toast.makeText(MenuActivity.this, "Started Search", Toast.LENGTH_LONG).show();
        firebaseSearchQuery = universitiesDatabase.orderByChild("title").startAt(searchText).endAt(searchText + "\uf8ff");

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Card, CardViewHolder>(

                Card.class,
                R.layout.card,
                CardViewHolder.class,
                firebaseSearchQuery

        ) {
            @Override
            protected void populateViewHolder(CardViewHolder viewHolder, Card model, int position) {
                p = position;
                viewHolder.setDetails(getApplicationContext(), model.getTitle(), model.getTitle_descr(), model.getLogo(), model.getImage(), model.getDescr(),  model.getSite(), model.getX(), model.getY());

            }
        };

        listOfCards.setAdapter(firebaseRecyclerAdapter);
    }

    public void enableSearchPanel(View v) {
        v.setFocusable(true);
        v.setEnabled(true);
        v.setFocusableInTouchMode(true);
        v.setClickable(true);
    }

//    private void displayAllCards() {
////        //ListView listOfCards = findViewById(R.id.list_of_cards);
////        //ArrayList<Card> listItems = new ArrayList<>();
////
////        adapter = new FirebaseListAdapter<Card>(this, Card.class, R.layout.card,
////                FirebaseDatabase.getInstance().getReference().child("Universities")) {
////            @Override
////            protected void populateView(View v, Card model, int position) {
////                TextView vis_title, vis_descr;
////                ImageView vis_image;
////                    vis_title = v.findViewById(R.id.card_title);
////                    vis_descr = v.findViewById(R.id.card_descr);
////                    vis_image = v.findViewById(R.id.card_image);
////
////                    vis_title.setText(model.getTitle());
////                    vis_descr.setText(model.getTitle_descr());
////                    Picasso.with(MenuActivity.this).load(model.getLogo()).into(vis_image);
////
////                    TextView inv_descr = v.findViewById(R.id.card_inv_descr);
////                    TextView inv_image = v.findViewById(R.id.card_inv_image);
////                    TextView inv_site = v.findViewById(R.id.card_inv_site);
////                    TextView inv_x = v.findViewById(R.id.card_inv_x);
////                    TextView inv_y = v.findViewById(R.id.card_inv_y);
////
////                    inv_image.setText(model.getImage());
////                    inv_descr.setText(model.getDescr());
////                    inv_site.setText(model.getSite());
////                    inv_x.setText((model.getX()).toString());
////                    inv_y.setText((model.getY()).toString());
////            }
////        };
////        listOfCards.setAdapter(adapter);
////    }
//    private OnItemClickListener myListener;
//
//    public interface OnItemClickListener {
//        void onItemClick(int position);
//    }
//
//    public void setOnItemClickListener(OnItemClickListener listener){
//        myListener = listener;
//    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {

        View view;


        public CardViewHolder(View itemView) {
            //, final OnItemClickListener listener)
            super(itemView);
            view = itemView;
//            view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
////                    if (listener != null){
////                        int position = getAdapterPosition();
////                        if(position != )
////                    }
//                String title = ((TextView)(view.findViewById(R.id.card_title))).getText().toString();
//                String descr = ((TextView)(view.findViewById(R.id.card_inv_descr))).getText().toString();
//                String image = ((TextView)(view.findViewById(R.id.card_inv_image))).getText().toString();
//                String site = ((TextView)(view.findViewById(R.id.card_inv_site))).getText().toString();
//                String x = ((TextView)(view.findViewById(R.id.card_inv_x))).getText().toString();
//                String y = ((TextView)(view.findViewById(R.id.card_inv_y))).getText().toString();
//
//                Intent toDescriptionActivity = new Intent(MenuActivity.this, DescriptionActivity.class);
//
//                toDescriptionActivity.putExtra("title", title);
//                toDescriptionActivity.putExtra("descr", descr);
//                toDescriptionActivity.putExtra("image", image);
//                toDescriptionActivity.putExtra("site", site);
//                toDescriptionActivity.putExtra("x", Double.parseDouble(x));
//                toDescriptionActivity.putExtra("y", Double.parseDouble(y));
//                startActivity(toDescriptionActivity);
//
//                }
//            });

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