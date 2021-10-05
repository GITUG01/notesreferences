package com.example.notesreferences;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class CreateNoteFragment extends Fragment {

    private sendData sendData;
    EditText title;
    EditText description;
    Button save;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        sendData = (sendData) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_note, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        title = view.findViewById(R.id.title_et);
        description = view.findViewById(R.id.description_et);
        save = view.findViewById(R.id.save_btn);
        save.setOnClickListener(view1 -> {
            sendData.sendData(title.getText().toString(), description.getText().toString());
            getFragmentManager().beginTransaction().remove(CreateNoteFragment.this).commit();
//            getActivity().getFragmentManager().popBackStack();
        });

    }

    interface sendData {
        void sendData(String title, String description);
    }
}