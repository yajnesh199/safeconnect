package com.example.sample1;

import static java.lang.Integer.parseInt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Roomdb extends AppCompatActivity {
    EditText et1,et2,et3,et4,et5,et6;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roomdb);

     et1=findViewById(R.id.et1);
    et2=findViewById(R.id.et2);
    et3=findViewById(R.id.et3);
    et4=findViewById(R.id.et4);
    et5=findViewById(R.id.et5);
    et6=findViewById(R.id.et6);
    btn=findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            getroomdata();
        }
    });
}
    public void getroomdata() {
        database  db = Room.databaseBuilder(Roomdb.this,
                database.class, "chat_DB").allowMainThreadQueries().build();
        UserDoa userdao = db.userDao();
        userdao.insertAll(new User(parseInt(et1.getText().toString()),parseInt(et2.getText().toString()),et3.getText().toString(),et4.getText().toString(),et5.getText().toString(),et6.getText().toString()));
        Log.e("bt_device", "inserted successfully");
    }

}