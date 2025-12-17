package com.edu.kidsapp;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

/**
 * MiniQuizFragment - Multiple Choice Quiz Game
 * 
 * CORE MECHANIC:
 * User sees a question with an image and must select the correct answer
 * from 4 multiple choice options (A, B, C, D).
 * 
 * FEATURES:
 * - Card-based colorful UI matching app's pastel theme
 * - 5 questions per quiz
 * - Instant feedback (correct/incorrect)
 * - Progress tracking with stars
 * - Completion marks activity as done
 */
public class MiniQuizFragment extends Fragment {

    // UI Components
    private TextView tvQuestionProgress;
    private ImageView imgQuestion;
    private TextView tvQuestion;
    private TextView tvAnswerA, tvAnswerB, tvAnswerC, tvAnswerD;
    private MaterialCardView cardAnswerA, cardAnswerB, cardAnswerC, cardAnswerD;
    private MaterialCardView cardNextButton;
    private TextView btnNext;

    // Quiz Data
    private List<QuizQuestion> questions;
    private int currentQuestionIndex = 0;
    private int correctAnswers = 0;
    
    // Level tracking
    private int levelId = 1;
    private String activityType = "miniquiz";
    
    // Answer tracking
    private boolean isAnswered = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Get arguments if passed from LevelDetailFragment
        if (getArguments() != null) {
            levelId = getArguments().getInt("levelId", 1);
            activityType = getArguments().getString("activityType", "miniquiz");
        }
        
        // Initialize placeholder quiz data
        initializeQuestions();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mini_quiz, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize views
        initViews(view);

        // Load first question
        loadQuestion();

        // Setup click listeners
        setupClickListeners();
    }

    /**
     * Initialize UI components
     */
    private void initViews(View view) {
        tvQuestionProgress = view.findViewById(R.id.tv_question_progress);
        imgQuestion = view.findViewById(R.id.img_question);
        tvQuestion = view.findViewById(R.id.tv_question);
        tvAnswerA = view.findViewById(R.id.tv_answer_a);
        tvAnswerB = view.findViewById(R.id.tv_answer_b);
        tvAnswerC = view.findViewById(R.id.tv_answer_c);
        tvAnswerD = view.findViewById(R.id.tv_answer_d);
        cardAnswerA = view.findViewById(R.id.cardAnswerA);
        cardAnswerB = view.findViewById(R.id.cardAnswerB);
        cardAnswerC = view.findViewById(R.id.cardAnswerC);
        cardAnswerD = view.findViewById(R.id.cardAnswerD);
        cardNextButton = view.findViewById(R.id.cardNextButton);
        btnNext = view.findViewById(R.id.btn_next);
    }

    /**
     * Initialize placeholder quiz questions
     * TODO: Replace with actual data from database/API
     */
    private void initializeQuestions() {
        questions = new ArrayList<>();
        
        // Question 1
        questions.add(new QuizQuestion(
            "What is this fruit?",
            R.drawable.ic_launcher_foreground, // Placeholder image
            "Apple", "Banana", "Cat", "Dog",
            0 // Correct answer index (A = 0)
        ));
        
        // Question 2
        questions.add(new QuizQuestion(
            "What color is the sky?",
            R.drawable.ic_launcher_foreground,
            "Red", "Blue", "Green", "Yellow",
            1 // Correct answer index (B = 1)
        ));
        
        // Question 3
        questions.add(new QuizQuestion(
            "How many legs does a cat have?",
            R.drawable.ic_launcher_foreground,
            "Two", "Three", "Four", "Five",
            2 // Correct answer index (C = 2)
        ));
        
        // Question 4
        questions.add(new QuizQuestion(
            "What animal says 'Woof'?",
            R.drawable.ic_launcher_foreground,
            "Cat", "Bird", "Fish", "Dog",
            3 // Correct answer index (D = 3)
        ));
        
        // Question 5
        questions.add(new QuizQuestion(
            "What do we drink?",
            R.drawable.ic_launcher_foreground,
            "Book", "Water", "Chair", "Ball",
            1 // Correct answer index (B = 1)
        ));
    }

    /**
     * Load current question into UI
     */
    private void loadQuestion() {
        if (currentQuestionIndex >= questions.size()) {
            // Quiz completed
            showCompletionDialog();
            return;
        }

        QuizQuestion question = questions.get(currentQuestionIndex);
        
        // Update progress
        tvQuestionProgress.setText("Question " + (currentQuestionIndex + 1) + "/" + questions.size());
        
        // Update question and image
        tvQuestion.setText(question.questionText);
        imgQuestion.setImageResource(question.imageResId);
        
        // Update answer options
        tvAnswerA.setText(question.optionA);
        tvAnswerB.setText(question.optionB);
        tvAnswerC.setText(question.optionC);
        tvAnswerD.setText(question.optionD);
        
        // Reset answer cards to default colors
        resetAnswerCards();
        
        // Hide next button
        cardNextButton.setVisibility(View.GONE);
        isAnswered = false;
    }

    /**
     * Reset answer cards to default pastel colors
     */
    private void resetAnswerCards() {
        cardAnswerA.setCardBackgroundColor(getResources().getColor(R.color.pastel_purple, null));
        cardAnswerB.setCardBackgroundColor(getResources().getColor(R.color.pastel_blue, null));
        cardAnswerC.setCardBackgroundColor(getResources().getColor(R.color.pastel_green, null));
        cardAnswerD.setCardBackgroundColor(getResources().getColor(R.color.pastel_orange, null));
        
        // Re-enable all cards
        cardAnswerA.setEnabled(true);
        cardAnswerB.setEnabled(true);
        cardAnswerC.setEnabled(true);
        cardAnswerD.setEnabled(true);
    }

    /**
     * Setup click listeners for answer options
     */
    private void setupClickListeners() {
        cardAnswerA.setOnClickListener(v -> handleAnswer(0));
        cardAnswerB.setOnClickListener(v -> handleAnswer(1));
        cardAnswerC.setOnClickListener(v -> handleAnswer(2));
        cardAnswerD.setOnClickListener(v -> handleAnswer(3));
        
        btnNext.setOnClickListener(v -> {
            currentQuestionIndex++;
            loadQuestion();
        });
    }

    /**
     * Handle answer selection
     * @param selectedIndex Index of selected answer (0=A, 1=B, 2=C, 3=D)
     */
    private void handleAnswer(int selectedIndex) {
        if (isAnswered) return; // Prevent multiple selections
        
        isAnswered = true;
        QuizQuestion question = questions.get(currentQuestionIndex);
        
        // Get selected card
        MaterialCardView selectedCard = null;
        switch (selectedIndex) {
            case 0: selectedCard = cardAnswerA; break;
            case 1: selectedCard = cardAnswerB; break;
            case 2: selectedCard = cardAnswerC; break;
            case 3: selectedCard = cardAnswerD; break;
        }
        
        // Check if answer is correct
        if (selectedIndex == question.correctAnswerIndex) {
            // Correct answer - turn green
            selectedCard.setCardBackgroundColor(Color.parseColor("#4CAF50")); // Bright green
            correctAnswers++;
            Toast.makeText(requireContext(), "✅ Correct! Great job!", Toast.LENGTH_SHORT).show();
        } else {
            // Wrong answer - turn red
            selectedCard.setCardBackgroundColor(Color.parseColor("#F44336")); // Bright red
            
            // Highlight correct answer in green
            MaterialCardView correctCard = null;
            switch (question.correctAnswerIndex) {
                case 0: correctCard = cardAnswerA; break;
                case 1: correctCard = cardAnswerB; break;
                case 2: correctCard = cardAnswerC; break;
                case 3: correctCard = cardAnswerD; break;
            }
            correctCard.setCardBackgroundColor(Color.parseColor("#4CAF50"));
            
            Toast.makeText(requireContext(), "❌ Oops! Try again next time", Toast.LENGTH_SHORT).show();
        }
        
        // Disable all answer cards
        cardAnswerA.setEnabled(false);
        cardAnswerB.setEnabled(false);
        cardAnswerC.setEnabled(false);
        cardAnswerD.setEnabled(false);
        
        // Show next button
        cardNextButton.setVisibility(View.VISIBLE);
    }

    /**
     * Show completion dialog when quiz is finished
     */
    private void showCompletionDialog() {
        int totalQuestions = questions.size();
        int percentage = (correctAnswers * 100) / totalQuestions;
        
        String title = "🎉 Quiz Completed!";
        String message = "You got " + correctAnswers + "/" + totalQuestions + " correct!\n\n";
        
        if (percentage >= 80) {
            message += "⭐⭐⭐ Excellent work! You're a star!";
        } else if (percentage >= 60) {
            message += "⭐⭐ Good job! Keep practicing!";
        } else {
            message += "⭐ Nice try! You'll do better next time!";
        }
        
        new AlertDialog.Builder(requireContext())
            .setTitle(title)
            .setMessage(message)
            .setCancelable(false)
            .setPositiveButton("Finish", (dialog, which) -> {
                // Mark activity as completed
                ProgressManager.getInstance(requireContext())
                    .setActivityCompleted(levelId, activityType);
                
                // Navigate back to level detail
                Navigation.findNavController(requireView()).popBackStack();
            })
            .show();
    }

    /**
     * Inner class to represent a quiz question
     */
    private static class QuizQuestion {
        String questionText;
        int imageResId;
        String optionA, optionB, optionC, optionD;
        int correctAnswerIndex; // 0=A, 1=B, 2=C, 3=D

        QuizQuestion(String questionText, int imageResId, 
                    String optionA, String optionB, String optionC, String optionD, 
                    int correctAnswerIndex) {
            this.questionText = questionText;
            this.imageResId = imageResId;
            this.optionA = optionA;
            this.optionB = optionB;
            this.optionC = optionC;
            this.optionD = optionD;
            this.correctAnswerIndex = correctAnswerIndex;
        }
    }
}
