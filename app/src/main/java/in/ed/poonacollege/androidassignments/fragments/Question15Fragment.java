package in.ed.poonacollege.androidassignments.fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

import in.ed.poonacollege.androidassignments.R;
import in.ed.poonacollege.androidassignments.db.DatabaseEmployee;
import in.ed.poonacollege.androidassignments.model.Department;
import in.ed.poonacollege.androidassignments.model.Employee;

public class Question15Fragment extends Fragment {


    public Question15Fragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_question15, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Spinner deptSpinner = view.findViewById(R.id.departments_spinner);
        Spinner empSpinner = view.findViewById(R.id.employee_spinner);
        Button deleteEmpButton = view.findViewById(R.id.delete_employee);

        DatabaseEmployee db = new DatabaseEmployee(getContext());

        ArrayList<Department> departments = db.getAllDepartments();

        deptSpinner.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, departments));

        deptSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Department department  = departments.get(position);
                empSpinner.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, db.getAllEmployees(department.getNo())));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        deleteEmpButton.setOnClickListener(v -> {
            if(empSpinner.getSelectedItem() == null) return;
            Employee employee = (Employee) empSpinner.getSelectedItem();
            db.deleteEmployee(employee.getNo());
            empSpinner.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, db.getAllEmployees(employee.getDno())));
        });

    }
}
