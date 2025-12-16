package com.edu.kidsapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * LessonAdapter - ViewPager2 adapter for flashcard display
 * Handles vocabulary card rendering and audio playback
 */
public class LessonAdapter extends RecyclerView.Adapter<LessonAdapter.FlashcardViewHolder> {

    private List<Vocab> vocabList;

    public LessonAdapter(List<Vocab> vocabList) {
        this.vocabList = vocabList;
    }

    @NonNull
    @Override
    public FlashcardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_flashcard, parent, false);
        return new FlashcardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FlashcardViewHolder holder, int position) {
        Vocab vocab = vocabList.get(position);

        // Set text content
        holder.tvEnglish.setText(vocab.getEnglish());
        holder.tvVietnamese.setText(vocab.getVietnamese());

        // Set image (using placeholder for now)
        holder.imgVocab.setImageResource(R.drawable.placeholder_school);

        // Speaker button click handler
        holder.btnSpeaker.setOnClickListener(v -> {
            Toast.makeText(v.getContext(), 
                    "Playing sound: " + vocab.getEnglish(), 
                    Toast.LENGTH_SHORT).show();
            // TODO: Implement actual text-to-speech or audio playback
        });
    }

    @Override
    public int getItemCount() {
        return vocabList.size();
    }

    static class FlashcardViewHolder extends RecyclerView.ViewHolder {
        ImageView imgVocab;
        TextView tvEnglish;
        TextView tvVietnamese;
        ImageButton btnSpeaker;

        public FlashcardViewHolder(@NonNull View itemView) {
            super(itemView);
            imgVocab = itemView.findViewById(R.id.imgVocab);
            tvEnglish = itemView.findViewById(R.id.tvEnglish);
            tvVietnamese = itemView.findViewById(R.id.tvVietnamese);
            btnSpeaker = itemView.findViewById(R.id.btnSpeaker);
        }
    }
}
