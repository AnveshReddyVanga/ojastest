package com.example.ojastest.ui;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ojastest.R;
import com.example.ojastest.databinding.ItemOjasBinding;
import com.example.ojastest.dtos.Hit;

import java.util.ArrayList;
import java.util.List;

public class OjasAdapter extends RecyclerView.Adapter<OjasAdapter.MyViewHolder> {

    private List<Hit> mDataset = new ArrayList<>();


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private ItemOjasBinding binding;

        public MyViewHolder(ItemOjasBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public OjasAdapter() {
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        // create a new view
        ItemOjasBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_ojas, parent, false);
        return new MyViewHolder(binding);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Hit post = mDataset.get(position);
        holder.binding.setPost(post);
    }

    public void addAll(List<Hit> mDataset) {
        this.mDataset.addAll(mDataset);
        notifyDataSetChanged();
    }

    public void clear() {
        mDataset.clear();
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
