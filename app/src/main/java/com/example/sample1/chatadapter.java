package com.example.sample1;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class chatadapter extends RecyclerView.Adapter< chatadapter.myviewholder> {
    ArrayList<String> stringList;

    public chatadapter(FragmentActivity activity, ArrayList<String> stringList) {
        this.stringList = stringList;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerchatrows, parent, false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder,  int position) {
        //String data = stringList.get(position);
        holder.tvv_paired.setText(stringList.get(position).toString());

    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }

    class myviewholder extends RecyclerView.ViewHolder {
        TextView tvv_paired;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            tvv_paired= itemView.findViewById(R.id.tvv_paired);

        }
    }
}