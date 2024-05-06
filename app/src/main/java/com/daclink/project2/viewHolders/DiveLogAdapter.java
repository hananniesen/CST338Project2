package com.daclink.project2.viewHolders;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.daclink.project2.database.entities.DiveLog;

public class DiveLogAdapter extends ListAdapter<DiveLog, DiveLogViewHolder> {
    public DiveLogAdapter(@NonNull DiffUtil.ItemCallback<DiveLog> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public DiveLogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return DiveLogViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull DiveLogViewHolder holder, int position) {
        DiveLog current = getItem(position);
        holder.bind(current.toString());
    }

    public static class DiveLogDiff extends DiffUtil.ItemCallback<DiveLog> {
        @Override
        public boolean areItemsTheSame(@NonNull DiveLog oldItem, @NonNull DiveLog newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull DiveLog oldItem, @NonNull DiveLog newItem) {
            return oldItem.equals(newItem);
        }
    }
}
