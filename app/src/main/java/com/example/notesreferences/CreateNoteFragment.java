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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class CreateNoteFragment extends Fragment {

    private sendDataToDayNote sendDataToDayNote;
    private sendData sendData;
    EditText title;
    EditText description;
    Button save;
    CheckBox day;
    CheckBox longTerm;
    CheckBox temporary;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.sendData = (sendData) context;
        this.sendDataToDayNote = (sendDataToDayNote) context;
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

        day = view.findViewById(R.id.day_note_chb);
        longTerm = view.findViewById(R.id.long_term_chb);
        temporary = view.findViewById(R.id.temporary_chb);

        title = view.findViewById(R.id.title_et);
        description = view.findViewById(R.id.description_et);
        save = view.findViewById(R.id.save_btn);
        save.setOnClickListener(view1 -> {

            if (day.isChecked()){
//                sendData.sendData(title.getText().toString(), description.getText().toString());
                bundleSendData("dataToDayNote");
                getFragmentManager().beginTransaction().addToBackStack(null).remove(CreateNoteFragment.this).commit();

            } else if(longTerm.isChecked()){
                bundleSendData("dataToLongTerm");
                getFragmentManager().beginTransaction().addToBackStack(null).remove(CreateNoteFragment.this).commit();
            } else if (temporary.isChecked()) {
                bundleSendData("dataToTemporary");
                getFragmentManager().beginTransaction().addToBackStack(null).remove(CreateNoteFragment.this).commit();
            } else {
                Toast.makeText(getContext(), "Please, choose category", Toast.LENGTH_SHORT).show();
            }

//            getFragmentManager().beginTransaction().addToBackStack(null).remove(CreateNoteFragment.this).commit();
//            sendDataToDayNote.sendDataToDayNote(title.getText().toString(), description.getText().toString());


        });
    }

    public void bundleSendData(String requestKey){
        Bundle result = new Bundle();
        result.putString("title", title.getText().toString());
        result.putString("description", description.getText().toString());
        getParentFragmentManager().setFragmentResult(requestKey, result);
    }

    interface sendData {
        void sendData(String title, String description);
    }

    interface sendDataToDayNote {
        void sendDataToDayNote(String title, String description);
    }

    interface sendDataToLongTerm {
        void sendDataToLongTerm(String title, String description);
    }

    interface sendDataToProductList {
        void sendDataToProductList(String title, String description);
    }

    interface sendDataToTemporary {
        void sendDataToTemporary(String title, String description);
    }
}