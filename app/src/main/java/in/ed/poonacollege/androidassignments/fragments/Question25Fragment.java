package in.ed.poonacollege.androidassignments.fragments;


import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import in.ed.poonacollege.androidassignments.R;

public class Question25Fragment extends Fragment {

    private RadioButton redButton, greenButton, blueButton;
    private CheckBox boldButton, italicButton, underlineButton;
    private Button clearButton, displayButton;
    private EditText nameField, messageField;
    private TextView labelText;

    public Question25Fragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_question25, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        redButton = view.findViewById(R.id.red);
        greenButton = view.findViewById(R.id.green);
        blueButton = view.findViewById(R.id.blue);
        boldButton = view.findViewById(R.id.bold);
        italicButton = view.findViewById(R.id.italic);
        underlineButton = view.findViewById(R.id.underline);
        clearButton = view.findViewById(R.id.clear);
        displayButton = view.findViewById(R.id.display);
        nameField = view.findViewById(R.id.name);
        messageField = view.findViewById(R.id.message);
        labelText = view.findViewById(R.id.label);

        displayButton.setOnClickListener(v -> {
            String name = nameField.getText().toString();
            String message = messageField.getText().toString();

            SpannableString label = new SpannableString(name + " , " + message);
            if(redButton.isChecked()){
                label.setSpan(new ForegroundColorSpan(Color.RED), 0, label.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            } else if(greenButton.isChecked()){
                label.setSpan(new ForegroundColorSpan(Color.GREEN), 0, label.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            } else if(blueButton.isChecked()){
                label.setSpan(new ForegroundColorSpan(Color.BLUE), 0, label.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            if(boldButton.isChecked()){
                label.setSpan(new StyleSpan(Typeface.BOLD), 0, label.length(), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            if(italicButton.isChecked()){
                label.setSpan(new StyleSpan(Typeface.ITALIC), 0, label.length(), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            if(underlineButton.isChecked()){
                label.setSpan(new UnderlineSpan(), 0, label.length(), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            labelText.setText(label);
        });

        clearButton.setOnClickListener((v)->{
            labelText.setText("");
            nameField.setText("");
            messageField.setText("");
            boldButton.setChecked(false);
            italicButton.setChecked(false);
            underlineButton.setChecked(false);
            redButton.setChecked(false);
            greenButton.setChecked(false);
            blueButton.setChecked(false);
        });
    }
}
