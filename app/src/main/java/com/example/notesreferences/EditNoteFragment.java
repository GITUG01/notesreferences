package com.example.notesreferences;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import com.example.notesreferences.domain.NoteEntity;

public class EditNoteFragment extends Fragment {

    EditText titleEt;
    EditText descriptionEt;

    String title;
    String description;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_note, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

//        view.findViewById(R.id.toolbar_title);

        titleEt = view.findViewById(R.id.title_edit_note);
        descriptionEt = view.findViewById(R.id.description_edit_note);

        super.onViewCreated(view, savedInstanceState);


        getParentFragmentManager().setFragmentResultListener(MainActivity.DATA_T0_EDIT, this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                titleEt.setText(result.getString("title"));
                descriptionEt.setText(result.getString("description"));

//                NoteEntity note = (NoteEntity) result.getSerializable("gg");
//                note.setTitle(titleEt.getText().toString());
//                note.setDetale(descriptionEt.getText().toString());
            }
        });

    }
}
