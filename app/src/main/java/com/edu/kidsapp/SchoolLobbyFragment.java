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
 * SchoolLobbyFragment - Entry point for School section
 * Displays different classroom options (Academy, Music Room, Speaking Lab)
 */
public class SchoolLobbyFragment extends Fragment {

    private RecyclerView rvClassrooms;
    private ClassroomAdapter adapter;
    private List<Classroom> classroomList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_school_lobby, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize RecyclerView
        rvClassrooms = view.findViewById(R.id.rv_classrooms);
        rvClassrooms.setLayoutManager(new LinearLayoutManager(getContext()));

        // Create classroom data
        classroomList = createClassrooms();

        // Setup adapter with click listener
        adapter = new ClassroomAdapter(classroomList, new ClassroomAdapter.OnClassroomClickListener() {
            @Override
            public void onClassroomClick(Classroom classroom, int position) {
                handleClassroomClick(classroom);
            }
        });

        rvClassrooms.setAdapter(adapter);
    }

    /**
     * Create list of available classrooms
     */
    private List<Classroom> createClassrooms() {
        List<Classroom> classrooms = new ArrayList<>();

        // 1. The Academy - Main learning path (Adventure Map)
        classrooms.add(new Classroom(
                1,
                "The Academy",
                "Level-based learning journey",
                R.drawable.placeholder_school,
                R.color.pastel_orange
        ));

        // 2. Music Room - Placeholder for future feature
        classrooms.add(new Classroom(
                2,
                "Music Room",
                "Sing along and learn with songs",
                R.drawable.placeholder_game,
                R.color.pastel_purple
        ));

        // 3. Speaking Lab - Placeholder for future feature
        classrooms.add(new Classroom(
                3,
                "Speaking Lab",
                "Practice pronunciation and speaking",
                R.drawable.placeholder_game,
                R.color.pastel_blue
        ));

        return classrooms;
    }

    /**
     * Handle classroom card click
     */
    private void handleClassroomClick(Classroom classroom) {
        switch (classroom.getId()) {
            case 1:
                // Navigate to Adventure Map (Saga Map)
                Navigation.findNavController(requireView())
                        .navigate(R.id.action_school_lobby_to_map);
                break;

            case 2:
                // Music Room - Placeholder
                Toast.makeText(getContext(),
                        "🎵 Music Room - Coming Soon!",
                        Toast.LENGTH_SHORT).show();
                break;

            case 3:
                // Speaking Lab - Placeholder
                Toast.makeText(getContext(),
                        "🎤 Speaking Lab - Coming Soon!",
                        Toast.LENGTH_SHORT).show();
                break;

            default:
                Toast.makeText(getContext(),
                        "Unknown classroom",
                        Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
