package com.example.registration;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYouListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MenuRecyclerAdapter extends RecyclerView.Adapter<MenuRecyclerAdapter.CardViewHolder> {

    Context ctx;

    List<Card> list = new ArrayList<Card>();

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        View view;
        public CardViewHolder(View itemView) {
            super(itemView);
            view = itemView;
        }
        public void setDetails(final Context ctx, final Card model){

            ((TextView)view.findViewById(R.id.card_title)).setText(model.getTitle());
            ((TextView)view.findViewById(R.id.card_descr)).setText(model.getTitle_descr());

            Picasso.with(ctx).load(model.getLogo()).into((ImageView)view.findViewById(R.id.card_image));
            //ImageView imView = view.findViewById(R.id.card_image);

//            GlideToVectorYou
//                    .init()
//                    .with(ctx)
//                    .withListener(new GlideToVectorYouListener() {
//                        @Override
//                        public void onLoadFailed() {
//                            Toast.makeText(ctx, "Load failed", Toast.LENGTH_SHORT).show();
//                        }
//
//                        @Override
//                        public void onResourceReady() {
//                            Toast.makeText(ctx, "Image ready", Toast.LENGTH_SHORT).show();
//                        }
//                    })
//                    //.setPlaceHolder(placeholderLoading, placeholderError)
//                    .load(Uri.parse(model.getLogo()), imView);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent toDescriptionActivity = new Intent(ctx, DescriptionActivity.class);
                    toDescriptionActivity.putExtra("model", model);
                    ctx.startActivity(toDescriptionActivity);
                }
            });
        }
    }


    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CardViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        holder.setDetails(ctx, list.get(position));
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public void updateList(ArrayList list){
        this.list = list;
        notifyDataSetChanged();
    }

    public void setContext(Context ctx){
        this.ctx = ctx;
        notifyDataSetChanged();
    }
}
