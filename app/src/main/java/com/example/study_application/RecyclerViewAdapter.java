package com.example.study_application;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
        this.mNames = mNames;
        this.mIds = mIds;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contents, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        holder.carouselText.setText(mNames.get(position));
        holder.carouselButton.setOnClickListener(v -> {
            Log.d(TAG, "onClick: On an a button: " + mNames.get(position));
            Intent intent = new Intent(mContext, ContentPoppupScreen.class);
            intent.putExtra(EXTRA_NUMBER, mIds.get(position));
            Pair mLayout = Pair.create(holder.layout, "shared_container");
            Pair textName = Pair.create(holder.carouselButton, "transition_button");
            Pair textBody = Pair.create(holder.carouselText, "transition_text");
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) mContext, mLayout, textName, textBody);
            mContext.startActivity(intent, options.toBundle());
        });
    }

    @Override
    public int getItemCount() {
        return mNames.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView carouselText;
        Button carouselButton;
        CardView layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            carouselText = itemView.findViewById(R.id.carouselText);
            carouselButton = itemView.findViewById(R.id.carouselButton);
            layout = itemView.findViewById(R.id.layout);
        }
    }
}
