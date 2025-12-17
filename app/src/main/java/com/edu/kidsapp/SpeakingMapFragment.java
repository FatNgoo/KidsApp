package com.edu.kidsapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * SpeakingMapFragment - Displays available speaking lessons organized by categories
 */
public class SpeakingMapFragment extends Fragment {

    private RecyclerView recyclerViewLessons;
    private SpeakingLessonAdapter lessonAdapter;
    private List<SpeakingLesson> lessons;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_speaking_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize views
        recyclerViewLessons = view.findViewById(R.id.recyclerViewLessons);
        View btnBack = view.findViewById(R.id.btnBack);

        // Setup RecyclerView
        setupRecyclerView();

        // Create lesson data
        createLessons();

        // Set adapter with navigation to Speaking Lab
        lessonAdapter = new SpeakingLessonAdapter(lessons, lesson -> {
            // Navigate to Speaking Lab with lesson data
            Bundle bundle = new Bundle();
            bundle.putInt("lessonId", lesson.getId());
            bundle.putString("lessonTitle", lesson.getTitle());
            bundle.putString("lessonCategory", lesson.getCategory());
            
            // Pass vocab items
            ArrayList<String> words = new ArrayList<>();
            ArrayList<String> translations = new ArrayList<>();
            ArrayList<Integer> imageIds = new ArrayList<>();
            
            for (VocabItem item : lesson.getVocabItems()) {
                words.add(item.getWord());
                translations.add(item.getTranslation());
                imageIds.add(item.getImageResId());
            }
            
            bundle.putStringArrayList("words", words);
            bundle.putStringArrayList("translations", translations);
            bundle.putIntegerArrayList("imageIds", imageIds);
            
            Navigation.findNavController(view).navigate(R.id.action_speaking_map_to_speaking_lab, bundle);
        });
        recyclerViewLessons.setAdapter(lessonAdapter);

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
        recyclerViewLessons.setLayoutManager(layoutManager);
    }

    /**
     * Create speaking lessons with vocabulary items by category
     */
    private void createLessons() {
        lessons = new ArrayList<>();

        // === ANIMALS Category ===
        lessons.add(new SpeakingLesson(
                1,
                "Farm Animals",
                "Practice farm animal names",
                "Animals",
                createFarmAnimalsVocab(),
                android.R.drawable.ic_btn_speak_now,
                false  // Unlocked
        ));

        lessons.add(new SpeakingLesson(
                2,
                "Wild Animals",
                "Learn wild animal names",
                "Animals",
                createWildAnimalsVocab(),
                android.R.drawable.ic_btn_speak_now,
                false
        ));

        lessons.add(new SpeakingLesson(
                3,
                "Pet Animals",
                "Common pet names",
                "Animals",
                createPetAnimalsVocab(),
                android.R.drawable.ic_btn_speak_now,
                true  // Locked
        ));

        // === COLORS Category ===
        lessons.add(new SpeakingLesson(
                4,
                "Basic Colors",
                "Learn color names",
                "Colors",
                createBasicColorsVocab(),
                android.R.drawable.ic_btn_speak_now,
                false
        ));

        // === NUMBERS Category ===
        lessons.add(new SpeakingLesson(
                5,
                "Numbers 1-10",
                "Count from 1 to 10",
                "Numbers",
                createNumbersVocab(),
                android.R.drawable.ic_btn_speak_now,
                false
        ));

        // === FOOD Category ===
        lessons.add(new SpeakingLesson(
                6,
                "Fruits",
                "Practice fruit names",
                "Food",
                createFruitsVocab(),
                android.R.drawable.ic_btn_speak_now,
                false
        ));

        lessons.add(new SpeakingLesson(
                7,
                "Vegetables",
                "Learn vegetable names",
                "Food",
                createVegetablesVocab(),
                android.R.drawable.ic_btn_speak_now,
                true  // Locked
        ));
    }

    // === Vocabulary Creation Methods ===

    private List<VocabItem> createFarmAnimalsVocab() {
        return Arrays.asList(
                new VocabItem(1, "COW", "Con bò", R.drawable.placeholder_school),
                new VocabItem(2, "PIG", "Con lợn", R.drawable.placeholder_school),
                new VocabItem(3, "CHICKEN", "Con gà", R.drawable.placeholder_school),
                new VocabItem(4, "HORSE", "Con ngựa", R.drawable.placeholder_school),
                new VocabItem(5, "SHEEP", "Con cừu", R.drawable.placeholder_school)
        );
    }

    private List<VocabItem> createWildAnimalsVocab() {
        return Arrays.asList(
                new VocabItem(1, "LION", "Con sư tử", R.drawable.placeholder_school),
                new VocabItem(2, "ELEPHANT", "Con voi", R.drawable.placeholder_school),
                new VocabItem(3, "TIGER", "Con hổ", R.drawable.placeholder_school),
                new VocabItem(4, "MONKEY", "Con khỉ", R.drawable.placeholder_school),
                new VocabItem(5, "GIRAFFE", "Con hươu cao cổ", R.drawable.placeholder_school)
        );
    }

    private List<VocabItem> createPetAnimalsVocab() {
        return Arrays.asList(
                new VocabItem(1, "DOG", "Con chó", R.drawable.placeholder_school),
                new VocabItem(2, "CAT", "Con mèo", R.drawable.placeholder_school),
                new VocabItem(3, "RABBIT", "Con thỏ", R.drawable.placeholder_school),
                new VocabItem(4, "BIRD", "Con chim", R.drawable.placeholder_school),
                new VocabItem(5, "FISH", "Con cá", R.drawable.placeholder_school)
        );
    }

    private List<VocabItem> createBasicColorsVocab() {
        return Arrays.asList(
                new VocabItem(1, "RED", "Màu đỏ", R.drawable.placeholder_school),
                new VocabItem(2, "BLUE", "Màu xanh dương", R.drawable.placeholder_school),
                new VocabItem(3, "GREEN", "Màu xanh lá", R.drawable.placeholder_school),
                new VocabItem(4, "YELLOW", "Màu vàng", R.drawable.placeholder_school),
                new VocabItem(5, "ORANGE", "Màu cam", R.drawable.placeholder_school)
        );
    }

    private List<VocabItem> createNumbersVocab() {
        return Arrays.asList(
                new VocabItem(1, "ONE", "Số một", R.drawable.placeholder_school),
                new VocabItem(2, "TWO", "Số hai", R.drawable.placeholder_school),
                new VocabItem(3, "THREE", "Số ba", R.drawable.placeholder_school),
                new VocabItem(4, "FOUR", "Số bốn", R.drawable.placeholder_school),
                new VocabItem(5, "FIVE", "Số năm", R.drawable.placeholder_school)
        );
    }

    private List<VocabItem> createFruitsVocab() {
        return Arrays.asList(
                new VocabItem(1, "APPLE", "Quả táo", R.drawable.food_apple),
                new VocabItem(2, "BANANA", "Quả chuối", R.drawable.food_banana),
                new VocabItem(3, "ORANGE", "Quả cam", R.drawable.placeholder_school),
                new VocabItem(4, "GRAPE", "Quả nho", R.drawable.placeholder_school),
                new VocabItem(5, "STRAWBERRY", "Dâu tây", R.drawable.placeholder_school)
        );
    }

    private List<VocabItem> createVegetablesVocab() {
        return Arrays.asList(
                new VocabItem(1, "CARROT", "Cà rốt", R.drawable.placeholder_school),
                new VocabItem(2, "TOMATO", "Cà chua", R.drawable.placeholder_school),
                new VocabItem(3, "CABBAGE", "Bắp cải", R.drawable.food_cabbage),
                new VocabItem(4, "BROCCOLI", "Bông cải xanh", R.drawable.placeholder_school),
                new VocabItem(5, "POTATO", "Khoai tây", R.drawable.placeholder_school)
        );
    }
}
