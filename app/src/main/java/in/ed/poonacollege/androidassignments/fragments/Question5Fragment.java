package in.ed.poonacollege.androidassignments.fragments;


import android.database.DataSetObserver;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import java.util.ArrayList;

import in.ed.poonacollege.androidassignments.R;

public class Question5Fragment extends Fragment implements View.OnClickListener {


    private Button addToSpinnerButton, removeFromSpinnerButton;
    private EditText itemField;
    private Spinner itemSpinner;

    private ArrayList<String> items = new ArrayList<>();
    private ArrayAdapter<String> itemAdapter;

    public Question5Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_question5, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addToSpinnerButton = view.findViewById(R.id.addToSpinnerButton);
        removeFromSpinnerButton = view.findViewById(R.id.removeFromSpinnerButton);

        itemField = view.findViewById(R.id.itemField);
        itemSpinner = view.findViewById(R.id.itemSpinner);

        itemAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, items);
        itemAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        itemSpinner.setAdapter(itemAdapter);

        addToSpinnerButton.setOnClickListener(this);
        removeFromSpinnerButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String item = itemField.getEditableText().toString();

        switch (v.getId()){
            case R.id.addToSpinnerButton:
                if(!item.isEmpty()){
                    items.add(item);
                    itemAdapter.notifyDataSetChanged();
                }
                break;


            case R.id.removeFromSpinnerButton:
                if(!item.isEmpty()){
                    items.remove(item);
                    itemAdapter.notifyDataSetChanged();
                }
                break;
        }
    }
}
