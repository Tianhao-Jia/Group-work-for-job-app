package com.example.myapplication;

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

public class viewReviewAdapter extends FirebaseRecyclerAdapter<Review,viewReviewAdapter.reviewsViewholder> {

    @Override
    protected void onBindViewHolder(@NonNull reviewsViewholder holder, int position, @NonNull Review model){
        holder.review_person_name.setText(model.getName());
        holder.review_person_email.setText(model.getEmail());
        holder.review_person_rating.setText(model.getReview());
    }

    @NonNull
    @Override
    public reviewsViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from((parent.getContext())).inflate(R.layout.review_person_info, parent, false);
        return new viewReviewAdapter.reviewsViewholder(view);
    }

    public viewReviewAdapter(@NonNull FirebaseRecyclerOptions<Review> options){
        super(options);
    }


    class reviewsViewholder extends RecyclerView.ViewHolder {
        TextView review_person_name;
        TextView review_person_email;
        TextView review_person_rating;
        Button review_person_btn;

        public reviewsViewholder(@NonNull View itemView){
            super(itemView);
            review_person_btn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    Log.d("demo",review_person_name.getText().toString());
                }
            });
        }
    }

}
