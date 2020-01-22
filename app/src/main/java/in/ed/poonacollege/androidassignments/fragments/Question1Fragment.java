package in.ed.poonacollege.androidassignments.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import in.ed.poonacollege.androidassignments.Utility;
import in.ed.poonacollege.androidassignments.R;

public class Question1Fragment extends Fragment implements View.OnClickListener {

    private EditText emailField;
    private EditText passwordField;

    public Question1Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_question1, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        emailField = view.findViewById(R.id.email);
        passwordField = view.findViewById(R.id.password);
        Button loginButton = view.findViewById(R.id.login_button);
        TextView registerText = view.findViewById(R.id.register_text);

        loginButton.setOnClickListener(this);
        registerText.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_button:
                onLoginClicked();
                break;
            case R.id.register_text:
                if(getActivity() != null){
                    NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                    navController.navigate(R.id.question_2);
                }
                break;
        }
    }

    private void onLoginClicked() {
        String email = emailField.getEditableText().toString();
        String password = passwordField.getEditableText().toString();

        if(!Utility.isValidEmail(email)){
            emailField.setError(getString(R.string.invalid_email));
            return;
        }

        if(!Utility.isPasswordValid(password)){
            passwordField.setError(getString(R.string.password_invalid));
            return;

        }
        Toast.makeText(getContext(), "Login Successful :)", Toast.LENGTH_SHORT).show();
    }
}
