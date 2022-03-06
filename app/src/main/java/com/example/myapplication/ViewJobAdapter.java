package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;


/**
 * based on code from csci3130 lab on march 2nd that Dhrumil presented.
 */
public class ViewJobAdapter extends FirebaseRecyclerAdapter<Job, ViewJobAdapter.JobViewHolder> {



    private JobViewHolder holder;
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ViewJobAdapter(@NonNull FirebaseRecyclerOptions<Job> options) {
        super(options);
    }

    @NonNull
    @Override
    public JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_layout, parent, false);

        view.findViewById(R.id.jobLayoutJobTitle);
        view.findViewById(R.id.jobLayoutEmployerEmail);
        view.findViewById(R.id.jobLayoutDescription);
        view.findViewById(R.id.jobLayoutHourlyRate);
        view.findViewById(R.id.jobLayoutLatitude);
        view.findViewById(R.id.jobLayoutLongitude);

        view.findViewById(R.id.jobLayoutApply);
        view.findViewById(R.id.jobLayoutViewOnMap);
        return new JobViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JobViewHolder holder, int position, @NonNull Job job) {
        holder.jobLayoutDescription.setText(job.getDescription());
        holder.jobLayoutEmployerEmail.setText(job.getEmployerEmail());
        holder.jobLayoutJobTitle.setText(job.getJobTitle());
        holder.jobLayoutHourlyRate.setText(String.valueOf(job.getCompensation()));
        holder.jobLayoutLatitude.setText(String.valueOf(job.getLocation().getLatitude()));
        holder.jobLayoutLongitude.setText(String.valueOf(job.getLocation().getLongitude()));

        this.holder = holder;
        //if I want to have the buttons do something this is where they need to be implemented


    }

    //citation from lab on march 2nd
    public class JobViewHolder extends RecyclerView.ViewHolder {
        private TextView jobLayoutJobTitle;
        private TextView jobLayoutEmployerEmail;
        private final TextView jobLayoutDescription;
        private final TextView jobLayoutHourlyRate;
        private final TextView jobLayoutLatitude;
        private final TextView jobLayoutLongitude;
        private final Button jobLayoutApply;
        private final Button jobLayoutViewOnMap;
        private final Context context;

        public JobViewHolder(@NonNull View itemView) {
            super(itemView);

            jobLayoutJobTitle = itemView.findViewById(R.id.jobLayoutJobTitle);
            jobLayoutEmployerEmail = itemView.findViewById(R.id.jobLayoutEmployerEmail);
            jobLayoutDescription = itemView.findViewById(R.id.jobLayoutDescription);
            jobLayoutHourlyRate = itemView.findViewById(R.id.jobLayoutHourlyRate);
            jobLayoutLatitude = itemView.findViewById(R.id.jobLayoutLatitude);
            jobLayoutLongitude = itemView.findViewById(R.id.jobLayoutLongitude);

            jobLayoutApply = itemView.findViewById(R.id.jobLayoutApply);
            jobLayoutViewOnMap = itemView.findViewById(R.id.jobLayoutViewOnMap);

            context = itemView.getContext();

        }
    }

    public JobViewHolder getHolder() {
        return holder;
    }

    public void setHolder(JobViewHolder holder) {
        this.holder = holder;
    }
}

