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
 * SpeakingLessonAdapter - RecyclerView adapter for speaking lessons
 */
public class SpeakingLessonAdapter extends RecyclerView.Adapter<SpeakingLessonAdapter.LessonViewHolder> {

    private List<SpeakingLesson> lessons;
    private OnLessonClickListener listener;

    public interface OnLessonClickListener {
        void onLessonClick(SpeakingLesson lesson);
    }

    public SpeakingLessonAdapter(List<SpeakingLesson> lessons, OnLessonClickListener listener) {
        this.lessons = lessons;
        this.listener = listener;
    }

    @NonNull
    @Override
    public LessonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_speaking_lesson, parent, false);
        return new LessonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LessonViewHolder holder, int position) {
        SpeakingLesson lesson = lessons.get(position);
        holder.bind(lesson);
    }

    @Override
    public int getItemCount() {
        return lessons.size();
    }

    class LessonViewHolder extends RecyclerView.ViewHolder {
        private MaterialCardView lessonIconContainer;
        private ImageView imgLessonIcon;
        private ImageView imgLockIcon;
        private TextView tvCategory;
        private TextView tvLessonTitle;
        private TextView tvLessonDescription;
        private TextView tvWordCount;
        private ImageView btnStartLesson;

        public LessonViewHolder(@NonNull View itemView) {
            super(itemView);

            lessonIconContainer = itemView.findViewById(R.id.lessonIconContainer);
            imgLessonIcon = itemView.findViewById(R.id.imgLessonIcon);
            imgLockIcon = itemView.findViewById(R.id.imgLockIcon);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvLessonTitle = itemView.findViewById(R.id.tvLessonTitle);
            tvLessonDescription = itemView.findViewById(R.id.tvLessonDescription);
            tvWordCount = itemView.findViewById(R.id.tvWordCount);
            btnStartLesson = itemView.findViewById(R.id.btnStartLesson);
        }

        public void bind(SpeakingLesson lesson) {
            // Set lesson info
            tvCategory.setText(lesson.getCategory().toUpperCase());
            tvLessonTitle.setText(lesson.getTitle());
            tvLessonDescription.setText(lesson.getDescription());
            tvWordCount.setText(String.valueOf(lesson.getVocabCount()));

            // Handle locked state
            if (lesson.isLocked()) {
                imgLockIcon.setVisibility(View.VISIBLE);
                imgLessonIcon.setAlpha(0.5f);
                itemView.setAlpha(0.7f);
                itemView.setEnabled(false);
                btnStartLesson.setEnabled(false);
            } else {
                imgLockIcon.setVisibility(View.GONE);
                imgLessonIcon.setAlpha(1.0f);
                itemView.setAlpha(1.0f);
                itemView.setEnabled(true);
                btnStartLesson.setEnabled(true);

                // Click listeners
                itemView.setOnClickListener(v -> {
                    if (listener != null) {
                        listener.onLessonClick(lesson);
                    }
                });

                btnStartLesson.setOnClickListener(v -> {
                    if (listener != null) {
                        listener.onLessonClick(lesson);
                    }
                });
            }

            // Set category color
            setCategoryColor(lesson.getCategory());
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
                    colorResId = R.color.pastel_purple;
                    break;
                case "food":
                    colorResId = R.color.pastel_blue;
                    break;
                default:
                    colorResId = R.color.pastel_blue;
            }

            lessonIconContainer.setCardBackgroundColor(
                    itemView.getContext().getResources().getColor(colorResId)
            );
            tvCategory.setBackgroundColor(
                    itemView.getContext().getResources().getColor(colorResId)
            );
        }
    }
}
