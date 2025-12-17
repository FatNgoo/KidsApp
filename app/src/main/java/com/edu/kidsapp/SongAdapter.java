package com.edu.kidsapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.List;

/**
 * SongAdapter - RecyclerView adapter for displaying songs in Music Map
 */
public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder> {

    private List<SongCategory> songs;
    private OnSongClickListener listener;

    public interface OnSongClickListener {
        void onSongClick(SongCategory song);
    }

    public SongAdapter(List<SongCategory> songs, OnSongClickListener listener) {
        this.songs = songs;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_song, parent, false);
        return new SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
        SongCategory song = songs.get(position);
        holder.bind(song);
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    class SongViewHolder extends RecyclerView.ViewHolder {
        private MaterialCardView songIconContainer;
        private ImageView imgSongIcon;
        private ImageView imgLockIcon;
        private TextView tvCategory;
        private TextView tvSongTitle;
        private TextView tvSongSubtitle;
        private TextView tvBlankCount;
        private ImageView btnPlaySong;

        public SongViewHolder(@NonNull View itemView) {
            super(itemView);

            songIconContainer = itemView.findViewById(R.id.songIconContainer);
            imgSongIcon = itemView.findViewById(R.id.imgSongIcon);
            imgLockIcon = itemView.findViewById(R.id.imgLockIcon);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvSongTitle = itemView.findViewById(R.id.tvSongTitle);
            tvSongSubtitle = itemView.findViewById(R.id.tvSongSubtitle);
            tvBlankCount = itemView.findViewById(R.id.tvBlankCount);
            btnPlaySong = itemView.findViewById(R.id.btnPlaySong);
        }

        public void bind(SongCategory song) {
            // Set song info
            tvCategory.setText(song.getCategory().toUpperCase());
            tvSongTitle.setText(song.getTitle());
            tvSongSubtitle.setText(song.getSubtitle());
            tvBlankCount.setText(String.valueOf(song.getTotalBlanks()));

            // Set icon based on category
            setCategoryIcon(song.getCategory());

            // Handle locked state
            if (song.isLocked()) {
                imgLockIcon.setVisibility(View.VISIBLE);
                imgSongIcon.setAlpha(0.5f);
                itemView.setAlpha(0.7f);
                itemView.setEnabled(false);
                btnPlaySong.setEnabled(false);
            } else {
                imgLockIcon.setVisibility(View.GONE);
                imgSongIcon.setAlpha(1.0f);
                itemView.setAlpha(1.0f);
                itemView.setEnabled(true);
                btnPlaySong.setEnabled(true);

                // Click listener
                itemView.setOnClickListener(v -> {
                    if (listener != null) {
                        listener.onSongClick(song);
                    }
                });

                btnPlaySong.setOnClickListener(v -> {
                    if (listener != null) {
                        listener.onSongClick(song);
                    }
                });
            }

            // Set category color
            setCategoryColor(song.getCategory());
        }

        /**
         * Set icon based on category
         */
        private void setCategoryIcon(String category) {
            // Use different icons for different categories
            switch (category.toLowerCase()) {
                case "animals":
                    imgSongIcon.setImageResource(android.R.drawable.ic_media_play);
                    break;
                case "colors":
                    imgSongIcon.setImageResource(android.R.drawable.ic_media_play);
                    break;
                case "numbers":
                    imgSongIcon.setImageResource(android.R.drawable.ic_media_play);
                    break;
                case "nursery rhymes":
                    imgSongIcon.setImageResource(android.R.drawable.ic_media_play);
                    break;
                default:
                    imgSongIcon.setImageResource(android.R.drawable.ic_media_play);
            }
        }

        /**
         * Set background color based on category
         */
        private void setCategoryColor(String category) {
            int colorResId;
            switch (category.toLowerCase()) {
                case "animals":
                    colorResId = R.color.pastel_green;
                    break;
                case "colors":
                    colorResId = R.color.pastel_orange;
                    break;
                case "numbers":
                    colorResId = R.color.pastel_blue;
                    break;
                case "nursery rhymes":
                    colorResId = R.color.pastel_purple;
                    break;
                default:
                    colorResId = R.color.pastel_purple;
            }

            songIconContainer.setCardBackgroundColor(
                    itemView.getContext().getResources().getColor(colorResId)
            );
            tvCategory.setBackgroundColor(
                    itemView.getContext().getResources().getColor(colorResId)
            );
        }
    }
}
