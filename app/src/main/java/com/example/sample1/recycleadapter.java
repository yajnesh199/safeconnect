package com.example.sample1;
//
//import android.bluetooth.BluetoothDevice;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.List;
//
//public class recycleadapter extends RecyclerView.Adapter {
//    private List<String> stringList;
//    @NonNull
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleviewrows, parent, false);
//        return new holder(view);
//        //return null;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return 0;
//    }
//
//    class holder extends RecyclerView.ViewHolder{
//        TextView tv;
//        public holder(@NonNull View itemView) {
//            super(itemView);
//            tv=(TextView) itemView.findViewById(R.id.tvv_paired);
//        }
//    }
//}


import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class recycleadapter extends RecyclerView.Adapter< recycleadapter.myviewholder> {
    ArrayList<String> stringList;

    public recycleadapter(FragmentActivity activity, ArrayList<String> stringList) {
        this.stringList = stringList;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleviewrows, parent, false);
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
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String id=tvv_paired.getText().toString();
                    Log.e("bt_id","he " + id);
                    Intent intent = new Intent(view.getContext(),chatActivity.class);

                    intent.putExtra("key", "value" );
                    view.getContext().startActivity(intent);
                }
            });
        }
    }
}

