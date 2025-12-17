package com.edu.kidsapp;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.flexbox.FlexboxLayout;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MusicRoomFragment - Learn through songs with fill-in-the-blank exercise
 * 
 * CORE MECHANIC:
 * - User listens to a song (simulated with play/pause button)
 * - Song pauses at specific points (blanks in lyrics)
 * - User must fill in missing words from word bank
 * - Check answers and get score
 * 
 * FEATURES:
 * - Dynamic lyrics display with blanks
 * - Word bank with shuffled words
 * - Answer checking with visual feedback
 * - Score tracking
 */
public class MusicRoomFragment extends Fragment {

    // UI Components
    private TextView tvSongTitle;
    private TextView tvSongSubtitle;
    private TextView tvProgress;
    private TextView tvScore;
    private MaterialButton btnPlayPause;
    private MaterialButton btnCheckAnswer;
    private ImageView imgMusicIcon;
    private LinearLayout layoutLyricsContainer;
    private FlexboxLayout layoutWordBank;
    private MaterialCardView cardScore;

    // Game Data
    private Song currentSong;
    private List<String> wordBankList;
    private Map<Integer, TextView> blankTextViews;  // Map blank index to TextView
    private Map<Integer, String> userAnswers;  // Map blank index to user's answer
    private int currentBlankIndex = 0;
    private boolean isPlaying = false;
    private boolean answersChecked = false;

    // Simulated music playback
    private Handler playbackHandler = new Handler();
    private int playbackPosition = 0;
    private static final int PLAYBACK_INTERVAL = 2000;  // 2 seconds per line

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_music_room, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize UI
        initializeViews(view);

        // Load song (from arguments or use default)
        loadSong();

        // Setup UI
        setupSongInfo();
        setupLyrics();
        setupWordBank();
        setupClickListeners();
    }

    private void initializeViews(View view) {
        tvSongTitle = view.findViewById(R.id.tv_song_title);
        tvSongSubtitle = view.findViewById(R.id.tv_song_subtitle);
        tvProgress = view.findViewById(R.id.tv_progress);
        tvScore = view.findViewById(R.id.tv_score);
        btnPlayPause = view.findViewById(R.id.btn_play_pause);
        btnCheckAnswer = view.findViewById(R.id.btn_check_answer);
        imgMusicIcon = view.findViewById(R.id.img_music_icon);
        layoutLyricsContainer = view.findViewById(R.id.layout_lyrics_container);
        layoutWordBank = view.findViewById(R.id.layout_word_bank);
        cardScore = view.findViewById(R.id.cardScore);

        blankTextViews = new HashMap<>();
        userAnswers = new HashMap<>();
    }

    /**
     * Load song from arguments or use default sample song
     */
    private void loadSong() {
        Bundle args = getArguments();
        
        if (args != null && args.containsKey("songTitle")) {
            // Load song from arguments (passed from Music Map)
            int songId = args.getInt("songId", 1);
            String title = args.getString("songTitle", "Twinkle Twinkle Little Star");
            String subtitle = args.getString("songSubtitle", "Classic Nursery Rhyme");
            String[] lyrics = args.getStringArray("lyrics");
            String[] correctAnswers = args.getStringArray("correctAnswers");

            currentSong = new Song(songId, title, subtitle, lyrics, correctAnswers);
        } else {
            // Use default sample song for testing
            loadSampleSong();
        }
    }

    /**
     * Load a sample song for demonstration (fallback)
     */
    private void loadSampleSong() {
        // Sample song: "Twinkle Twinkle Little Star"
        String[] lyrics = {
                "Twinkle, twinkle, little _____",
                "How I wonder what you _____",
                "Up above the world so _____",
                "Like a diamond in the _____",
                "Twinkle, twinkle, little star",
                "How I _____ what you are"
        };

        String[] correctAnswers = {"star", "are", "high", "sky", "wonder"};

        currentSong = new Song(
                1,
                "Twinkle Twinkle Little Star",
                "Classic Nursery Rhyme",
                lyrics,
                correctAnswers
        );
    }

    /**
     * Display song title and subtitle
     */
    private void setupSongInfo() {
        tvSongTitle.setText(currentSong.getTitle());
        tvSongSubtitle.setText(currentSong.getSubtitle());
    }

    /**
     * Generate lyrics display with blanks
     */
    private void setupLyrics() {
        layoutLyricsContainer.removeAllViews();
        blankTextViews.clear();

        int blankIndex = 0;

        for (int i = 0; i < currentSong.getLyrics().length; i++) {
            String line = currentSong.getLyrics()[i];

            // Create horizontal layout for each line
            LinearLayout lineLayout = new LinearLayout(getContext());
            lineLayout.setOrientation(LinearLayout.HORIZONTAL);
            lineLayout.setGravity(Gravity.CENTER);
            LinearLayout.LayoutParams lineParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            lineParams.setMargins(0, 8, 0, 8);
            lineLayout.setLayoutParams(lineParams);

            // Split line by blank marker
            if (line.contains("_____")) {
                String[] parts = line.split("_____");

                // Add text before blank
                if (parts.length > 0 && !parts[0].isEmpty()) {
                    TextView textBefore = createLyricsTextView(parts[0]);
                    lineLayout.addView(textBefore);
                }

                // Add blank (interactive TextView)
                TextView blankView = createBlankTextView(blankIndex);
                blankTextViews.put(blankIndex, blankView);
                lineLayout.addView(blankView);
                blankIndex++;

                // Add text after blank
                if (parts.length > 1 && !parts[1].isEmpty()) {
                    TextView textAfter = createLyricsTextView(parts[1]);
                    lineLayout.addView(textAfter);
                }
            } else {
                // Regular line without blank
                TextView textView = createLyricsTextView(line);
                lineLayout.addView(textView);
            }

            layoutLyricsContainer.addView(lineLayout);
        }
    }

    /**
     * Create TextView for regular lyrics text
     */
    private TextView createLyricsTextView(String text) {
        TextView textView = new TextView(getContext());
        textView.setText(text);
        textView.setTextSize(16);
        textView.setTextColor(getResources().getColor(R.color.text_dark));
        textView.setFontFeatureSettings("sans-serif-medium");
        return textView;
    }

    /**
     * Create interactive blank TextView
     */
    private TextView createBlankTextView(final int blankIndex) {
        TextView blankView = new TextView(getContext());
        blankView.setText("_____");
        blankView.setTextSize(16);
        blankView.setTextColor(getResources().getColor(R.color.pastel_purple));
        blankView.setBackgroundResource(R.drawable.letter_slot_empty);
        blankView.setPadding(16, 8, 16, 8);
        blankView.setGravity(Gravity.CENTER);
        blankView.setMinWidth(120);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(4, 0, 4, 0);
        blankView.setLayoutParams(params);

        // Click listener to clear the blank
        blankView.setOnClickListener(v -> {
            if (!answersChecked) {
                clearBlank(blankIndex);
            }
        });

        return blankView;
    }

    /**
     * Setup word bank with shuffled words
     */
    private void setupWordBank() {
        layoutWordBank.removeAllViews();

        // Get all correct answers and shuffle them
        wordBankList = new ArrayList<>();
        for (String answer : currentSong.getCorrectAnswers()) {
            wordBankList.add(answer);
        }
        Collections.shuffle(wordBankList);

        // Create word buttons
        for (String word : wordBankList) {
            MaterialButton wordButton = createWordButton(word);
            layoutWordBank.addView(wordButton);
        }
    }

    /**
     * Create clickable word button for word bank
     */
    private MaterialButton createWordButton(final String word) {
        MaterialButton button = new MaterialButton(getContext());
        button.setText(word.toUpperCase());
        button.setTextSize(14);
        button.setTextColor(getResources().getColor(R.color.card_white));
        button.setBackgroundTintList(getResources().getColorStateList(R.color.pastel_blue));
        button.setCornerRadius(12);
        button.setTag(word);  // Store original word

        FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(
                FlexboxLayout.LayoutParams.WRAP_CONTENT,
                FlexboxLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(8, 8, 8, 8);
        button.setLayoutParams(params);

        // Click listener to fill blank
        button.setOnClickListener(v -> {
            if (!answersChecked) {
                fillNextBlank(word, button);
            }
        });

        return button;
    }

    /**
     * Fill the next empty blank with selected word
     */
    private void fillNextBlank(String word, MaterialButton button) {
        // Find next empty blank
        for (int i = 0; i < currentSong.getTotalBlanks(); i++) {
            TextView blankView = blankTextViews.get(i);
            if (blankView.getText().toString().equals("_____")) {
                // Fill this blank
                blankView.setText(word.toUpperCase());
                blankView.setBackgroundResource(R.drawable.letter_slot_filled);
                userAnswers.put(i, word);

                // Disable and grey out the word button
                button.setEnabled(false);
                button.setAlpha(0.5f);

                // Provide feedback
                Toast.makeText(getContext(), "Word placed!", Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }

    /**
     * Clear a specific blank
     */
    private void clearBlank(int blankIndex) {
        TextView blankView = blankTextViews.get(blankIndex);
        String currentWord = blankView.getText().toString();

        if (!currentWord.equals("_____")) {
            // Reset blank
            blankView.setText("_____");
            blankView.setBackgroundResource(R.drawable.letter_slot_empty);
            userAnswers.remove(blankIndex);

            // Re-enable corresponding word button
            String wordToRestore = currentWord.toLowerCase();
            for (int i = 0; i < layoutWordBank.getChildCount(); i++) {
                View child = layoutWordBank.getChildAt(i);
                if (child instanceof MaterialButton) {
                    MaterialButton btn = (MaterialButton) child;
                    if (btn.getTag().equals(wordToRestore)) {
                        btn.setEnabled(true);
                        btn.setAlpha(1.0f);
                        break;
                    }
                }
            }

            Toast.makeText(getContext(), "Word removed", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Setup button click listeners
     */
    private void setupClickListeners() {
        btnPlayPause.setOnClickListener(v -> togglePlayPause());
        btnCheckAnswer.setOnClickListener(v -> checkAnswers());
    }

    /**
     * Toggle music playback (simulated)
     */
    private void togglePlayPause() {
        if (isPlaying) {
            pauseMusic();
        } else {
            playMusic();
        }
    }

    /**
     * Simulate music playback
     */
    private void playMusic() {
        isPlaying = true;
        btnPlayPause.setText("⏸️ Pause");
        imgMusicIcon.setImageResource(android.R.drawable.ic_media_pause);

        // Simulate playback progress
        playbackHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isPlaying && playbackPosition < currentSong.getLyrics().length) {
                    tvProgress.setText("Playing line " + (playbackPosition + 1) + " of " +
                            currentSong.getLyrics().length);
                    playbackPosition++;

                    if (playbackPosition >= currentSong.getLyrics().length) {
                        pauseMusic();
                        tvProgress.setText("Song finished!");
                    } else {
                        playbackHandler.postDelayed(this, PLAYBACK_INTERVAL);
                    }
                }
            }
        }, PLAYBACK_INTERVAL);

        Toast.makeText(getContext(), "🎵 Music playing... Listen carefully!", Toast.LENGTH_SHORT).show();
    }

    /**
     * Pause music
     */
    private void pauseMusic() {
        isPlaying = false;
        btnPlayPause.setText("▶️ Play Song");
        imgMusicIcon.setImageResource(android.R.drawable.ic_media_play);
        playbackHandler.removeCallbacksAndMessages(null);
    }

    /**
     * Check user answers and provide feedback
     */
    private void checkAnswers() {
        if (userAnswers.size() < currentSong.getTotalBlanks()) {
            Toast.makeText(getContext(),
                    "Please fill in all blanks first!",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        answersChecked = true;
        int correctCount = 0;

        // Check each answer
        for (int i = 0; i < currentSong.getTotalBlanks(); i++) {
            String userAnswer = userAnswers.get(i);
            String correctAnswer = currentSong.getCorrectAnswers()[i];
            TextView blankView = blankTextViews.get(i);

            if (userAnswer != null && userAnswer.equalsIgnoreCase(correctAnswer)) {
                // Correct answer - green background
                blankView.setBackgroundColor(getResources().getColor(R.color.pastel_green));
                correctCount++;
            } else {
                // Wrong answer - red/orange background
                blankView.setBackgroundColor(getResources().getColor(R.color.pastel_orange));
            }
        }

        // Show score
        cardScore.setVisibility(View.VISIBLE);
        tvScore.setText("Score: " + correctCount + "/" + currentSong.getTotalBlanks());

        // Show result dialog
        showResultDialog(correctCount);
    }

    /**
     * Show result dialog with encouragement
     */
    private void showResultDialog(int correctCount) {
        int totalBlanks = currentSong.getTotalBlanks();
        double percentage = (correctCount * 100.0) / totalBlanks;

        String title;
        String message;

        if (percentage == 100) {
            title = "🌟 Perfect! 🌟";
            message = "Excellent work! You got all " + totalBlanks + " words correct!";
        } else if (percentage >= 80) {
            title = "😊 Great Job!";
            message = "You got " + correctCount + " out of " + totalBlanks + " correct!\nKeep practicing!";
        } else if (percentage >= 60) {
            title = "👍 Good Try!";
            message = "You got " + correctCount + " out of " + totalBlanks + " correct.\nLet's try again!";
        } else {
            title = "💪 Keep Learning!";
            message = "You got " + correctCount + " out of " + totalBlanks + " correct.\nPractice makes perfect!";
        }

        new AlertDialog.Builder(getContext())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Try Again", (dialog, which) -> resetGame())
                .setNegativeButton("Back", (dialog, which) -> requireActivity().onBackPressed())
                .show();
    }

    /**
     * Reset the game for another attempt
     */
    private void resetGame() {
        answersChecked = false;
        userAnswers.clear();
        playbackPosition = 0;
        cardScore.setVisibility(View.GONE);

        // Reset lyrics
        setupLyrics();

        // Reset word bank
        setupWordBank();

        Toast.makeText(getContext(), "Game reset! Try again 🎵", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Clean up handler
        if (playbackHandler != null) {
            playbackHandler.removeCallbacksAndMessages(null);
        }
    }
}
