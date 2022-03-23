package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;

public class SendPayment extends AppCompatActivity implements PayAdapter.IJobListener{
    public static final String clientKey = "AUwzd2smvn2XJ8PgS7ZIenq1tCrNNhW8uS8F5D9WQS2lhpnNNYfKhayF95w-c_ZEp_pP_oCgnpayyq5J";
    public static final int PAYPAL_REQUEST_CODE = 123;

    private FirebaseDatabase firebaseDB;
    private DatabaseReference firebaseDBRef;


    private RecyclerView payRecyclerView;
    private PayAdapter  payAdapter;
    private ArrayList<Job> jobs = new ArrayList<>();

    // Paypal Configuration Object
    private static PayPalConfiguration config = new PayPalConfiguration()
            // Start with mock environment.  When ready,
            // switch to sandbox (ENVIRONMENT_SANDBOX)
            // or live (ENVIRONMENT_PRODUCTION)
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            // on below line we are passing a client id.
            .clientId(clientKey);


    private EditText amountEdt;

    private TextView paymentTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_payment);
        connectFirebase();
        firebaseDB = FirebaseUtils.connectFirebase();
        firebaseDBRef = firebaseDB.getReference(FirebaseUtils.JOBS_COLLECTION);


        payRecyclerView = findViewById(R.id.paymentRecycler);
        payRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //citation based on code from Dhrumils lab presentation on march 2nd in this course csci3130
        FirebaseRecyclerOptions<Job> options = new FirebaseRecyclerOptions.Builder<Job>()
                .setQuery(firebaseDBRef, Job.class)
                .build();

        payAdapter = new PayAdapter(options);
        payAdapter.setInterface(this);
        payRecyclerView.setAdapter(payAdapter);



        firebaseDBRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Log.e("Count " ,""+snapshot.getChildrenCount());
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    Job post = postSnapshot.getValue(Job.class);
                    Log.e("Get Data", post.getDescription());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });





//        payRecyclerView = findViewById(R.id.paymentRecycler);
//
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        payRecyclerView.setLayoutManager(linearLayoutManager);
//
//
//
//        //citation based on code from Dhrumils lab presentation on march 2nd in this course csci3130
//        FirebaseRecyclerOptions<Job> options
//                = new FirebaseRecyclerOptions.Builder<Job>()
//                .setQuery(firebaseDBRef, Job.class)
//                .build();
//
//        payAdapter = new PayAdapter(options);
//        payAdapter.setInterface(this);
//        payRecyclerView.setAdapter(payAdapter);
//        payAdapter.startListening();

        // on below line adding click listener to our make payment button.
//        makePaymentBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // calling a method to get payment.
//                getPayment();
//            }
//        });

    }

//    private void initRecyclerView(){
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        payRecyclerView.setLayoutManager(linearLayoutManager);
//
//        //citation based on code from Dhrumils lab presentation on march 2nd in this course csci3130
//        FirebaseRecyclerOptions<Job> options = new FirebaseRecyclerOptions.Builder<Job>()
//                .setQuery(FirebaseDatabase.getInstance(FirebaseUtils.FIREBASE_URL)
//                        .getReference().child("jobs"), Job.class)
//                .build();
//
//        payAdapter = new PayAdapter(options);
//        payAdapter.setInterface(this);
//        payRecyclerView.setAdapter(payAdapter);
//    }

    private void insertFakeJobs() {
        for(int i = 0; i < 10; i++) {
            Job x = new Job("email@dal.ca", Integer.toString(i), "", new Location(0, 0), Integer.toString(i));
            x.setCompensation(100);
            jobs.add(x);
        }
    }

    @Override
    public void onPayClick(int position) {
        Job x = jobs.get(position);
        getPayment(Double.toString(x.getCompensation()));
        Log.d("TEST", "onPayClick: " + x.getHash());
    }

    @Override
    public void onCancelClick(int position) {
        Job x = jobs.get(position);
        jobs.remove(position);
        Log.d("TEST", "onCancelClick: " + x.getHash());
    }

    private void getPayment(String amount) {

        // Creating a paypal payment on below line.
        PayPalPayment payment = new PayPalPayment(new BigDecimal(String.valueOf(amount)), "USD", "Course Fees",
                PayPalPayment.PAYMENT_INTENT_SALE);

        // Creating Paypal Payment activity intent
        Intent intent = new Intent(this, PaymentActivity.class);

        //putting the paypal configuration to the intent
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

        // Putting paypal payment to the intent
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);

        // Starting the intent activity for result
        // the request code will be used on the method onActivityResult
        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // If the result is from paypal
        if (requestCode == PAYPAL_REQUEST_CODE) {

            // If the result is OK i.e. user has not canceled the payment
            if (resultCode == Activity.RESULT_OK) {

                // Getting the payment confirmation
                PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

                // if confirmation is not null
                if (confirm != null) {
                    try {
                        // Getting the payment details
                        String paymentDetails = confirm.toJSONObject().toString(4);
                        // on below line we are extracting json response and displaying it in a text view.
                        JSONObject payObj = new JSONObject(paymentDetails);
                        String payID = payObj.getJSONObject("response").getString("id");
                        String state = payObj.getJSONObject("response").getString("state");
                        paymentTV.setText("Payment " + state + "\n with payment id is " + payID);
                    } catch (JSONException e) {
                        // handling json exception on below line
                        Log.e("Error", "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                // on below line we are checking the payment status.
                Log.i("paymentExample", "The user canceled.");
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                // on below line when the invalid paypal config is submitted.
                Log.i("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            }
        }
    }

    private void connectFirebase(){
        firebaseDB = FirebaseUtils.connectFirebase();
        firebaseDBRef = firebaseDB.getReference(FirebaseUtils.JOBS_COLLECTION);
    }


    @Override
    protected void onStart() {
        super.onStart();
        payAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        payAdapter.startListening();
    }


}
