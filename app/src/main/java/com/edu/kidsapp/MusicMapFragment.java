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
import java.util.List;

/**
 * MusicMapFragment - Displays available songs organized by categories
 * User selects a song to practice in Music Room
 */
public class MusicMapFragment extends Fragment {

    private RecyclerView recyclerViewSongs;
    private SongAdapter songAdapter;
    private List<SongCategory> songs;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_music_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize views
        recyclerViewSongs = view.findViewById(R.id.recyclerViewSongs);
        View btnBack = view.findViewById(R.id.btnBack);

        // Setup RecyclerView
        setupRecyclerView();

        // Create song data
        createSongs();

        // Set adapter with navigation to Music Room
        songAdapter = new SongAdapter(songs, song -> {
            // Navigate to Music Room with song data
            Bundle bundle = new Bundle();
            bundle.putInt("songId", song.getId());
            bundle.putString("songTitle", song.getTitle());
            bundle.putString("songSubtitle", song.getSubtitle());
            bundle.putString("songCategory", song.getCategory());
            bundle.putStringArray("lyrics", song.getLyrics());
            bundle.putStringArray("correctAnswers", song.getCorrectAnswers());
            
            Navigation.findNavController(view).navigate(R.id.action_music_map_to_music_room, bundle);
        });
        recyclerViewSongs.setAdapter(songAdapter);

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
        recyclerViewSongs.setLayoutManager(layoutManager);
    }

    /**
     * Create songs organized by categories with theme-related vocabulary
     */
    private void createSongs() {
        songs = new ArrayList<>();

        // === NURSERY RHYMES Category ===
        songs.add(new SongCategory(
                1,
                "Twinkle Twinkle Little Star",
                "Classic bedtime song",
                "Nursery Rhymes",
                new String[]{
                        "Twinkle, twinkle, little _____",
                        "How I wonder what you _____",
                        "Up above the world so _____",
                        "Like a diamond in the _____",
                        "Twinkle, twinkle, little star",
                        "How I _____ what you are"
                },
                new String[]{"star", "are", "high", "sky", "wonder"},
                android.R.drawable.ic_media_play,
                false  // Unlocked
        ));

        // === ANIMALS Category ===
        songs.add(new SongCategory(
                2,
                "Old MacDonald Had a Farm",
                "Learn animal sounds",
                "Animals",
                new String[]{
                        "Old MacDonald had a _____",
                        "E-I-E-I-O",
                        "And on that farm he had a _____",
                        "E-I-E-I-O",
                        "With a moo-moo here",
                        "And a moo-moo there",
                        "Here a _____, there a _____",
                        "Everywhere a _____-moo"
                },
                new String[]{"farm", "cow", "moo", "moo", "moo"},
                android.R.drawable.ic_media_play,
                false  // Unlocked
        ));

        songs.add(new SongCategory(
                3,
                "Mary Had a Little Lamb",
                "Sweet lamb story",
                "Animals",
                new String[]{
                        "Mary had a little _____",
                        "Little lamb, little lamb",
                        "Mary had a little lamb",
                        "Its fleece was white as _____",
                        "And everywhere that Mary _____",
                        "Mary went, Mary went",
                        "The lamb was sure to _____"
                },
                new String[]{"lamb", "snow", "went", "go"},
                android.R.drawable.ic_media_play,
                false
        ));

        // === COLORS Category ===
        songs.add(new SongCategory(
                4,
                "Rainbow Song",
                "Learn color names",
                "Colors",
                new String[]{
                        "Red and yellow and pink and _____",
                        "Purple and orange and _____",
                        "I can sing a _____",
                        "Sing a rainbow",
                        "Rainbow colors everywhere"
                },
                new String[]{"green", "blue", "rainbow"},
                android.R.drawable.ic_media_play,
                false
        ));

        // === NUMBERS Category ===
        songs.add(new SongCategory(
                5,
                "Five Little Ducks",
                "Count from 5 to 0",
                "Numbers",
                new String[]{
                        "Five little ducks went out one _____",
                        "Over the hills and far _____",
                        "Mother duck said quack quack quack _____",
                        "But only four little ducks came _____"
                },
                new String[]{"day", "away", "quack", "back"},
                android.R.drawable.ic_media_play,
                false
        ));

        songs.add(new SongCategory(
                6,
                "Ten Little Fingers",
                "Count your fingers",
                "Numbers",
                new String[]{
                        "I have ten little _____",
                        "And they all belong to _____",
                        "I can make them do things",
                        "Would you like to see?",
                        "I can shut them up _____",
                        "I can open them up _____"
                },
                new String[]{"fingers", "me", "tight", "wide"},
                android.R.drawable.ic_media_play,
                true  // Locked - requires completion of previous song
        ));

        // === More Animals ===
        songs.add(new SongCategory(
                7,
                "Baa Baa Black Sheep",
                "Sheep with wool",
                "Animals",
                new String[]{
                        "Baa, baa, black _____",
                        "Have you any _____?",
                        "Yes sir, yes sir",
                        "Three bags _____",
                        "One for my master",
                        "One for my dame",
                        "And one for the little boy",
                        "Who lives down the _____"
                },
                new String[]{"sheep", "wool", "full", "lane"},
                android.R.drawable.ic_media_play,
                true  // Locked
        ));

        // === More Nursery Rhymes ===
        songs.add(new SongCategory(
                8,
                "Wheels on the Bus",
                "City bus adventure",
                "Nursery Rhymes",
                new String[]{
                        "The wheels on the bus go _____ and round",
                        "Round and round, round and round",
                        "The wheels on the bus go round and _____",
                        "All through the _____"
                },
                new String[]{"round", "round", "town"},
                android.R.drawable.ic_media_play,
                true  // Locked
        ));
    }
}
