package com.edu.kidsapp;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * LessonFragment - Flashcard lesson screen using ViewPager2
 * Displays vocabulary cards with progress tracking and navigation
 */
public class LessonFragment extends Fragment {

    private ViewPager2 viewPagerLesson;
    private LinearProgressIndicator progressIndicator;
    private MaterialButton btnNext;
    private LessonAdapter adapter;
    private List<Vocab> vocabList;
    private int currentPosition = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lesson, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize views
        viewPagerLesson = view.findViewById(R.id.viewPagerLesson);
        progressIndicator = view.findViewById(R.id.progressIndicator);
        btnNext = view.findViewById(R.id.btnNext);
        View btnClose = view.findViewById(R.id.btnClose);

        // Create dummy vocabulary data
        createVocabularyData();

        // Setup ViewPager2
        setupViewPager();

        // Setup progress indicator
        progressIndicator.setMax(vocabList.size());
        progressIndicator.setProgress(1);

        // Setup Next button
        setupNextButton();

        // Setup Close button
        btnClose.setOnClickListener(v -> showQuitDialog());
    }

    /**
     * Create dummy vocabulary data: 5 words
     */
    private void createVocabularyData() {
        vocabList = new ArrayList<>();
        vocabList.add(new Vocab("Apple", "Quả táo", "placeholder"));
        vocabList.add(new Vocab("Banana", "Quả chuối", "placeholder"));
        vocabList.add(new Vocab("Cat", "Con mèo", "placeholder"));
        vocabList.add(new Vocab("Dog", "Con chó", "placeholder"));
        vocabList.add(new Vocab("Elephant", "Con voi", "placeholder"));
    }

    /**
     * Setup ViewPager2 with adapter and page change listener
     */
    private void setupViewPager() {
        adapter = new LessonAdapter(vocabList);
        viewPagerLesson.setAdapter(adapter);

        // Listen for page changes to update progress
        viewPagerLesson.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                currentPosition = position;
                updateProgress();
                updateNextButton();
            }
        });
    }

    /**
     * Update progress indicator based on current position
     */
    private void updateProgress() {
        progressIndicator.setProgress(currentPosition + 1);
    }

    /**
     * Update Next button text and behavior based on position
     */
    private void updateNextButton() {
        if (currentPosition == vocabList.size() - 1) {
            // Last item: change to FINISH
            btnNext.setText("FINISH");
        } else {
            // Not last: keep as CONTINUE
            btnNext.setText("CONTINUE");
        }
    }

    /**
     * Setup Next button click handler
     */
    private void setupNextButton() {
        btnNext.setOnClickListener(v -> {
            if (currentPosition < vocabList.size() - 1) {
                // Move to next card
                viewPagerLesson.setCurrentItem(currentPosition + 1, true);
            } else {
                // Last card: finish lesson
                finishLesson();
            }
        });
    }

    /**
     * Finish the lesson and navigate back
     */
    private void finishLesson() {
        Toast.makeText(requireContext(), 
                "Lesson Complete! 🎉", 
                Toast.LENGTH_LONG).show();
        
        // Navigate to Word Workshop (Spelling Game)
        Navigation.findNavController(requireView()).navigate(R.id.action_lesson_to_word_workshop);
    }

    /**
     * Show quit confirmation dialog
     */
    private void showQuitDialog() {
        new AlertDialog.Builder(requireContext())
                .setTitle("Quit Learning?")
                .setMessage("Are you sure you want to quit this lesson?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    Navigation.findNavController(requireView()).popBackStack();
                })
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .show();
    }
}
