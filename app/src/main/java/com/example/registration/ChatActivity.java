package com.example.registration;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseListAdapter;
import com.github.library.bubbleview.BubbleTextView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class ChatActivity extends AppCompatActivity {

    private static int SIGN_IN_CODE = 1;
    private RelativeLayout activity_main;
    private FirebaseListAdapter<Message> adapter;
    private FloatingActionButton sendButton;

    private String title;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SIGN_IN_CODE) {
            if(resultCode == RESULT_OK){
                displayAllMassages();
            }
            else{
                finish();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_chat);

        activity_main = findViewById(R.id.activity_main);
        sendButton = findViewById(R.id.btnSend);

        Intent fromDescriptionActivity = getIntent();
        Card card = fromDescriptionActivity.getParcelableExtra("model");
        title = card.getTitle();

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText textField = findViewById(R.id.messageField);
                textField.setTextColor(getResources().getColor(R.color.white));
                if(textField.getText().toString().equals(""))
                    return;
                FirebaseDatabase.getInstance().getReference().child("Messages").child(title).push().setValue(
                        new Message(
                                FirebaseAuth.getInstance().getCurrentUser().getEmail(),
                                textField.getText().toString()
                        )
                );
                textField.setText("");
            }
        });

        if(FirebaseAuth.getInstance().getCurrentUser() == null)
            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().build(), SIGN_IN_CODE);
        else
        displayAllMassages();
    }
    private void displayAllMassages() {
        ListView listOfMessages = findViewById(R.id.list_of_messages);
        adapter = new FirebaseListAdapter<Message>(this, Message.class, R.layout.message, FirebaseDatabase.getInstance().getReference().child("Messages").child(title)) {
            @Override
            protected void populateView(View v, Message message, int position) {
                TextView mess_user, mess_time;
                BubbleTextView mess_text;
                mess_user = v.findViewById(R.id.message_user);
                mess_time = v.findViewById(R.id.message_time);
                mess_text = v.findViewById(R.id.message_text);

                mess_text.setText(message.getUserName());
                mess_user.setText(message.getTextMessage());
                mess_time.setText(DateFormat.format("dd-MM-yyyy HH:mm:ss", message.getMessageTime()));
            }
        };
        listOfMessages.setAdapter(adapter);
    }
}
