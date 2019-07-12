package com.example.yukngaji;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {
    private EditText editText;
    private PesanAdapter messageAdapter;
    private ListView messagesView;
    ImageButton send;
    DatabaseReference reference1, reference2;
    private ArrayList<itempesan> Listpesan;
    FirebaseAuth mAuth;
    String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Intent intent = getIntent();
        uid = intent.getStringExtra("uid");
        String nama = intent.getStringExtra("nama");
        setTitle(nama);
        editText = findViewById(R.id.editText);
        messagesView = findViewById(R.id.messages_view);
        send=findViewById(R.id.sendpesan);
        reference1 = FirebaseDatabase.getInstance().getReference();
        reference2 = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        tampil();
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sends();
            }
        });
    }
    public void sends(){
        String message = editText.getText().toString();
        if (message.length() > 0) {
        reference1.child("Chat").child(mAuth.getUid()).child(uid).push()
                .setValue(new itempesan(message, "", true))
                .addOnSuccessListener(this, new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        editText.setText("");
                    }
                });
        reference2.child("Chat").child(uid).child(mAuth.getUid()).push()
                .setValue(new itempesan(message, "", false))
                .addOnSuccessListener(this, new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        editText.setText("");
                    }
                });
        }
    }
//    public void sendMessage(View view) {
//        String message = editText.getText().toString();
//        if (message.length() > 0) {
//            itempesan pesan=new itempesan(editText.getText().toString(),"alfin",true);
//            messageAdapter.add(pesan);
//            messagesView.setSelection(messagesView.getCount() - 1);
//            editText.getText().clear();
//        }
//        else {
//            itempesan pesan=new itempesan("alfin","alfin",false);
//            messageAdapter.add(pesan);
//            messagesView.setSelection(messagesView.getCount() - 1);
//        }
//    }
    public void tampil(){
        reference1.child("Chat").child(mAuth.getUid()).child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Listpesan=new ArrayList<>();
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    itempesan itempesans = noteDataSnapshot.getValue(itempesan.class);
                    Listpesan.add(itempesans);
                }
                messageAdapter = new PesanAdapter(ChatActivity.this,Listpesan);
                messagesView.setAdapter(messageAdapter);
                messagesView.setSelection(messagesView.getCount() - 1);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
