package in.ed.poonacollege.androidassignments.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import in.ed.poonacollege.androidassignments.R;

public class Question19Fragment extends Fragment {

    public Question19Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_question19, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView image = view.findViewById(R.id.image);
        Button changeButton = view.findViewById(R.id.change_button);

        changeButton.setOnClickListener((v)->{
            image.setImageResource(R.drawable.image_2);
        });
    }

}
