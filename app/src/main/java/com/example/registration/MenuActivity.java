package com.example.registration;

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

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.FirebaseDatabase;

public class MenuActivity extends AppCompatActivity{

    private ListView listOfCards;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);



        String[] title_logos = getResources().getStringArray(R.array.title_logos);
        String[] description_logos = getResources().getStringArray(R.array.description_logos);
        int[] image_logos = {R.drawable.hse, R.drawable.mfti, R.drawable.mgu, R.drawable.rhtu, };

        Card[] cards = new Card[title_logos.length];

        for(int i = 0; i < title_logos.length; i++){
            cards[i] = new Card(title_logos[i], description_logos[i], image_logos[i]);
        }

        listOfCards = (ListView) findViewById(R.id.list_of_cards);

        listOfCards.setAdapter(new CardAdapter(this, R.layout.card, cards));
        final Intent toDescriptionActivity = new Intent(this, DescriptionActivity.class);
        listOfCards.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                toDescriptionActivity.putExtra("position", position);
                startActivity(toDescriptionActivity);
            }
        });
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
