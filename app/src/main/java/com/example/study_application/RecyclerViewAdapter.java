package com.example.study_application;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    public static final String EXTRA_NUMBER = "package com.example.study_application";
    private static final String TAG = "RecyclerViewAdapter";

    //vars
    private final ArrayList<String> mNames;
    private final Context mContext;

    public RecyclerViewAdapter(Context mContext, ArrayList<String> mNames) {
        this.mNames = mNames;
        this.mContext = mContext;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contents, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerViewAdapter.ViewHolder holder, int position) {
        holder.carouselText.setText(mNames.get(position));
        holder.carouselButton.setOnClickListener(v -> {
            Log.d(TAG, "onClick: On an a button: " + mNames.get(position));
            Intent intent = new Intent(mContext, ContentPoppupScreen.class);
            intent.putExtra(EXTRA_NUMBER, position + 1);
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mNames.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView carouselText;
        Button carouselButton;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            carouselText = itemView.findViewById(R.id.carouselText);
            carouselButton = itemView.findViewById(R.id.carouselButton);
        }
    }
}
