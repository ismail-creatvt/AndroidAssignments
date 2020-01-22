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

public class Question2Fragment extends Fragment implements View.OnClickListener {

    private EditText emailField;
    private EditText passwordField;
    private EditText nameField;

    public Question2Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_question2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        emailField = view.findViewById(R.id.email);
        nameField = view.findViewById(R.id.name);
        passwordField = view.findViewById(R.id.password);
        Button registerButton = view.findViewById(R.id.register_button);
        TextView signInText = view.findViewById(R.id.sign_in_text);

        registerButton.setOnClickListener(this);
        signInText.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_button:
                onRegisterClicked();
                break;
            case R.id.sign_in_text:
                if(getActivity() != null){
                    NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                    navController.popBackStack();
                }
                break;
        }
    }

    private void onRegisterClicked() {
        String name = nameField.getEditableText().toString();
        String email = emailField.getEditableText().toString();
        String password = passwordField.getEditableText().toString();

        if(!Utility.isValidName(name)){
            nameField.setError(getString(R.string.invalid_name));
            return;
        }

        if(!Utility.isValidEmail(email)){
            emailField.setError(getString(R.string.invalid_email));
            return;
        }

        if(!Utility.isPasswordValid(password)){
            passwordField.setError(getString(R.string.password_invalid));
            return;
        }

        Toast.makeText(getContext(), "Registration Successful :)", Toast.LENGTH_SHORT).show();
    }
}
