package com.example.myapplication;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.ViewHolder> {
    List<Job> list;
    public JobAdapter(List<Job> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.item_job,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Job job = list.get(position);
        Location location = job.getLocation();
        Log.d("testing:", location.getLatitude() + " " + location.getLongitude());
        holder.tvTitle.setText(job.getJobTitle());
        holder.tvDesc.setText(job.getDescription());
        holder.tvLocation.setText("location:"+job.getLocation().getLatitude() +"-"+job.getLocation().getLongitude());
        holder.tvJobCategory.setText(job.getCategory());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener!=null){
                    onItemClickListener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvTitle;
        TextView tvDesc;
        TextView tvLocation;
        TextView tvJobCategory;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDesc = itemView.findViewById(R.id.tvDesc);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            tvJobCategory = itemView.findViewById(R.id.jobCategoryTV);
        }
    }
    public interface OnItemClickListener{
        void onItemClick(int postion);
    }
    OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
