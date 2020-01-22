package in.ed.poonacollege.androidassignments.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import in.ed.poonacollege.androidassignments.R;

public class FinalResultFragment extends Fragment {

    public FinalResultFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_final_result, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FinalResultFragmentArgs args = FinalResultFragmentArgs.fromBundle(getArguments());
        TextView correctAnswers = view.findViewById(R.id.correct_answers);
        TextView totalQuestions = view.findViewById(R.id.total_questions);

        correctAnswers.setText(String.valueOf(args.getCorrectAnswers()));
        totalQuestions.setText(String.valueOf(args.getTotalQuestions()));

    }
}
