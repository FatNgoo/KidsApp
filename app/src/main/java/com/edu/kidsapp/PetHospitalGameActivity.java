package com.edu.kidsapp;

import android.animation.ObjectAnimator;
import android.content.ClipData;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.speech.tts.TextToSpeech;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * PetHospitalGameActivity - Interactive Pet Hospital Treatment Game
 * Features doctor-pet dialogue and step-by-step healing process
 * Teaches Body & Health vocabulary
 */
public class PetHospitalGameActivity extends AppCompatActivity {

    // Treatment Steps
    private enum TreatmentStep {
        PET_COMPLAINT,       // Pet says they feel sick
        DOCTOR_EXAMINE,      // Doctor examines the pet
        DOCTOR_NEED_TOOL1,   // Doctor requests first tool
        DOCTOR_NEED_TOOL2,   // Doctor requests second tool
        TREATING,            // Doctor is treating
        DOCTOR_DONE,         // Doctor says "All better now!"
        PET_THANKS           // Pet says "Thank you doctor!"
    }

    // UI Components
    private ImageView imgPet, imgDoctor, imgHospitalBed, imgTreatmentIcon;
    private TextView tvScore, tvTreatmentStatus;
    private FrameLayout petSpeechBubble, doctorSpeechBubble;
    private TextView tvPetSpeech, tvDoctorSpeech;
    private GridLayout medicalToolsGrid;

    // Game State
    private TreatmentStep currentStep = TreatmentStep.PET_COMPLAINT;
    private int level = 1;
    private String disease = "FEVER";
    private String petType = "DOG";
    private int score = 0;
    private List<String> usedTools = new ArrayList<>();

    // Text to Speech
    private TextToSpeech textToSpeech;
    private boolean ttsReady = false;

    // Handler for delays
    private Handler handler = new Handler(Looper.getMainLooper());

    // Medical tools based on disease
    private static final MedicalTool[] FEVER_TOOLS = {
            new MedicalTool("THERMOMETER", "Thermometer", "🌡️", R.drawable.ic_thermometer),
            new MedicalTool("MEDICINE", "Medicine", "💊", R.drawable.ic_medicine),
            new MedicalTool("STETHOSCOPE", "Stethoscope", "🩺", R.drawable.ic_stethoscope),
            new MedicalTool("BANDAGE", "Bandage", "🩹", R.drawable.ic_bandage)
    };

    private static final MedicalTool[] BROKEN_LEG_TOOLS = {
            new MedicalTool("STETHOSCOPE", "Stethoscope", "🩺", R.drawable.ic_stethoscope),
            new MedicalTool("BANDAGE", "Bandage", "🩹", R.drawable.ic_bandage),
            new MedicalTool("THERMOMETER", "Thermometer", "🌡️", R.drawable.ic_thermometer),
            new MedicalTool("MEDICINE", "Medicine", "💊", R.drawable.ic_medicine)
    };

    private static final MedicalTool[] STOMACH_TOOLS = {
            new MedicalTool("STETHOSCOPE", "Stethoscope", "🩺", R.drawable.ic_stethoscope),
            new MedicalTool("MEDICINE", "Medicine", "💊", R.drawable.ic_medicine),
            new MedicalTool("THERMOMETER", "Thermometer", "🌡️", R.drawable.ic_thermometer),
            new MedicalTool("BANDAGE", "Bandage", "🩹", R.drawable.ic_bandage)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_hospital_game);

        // Hide action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Get level data from intent
        level = getIntent().getIntExtra("LEVEL", 1);
        disease = getIntent().getStringExtra("DISEASE");
        petType = getIntent().getStringExtra("PET_TYPE");

        // Initialize text to speech
        initTextToSpeech();

        // Initialize views
        initializeViews();

        // Setup medical tools grid
        setupMedicalToolsGrid();

        // Set pet image
        setPetImage();

        // Start treatment sequence
        startTreatmentSequence();
    }

    private void initTextToSpeech() {
        textToSpeech = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                int result = textToSpeech.setLanguage(Locale.US);
                ttsReady = (result != TextToSpeech.LANG_MISSING_DATA && 
                           result != TextToSpeech.LANG_NOT_SUPPORTED);
            }
        });
    }

    private void initializeViews() {
        imgPet = findViewById(R.id.imgPet);
        imgDoctor = findViewById(R.id.imgDoctor);
        imgHospitalBed = findViewById(R.id.imgHospitalBed);
        imgTreatmentIcon = findViewById(R.id.imgTreatmentIcon);
        tvScore = findViewById(R.id.tvScore);
        tvTreatmentStatus = findViewById(R.id.tvTreatmentStatus);
        
        petSpeechBubble = findViewById(R.id.petSpeechBubble);
        doctorSpeechBubble = findViewById(R.id.doctorSpeechBubble);
        tvPetSpeech = findViewById(R.id.tvPetSpeech);
        tvDoctorSpeech = findViewById(R.id.tvDoctorSpeech);
        
        medicalToolsGrid = findViewById(R.id.medicalToolsGrid);
    }

    private void setPetImage() {
        if ("CAT".equals(petType)) {
            imgPet.setImageResource(R.drawable.ic_pet_cat);
        } else {
            imgPet.setImageResource(R.drawable.ic_pet_dog);
        }
    }

    private void setupMedicalToolsGrid() {
        medicalToolsGrid.removeAllViews();
        
        MedicalTool[] tools = getToolsForDisease();
        for (MedicalTool tool : tools) {
            View itemView = createMedicalToolItem(tool);
            medicalToolsGrid.addView(itemView);
        }
    }

    private MedicalTool[] getToolsForDisease() {
        switch (disease) {
            case "BROKEN_LEG":
                return BROKEN_LEG_TOOLS;
            case "STOMACH_ACHE":
                return STOMACH_TOOLS;
            default:
                return FEVER_TOOLS;
        }
    }

    private View createMedicalToolItem(MedicalTool tool) {
        View itemView = LayoutInflater.from(this).inflate(
                R.layout.item_medical_tool, medicalToolsGrid, false);
        
        ImageView imgTool = itemView.findViewById(R.id.imgTool);
        TextView tvToolName = itemView.findViewById(R.id.tvToolName);
        
        imgTool.setImageResource(tool.drawableId);
        tvToolName.setText(tool.displayName);
        
        // Setup drag functionality
        itemView.setTag(tool.id);
        itemView.setOnTouchListener((view, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("tool", tool.id);
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDragAndDrop(data, shadowBuilder, view, 0);
                return true;
            }
            return false;
        });
        
        return itemView;
    }

    private void startTreatmentSequence() {
        // Step 1: Pet complains
        currentStep = TreatmentStep.PET_COMPLAINT;
        String complaint = getPetComplaint();
        showPetDialogue(complaint);
        speakText(complaint);
        
        // Step 2: Doctor examines
        handler.postDelayed(() -> {
            currentStep = TreatmentStep.DOCTOR_EXAMINE;
            hidePetDialogue();
            String examination = "Let me check you...";
            showDoctorDialogue(examination);
            speakText(examination);
            
            // Doctor walks closer (animation)
            animateDoctorExamine();
            
            // Step 3: Doctor requests first tool
            handler.postDelayed(() -> {
                currentStep = TreatmentStep.DOCTOR_NEED_TOOL1;
                hideDoctorDialogue();
                handler.postDelayed(() -> {
                    String request = getFirstToolRequest();
                    showDoctorDialogue(request);
                    speakText(request);
                    enableToolDragDrop(getFirstToolId());
                }, 1500);
            }, 3000);
        }, 3000);
    }

    private String getPetComplaint() {
        switch (disease) {
            case "BROKEN_LEG":
                return "My leg hurts so much!";
            case "STOMACH_ACHE":
                return "My tummy really hurts...";
            default:
                return "I have a fever... I feel hot...";
        }
    }

    private String getFirstToolRequest() {
        switch (disease) {
            case "BROKEN_LEG":
                return "I need the stethoscope first";
            case "STOMACH_ACHE":
                return "Let me listen with the stethoscope";
            default:
                return "I need the thermometer to check";
        }
    }

    private String getFirstToolId() {
        switch (disease) {
            case "BROKEN_LEG":
            case "STOMACH_ACHE":
                return "STETHOSCOPE";
            default:
                return "THERMOMETER";
        }
    }

    private String getSecondToolRequest() {
        switch (disease) {
            case "BROKEN_LEG":
                return "Now I need the bandage";
            case "STOMACH_ACHE":
                return "You need this medicine";
            default:
                return "Here's your medicine";
        }
    }

    private String getSecondToolId() {
        switch (disease) {
            case "BROKEN_LEG":
                return "BANDAGE";
            default:
                return "MEDICINE";
        }
    }

    private void showPetDialogue(String message) {
        tvPetSpeech.setText(message);
        petSpeechBubble.setVisibility(View.VISIBLE);
        petSpeechBubble.setAlpha(0f);
        petSpeechBubble.animate().alpha(1f).setDuration(300).start();
        
        // Pet shakes sadly
        animatePetSick();
    }

    private void hidePetDialogue() {
        petSpeechBubble.animate().alpha(0f).setDuration(300)
            .withEndAction(() -> petSpeechBubble.setVisibility(View.GONE))
            .start();
    }

    private void showDoctorDialogue(String message) {
        tvDoctorSpeech.setText(message);
        doctorSpeechBubble.setVisibility(View.VISIBLE);
        doctorSpeechBubble.setAlpha(0f);
        doctorSpeechBubble.animate().alpha(1f).setDuration(300).start();
    }

    private void hideDoctorDialogue() {
        doctorSpeechBubble.animate().alpha(0f).setDuration(300)
            .withEndAction(() -> doctorSpeechBubble.setVisibility(View.GONE))
            .start();
    }

    private void speakText(String text) {
        if (ttsReady && textToSpeech != null) {
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        }
    }

    private void animateDoctorExamine() {
        imgDoctor.animate()
            .scaleX(1.1f)
            .scaleY(1.1f)
            .setDuration(300)
            .withEndAction(() -> 
                imgDoctor.animate()
                    .scaleX(1.0f)
                    .scaleY(1.0f)
                    .setDuration(300)
                    .start()
            )
            .start();
    }

    private void animatePetSick() {
        // Pet trembles slightly
        ObjectAnimator shake = ObjectAnimator.ofFloat(imgPet, "rotation", 0f, -2f, 2f, -2f, 2f, 0f);
        shake.setDuration(400);
        shake.setRepeatCount(2);
        shake.start();
    }

    private void enableToolDragDrop(String toolId) {
        // Setup drop listener on doctor
        imgDoctor.setOnDragListener((view, event) -> {
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    return true;

                case DragEvent.ACTION_DRAG_ENTERED:
                    view.animate().scaleX(1.15f).scaleY(1.15f).setDuration(200).start();
                    return true;

                case DragEvent.ACTION_DRAG_EXITED:
                    view.animate().scaleX(1.0f).scaleY(1.0f).setDuration(200).start();
                    return true;

                case DragEvent.ACTION_DROP:
                    view.animate().scaleX(1.0f).scaleY(1.0f).setDuration(200).start();
                    
                    ClipData.Item item = event.getClipData().getItemAt(0);
                    String droppedTool = item.getText().toString();

                    handleToolDelivery(droppedTool, toolId);
                    return true;

                case DragEvent.ACTION_DRAG_ENDED:
                    view.animate().scaleX(1.0f).scaleY(1.0f).setDuration(200).start();
                    return true;

                default:
                    return false;
            }
        });
    }

    private void handleToolDelivery(String droppedTool, String expectedTool) {
        if (droppedTool.equals(expectedTool)) {
            usedTools.add(droppedTool);
            hideDoctorDialogue();
            
            // Show tool in treatment area
            showToolInUse(droppedTool);
            
            animateDoctorReceive();
            
            Toast.makeText(this, "Perfect! ✓", Toast.LENGTH_SHORT).show();
            
            handler.postDelayed(() -> {
                if (currentStep == TreatmentStep.DOCTOR_NEED_TOOL1) {
                    // Request second tool
                    currentStep = TreatmentStep.DOCTOR_NEED_TOOL2;
                    String request = getSecondToolRequest();
                    showDoctorDialogue(request);
                    speakText(request);
                    enableToolDragDrop(getSecondToolId());
                } else if (currentStep == TreatmentStep.DOCTOR_NEED_TOOL2) {
                    // Start treating
                    currentStep = TreatmentStep.TREATING;
                    startTreating();
                }
            }, 1500);
        } else {
            Toast.makeText(this, "Wrong tool! ❌", Toast.LENGTH_SHORT).show();
            animateShake(imgDoctor);
        }
    }

    private void showToolInUse(String toolId) {
        imgTreatmentIcon.setVisibility(View.VISIBLE);
        
        int drawableId = R.drawable.ic_thermometer;
        switch (toolId) {
            case "STETHOSCOPE":
                drawableId = R.drawable.ic_stethoscope;
                break;
            case "BANDAGE":
                drawableId = R.drawable.ic_bandage;
                break;
            case "MEDICINE":
                drawableId = R.drawable.ic_medicine;
                break;
        }
        
        imgTreatmentIcon.setImageResource(drawableId);
        imgTreatmentIcon.setAlpha(0f);
        imgTreatmentIcon.setScaleX(0.5f);
        imgTreatmentIcon.setScaleY(0.5f);
        
        imgTreatmentIcon.animate()
            .alpha(1f)
            .scaleX(1f)
            .scaleY(1f)
            .setDuration(400)
            .start();
    }

    private void animateDoctorReceive() {
        imgDoctor.animate()
            .scaleX(1.15f)
            .scaleY(1.15f)
            .translationY(-15f)
            .setDuration(200)
            .withEndAction(() -> 
                imgDoctor.animate()
                    .scaleX(1.0f)
                    .scaleY(1.0f)
                    .translationY(0f)
                    .setDuration(200)
                    .start()
            )
            .start();
    }

    private void startTreating() {
        hideDoctorDialogue();
        
        // Show treating status
        tvTreatmentStatus.setVisibility(View.VISIBLE);
        tvTreatmentStatus.setText("💉");
        
        // Animate treating
        animateTreating();
        
        // Finish treatment after 3 seconds
        handler.postDelayed(() -> {
            currentStep = TreatmentStep.DOCTOR_DONE;
            tvTreatmentStatus.setVisibility(View.GONE);
            imgTreatmentIcon.setVisibility(View.GONE);
            
            showDoctorDialogue("All better now!");
            speakText("All better now!");
            
            // Pet is healed - remove bandage
            healPet();
            
            handler.postDelayed(() -> {
                petThankYou();
            }, 2500);
        }, 3000);
    }

    private void animateTreating() {
        // Treatment icon pulses
        ObjectAnimator pulse = ObjectAnimator.ofFloat(imgTreatmentIcon, "scaleX", 1f, 1.2f, 1f);
        ObjectAnimator pulseY = ObjectAnimator.ofFloat(imgTreatmentIcon, "scaleY", 1f, 1.2f, 1f);
        pulse.setDuration(600);
        pulseY.setDuration(600);
        pulse.setRepeatCount(4);
        pulseY.setRepeatCount(4);
        pulse.start();
        pulseY.start();
        
        // Status emoji floats
        ObjectAnimator float_anim = ObjectAnimator.ofFloat(tvTreatmentStatus, "translationY", 0f, -20f, 0f);
        float_anim.setDuration(800);
        float_anim.setRepeatCount(3);
        float_anim.start();
    }

    private void healPet() {
        // Pet becomes happy - change image or add happy animation
        animatePetHealed();
    }

    private void animatePetHealed() {
        // Pet jumps with joy
        imgPet.animate()
            .scaleX(1.2f)
            .scaleY(1.2f)
            .rotation(10f)
            .setDuration(300)
            .withEndAction(() -> 
                imgPet.animate()
                    .scaleX(1.0f)
                    .scaleY(1.0f)
                    .rotation(0f)
                    .setDuration(300)
                    .start()
            )
            .start();
    }

    private void petThankYou() {
        currentStep = TreatmentStep.PET_THANKS;
        hideDoctorDialogue();
        
        handler.postDelayed(() -> {
            showPetDialogue("Thank you doctor! I feel great!");
            speakText("Thank you doctor! I feel great!");
            
            score++;
            tvScore.setText("⭐ " + score);
            
            animateCelebration();
            
            handler.postDelayed(() -> {
                Toast.makeText(this, "🎉 Level Complete! 🎉", Toast.LENGTH_LONG).show();
                handler.postDelayed(() -> finish(), 2000);
            }, 4000);
        }, 500);
    }

    private void animateCelebration() {
        // Both doctor and pet celebrate
        imgDoctor.animate()
            .scaleX(1.2f)
            .scaleY(1.2f)
            .setDuration(300)
            .withEndAction(() -> 
                imgDoctor.animate()
                    .scaleX(1.0f)
                    .scaleY(1.0f)
                    .setDuration(300)
                    .start()
            )
            .start();
    }

    private void animateShake(View view) {
        ObjectAnimator shake = ObjectAnimator.ofFloat(view, "translationX", 
                0f, -25f, 25f, -25f, 25f, 0f);
        shake.setDuration(400);
        shake.start();
    }

    @Override
    protected void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        super.onDestroy();
    }

    private static class MedicalTool {
        String id;
        String displayName;
        String emoji;
        int drawableId;

        MedicalTool(String id, String displayName, String emoji, int drawableId) {
            this.id = id;
            this.displayName = displayName;
            this.emoji = emoji;
            this.drawableId = drawableId;
        }
    }
}
