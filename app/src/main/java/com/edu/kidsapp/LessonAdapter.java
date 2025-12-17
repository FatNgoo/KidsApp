package com.edu.kidsapp;

import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * LessonAdapter - ViewPager2 adapter for flashcard display
 * Handles vocabulary card rendering and audio playback
 */
public class LessonAdapter extends RecyclerView.Adapter<LessonAdapter.FlashcardViewHolder> {

    public interface TtsReadyProvider {
        boolean isReady();
    }

    private List<Vocab> vocabList;
    private TextToSpeech textToSpeech;
    private TtsReadyProvider ttsReadyProvider;

    public LessonAdapter(List<Vocab> vocabList, TextToSpeech textToSpeech, TtsReadyProvider ttsReadyProvider) {
        this.vocabList = vocabList;
        this.textToSpeech = textToSpeech;
        this.ttsReadyProvider = ttsReadyProvider;
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

        // Speaker button click handler - Play pronunciation
        holder.btnSpeaker.setOnClickListener(v -> {
            if (ttsReadyProvider.isReady() && textToSpeech != null) {
                textToSpeech.speak(vocab.getEnglish(), TextToSpeech.QUEUE_FLUSH, null, null);
                // Animate button
                holder.btnSpeaker.animate()
                        .scaleX(0.8f).scaleY(0.8f)
                        .setDuration(100)
                        .withEndAction(() -> holder.btnSpeaker.animate()
                                .scaleX(1f).scaleY(1f)
                                .setDuration(100)
                                .start())
                        .start();
            }
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
