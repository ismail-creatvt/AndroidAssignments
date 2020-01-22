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

public class Question9Fragment extends Fragment {

    private float totalBalance = 0;

    public Question9Fragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_question9, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button withdraw = view.findViewById(R.id.withdraw);
        Button deposit = view.findViewById(R.id.deposit);

        EditText amountField = view.findViewById(R.id.amount);
        TextView totalBalanceText = view.findViewById(R.id.total_balance);

        totalBalanceText.setText(String.valueOf(totalBalance));

        withdraw.setOnClickListener((v)->{
            float amount = Float.valueOf(amountField.getEditableText().toString());
            if(totalBalance < amount){
                showToast("Insufficient Balance");
            } else{
                totalBalance -= amount;
                showToast(amount + " withdrawn");
            }
            amountField.setText("");
            totalBalanceText.setText(String.valueOf(totalBalance));
        });

        deposit.setOnClickListener((v)->{
            float amount = Float.valueOf(amountField.getEditableText().toString());

            totalBalance += amount;
            showToast(amount + " deposited");
            amountField.setText("");
            totalBalanceText.setText(String.valueOf(totalBalance));
        });
    }

    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
