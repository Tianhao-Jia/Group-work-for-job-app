package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class JobEmployerAdapter extends RecyclerView.Adapter<JobEmployerAdapter.ViewHolder> {
    List<Job> list;

    Context context;
    public JobEmployerAdapter(List<Job> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.job_employer_layout, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Job job = list.get(position);
        Location location = job.getLocation();
        Log.d("testing:", location.getLatitude() + " " + location.getLongitude());
        holder.jobTitle.setText(job.getJobTitle());
        holder.jobEmail.setText(job.getEmployerEmail());
        holder.jobDesc.setText(job.getDescription());
        holder.jobHourlyRate.setText(String.valueOf(job.getCompensation()));
        holder.jobLatitude.setText("latitude: " + location.getLatitude());
        holder.jobLongitude.setText("longitude: " + location.getLongitude());

        //TODO set up the buttons to do stuff and the drop down, its not done here but below in ViewHolder class
//        holder.jobSeeApplications.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(ViewApplications.class);
//                startActivity(intent);
//            }
//        });
        //holder.jobSuggestionFilter
        //holder.jobSuggestionButton
        //holder needs the rest of the spots filled in.
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView jobTitle;
        TextView jobEmail;
        TextView jobDesc;
        TextView jobHourlyRate;
        TextView jobLatitude;
        TextView jobLongitude;
        Button jobSeeApplications;
        Spinner jobSuggestionFilter;
        Button jobSuggestionButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            jobTitle = itemView.findViewById(R.id.jobEmployerLayoutJobTitle);
            jobEmail = itemView.findViewById(R.id.jobEmployerLayoutEmployerEmail);
            jobDesc = itemView.findViewById(R.id.jobEmployerLayoutDescription);
            jobHourlyRate = itemView.findViewById(R.id.jobEmployerLayoutHourlyRate);
            jobLatitude = itemView.findViewById(R.id.jobEmployerLayoutLatitude);
            jobLongitude = itemView.findViewById(R.id.jobEmployerLayoutLongitude);
            jobSeeApplications = itemView.findViewById(R.id.jobEmployerLayoutCurrentAppsButton);
            jobSuggestionFilter = itemView.findViewById(R.id.jobEmployerLayoutSpinner);
            jobSuggestionButton = itemView.findViewById(R.id.jobEmployerLayoutSuggestButton);

            jobSeeApplications.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    view.getContext().startActivity(new Intent(view.getContext(), ViewApplications.class));
                }
            });

        }
    }
}

