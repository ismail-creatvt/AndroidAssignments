package in.ed.poonacollege.androidassignments.fragments;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import in.ed.poonacollege.androidassignments.R;

public class Question17Fragment extends Fragment {


    public Question17Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_question17, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText string_field = view.findViewById(R.id.string_field);
        RadioButton uppercase = view.findViewById(R.id.uppercase);
        RadioButton lowercase = view.findViewById(R.id.lowercase);
        RadioButton right_five = view.findViewById(R.id.right_five);
        RadioButton left_five = view.findViewById(R.id.left_five);
        Button transform_button = view.findViewById(R.id.transform_button);
        TextView output = view.findViewById(R.id.output);

        transform_button.setOnClickListener(v -> {
            if(TextUtils.isEmpty(string_field.getText())) return;

            String input = string_field.getText().toString();

            if(uppercase.isChecked()){
                output.setText(input.toUpperCase());
            }

            if(lowercase.isChecked()){
                output.setText(input.toLowerCase());
            }

            if(right_five.isChecked()){
                output.setText(input.substring(Math.max(0, input.length()-5)));
            }

            if(left_five.isChecked()){
                output.setText(input.substring(0, Math.min(5, input.length())));
            }
        });
    }

}
