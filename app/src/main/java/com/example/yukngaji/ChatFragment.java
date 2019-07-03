package com.example.yukngaji;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment {
    private EditText editText;
    private PesanAdapter messageAdapter;
    private ListView messagesView;
    ImageButton send;


    public ChatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_chat, container, false);
        editText = view.findViewById(R.id.editText);
        //messageAdapter = new PesanAdapter(getActivity());
        messagesView = view.findViewById(R.id.messages_view);
        send=view.findViewById(R.id.sendpesan);
        messagesView.setAdapter(messageAdapter);
        final itempesan message=new itempesan("dsadsadasdasd","dasdasdasdad",false);
        messageAdapter.add(message);
        messagesView.setSelection(messagesView.getCount() - 1);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage(v);
            }
        });
        return view;
    }
    public void sendMessage(View view) {
        String message = editText.getText().toString();
        if (message.length() > 0) {
            itempesan pesan=new itempesan(editText.getText().toString(),"alfin",true);
            messageAdapter.add(pesan);
            messagesView.setSelection(messagesView.getCount() - 1);
            editText.getText().clear();
        }
        else {
            itempesan pesan=new itempesan("alfin","alfin",false);
            messageAdapter.add(pesan);
            messagesView.setSelection(messagesView.getCount() - 1);
        }
    }

}
