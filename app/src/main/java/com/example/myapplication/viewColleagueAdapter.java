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

public class viewColleagueAdapter extends FirebaseRecyclerAdapter<Colleague,viewColleagueAdapter.colleaguesViewholder> {

    @Override
    protected void onBindViewHolder(@NonNull colleaguesViewholder holder, int position, @NonNull Colleague model){
        holder.review_person_name.setText(model.getName());
        holder.review_person_email.setText(model.getEmail());
    }

    @NonNull
    @Override
    public colleaguesViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from((parent.getContext())).inflate(R.layout.review_person_info, parent, false);
        return new viewColleagueAdapter.colleaguesViewholder(view);
    }

    public viewColleagueAdapter(@NonNull FirebaseRecyclerOptions<Colleague> options){
        super(options);
    }


    class colleaguesViewholder extends RecyclerView.ViewHolder {
        TextView review_person_name;
        TextView review_person_email;
        Button review_person_btn;

        public colleaguesViewholder(@NonNull View itemView){
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
