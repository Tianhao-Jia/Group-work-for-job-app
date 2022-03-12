package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


/**
 * based on code from csci3130 lab on march 2nd that Dhrumil presented.
 */
public class ViewApplicationAdapter extends FirebaseRecyclerAdapter<Application, ViewApplicationAdapter.ApplicationViewHolder> {

    private static ArrayList<Application> applicationArrayList = new ArrayList<>(10);
    private static ArrayList<ApplicationViewHolder> holderArrayList = new ArrayList<>(10);
    private ApplicationViewHolder holder;
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ViewApplicationAdapter(@NonNull FirebaseRecyclerOptions<Application> options) {
        super(options);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewApplicationAdapter.ApplicationViewHolder holder, int position, @NonNull Application application) {
        holder.applicantEmail.setText("Email: " + application.getEmployeeEmail());
        holder.accept.setVisibility(View.VISIBLE);
        holder.ignore.setVisibility(View.VISIBLE);

        this.holder = holder;
        holderArrayList.add(holder);
        applicationArrayList.add(application);
        //if I want to have the buttons do something this is where they need to be implemented

    }

    public void onBindViewHolder(@NonNull ViewApplicationAdapter.ApplicationViewHolder holder, boolean state) {

        //make everything go away
        if (state) {
            holder.applicantEmail.setText("");
            holder.applicantName.setText("");
        }

        this.holder = holder;
        //if I want to have the buttons do something this is where they need to be implemented

    }

    @NonNull
    @Override
    public ApplicationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_application, parent, false);

        return new ApplicationViewHolder(view);
    }


    public class ApplicationViewHolder extends RecyclerView.ViewHolder {

        private Context context;
        private TextView applicantName;
        private TextView applicantEmail;
        private Button accept, ignore;

        public ApplicationViewHolder(@NonNull View itemView) {
            super(itemView);
            applicantEmail = itemView.findViewById(R.id.applicantName);
            applicantName = itemView.findViewById(R.id.jobApplicationEmail);
            ignore = itemView.findViewById(R.id.ignoreApplication);
            accept = itemView.findViewById(R.id.acceptApplication);
            context = itemView.getContext();
        }

    }

    public ApplicationViewHolder getHolder() {
        return holder;
    }

    public void setHolder(ApplicationViewHolder holder) {
        this.holder = holder;
    }

    public static ArrayList<ApplicationViewHolder> getHolderArrayList() {
        return holderArrayList;
    }

    public static void setHolderArrayList(ArrayList<ApplicationViewHolder> holderArrayList) {
        ViewApplicationAdapter.holderArrayList = holderArrayList;
    }

    public static ArrayList<Application> getApplicationArrayList() {
        return applicationArrayList;
    }

    public static void setApplicationArrayList(ArrayList<Application> applicationArrayList) {
        ViewApplicationAdapter.applicationArrayList = applicationArrayList;
    }
}