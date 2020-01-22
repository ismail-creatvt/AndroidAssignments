package in.ed.poonacollege.androidassignments.fragments;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import in.ed.poonacollege.androidassignments.R;
import in.ed.poonacollege.androidassignments.db.Database;
import in.ed.poonacollege.androidassignments.model.Customer;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class Question13Fragment extends Fragment {

    private RecyclerView customerList;
    private ArrayList<Customer> customers;

    public Question13Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_question13, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        customerList = view.findViewById(R.id.customer_list);
        FloatingActionButton addCustomer = view.findViewById(R.id.add_customer);

        Database database = new Database(getContext());
        customers = database.getAllCustomers();

        customerList.setAdapter(new CustomerListAdapter(customers));
        customerList.setLayoutManager(new LinearLayoutManager(getContext()));

        addCustomer.setOnClickListener(v -> showAddCustomerDialog());

        ItemTouchHelper customerTouchHelper = new ItemTouchHelper(new CustomerItemTouchCallback());
        customerTouchHelper.attachToRecyclerView(customerList);
    }

    class CustomerItemTouchCallback extends ItemTouchHelper.SimpleCallback{

        public CustomerItemTouchCallback() {
            super(0, ItemTouchHelper.RIGHT);
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            Database db = new Database(getContext());
            db.deleteCustomer(customers.get(position).getId());
            customers.remove(position);
            if(customerList.getAdapter() != null){
                customerList.getAdapter().notifyItemRemoved(position);
            }
        }
    }

    private void showAddCustomerDialog() {
        if(getContext() == null) return;

        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.add_customer_dialog);
        if(dialog.getWindow() != null){
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.getWindow().setLayout(MATCH_PARENT, MATCH_PARENT);
        }

        EditText nameField = dialog.findViewById(R.id.name_field);
        EditText addressField = dialog.findViewById(R.id.address_field);
        EditText phoneField = dialog.findViewById(R.id.phone_field);

        Button doneButton = dialog.findViewById(R.id.done_button);
        Button cancelButton = dialog.findViewById(R.id.cancel_button);

        cancelButton.setOnClickListener(v-> dialog.dismiss());
        doneButton.setOnClickListener(v->{
            String name = nameField.getText().toString();
            String address = addressField.getText().toString();
            String phone = phoneField.getText().toString();

            Database database = new Database(getContext());
            Customer customer = new Customer(name, address, phone);
            customer.setId((int)System.currentTimeMillis()/1000);
            database.insertCustomer(customer);

            customers.add(customer);

            if(customerList.getAdapter() != null){
                customerList.getAdapter().notifyItemInserted(customers.size()-1);
            }
            dialog.dismiss();
        });
        dialog.show();
    }
}
