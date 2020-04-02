package com.example.registration;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;

public class MenuActivity extends AppCompatActivity{

    private EditText search_panel;
    private Boolean flag = false;

    private FirebaseListAdapter<Card> adapter;
    private Intent toDescriptionActivity;

    private String title;
    private String descr;
    private String image;
    private String site;
    private String x;
    private String y;

    private String textTosearch;

    private ListView listOfCards;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        search_panel = findViewById(R.id.search_panel);
//        search_panel.addTextChangedListener(new TextWatcher() {
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if(s.toString().equals("")){
//                    displayAllCards();
//                } else {
//                    searchItem();
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//            }
//
//        });
        listOfCards = findViewById(R.id.list_of_cards);

        toDescriptionActivity = new Intent(this, DescriptionActivity.class);
        listOfCards.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                title = ((TextView)(view.findViewById(R.id.card_title))).getText().toString();
                descr = ((TextView)(view.findViewById(R.id.card_inv_descr))).getText().toString();
                image = ((TextView)(view.findViewById(R.id.card_inv_image))).getText().toString();
                site = ((TextView)(view.findViewById(R.id.card_inv_site))).getText().toString();
                x = ((TextView)(view.findViewById(R.id.card_inv_x))).getText().toString();
                y = ((TextView)(view.findViewById(R.id.card_inv_y))).getText().toString();

                toDescriptionActivity.putExtra("title", title);
                toDescriptionActivity.putExtra("descr", descr);
                toDescriptionActivity.putExtra("image", image);
                toDescriptionActivity.putExtra("site", site);
                toDescriptionActivity.putExtra("x", Double.parseDouble(x));
                toDescriptionActivity.putExtra("y", Double.parseDouble(y));
                startActivity(toDescriptionActivity);
            }
        });
        displayAllCards();
    }

    public void searchItem(){
        flag = true;
        displayAllCards();
    }

    private void displayAllCards() {
        //ListView listOfCards = findViewById(R.id.list_of_cards);
        //ArrayList<Card> listItems = new ArrayList<>();

        adapter = new FirebaseListAdapter<Card>(this, Card.class, R.layout.card,
                FirebaseDatabase.getInstance().getReference().child("Universities")) {
            @Override
            protected void populateView(View v, Card model, int position) {
                TextView vis_title, vis_descr;
                ImageView vis_image;
//                if(flag == true) {
//                    if(model.getTitle().equals(textTosearch)) {
//                        vis_title = v.findViewById(R.id.card_title);
//                        vis_descr = v.findViewById(R.id.card_descr);
//                        vis_image = v.findViewById(R.id.card_image);
//
//                        vis_title.setText(model.getTitle());
//                        vis_descr.setText(model.getTitle_descr());
//                        Picasso.with(MenuActivity.this).load(model.getLogo()).into(vis_image);
//
//                        TextView inv_descr = v.findViewById(R.id.card_inv_descr);
//                        TextView inv_image = v.findViewById(R.id.card_inv_image);
//                        TextView inv_site = v.findViewById(R.id.card_inv_site);
//                        TextView inv_x = v.findViewById(R.id.card_inv_x);
//                        TextView inv_y = v.findViewById(R.id.card_inv_y);
//
//                        inv_image.setText(model.getImage());
//                        inv_descr.setText(model.getDescr());
//                        inv_site.setText(model.getSite());
//                        inv_x.setText((model.getX()).toString());
//                        inv_y.setText((model.getY()).toString());
//                    }
//                }
//                else {
                    vis_title = v.findViewById(R.id.card_title);
                    vis_descr = v.findViewById(R.id.card_descr);
                    vis_image = v.findViewById(R.id.card_image);

                    vis_title.setText(model.getTitle());
                    vis_descr.setText(model.getTitle_descr());
                    Picasso.with(MenuActivity.this).load(model.getLogo()).into(vis_image);

                    TextView inv_descr = v.findViewById(R.id.card_inv_descr);
                    TextView inv_image = v.findViewById(R.id.card_inv_image);
                    TextView inv_site = v.findViewById(R.id.card_inv_site);
                    TextView inv_x = v.findViewById(R.id.card_inv_x);
                    TextView inv_y = v.findViewById(R.id.card_inv_y);

                    inv_image.setText(model.getImage());
                    inv_descr.setText(model.getDescr());
                    inv_site.setText(model.getSite());
                    inv_x.setText((model.getX()).toString());
                    inv_y.setText((model.getY()).toString());
//                }
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