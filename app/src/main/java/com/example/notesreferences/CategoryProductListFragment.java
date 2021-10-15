package com.example.notesreferences;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class CategoryProductListFragment extends Fragment {

    Button button;
    EditText product;
    LinearLayout linearLayout;
    hideKeyboard hideKeyboard;

    @Override
    public void onAttach(@NonNull Context context) {
        this.hideKeyboard = (hideKeyboard) context;
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category_product_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        linearLayout = view.findViewById(R.id.product_list_layout);
        product = view.findViewById(R.id.product_name);

        LinearLayout.LayoutParams ml = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ml.setMargins(16, 0, 0, 10);
        button = view.findViewById(R.id.add_btn);
        button.setOnClickListener(v -> {
            CheckBox checkBox = new CheckBox(getContext());
            checkBox.setText(product.getText().toString());
            product.setText("");
            checkBox.offsetLeftAndRight(20);
            checkBox.setLayoutParams(ml);
            linearLayout.addView(checkBox);
            hideKeyboard.hideKeyboard();

        });

        super.onViewCreated(view, savedInstanceState);
    }

    interface hideKeyboard {
        void hideKeyboard();
    }
}