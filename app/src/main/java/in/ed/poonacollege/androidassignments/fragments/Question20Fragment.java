package in.ed.poonacollege.androidassignments.fragments;


import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import in.ed.poonacollege.androidassignments.R;

public class Question20Fragment extends Fragment {


    public Question20Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_question20, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.click_me).setOnClickListener((v)->{
            new AlertDialog.Builder(getContext())
                    .setMessage("Are you a student?")
                    .setPositiveButton(R.string.yes,(dialog, which)->{
                        dialog.dismiss();
                        Toast.makeText(getContext(), "You are a student", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton(R.string.no,(dialog, which)->{
                        dialog.dismiss();
                        Toast.makeText(getContext(), "You are not a student", Toast.LENGTH_SHORT).show();
                    }).show();
        });
    }
}
