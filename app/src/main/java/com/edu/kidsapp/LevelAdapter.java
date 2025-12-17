package com.edu.kidsapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * LevelAdapter - RecyclerView adapter for Adventure Map level nodes
 * Displays levels with title, image, and star progress
 */
public class LevelAdapter extends RecyclerView.Adapter<LevelAdapter.LevelViewHolder> {

    private List<LevelModel> levels;
    private OnLevelClickListener listener;
    private ProgressManager progressManager;

    public interface OnLevelClickListener {
        void onLevelClick(LevelModel level);
    }

    public LevelAdapter(List<LevelModel> levels, OnLevelClickListener listener, ProgressManager progressManager) {
        this.levels = levels;
        this.listener = listener;
        this.progressManager = progressManager;
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
        
        // Set level title
        holder.tvLevelTitle.setText(level.getName());

        // Get progress for this level
        LevelProgress progress = progressManager.getLevelProgress(level.getId());
        int stars = progress.getStarsEarned();
        boolean isUnlocked = progressManager.isLevelUnlocked(level.getId());

        // Update stars visibility and state
        updateStars(holder, stars);

        // Update lock status
        if (!isUnlocked) {
            holder.tvLockStatus.setVisibility(View.VISIBLE);
            holder.tvLockStatus.setText("🔒 Complete Level " + (level.getId() - 1) + " first");
            holder.itemView.setAlpha(0.6f);
        } else {
            holder.tvLockStatus.setVisibility(View.GONE);
            holder.itemView.setAlpha(1.0f);
        }

        // Click listener
        holder.itemView.setOnClickListener(v -> {
            if (isUnlocked && listener != null) {
                listener.onLevelClick(level);
            }
        });
    }

    @Override
    public int getItemCount() {
        return levels.size();
    }

    /**
     * Update star display based on earned stars
     */
    private void updateStars(LevelViewHolder holder, int starCount) {
        holder.star1.setImageResource(starCount >= 1 ? 
            android.R.drawable.btn_star_big_on : android.R.drawable.btn_star_big_off);
        holder.star2.setImageResource(starCount >= 2 ? 
            android.R.drawable.btn_star_big_on : android.R.drawable.btn_star_big_off);
        holder.star3.setImageResource(starCount >= 3 ? 
            android.R.drawable.btn_star_big_on : android.R.drawable.btn_star_big_off);
    }

    static class LevelViewHolder extends RecyclerView.ViewHolder {
        ImageView imgLevelIcon;
        TextView tvLevelNumber;
        TextView tvLevelTitle;
        ImageView star1, star2, star3;
        TextView tvLockStatus;

        public LevelViewHolder(@NonNull View itemView) {
            super(itemView);
            imgLevelIcon = itemView.findViewById(R.id.imgLevelIcon);
            tvLevelNumber = itemView.findViewById(R.id.tvLevelNumber);
            tvLevelTitle = itemView.findViewById(R.id.tvLevelTitle);
            star1 = itemView.findViewById(R.id.star1);
            star2 = itemView.findViewById(R.id.star2);
            star3 = itemView.findViewById(R.id.star3);
            tvLockStatus = itemView.findViewById(R.id.tvLockStatus);
        }
    }
}
