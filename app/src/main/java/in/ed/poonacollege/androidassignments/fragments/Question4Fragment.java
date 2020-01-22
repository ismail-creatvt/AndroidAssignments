package in.ed.poonacollege.androidassignments.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import in.ed.poonacollege.androidassignments.Utility;
import in.ed.poonacollege.androidassignments.R;

public class Question4Fragment extends Fragment {


    public Question4Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_question4, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button validateButton = view.findViewById(R.id.validate_button);
        EditText phoneField = view.findViewById(R.id.number_field);

        validateButton.setOnClickListener((v)->{
            String phone = phoneField.getEditableText().toString();
            if(!Utility.isValidPhone(phone)){
                phoneField.setError("Invalid Phone!");
            } else{
                Toast.makeText(getContext(), "Phone is Valid", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
