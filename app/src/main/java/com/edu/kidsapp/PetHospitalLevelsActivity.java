package com.edu.kidsapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.card.MaterialCardView;

/**
 * PetHospitalLevelsActivity - Level Selection for Pet Hospital Game
 * Each level teaches different Body & Health vocabulary
 */
public class PetHospitalLevelsActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private MaterialCardView cardLevel1, cardLevel2, cardLevel3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_hospital_levels);

        // Hide action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        initializeViews();
        setupClickListeners();
    }

    private void initializeViews() {
        btnBack = findViewById(R.id.btnBack);
        cardLevel1 = findViewById(R.id.cardLevel1);
        cardLevel2 = findViewById(R.id.cardLevel2);
        cardLevel3 = findViewById(R.id.cardLevel3);
    }

    private void setupClickListeners() {
        btnBack.setOnClickListener(v -> finish());

        // Level 1: Fever Treatment (Dog)
        cardLevel1.setOnClickListener(v -> {
            Intent intent = new Intent(PetHospitalLevelsActivity.this, PetHospitalGameActivity.class);
            intent.putExtra("LEVEL", 1);
            intent.putExtra("DISEASE", "FEVER");
            intent.putExtra("PET_TYPE", "DOG");
            startActivity(intent);
        });

        // Level 2: Broken Leg (Cat)
        cardLevel2.setOnClickListener(v -> {
            Intent intent = new Intent(PetHospitalLevelsActivity.this, PetHospitalGameActivity.class);
            intent.putExtra("LEVEL", 2);
            intent.putExtra("DISEASE", "BROKEN_LEG");
            intent.putExtra("PET_TYPE", "CAT");
            startActivity(intent);
        });

        // Level 3: Stomach Ache (Dog)
        cardLevel3.setOnClickListener(v -> {
            Intent intent = new Intent(PetHospitalLevelsActivity.this, PetHospitalGameActivity.class);
            intent.putExtra("LEVEL", 3);
            intent.putExtra("DISEASE", "STOMACH_ACHE");
            intent.putExtra("PET_TYPE", "DOG");
            startActivity(intent);
        });
    }
}
