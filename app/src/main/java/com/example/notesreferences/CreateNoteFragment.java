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
    private startCategoryDayNoteFragment startCategoryDayNoteFragment;
    private startCategoryLongTermFragment startCategoryLongTermFragment;
    private startTemporaryFragment startTemporaryFragment;
//    NoteViewHolderMainFragment noteViewHolderMainFragment = new NoteViewHolderMainFragment();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.sendData = (sendData) context;
        this.sendDataToDayNote = (sendDataToDayNote) context;
        this.startCategoryDayNoteFragment = (startCategoryDayNoteFragment) context;
        this.startCategoryLongTermFragment = (startCategoryLongTermFragment)context;
        this.startTemporaryFragment = (startTemporaryFragment)context;
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
                sendData.sendData(title.getText().toString(), description.getText().toString());
                bundleSendData(MainActivity.DATA_DAY_NOTE);
                bundleSendData(MainActivity.DATA_TO_MAIN);
                startCategoryDayNoteFragment.startCategoryDayNoteFragment();

            } else if (longTerm.isChecked()) {
                bundleSendData(MainActivity.DATA_LONG_TERM);
                bundleSendData(MainActivity.DATA_TO_MAIN);
                startCategoryLongTermFragment.startCategoryLongTermFragment();
            } else if (temporary.isChecked()) {
                bundleSendData(MainActivity.DATA_TEMPORARY);
                bundleSendData(MainActivity.DATA_TO_MAIN);
                startTemporaryFragment.startTemporaryFragment();
            } else {
                Toast.makeText(getContext(), "Please, choose category", Toast.LENGTH_SHORT).show();
            }

        });
    }

    public void bundleSendData(String requestKey) {
        Bundle result = new Bundle();
        result.putString("title", title.getText().toString());
        result.putString("description", description.getText().toString());
        getParentFragmentManager().setFragmentResult(requestKey, result);
    }

    interface startCategoryDayNoteFragment {
        void startCategoryDayNoteFragment();
    }

    interface startCategoryLongTermFragment {
        void startCategoryLongTermFragment();
    }

    interface startTemporaryFragment {
        void startTemporaryFragment();
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

    interface closeCreateNote {
        void closeCreateNote();
    }
}