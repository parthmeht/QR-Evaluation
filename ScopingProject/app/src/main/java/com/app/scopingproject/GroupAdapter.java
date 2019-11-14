package com.app.scopingproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.List;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.ViewHolder> {
    private List<Group> groupList;

    public GroupAdapter(List<Group> groupList) {
        this.groupList = groupList;
    }

    @NonNull
    @Override
    public GroupAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_group, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GroupAdapter.ViewHolder holder, int position) {
        Group group = groupList.get(position);

        // Set item views based on your views and data model
        TextView groupNameTextView = holder.groupNameTextView;
        groupNameTextView.setText(group.getName());
        TextView avgScoreTextView = holder.avgScoreTextView;
        DecimalFormat df = new DecimalFormat("0.##");
        avgScoreTextView.setText(df.format(group.getAvgScore()));
    }

    @Override
    public int getItemCount() {
        return groupList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView groupNameTextView, avgScoreTextView;

        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            groupNameTextView = (TextView) itemView.findViewById(R.id.groupNameTextView);
            avgScoreTextView = (TextView) itemView.findViewById(R.id.avgScoreTextView);
        }
    }
}
