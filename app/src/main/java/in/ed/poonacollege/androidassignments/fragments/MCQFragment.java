package in.ed.poonacollege.androidassignments.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import in.ed.poonacollege.androidassignments.R;
import in.ed.poonacollege.androidassignments.model.Question;

import static in.ed.poonacollege.androidassignments.fragments.Question6Fragment.QUESTION;

public class MCQFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {

    TextView questionText;
    RadioButton option1, option2, option3, option4;
    Question question;
    private OnOptionItemSelectedListener onOptionsItemSelectedListener;

    public void setOnOptionsItemSelectedListener(OnOptionItemSelectedListener onOptionsItemSelectedListener) {
        this.onOptionsItemSelectedListener = onOptionsItemSelectedListener;
    }

    public MCQFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mcq, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(getArguments() != null){
            question = getArguments().getParcelable(QUESTION);
        }

        questionText = view.findViewById(R.id.question);

        option1 = view.findViewById(R.id.option_1);
        option2 = view.findViewById(R.id.option_2);
        option3 = view.findViewById(R.id.option_3);
        option4 = view.findViewById(R.id.option_4);

        questionText.setText(question.getQuestion());
        option1.setText(question.getOption1());
        option2.setText(question.getOption2());
        option3.setText(question.getOption3());
        option4.setText(question.getOption4());

        option1.setOnCheckedChangeListener(this);
        option2.setOnCheckedChangeListener(this);
        option3.setOnCheckedChangeListener(this);
        option4.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.option_1:
                if(isChecked && onOptionsItemSelectedListener != null){
                    onOptionsItemSelectedListener.onOptionSelected(question, 0);
                }
                break;
            case R.id.option_2:
                if(isChecked && onOptionsItemSelectedListener != null){
                    onOptionsItemSelectedListener.onOptionSelected(question, 1);
                }
                break;
            case R.id.option_3:
                if(isChecked && onOptionsItemSelectedListener != null){
                    onOptionsItemSelectedListener.onOptionSelected(question, 2);
                }
                break;
            case R.id.option_4:
                if(isChecked && onOptionsItemSelectedListener != null){
                    onOptionsItemSelectedListener.onOptionSelected(question, 3);
                }
                break;
        }
    }

    public interface OnOptionItemSelectedListener{
        void onOptionSelected(Question question, int index);
    }
}
