package com.example.registration;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

//public class CardAdapter extends ArrayAdapter<Card> {
//
//    public CardAdapter(@NonNull Context context, int resource, @NonNull Card[] cards) {
//        super(context, resource, cards);
//    }
//    @NonNull
//    @Override
//    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View view = inflater.inflate(R.layout.card, null);
//        ((TextView)view.findViewById(R.id.card_title)).setText(getItem(position).getTitle());
//        ((TextView)view.findViewById(R.id.card_descr)).setText(getItem(position).getTitle_descr());
//        ((ImageView)view.findViewById(R.id.card_image)).setImageResource(Integer.parseInt(String.valueOf((getItem(position)).getLogo())));
//        return view;
//    }
//}
