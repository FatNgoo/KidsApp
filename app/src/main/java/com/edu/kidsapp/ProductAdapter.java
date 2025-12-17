package com.edu.kidsapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * ProductAdapter - RecyclerView adapter for shop products
 */
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> productList;
    private OnProductBuyListener buyListener;

    /**
     * Interface for buy button callback
     */
    public interface OnProductBuyListener {
        void onProductBuy(Product product, int position);
    }

    /**
     * Constructor
     * @param productList List of products to display
     * @param buyListener Callback for buy button clicks
     */
    public ProductAdapter(List<Product> productList, OnProductBuyListener buyListener) {
        this.productList = productList;
        this.buyListener = buyListener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);

        // Bind data to views
        holder.imgProduct.setImageResource(product.getImageResId());
        holder.tvName.setText(product.getName());
        holder.tvPrice.setText(product.getPrice() + " Coins");

        // Handle buy button click
        holder.btnBuy.setOnClickListener(v -> {
            // Show toast feedback
            Toast.makeText(v.getContext(),
                    "You bought " + product.getName() + " for " + product.getPrice() + " coins!",
                    Toast.LENGTH_SHORT).show();

            // Trigger callback
            if (buyListener != null) {
                buyListener.onProductBuy(product, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    /**
     * ViewHolder for product items
     */
    static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct;
        TextView tvName;
        TextView tvPrice;
        Button btnBuy;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.img_product);
            tvName = itemView.findViewById(R.id.tv_name);
            tvPrice = itemView.findViewById(R.id.tv_price);
            btnBuy = itemView.findViewById(R.id.btn_buy);
        }
    }
}
