package com.example.notesreferences;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class CreateNoteFragment extends Fragment {

    EditText title;
    EditText description;
    Button save;
    CheckBox day;
    CheckBox longTerm;
    CheckBox temporary;
    private sendDataToDayNote sendDataToDayNote;
    private sendData sendData;
    private sendDataToLongTerm sendDataToLongTerm;
    private startMainFragment startMainFragment;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.sendData = (sendData) context;
        this.sendDataToDayNote = (sendDataToDayNote) context;
//        this.sendDataToLongTerm = (sendDataToLongTerm) context;
        this.startMainFragment = (startMainFragment) context;
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

            if (day.isChecked()) {
                bundleSendData(MainActivity.DATA_DAY_NOTE, MainActivity.DATA_DAY_NOTE);
                startMainFragment.startMainFragment();

            } else if (longTerm.isChecked()) {
                bundleSendData(MainActivity.DATA_LONG_TERM, MainActivity.DATA_LONG_TERM_TO_MAIN);
                startMainFragment.startMainFragment();
            } else if (temporary.isChecked()) {
                bundleSendData(MainActivity.DATA_TEMPORARY, MainActivity.DATA_TEMPORARY_TO_MAIN);
                startMainFragment.startMainFragment();
            } else {
                Toast.makeText(getContext(), "Please, choose category", Toast.LENGTH_SHORT).show();
            }

        });
    }

    public void bundleSendData(String requestKey, String requestKey2) {
        Bundle result = new Bundle();
        result.putString("title", title.getText().toString());
        result.putString("description", description.getText().toString());
        getParentFragmentManager().setFragmentResult(requestKey2, result);
        getParentFragmentManager().setFragmentResult(requestKey, result);
    }

    interface startMainFragment {
        void startMainFragment();
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

    interface closeCreateNote {
        void closeCreateNote();
    }
}