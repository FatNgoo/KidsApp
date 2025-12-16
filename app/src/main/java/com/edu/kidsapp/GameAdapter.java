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
 * GameAdapter - RecyclerView adapter for game cards in lobby
 * Displays game thumbnails, titles, and ticket costs in grid layout
 */
public class GameAdapter extends RecyclerView.Adapter<GameAdapter.GameViewHolder> {

    private List<GameModel> games;
    private OnGameClickListener listener;

    public interface OnGameClickListener {
        void onGameClick(GameModel game);
    }

    public GameAdapter(List<GameModel> games, OnGameClickListener listener) {
        this.games = games;
        this.listener = listener;
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_game_card, parent, false);
        return new GameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
        GameModel game = games.get(position);

        // Set game data
        holder.tvGameTitle.setText(game.getName());
        holder.tvCost.setText(game.getTicketCost() + " 🎫");
        holder.imgGameThumb.setImageResource(game.getImageRes());

        // Click listener
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onGameClick(game);
            }
        });
    }

    @Override
    public int getItemCount() {
        return games.size();
    }

    static class GameViewHolder extends RecyclerView.ViewHolder {
        ImageView imgGameThumb;
        TextView tvGameTitle;
        TextView tvCost;

        public GameViewHolder(@NonNull View itemView) {
            super(itemView);
            imgGameThumb = itemView.findViewById(R.id.imgGameThumb);
            tvGameTitle = itemView.findViewById(R.id.tvGameTitle);
            tvCost = itemView.findViewById(R.id.tvCost);
        }
    }
}
