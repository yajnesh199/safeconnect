package com.example.sample1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
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

public class chatadapter extends RecyclerView.Adapter<chatadapter.myviewholder> {
    ArrayList<String> stringList;
    Context context;

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
    public void onBindViewHolder(@NonNull myviewholder holder, @SuppressLint("RecyclerView") int position) {
        //String data = stringList.get(position);
        holder.tvv_paired.setText(stringList.get(position).toString());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("M","n" + position);
                //  String id=tvv_paired.getText().toString();
                //  Log.e("bt_id","he " + id);
                Intent intent = new Intent(view.getContext(),chatActivity.class);
                intent.putExtra("key", " " + stringList.get(position));
                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }

    class myviewholder extends RecyclerView.ViewHolder {
        TextView tvv_paired;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            tvv_paired = itemView.findViewById(R.id.tvv_paired);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent = new Intent(view.getContext(), chatActivity.class);
//                    intent.putExtra("key", "value ");
//                    view.getContext().startActivity(intent);
//
//                }
//            });
        }
    }
}