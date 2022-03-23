package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class PayAdapter extends RecyclerView.Adapter<PayAdapter.PayViewHolder> {
    ArrayList<Job> jobs = new ArrayList<>();
    private JobListener jobListener;

    public PayAdapter(ArrayList<Job> jobs, JobListener jobListener) {
        this.jobs = jobs;
        this.jobListener = jobListener;
    }

    @NonNull
    @Override
    public PayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_payment_row, parent, false);
        PayViewHolder payViewHolder = new PayViewHolder(view, jobListener);

        return payViewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull PayViewHolder holder, int position) {
        Job job = jobs.get(position);
        holder.jobTitle.setText(job.getJobTitle());
        holder.jobPrice.setText(Double.toString(job.getCompensation()));

        holder.payUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jobListener.onPayClick(holder.getAbsoluteAdapterPosition());
            }
        });

        holder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jobListener.onCancelClick(holder.getAbsoluteAdapterPosition());
            }
        });
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return this.jobs.size();
    }

    public static class PayViewHolder extends RecyclerView.ViewHolder {

        TextView jobTitle;
        TextView jobPrice;
        Button cancel;
        Button payUser;
        JobListener jobListener;

        public PayViewHolder(@NotNull View itemView, JobListener jobListener) {
            super(itemView);
            jobTitle = itemView.findViewById(R.id.payUserTitle);
            payUser = itemView.findViewById(R.id.payUserBtn);
            cancel = itemView.findViewById(R.id.payCancelBtn);
            jobPrice = itemView.findViewById(R.id.jobCompTV);

            this.jobListener = jobListener;
        }
    }

    //Implement this interface in client class, pass it when constructing RecyclerView
    public interface JobListener{
        void onPayClick(int position);
        void onCancelClick(int position);
    }

}
