package in.ed.poonacollege.androidassignments.fragments;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import in.ed.poonacollege.androidassignments.R;

public class Question24Fragment extends Fragment {

    private static final int REQUEST_ATTACHMENT = 2;
    private static final int SEND_MAIL = 4;
    private Uri fileUri = null;
    private EditText emailField, subjectField, messageField;

    public Question24Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_question24, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        emailField = view.findViewById(R.id.email);
        subjectField = view.findViewById(R.id.subject);
        messageField = view.findViewById(R.id.message);

        Button addAttachment = view.findViewById(R.id.add_attachment);
        Button sendEmail = view.findViewById(R.id.send_email);

        addAttachment.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_PICK);
            intent.setType("*/*");
            startActivityForResult(Intent.createChooser(intent, "Select attachment..."), REQUEST_ATTACHMENT);
        });

        sendEmail.setOnClickListener((v)->{
            String email = emailField.getText().toString();
            String subject = subjectField.getText().toString();
            String message = messageField.getText().toString();

            Intent sendEmailIntent = new Intent();
            sendEmailIntent.setType("plain/text");
            sendEmailIntent.setAction(Intent.ACTION_SEND);
            sendEmailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{ email });
            sendEmailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
            sendEmailIntent.putExtra(Intent.EXTRA_TEXT, message);

            if(fileUri != null){
                sendEmailIntent.putExtra(Intent.EXTRA_STREAM, fileUri);
            }
            startActivityForResult(Intent.createChooser(sendEmailIntent, "Select email application"), SEND_MAIL);
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_ATTACHMENT && resultCode == Activity.RESULT_OK && data != null){
             fileUri = data.getData();
             Toast.makeText(getContext(), "File is Attached", Toast.LENGTH_SHORT).show();
        } else if(requestCode == SEND_MAIL && resultCode == Activity.RESULT_OK){
            emailField.setText("");
            subjectField.setText("");
            messageField.setText("");
            fileUri = null;
        }
    }
}
