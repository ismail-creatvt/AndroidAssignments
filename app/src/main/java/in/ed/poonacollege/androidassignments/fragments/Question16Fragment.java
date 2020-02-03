package in.ed.poonacollege.androidassignments.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import in.ed.poonacollege.androidassignments.R;

public class Question16Fragment extends Fragment {


    public Question16Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_question16, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText number_field = view.findViewById(R.id.number_field);
        RadioButton oddeven = view.findViewById(R.id.oddeven);
        RadioButton sign = view.findViewById(R.id.sign);
        RadioButton square = view.findViewById(R.id.square);
        RadioButton factorial = view.findViewById(R.id.factorial);
        Button calculate_button = view.findViewById(R.id.calculate_button);
        TextView answer = view.findViewById(R.id.answer);

        calculate_button.setOnClickListener(v -> {
            if(TextUtils.isEmpty(number_field.getText())) return;

            int number = Integer.valueOf(number_field.getText().toString());

            if(oddeven.isChecked()){
                answer.setText("Number is " + (number%2==0 ? "even" : "odd"));
            }

            if(sign.isChecked()){
                answer.setText("Number is " + (number>0 ? "Positive" : number == 0 ? "Zero" : "Negative"));
            }

            if(square.isChecked()){
                answer.setText("Square is " + (number * number));
            }

            if(factorial.isChecked()){
                if(number < 0){
                    number = Math.abs(number);
                }
                answer.setText("Factorial is " + factorial(number));
            }
        });
    }

    private int factorial(int n) {
        if (n == 0)
            return 1;

        return n*factorial(n-1);
    }
}
