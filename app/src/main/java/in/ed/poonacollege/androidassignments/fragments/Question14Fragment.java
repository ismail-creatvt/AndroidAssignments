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
import android.widget.TextView;
import android.widget.Toast;

import in.ed.poonacollege.androidassignments.R;

public class Question14Fragment extends Fragment {

    public Question14Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_question14, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText numberField = view.findViewById(R.id.number_field);
        Button checkPalindrome = view.findViewById(R.id.check_palindrome);

        checkPalindrome.setOnClickListener((v)->{
            String number = numberField.getText().toString();
            if(new StringBuilder(number).reverse().toString().equals(number)){
                Toast.makeText(getContext(), "Number is Palindrome", Toast.LENGTH_LONG).show();
            } else{
                Toast.makeText(getContext(), "Number is not a Palindrome", Toast.LENGTH_LONG).show();
            }
        });
    }
}
