package com.edu.kidsapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * MapFragment - Adventure Map screen showing level progression
 * Displays a vertical path of level nodes with different states
 */
public class MapFragment extends Fragment {

    private RecyclerView recyclerViewLevels;
    private LevelAdapter levelAdapter;
    private List<LevelModel> levels;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize views
        recyclerViewLevels = view.findViewById(R.id.recyclerViewLevels);
        View btnBack = view.findViewById(R.id.btnBack);

        // Setup RecyclerView
        setupRecyclerView();

        // Create dummy data
        createDummyLevels();

        // Set adapter
        levelAdapter = new LevelAdapter(levels, level -> {
            // Handle level click - navigate to lesson if not locked
            if (!level.isLocked()) {
                Navigation.findNavController(view).navigate(R.id.action_map_to_lesson);
            } else {
                Toast.makeText(requireContext(), 
                        "Level " + level.getId() + " is locked", 
                        Toast.LENGTH_SHORT).show();
            }
        });
        recyclerViewLevels.setAdapter(levelAdapter);

        // Back button handler
        btnBack.setOnClickListener(v -> {
            Navigation.findNavController(view).popBackStack();
        });
    }

    /**
     * Setup RecyclerView with LinearLayoutManager
     */
    private void setupRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        recyclerViewLevels.setLayoutManager(layoutManager);
    }

    /**
     * Create dummy level data:
     * Level 1: Completed with 3 stars
     * Level 2: Unlocked (Current)
     * Level 3-10: Locked
     */
    private void createDummyLevels() {
        levels = new ArrayList<>();
        
        // Level 1: Completed with 3 stars
        levels.add(new LevelModel(1, "Colors", false, true, 3));
        
        // Level 2: Current (Unlocked but not completed)
        levels.add(new LevelModel(2, "Animals", false, false, 0));
        
        // Levels 3-10: Locked
        levels.add(new LevelModel(3, "Numbers", true, false, 0));
        levels.add(new LevelModel(4, "Fruits", true, false, 0));
        levels.add(new LevelModel(5, "Family", true, false, 0));
        levels.add(new LevelModel(6, "Body Parts", true, false, 0));
        levels.add(new LevelModel(7, "Weather", true, false, 0));
        levels.add(new LevelModel(8, "Food", true, false, 0));
        levels.add(new LevelModel(9, "Clothes", true, false, 0));
        levels.add(new LevelModel(10, "Actions", true, false, 0));
    }
}
