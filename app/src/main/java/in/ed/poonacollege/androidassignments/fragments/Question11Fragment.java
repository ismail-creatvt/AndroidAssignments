package in.ed.poonacollege.androidassignments.fragments;


import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import in.ed.poonacollege.androidassignments.R;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class Question11Fragment extends Fragment implements TextWatcher {

    private String[] EXTERNAL_STORAGE_PERMISSION = new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE , Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private boolean isPermissionGranted;

    private ArrayList<Student> students = new ArrayList<>();
    private ArrayList<Student> filteredStudents = new ArrayList<>();
    private RecyclerView studentsList;
    private FloatingActionButton addStudent;
    private String[] classArray = new String[]{ "M.Sc(C.S)", "B.Sc(C.S)", "MCA", "BCA", "MBA", "BBA", "MA", "BA"};
    private File file;
    private EditText searchField;

    public Question11Fragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_question11, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        studentsList = view.findViewById(R.id.studentsList);
        addStudent = view.findViewById(R.id.addStudent);
        searchField = view.findViewById(R.id.search_field);

        searchField.addTextChangedListener(this);
        studentsList.setAdapter(new StudentListAdapter());
        studentsList.setLayoutManager(new LinearLayoutManager(getContext()));

        addStudent.setOnClickListener((v)->{
            searchField.setText("");
            showAddUserDialog();
        });

        if(!isReadWritePermissionGranted()){
            ActivityCompat.requestPermissions(getActivity(), EXTERNAL_STORAGE_PERMISSION, REQUEST_EXTERNAL_STORAGE);
        } else{
            isPermissionGranted = true;
            createNewFile();
            studentsList.getAdapter().notifyDataSetChanged();
        }
    }

    private void createNewFile() {
        file = new File(getActivity().getExternalFilesDir("userInfo"),"user_info.csv");

        try {
            if(!file.exists()) file.createNewFile();
        } catch (Exception ignored){}
        if(file.exists() && file.canRead()){
            getFileData();
            filteredStudents.addAll(students);
        }
    }

    private void getFileData() {
        students.clear();
        try(FileInputStream is = new FileInputStream(file)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader
                    (is));
            try {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] RowData = line.split(",");
                    String name = RowData[0];
                    String classText = RowData[1];
                    int age = Integer.valueOf(RowData[2]);
                    students.add(new Student(name, classText, age));
                }
            } catch (IOException ex) {
                // handle exception
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    // handle exception
                }
            }
        }catch (Exception ignored){}
    }

    private void showAddUserDialog() {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.add_student_dialog);
        dialog.getWindow().setLayout(MATCH_PARENT, MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        EditText nameField = dialog.findViewById(R.id.name_field);
        Spinner classSpinner = dialog.findViewById(R.id.class_spinner);
        NumberPicker agePicker = dialog.findViewById(R.id.age_picker);
        Button addStudentButton = dialog.findViewById(R.id.add_student_button);

        ArrayAdapter<String> classAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, classArray);
        classSpinner.setAdapter(classAdapter);
        classAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        agePicker.setMinValue(15);
        agePicker.setMaxValue(35);

        addStudentButton.setOnClickListener(v -> {
            String name = nameField.getEditableText().toString();
            String classText = classSpinner.getSelectedItem().toString();
            int age = agePicker.getValue();

            Student student = new Student(name, classText, age);
            students.add(student);
            filteredStudents.add(student);

            studentsList.getAdapter().notifyDataSetChanged();

            if(isPermissionGranted){
                if(file.exists() && file.canWrite()){
                    try(FileOutputStream out = new FileOutputStream(file, true)){
                        OutputStreamWriter writer = new OutputStreamWriter(out);
                        writer.append(student.name)
                                .append(",")
                                .append(student.className)
                                .append(",")
                                .append(String.valueOf(student.age))
                                .append("\n");
                        writer.close();
                    }catch (Exception ignored){}
                }
            }
            dialog.dismiss();
        });

        dialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        isPermissionGranted = true;

        for(int i=0;i<permissions.length;i++){
            if(grantResults[i] == PackageManager.PERMISSION_DENIED){
                isPermissionGranted = false;
                break;
            }
        }

        if(!isPermissionGranted){
            showToast("Permission Denied! The students info won't be saved in a file");
        } else{
            createNewFile();
        }
    }

    private boolean isReadWritePermissionGranted() {
        if(getContext() == null) return false;
        return ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String searchString = searchField.getEditableText().toString().toLowerCase();

        filteredStudents.clear();

        for(Student stud:students){
            if(stud.name.toLowerCase().contains(searchString) || stud.className.toLowerCase().contains(searchString)){
                filteredStudents.add(stud);
            }
        }

        studentsList.getAdapter().notifyDataSetChanged();
    }

    class StudentListAdapter extends RecyclerView.Adapter<StudentListAdapter.StudentViewHolder>{

        @NonNull
        @Override
        public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new StudentViewHolder(getLayoutInflater().inflate(R.layout.student_info_item, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
            Student student = filteredStudents.get(position);
            holder.nameText.setText(student.name);
            holder.classText.setText(student.className);
            holder.ageText.setText(String.valueOf(student.age));
        }

        @Override
        public int getItemCount() {
            return filteredStudents.size();
        }

        private class StudentViewHolder extends RecyclerView.ViewHolder{

            TextView nameText, classText, ageText;

            public StudentViewHolder(@NonNull View itemView) {
                super(itemView);

                nameText =itemView.findViewById(R.id.name_text);
                classText =itemView.findViewById(R.id.class_text);
                ageText =itemView.findViewById(R.id.age_text);
            }
        }

    }

    private class Student {
        String name;
        String className;
        int age;

        public Student(String name, String className, int age) {
            this.name = name;
            this.className = className;
            this.age = age;
        }
    }
}
