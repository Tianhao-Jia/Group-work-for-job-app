package com.example.myapplication;

import android.os.IInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class PayAdapter extends RecyclerView.Adapter<PayAdapter.PayViewHolder> {
    private ArrayList<Application> apps = new ArrayList<>(10);
    private IJobListener jobListener;
    String TAG = "PayAdapter";



    public PayAdapter(ArrayList<Application> apps, IJobListener jobListener) {
        this.apps = apps;
        this.jobListener = jobListener;
    }

    public void setInterface(IJobListener jobListener) {
        this.jobListener = jobListener;
        Log.d(TAG, "setInterface: Adding interface");
    }


    public PayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_payment_row, parent, false);
        PayViewHolder payViewHolder = new PayAdapter.PayViewHolder(view, jobListener);

        return payViewHolder;
    }


    @Override
    public void onBindViewHolder(PayViewHolder holder, int position) {
        Application app = apps.get(position);

        holder.jobTitle.setText(app.getDescription());
        holder.jobPrice.setText("60");

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
        return this.apps.size();
    }

    public static class PayViewHolder extends RecyclerView.ViewHolder {

        TextView jobTitle;
        TextView jobPrice;
        Button cancel;
        Button payUser;
        IJobListener jobListener;

        public PayViewHolder(@NotNull View itemView, IJobListener jobListener) {
            super(itemView);
            Log.d("123", "PayViewHolder: Constructed view holder");
            jobTitle = itemView.findViewById(R.id.payUserTitle);
            payUser = itemView.findViewById(R.id.payUserBtn);
            cancel = itemView.findViewById(R.id.payCancelBtn);
            jobPrice = itemView.findViewById(R.id.jobCompTV);

            this.jobListener = jobListener;
        }
    }

    //Implement this interface in client class, pass it when constructing RecyclerView
    public interface IJobListener{
        void onPayClick(int position);
        void onCancelClick(int position);
    }



}
