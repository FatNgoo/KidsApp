package com.edu.kidsapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * GameLobbyFragment - Game Zone lobby screen
 * Displays available minigames with ticket costs
 */
public class GameLobbyFragment extends Fragment {

    private RecyclerView rvGames;
    private TextView tvTicketBalance;
    private GameAdapter adapter;
    private List<GameModel> games;
    private int userTickets = 3; // Mock data

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_game_lobby, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize views
        rvGames = view.findViewById(R.id.rvGames);
        tvTicketBalance = view.findViewById(R.id.tvTicketBalance);

        // Set ticket balance
        tvTicketBalance.setText(String.valueOf(userTickets));

        // Setup RecyclerView
        setupRecyclerView();

        // Create game data
        createGameData();

        // Set adapter
        adapter = new GameAdapter(games, game -> handleGameClick(game));
        rvGames.setAdapter(adapter);
    }

    /**
     * Setup RecyclerView with GridLayoutManager (2 columns)
     */
    private void setupRecyclerView() {
        GridLayoutManager layoutManager = new GridLayoutManager(requireContext(), 2);
        rvGames.setLayoutManager(layoutManager);
    }

    /**
     * Create game data list
     */
    private void createGameData() {
        games = new ArrayList<>();
        
        // Master Chef game
        games.add(new GameModel(
                1,
                "Master Chef",
                R.drawable.placeholder_thumb_chef,
                1,
                CookingGameActivity.class
        ));

        // Detective game
        games.add(new GameModel(
                2,
                "Detective",
                R.drawable.placeholder_thumb_detective,
                1,
                DetectiveGameActivity.class
        ));

        // Pet Hospital game
        games.add(new GameModel(
                3,
                "Pet Hospital",
                R.drawable.placeholder_thumb_chef,
                1,
                PetHospitalLevelsActivity.class
        ));
    }

    /**
     * Handle game card click
     * Check if user has enough tickets before starting game
     */
    private void handleGameClick(GameModel game) {
        if (userTickets >= game.getTicketCost()) {
            // User has enough tickets
            Toast.makeText(requireContext(), 
                    "Spending " + game.getTicketCost() + " Ticket...", 
                    Toast.LENGTH_SHORT).show();

            // Wait 500ms then start game
            new Handler().postDelayed(() -> {
                // Deduct ticket
                userTickets -= game.getTicketCost();
                tvTicketBalance.setText(String.valueOf(userTickets));

                // Start game activity
                Intent intent = new Intent(requireContext(), game.getDestinationActivity());
                startActivity(intent);
            }, 500);

        } else {
            // Not enough tickets - show dialog
            showOutOfTicketsDialog();
        }
    }

    /**
     * Show dialog when user is out of tickets
     */
    private void showOutOfTicketsDialog() {
        new AlertDialog.Builder(requireContext())
                .setTitle("Out of Tickets!")
                .setMessage("Go back to School to earn more tickets!")
                .setPositiveButton("Go Learn", (dialog, which) -> {
                    // Navigate back to home
                    Navigation.findNavController(requireView()).popBackStack(R.id.homeFragment, false);
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }
}
