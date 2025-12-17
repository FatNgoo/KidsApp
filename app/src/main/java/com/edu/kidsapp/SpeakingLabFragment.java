package com.edu.kidsapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * SpeakingLabFragment - Practice pronunciation with recording and playback
 * 
 * FEATURES:
 * - Display vocabulary word with image
 * - Tap image to hear pronunciation (simulated)
 * - Record user's voice
 * - Playback recorded voice
 * - Navigate through vocabulary items
 */
public class SpeakingLabFragment extends Fragment {

    // UI Components
    private TextView tvLessonTitle;
    private TextView tvProgress;
    private ImageView imgVocab;
    private TextView tvWord;
    private TextView tvTranslation;
    private TextView tvRecordingStatus;
    private MaterialButton btnRecord;
    private MaterialButton btnPlayback;
    private MaterialButton btnPrevious;
    private MaterialButton btnNext;

    // Data
    private List<VocabItem> vocabItems;
    private int currentIndex = 0;

    // Audio Recording
    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;
    private String audioFilePath;
    private boolean isRecording = false;
    private boolean hasRecording = false;
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;

    // Text to Speech
    private TextToSpeech textToSpeech;
    private boolean ttsReady = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_speaking_lab, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize UI
        initializeViews(view);

        // Initialize TTS
        initTextToSpeech();

        // Load lesson data from arguments
        loadLessonData();

        // Setup audio file path
        setupAudioPath();

        // Check permissions
        checkAudioPermission();

        // Display first vocabulary
        if (vocabItems != null && !vocabItems.isEmpty()) {
            displayVocab(currentIndex);
        }

        // Setup click listeners
        setupClickListeners();
    }

    private void initializeViews(View view) {
        tvLessonTitle = view.findViewById(R.id.tv_lesson_title);
        tvProgress = view.findViewById(R.id.tv_progress);
        imgVocab = view.findViewById(R.id.img_vocab);
        tvWord = view.findViewById(R.id.tv_word);
        tvTranslation = view.findViewById(R.id.tv_translation);
        tvRecordingStatus = view.findViewById(R.id.tv_recording_status);
        btnRecord = view.findViewById(R.id.btn_record);
        btnPlayback = view.findViewById(R.id.btn_playback);
        btnPrevious = view.findViewById(R.id.btn_previous);
        btnNext = view.findViewById(R.id.btn_next);
    }

    /**
     * Load lesson data from Bundle arguments
     */
    private void loadLessonData() {
        Bundle args = getArguments();
        if (args != null) {
            String lessonTitle = args.getString("lessonTitle", "Speaking Lab");
            tvLessonTitle.setText(lessonTitle);

            // Reconstruct vocab items
            ArrayList<String> words = args.getStringArrayList("words");
            ArrayList<String> translations = args.getStringArrayList("translations");
            ArrayList<Integer> imageIds = args.getIntegerArrayList("imageIds");

            vocabItems = new ArrayList<>();
            if (words != null && translations != null && imageIds != null) {
                for (int i = 0; i < words.size(); i++) {
                    vocabItems.add(new VocabItem(
                            i + 1,
                            words.get(i),
                            translations.get(i),
                            imageIds.get(i)
                    ));
                }
            }
        }
    }

    /**
     * Initialize text to speech
     */
    private void initTextToSpeech() {
        textToSpeech = new TextToSpeech(requireContext(), status -> {
            if (status == TextToSpeech.SUCCESS) {
                int result = textToSpeech.setLanguage(Locale.US);
                ttsReady = (result != TextToSpeech.LANG_MISSING_DATA &&
                        result != TextToSpeech.LANG_NOT_SUPPORTED);
            }
        });
    }

    /**
     * Setup audio file path for recording
     */
    private void setupAudioPath() {
        audioFilePath = requireActivity().getExternalCacheDir().getAbsolutePath()
                + "/speaking_recording.3gp";
    }

    /**
     * Check and request audio recording permission
     */
    private void checkAudioPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.RECORD_AUDIO},
                    REQUEST_RECORD_AUDIO_PERMISSION);
        }
    }

    /**
     * Display current vocabulary item
     */
    private void displayVocab(int index) {
        if (vocabItems == null || vocabItems.isEmpty() || index < 0 || index >= vocabItems.size()) {
            return;
        }

        VocabItem item = vocabItems.get(index);

        // Update UI
        tvWord.setText(item.getWord());
        tvTranslation.setText(item.getTranslation());
        imgVocab.setImageResource(item.getImageResId());
        tvProgress.setText((index + 1) + " / " + vocabItems.size());

        // Reset recording state for new word
        resetRecordingState();

        // Update navigation buttons
        btnPrevious.setEnabled(index > 0);
        btnNext.setEnabled(index < vocabItems.size() - 1);
    }

    /**
     * Setup click listeners
     */
    private void setupClickListeners() {
        // Image click - Play pronunciation (simulated)
        imgVocab.setOnClickListener(v -> playPronunciation());

        // Record button - Hold to record
        btnRecord.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    startRecording();
                    return true;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    stopRecording();
                    return true;
            }
            return false;
        });

        // Playback button
        btnPlayback.setOnClickListener(v -> playRecording());

        // Navigation buttons
        btnPrevious.setOnClickListener(v -> {
            if (currentIndex > 0) {
                currentIndex--;
                displayVocab(currentIndex);
            }
        });

        btnNext.setOnClickListener(v -> {
            if (currentIndex < vocabItems.size() - 1) {
                currentIndex++;
                displayVocab(currentIndex);
            }
        });
    }

    /**
     * Play pronunciation of current word using TextToSpeech
     */
    private void playPronunciation() {
        VocabItem item = vocabItems.get(currentIndex);
        
        // Play pronunciation with TTS
        if (ttsReady && textToSpeech != null) {
            textToSpeech.speak(item.getWord(), TextToSpeech.QUEUE_FLUSH, null, null);
        }

        // Visual feedback
        imgVocab.setAlpha(0.5f);
        new Handler().postDelayed(() -> imgVocab.setAlpha(1.0f), 500);
    }

    /**
     * Start recording user's voice
     */
    private void startRecording() {
        // Check permission
        if (ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getContext(), "Microphone permission required", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            // Release previous recorder if exists
            releaseRecorder();

            // Create new MediaRecorder
            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setOutputFile(audioFilePath);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mediaRecorder.prepare();
            mediaRecorder.start();

            isRecording = true;
            tvRecordingStatus.setText("🔴 Recording...");
            btnRecord.setText("🎤 Recording...");

        } catch (IOException e) {
            Toast.makeText(getContext(), "Recording failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Stop recording
     */
    private void stopRecording() {
        if (!isRecording || mediaRecorder == null) {
            return;
        }

        try {
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
            isRecording = false;
            hasRecording = true;

            tvRecordingStatus.setText("✓ Recording saved!");
            btnRecord.setText("🎤 Hold to Record");
            
            // Show playback button
            btnPlayback.setVisibility(View.VISIBLE);
            btnPlayback.setEnabled(true);

            Toast.makeText(getContext(), "Recording complete! 🎉", Toast.LENGTH_SHORT).show();

        } catch (RuntimeException e) {
            Toast.makeText(getContext(), "Stop recording failed", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Play recorded audio
     */
    private void playRecording() {
        if (!hasRecording) {
            Toast.makeText(getContext(), "No recording available", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            // Release previous player if exists
            releasePlayer();

            // Create new MediaPlayer
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(audioFilePath);
            mediaPlayer.prepare();
            mediaPlayer.start();

            tvRecordingStatus.setText("▶️ Playing your voice...");
            btnPlayback.setEnabled(false);

            // Re-enable button after playback
            mediaPlayer.setOnCompletionListener(mp -> {
                tvRecordingStatus.setText("✓ Playback complete");
                btnPlayback.setEnabled(true);
                releasePlayer();
            });

        } catch (IOException e) {
            Toast.makeText(getContext(), "Playback failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Reset recording state when changing words
     */
    private void resetRecordingState() {
        hasRecording = false;
        tvRecordingStatus.setText("Ready to record");
        btnRecord.setText("🎤 Hold to Record");
        btnPlayback.setVisibility(View.GONE);
        btnPlayback.setEnabled(false);
        
        releaseRecorder();
        releasePlayer();
    }

    /**
     * Release MediaRecorder resources
     */
    private void releaseRecorder() {
        if (mediaRecorder != null) {
            try {
                mediaRecorder.release();
            } catch (Exception e) {
                // Ignore
            }
            mediaRecorder = null;
        }
    }

    /**
     * Release MediaPlayer resources
     */
    private void releasePlayer() {
        if (mediaPlayer != null) {
            try {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
                mediaPlayer.release();
            } catch (Exception e) {
                // Ignore
            }
            mediaPlayer = null;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        releaseRecorder();
        releasePlayer();
        // Cleanup TTS
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        
        if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getContext(), "Microphone permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), 
                        "Microphone permission denied. Recording won't work.", 
                        Toast.LENGTH_LONG).show();
            }
        }
    }
}
