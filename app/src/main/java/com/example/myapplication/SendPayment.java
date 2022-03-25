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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.paypal.android.sdk.t;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SendPayment extends AppCompatActivity implements PayAdapter.IJobListener{
    public static final String clientKey = "AUvAZuJ0IvHFmyIeOdDhsVPmjFYiHqBwglRNzUBAdmV95xvNZk5DYumlNGpgAesKfoBF0Baf2FFB1z45";
    public static final int PAYPAL_REQUEST_CODE = 123;

    private FirebaseDatabase firebaseDB = FirebaseUtils.connectFirebase();
    private DatabaseReference firebaseDBRefJobs = firebaseDB.getReference(FirebaseUtils.JOBS_COLLECTION);
    private DatabaseReference firebaseDBRefOffers = firebaseDB.getReference(FirebaseUtils.OFFERS_COLLECTION);


    private RecyclerView payRecyclerView;
    private PayAdapter  payAdapter;
    private ArrayList<Job> jobs = new ArrayList<>();
    private ArrayList<Application> applications = new ArrayList<>();
    private ArrayList<String> appKeys = new ArrayList<>();

    // Paypal Configuration Object
    private static PayPalConfiguration config = new PayPalConfiguration()
            // Start with mock environment.  When ready,
            // switch to sandbox (ENVIRONMENT_SANDBOX)
            // or live (ENVIRONMENT_PRODUCTION)
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            // on below line we are passing a client id.
            .clientId(clientKey);


    private TextView paymentTV;
    private Button refreshBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Session.startSession(this);
        Session.login("ted@dal.ca", "123", "Employer");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_payment);
        paymentTV = findViewById(R.id.paymentTV);
        payRecyclerView = findViewById(R.id.paymentRecycler);
        refreshBtn = findViewById(R.id.payRefreshBtn);

        initRecyclerView();


        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPaymentPendingApplications("123");
            }
        });

    }

    private void initRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        payRecyclerView.setLayoutManager(linearLayoutManager);
        payAdapter = new PayAdapter(applications, appKeys, this);
        payRecyclerView.setAdapter(payAdapter);
    }

    private void getPaymentPendingApplications(String employerID) {

        final Query nameQuery = firebaseDB.getReference("applications").child(employerID);

        nameQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                applications.clear();
                appKeys.clear();
                Log.d("childCount", Long.toString(snapshot.getChildrenCount()));
                if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                    Application app;
                    for (DataSnapshot currentSnapShot : snapshot.getChildren()) {
                        app = currentSnapShot.getValue(Application.class);
                        Log.d("childCount", app.getEmployerEmail());
                        if (app != null && !app.isPaid() &&app.getAccepted()) {
                            Log.d("childCount", "Added");
                            applications.add(app);
                            appKeys.add(currentSnapShot.getKey());
                        }
                    }
                }
                payAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("SearchActivity", error.getMessage());
            }
        });
    }


    @Override
    public void onPayClick(int position) {
        Application app = applications.get(position);

        firebaseDBRefJobs.child(app.getJobID()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot != null) {
                    Job job = dataSnapshot.getValue(Job.class);
                    Log.d("job fetch", "onDataChange: " + job.getCompensation());
                    double compensation = job.getCompensation();
                    getPayment(compensation);
                    app.setPaid(true);
                    firebaseDB.getReference("applications").child(Session.getUserID())
                            .child(appKeys.get(position))
                            .setValue(app);
                    getPaymentPendingApplications(Session.getUserID());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onCancelClick(int position) {
        Application app = applications.get(position);
        app.setAccepted(false);
        firebaseDB.getReference("applications").child(Session.getUserID())
                .child(appKeys.get(position)).setValue(app);
        getPaymentPendingApplications(Session.getUserID());
    }


    private void getPayment(double amount) {
        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        PaymentRecord record = new PaymentRecord(Session.getEmail(), "emp@dal.ca",
                amount ,date);

        storePayment(record, firebaseDB);

//        // Creating a paypal payment on below line.
//        PayPalPayment payment = new PayPalPayment(new BigDecimal(amount), "CAD", title,
//                PayPalPayment.PAYMENT_INTENT_SALE);
//
//        // Creating Paypal Payment activity intent
//        Intent intent = new Intent(this, PaymentActivity.class);
//
//        //putting the paypal configuration to the intent
//        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
//
//
//        // Putting paypal payment to the intent
//        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
//
//
//
//
//        // Starting the intent activity for result
//        // the request code will be used on the method onActivityResult
//        startActivityForResult(intent, PAYPAL_REQUEST_CODE);


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





    protected boolean storePayment(PaymentRecord payment, FirebaseDatabase db) {
        db.getReference(FirebaseUtils.PAYMENT_COLLECTION).push().setValue(payment);
        return true;
    }

}
