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

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.viewHolder> {
    public static final String EXTRA_NUMBER = "package com.example.study_application";
    private static final String TAG = "RecyclerViewAdapter";

    //vars
    private final ArrayList<String> Names;
    private final ArrayList<String> Ids;
    private final Context context;

    public RecyclerViewAdapter(Context context, ArrayList<String> Names, ArrayList<String> Ids) {
        //used get the information from the homeScreen so i don't have to
        // make the method of reading and writing a file inside the recyclerview
        this.Names = Names;
        this.Ids = Ids;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // is used to set the amount of items allowed on the screen
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contents, parent, false);
        return new viewHolder(view);
    }

    // this is what updates the recycler view
    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.carouselText.setText(Names.get(position).replace("_"," "));
        holder.layout.setOnClickListener(v -> {
            Log.d(TAG, "onClick: On an a button: " + Names.get(position));
            Intent toContentPopupScreen = new Intent(context, ContentPopupScreen.class);
            toContentPopupScreen.putExtra(EXTRA_NUMBER, Ids.get(position));

            // this code is used for the transition between the cardView to another layout
            Pair layout = Pair.create(holder.layout, "shared_container");
            Pair textName = Pair.create(holder.carouselButton, "transition_button");
            Pair textBody = Pair.create(holder.carouselText, "transition_text");
            ActivityOptionsCompat transitionToNextScreen = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, layout, textName, textBody);

            //goes to the next screen
            context.startActivity(toContentPopupScreen, transitionToNextScreen.toBundle());
        });

        //checks if the button inside the layout is clicked instead
        holder.carouselButton.setOnClickListener(v -> {
            Intent toTaskScreen = new Intent(context, TaskScreen.class);
            toTaskScreen.putExtra(EXTRA_NUMBER, Ids.get(position));
            //sends user straight to the task start screen
            context.startActivity(toTaskScreen);
        });
    }

    //finds the amount of items for the recyclerview
    @Override
    public int getItemCount() {
        return Names.size();
    }

    //this code makes it so that i can call these names to
    // change there object attached in places like on viewBindHolder
    public static class viewHolder extends RecyclerView.ViewHolder {
        TextView carouselText;
        Button carouselButton;
        CardView layout;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            carouselText = itemView.findViewById(R.id.carouselText);
            carouselButton = itemView.findViewById(R.id.carouselButton);
            carouselButton.bringToFront();
            layout = itemView.findViewById(R.id.layout);
        }
    }
}
