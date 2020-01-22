package in.ed.poonacollege.androidassignments.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import in.ed.poonacollege.androidassignments.R;

public class Question12Fragment extends Fragment {

    private int firstNumber, secondNumber;
    private EditText firstNumberField, secondNumberField;
    private TextView firstNumberText, secondNumberText;

    public Question12Fragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_question12, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firstNumberField = view.findViewById(R.id.first_number);
        secondNumberField = view.findViewById(R.id.second_number);

        firstNumberText = view.findViewById(R.id.first_no_text);
        secondNumberText = view.findViewById(R.id.second_no_text);

        firstNumberField.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                firstNumber = getNumber(firstNumberField);
                firstNumberText.setText(String.valueOf(firstNumber));
                checkNumbers();
            }
        });

        secondNumberField.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                secondNumber = getNumber(secondNumberField);
                secondNumberText.setText(String.valueOf(secondNumber));
                checkNumbers();
            }
        });
    }

    private int getNumber(EditText numberField) {
        try {
            return Integer.valueOf(numberField.getEditableText().toString());
        }catch (NumberFormatException nfe){
            return 0;
        }
    }

    private void checkNumbers() {
        if(firstNumber > 10 && secondNumber > 10){
            Toast.makeText(getContext(), "Both numbers shouldn't be greater than 10. Please enter again.", Toast.LENGTH_SHORT).show();
            firstNumber = 0;
            secondNumber = 0;
            firstNumberField.setText("");
            secondNumberField.setText("");
        }
    }

    public interface SimpleTextWatcher extends TextWatcher {

        @Override
        default void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        default void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        default void afterTextChanged(Editable s) { }

    }
}
