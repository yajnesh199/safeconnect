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
//public class sendmsgadapter extends RecyclerView.Adapter<sendmsgadapter.myviewholder>{
//
//    @NonNull
//    @Override
//    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemsend, parent, false);
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
//        TextView sendmsg,datamsg;
//        public myviewholder(@NonNull View itemView) {
//            super(itemView);
//           sendmsg=itemView.findViewById(R.id.send_text);
//            datamsg=itemView.findViewById(R.id.datesend_text);
//        }
//    }
//}
