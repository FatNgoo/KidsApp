package com.edu.kidsapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * LevelAdapter - RecyclerView adapter for Adventure Map level nodes
 * Handles different states: locked, current, completed
 */
public class LevelAdapter extends RecyclerView.Adapter<LevelAdapter.LevelViewHolder> {

    private List<LevelModel> levels;
    private OnLevelClickListener listener;

    public interface OnLevelClickListener {
        void onLevelClick(LevelModel level);
    }

    public LevelAdapter(List<LevelModel> levels, OnLevelClickListener listener) {
        this.levels = levels;
        this.listener = listener;
    }

    @NonNull
    @Override
    public LevelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_level_node, parent, false);
        return new LevelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LevelViewHolder holder, int position) {
        LevelModel level = levels.get(position);
        
        // Set level number
        holder.tvLevelNumber.setText(String.valueOf(level.getId()));

        // Apply winding path effect: alternate left and right
        ConstraintLayout.LayoutParams params = 
                (ConstraintLayout.LayoutParams) holder.levelNode.getLayoutParams();
        
        if (position % 2 == 0) {
            // Even position: shift left
            params.setMarginStart(dpToPx(50));
            params.setMarginEnd(0);
        } else {
            // Odd position: shift right
            params.setMarginStart(0);
            params.setMarginEnd(dpToPx(50));
        }
        holder.levelNode.setLayoutParams(params);

        // Set state based on level status
        if (level.isCompleted()) {
            // Completed state
            holder.levelNode.setBackgroundResource(R.drawable.shape_level_completed);
            holder.levelNode.setEnabled(true);
            holder.levelNode.setClickable(true);
            
            // Show stars
            holder.starsContainer.setVisibility(View.VISIBLE);
            showStars(holder, level.getStars());
            
        } else if (level.isLocked()) {
            // Locked state
            holder.levelNode.setBackgroundResource(R.drawable.shape_level_locked);
            holder.levelNode.setEnabled(false);
            holder.levelNode.setClickable(false);
            holder.starsContainer.setVisibility(View.GONE);
            
        } else {
            // Current/Unlocked state
            holder.levelNode.setBackgroundResource(R.drawable.shape_level_current);
            holder.levelNode.setEnabled(true);
            holder.levelNode.setClickable(true);
            holder.starsContainer.setVisibility(View.GONE);
        }

        // Click listener
        holder.levelNode.setOnClickListener(v -> {
            if (!level.isLocked() && listener != null) {
                listener.onLevelClick(level);
            }
        });

        // Hide connector line for first item
        if (position == 0) {
            holder.connectorLine.setVisibility(View.INVISIBLE);
        } else {
            holder.connectorLine.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return levels.size();
    }

    /**
     * Show the appropriate number of stars
     */
    private void showStars(LevelViewHolder holder, int starCount) {
        holder.star1.setVisibility(starCount >= 1 ? View.VISIBLE : View.INVISIBLE);
        holder.star2.setVisibility(starCount >= 2 ? View.VISIBLE : View.INVISIBLE);
        holder.star3.setVisibility(starCount >= 3 ? View.VISIBLE : View.INVISIBLE);
    }

    /**
     * Convert dp to pixels
     */
    private int dpToPx(int dp) {
        float density = levels.get(0) != null ? 
                android.content.res.Resources.getSystem().getDisplayMetrics().density : 3f;
        return Math.round(dp * density);
    }

    static class LevelViewHolder extends RecyclerView.ViewHolder {
        FrameLayout levelNode;
        TextView tvLevelNumber;
        LinearLayout starsContainer;
        ImageView star1, star2, star3;
        View connectorLine;

        public LevelViewHolder(@NonNull View itemView) {
            super(itemView);
            levelNode = itemView.findViewById(R.id.levelNode);
            tvLevelNumber = itemView.findViewById(R.id.tvLevelNumber);
            starsContainer = itemView.findViewById(R.id.starsContainer);
            star1 = itemView.findViewById(R.id.star1);
            star2 = itemView.findViewById(R.id.star2);
            star3 = itemView.findViewById(R.id.star3);
            connectorLine = itemView.findViewById(R.id.connectorLine);
        }
    }
}
