package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class JobEmployeeAdapter extends RecyclerView.Adapter<JobEmployeeAdapter.ViewHolder> {
    List<Employee> list;

    Object selectedItem;
    Context context;
    public JobEmployeeAdapter(List<Employee> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.users_employee_layout, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Employee employee = list.get(position);

        holder.usersEmployeeLayoutFirstName.setText("First Name: " + employee.getFirstName());
        holder.usersEmployeeLayoutLastName.setText("Last Name: " + employee.getLastName());
        holder.usersEmployeeLayoutRating.setText("Rating: " + employee.getRating() + "");
        holder.usersEmployeeLayoutEmail.setText("Email: " + employee.getEmail());

        //TODO implement this
        if (employee.getDistance() != Double.MAX_VALUE) {
            holder.usersEmployeeLayoutDistance.setText("Distance: " + employee.getDistance() + " km");
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView usersEmployeeLayoutFirstName;
        TextView usersEmployeeLayoutLastName;
        TextView usersEmployeeLayoutRating;
        TextView usersEmployeeLayoutEmail;
        TextView usersEmployeeLayoutDistance;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            usersEmployeeLayoutFirstName = itemView.findViewById(R.id.usersEmployeeLayoutFirstName);
            usersEmployeeLayoutLastName = itemView.findViewById(R.id.usersEmployeeLayoutLastName);
            usersEmployeeLayoutRating = itemView.findViewById(R.id.usersEmployeeLayoutRating);
            usersEmployeeLayoutEmail = itemView.findViewById(R.id.usersEmployeeLayoutEmail);
            usersEmployeeLayoutDistance = itemView.findViewById(R.id.usersEmployeeLayoutDistance);


        }
    }
}

