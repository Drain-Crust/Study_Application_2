package com.example.study_application;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    public static final String EXTRA_NUMBER = "package com.example.study_application";
    private static final String TAG = "RecyclerViewAdapter";

    //vars
    private final ArrayList<String> mNames;
    private final ArrayList<String> mIds;
    private final Context mContext;

    public RecyclerViewAdapter(Context mContext, ArrayList<String> mNames, ArrayList<String> mIds) {
        //used get the information from the homeScreen so i don't have to
        // make the method of reading and writing a file inside the recyclerview
        this.mNames = mNames;
        this.mIds = mIds;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // is used to set the amount of items allowed on the screen
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contents, parent, false);
        return new ViewHolder(view);
    }

    // this is what updates the recycler view
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        holder.carouselText.setText(mNames.get(position).replace("_"," "));
        holder.layout.setOnClickListener(v -> {
            Log.d(TAG, "onClick: On an a button: " + mNames.get(position));
            Intent intent = new Intent(mContext, ContentPopupScreen.class);
            intent.putExtra(EXTRA_NUMBER, mIds.get(position));

            // this code is used for the transition between the cardview to another layout
            Pair mLayout = Pair.create(holder.layout, "shared_container");
            Pair textName = Pair.create(holder.carouselButton, "transition_button");
            Pair textBody = Pair.create(holder.carouselText, "transition_text");
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) mContext, mLayout, textName, textBody);

            //goes to the next scren
            mContext.startActivity(intent, options.toBundle());
        });

        //checks if the button inside the layout is clicked instead
        holder.carouselButton.setOnClickListener(v -> {
            Intent intent1 = new Intent(mContext, TaskScreen.class);
            intent1.putExtra(EXTRA_NUMBER, mIds.get(position));
            //sends user straight to the task start screen
            mContext.startActivity(intent1);
        });
    }

    //finds the amount of items for the recyclerview
    @Override
    public int getItemCount() {
        return mNames.size();
    }

    //this code makes it so that i can call these names to
    // change there object attached in places like on viewBindHolder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView carouselText;
        Button carouselButton;
        CardView layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            carouselText = itemView.findViewById(R.id.carouselText);
            carouselButton = itemView.findViewById(R.id.carouselButton);
            carouselButton.bringToFront();
            layout = itemView.findViewById(R.id.layout);
        }
    }
}
