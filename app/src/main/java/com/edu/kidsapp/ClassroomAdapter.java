package com.edu.kidsapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * ClassroomAdapter - RecyclerView adapter for classroom cards
 */
public class ClassroomAdapter extends RecyclerView.Adapter<ClassroomAdapter.ClassroomViewHolder> {

    private List<Classroom> classroomList;
    private OnClassroomClickListener clickListener;

    /**
     * Interface for classroom card clicks
     */
    public interface OnClassroomClickListener {
        void onClassroomClick(Classroom classroom, int position);
    }

    /**
     * Constructor
     * @param classroomList List of classrooms
     * @param clickListener Click callback
     */
    public ClassroomAdapter(List<Classroom> classroomList, OnClassroomClickListener clickListener) {
        this.classroomList = classroomList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ClassroomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_classroom, parent, false);
        return new ClassroomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassroomViewHolder holder, int position) {
        Classroom classroom = classroomList.get(position);

        // Bind data
        holder.imgIcon.setImageResource(classroom.getIconResId());
        holder.tvTitle.setText(classroom.getTitle());
        holder.tvDescription.setText(classroom.getDescription());
        holder.cardBackground.setBackgroundResource(classroom.getColorResId());

        // Handle click
        holder.itemView.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onClassroomClick(classroom, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return classroomList.size();
    }

    /**
     * ViewHolder for classroom items
     */
    static class ClassroomViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout cardBackground;
        ImageView imgIcon;
        TextView tvTitle;
        TextView tvDescription;

        public ClassroomViewHolder(@NonNull View itemView) {
            super(itemView);
            cardBackground = itemView.findViewById(R.id.cardBackground);
            imgIcon = itemView.findViewById(R.id.img_classroom_icon);
            tvTitle = itemView.findViewById(R.id.tv_classroom_title);
            tvDescription = itemView.findViewById(R.id.tv_classroom_description);
        }
    }
}
