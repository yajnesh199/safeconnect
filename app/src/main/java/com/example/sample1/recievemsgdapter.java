//package com.example.sample1;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//public class recievemsgdapter extends  RecyclerView.Adapter<recievemsgdapter.myviewholder> {
//    @NonNull
//    @Override
//    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemreceive, parent, false);
//        return new myviewholder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return 0;
//    }
//
//    public class myviewholder extends RecyclerView.ViewHolder {
//        TextView recievemsg,receivedate;
//        public myviewholder(@NonNull View itemView) {
//            super(itemView);
//            recievemsg=itemView.findViewById(R.id.receive_text);
//            receivedate=itemView.findViewById(R.id.datereceive_text);
//        }
//    }

//    @NonNull
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        return null;
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
//    public  class  recieveviewholder extends RecyclerView.ViewHolder{
//
//       TextView recievemsg;
//        public recieveviewholder(@NonNull View itemView) {
//            super(itemView);
//            recievemsg=itemView.findViewById(R.id.recievemsg);
//        }
//    }
//    public  class senderviewholder extends  RecyclerView.ViewHolder{
//        TextView sndmsg;
//        public senderviewholder(@NonNull View itemView) {
//            super(itemView);
//            sndmsg=itemView.findViewById(R.id.sendmsg);
//        }
//    }
//}


