package in.ed.poonacollege.androidassignments.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.ed.poonacollege.androidassignments.R;
import in.ed.poonacollege.androidassignments.model.Customer;

public class CustomerListAdapter extends RecyclerView.Adapter<CustomerListAdapter.CustomerViewHolder> {

    private ArrayList<Customer> mCustomers;

    public CustomerListAdapter(ArrayList<Customer> customers) {
        mCustomers = customers;
    }

    public void setCustomers(ArrayList<Customer> customers) {
        mCustomers = customers;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.customer_item, parent, false);
        return new CustomerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, int position) {
        Customer customer = mCustomers.get(position);
        holder.customerName.setText(customer.getCustomerName());
        holder.customerAddress.setText(customer.getCustomerAddress());
        holder.customerPhone.setText(customer.getCustomerPhone());
    }

    @Override
    public int getItemCount() {
        return mCustomers.size();
    }

    class CustomerViewHolder extends RecyclerView.ViewHolder{

        public TextView customerName, customerAddress, customerPhone;

        public CustomerViewHolder(@NonNull View itemView) {
            super(itemView);
            customerName = itemView.findViewById(R.id.customer_name);
            customerAddress = itemView.findViewById(R.id.customer_address);
            customerPhone = itemView.findViewById(R.id.customer_phone);
        }
    }
}
