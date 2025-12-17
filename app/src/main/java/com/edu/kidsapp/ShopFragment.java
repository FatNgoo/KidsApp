package com.edu.kidsapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * ShopFragment - Displays a grid of products for purchase
 */
public class ShopFragment extends Fragment {

    private RecyclerView rvProducts;
    private TextView tvBalance;
    private ProductAdapter adapter;
    private List<Product> productList;

    // Simulated user balance
    private int userBalance = 500;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shop, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize views
        rvProducts = view.findViewById(R.id.rv_products);
        tvBalance = view.findViewById(R.id.tv_balance);

        // Setup RecyclerView with GridLayoutManager (2 columns)
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        rvProducts.setLayoutManager(gridLayoutManager);

        // Create dummy product data
        productList = createDummyProducts();

        // Setup adapter with buy listener
        adapter = new ProductAdapter(productList, new ProductAdapter.OnProductBuyListener() {
            @Override
            public void onProductBuy(Product product, int position) {
                handlePurchase(product);
            }
        });

        rvProducts.setAdapter(adapter);

        // Update balance display
        updateBalanceDisplay();
    }

    /**
     * Create dummy product data
     */
    private List<Product> createDummyProducts() {
        List<Product> products = new ArrayList<>();

        // Add sample products (using placeholder images)
        products.add(new Product("Robot Toy", 50, R.drawable.ic_launcher_foreground));
        products.add(new Product("Doll", 40, R.drawable.ic_launcher_foreground));
        products.add(new Product("Toy Car", 60, R.drawable.ic_launcher_foreground));
        products.add(new Product("Puzzle", 30, R.drawable.ic_launcher_foreground));
        products.add(new Product("Ball", 20, R.drawable.ic_launcher_foreground));
        products.add(new Product("Teddy Bear", 70, R.drawable.ic_launcher_foreground));
        products.add(new Product("Action Figure", 80, R.drawable.ic_launcher_foreground));
        products.add(new Product("Board Game", 90, R.drawable.ic_launcher_foreground));

        return products;
    }

    /**
     * Handle product purchase
     */
    private void handlePurchase(Product product) {
        if (userBalance >= product.getPrice()) {
            // Deduct from balance
            userBalance -= product.getPrice();
            updateBalanceDisplay();

            Toast.makeText(getContext(),
                    "✅ Purchase successful! Remaining: " + userBalance + " coins",
                    Toast.LENGTH_SHORT).show();
        } else {
            // Not enough balance
            Toast.makeText(getContext(),
                    "❌ Not enough coins! You need " + product.getPrice() + " coins.",
                    Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Update balance display
     */
    private void updateBalanceDisplay() {
        tvBalance.setText("Your Balance: " + userBalance + " Coins 💰");
    }
}
