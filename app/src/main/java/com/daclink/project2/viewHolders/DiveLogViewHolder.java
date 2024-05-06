package com.daclink.project2.viewHolders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.daclink.project2.R;

public class DiveLogViewHolder extends RecyclerView.ViewHolder {

    private final TextView diveLogViewItem;

    private DiveLogViewHolder(View diveLogView) {
        super(diveLogView);
        diveLogViewItem = diveLogView.findViewById(R.id.recyclerItemTextView);
    }

    public void bind (String text) {
        diveLogViewItem.setText(text);
    }

    static DiveLogViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.divelog_recycler_item, parent, false);
        return new DiveLogViewHolder(view);
    }
}
