package com.wordpress.sreeharilive.foodapp.activity;

import android.app.ProgressDialog;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wordpress.sreeharilive.foodapp.Cart;
import com.wordpress.sreeharilive.foodapp.R;
import com.wordpress.sreeharilive.foodapp.model.Order;

public class CheckoutActivity extends AppCompatActivity {

    Spinner paymentsModeSpinner, localitySpinner;
    EditText addressET;
    TextInputLayout addressInputLayout;

    String[] modeOfPayments = {
            "Cash on Delivery",
            "Online Payment"
    };

    String localities[];

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);


        paymentsModeSpinner = (Spinner) findViewById(R.id.paymentsSpinner);
        localitySpinner = (Spinner) findViewById(R.id.localitiesSpinner);

        addressET = (EditText) findViewById(R.id.addressET);
        addressInputLayout = (TextInputLayout) findViewById(R.id.addressInputLayout);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        FirebaseDatabase.getInstance().getReference().child("loc")
                .addValueEventListener(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                int i = 0;
                                localities = new String[(int) dataSnapshot.getChildrenCount()];
                                for (DataSnapshot loc : dataSnapshot.getChildren()){
                                    localities[i++] = loc.child("l").getValue().toString();
                                }
                                progressDialog.dismiss();

                                ArrayAdapter<String> localitiesAdapter = new ArrayAdapter<>(CheckoutActivity.this, android.R.layout.simple_spinner_item, localities);
                                localitiesAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
                                localitySpinner.setAdapter(localitiesAdapter);

                                ArrayAdapter<String> paymentsAdapter = new ArrayAdapter<>(CheckoutActivity.this, android.R.layout.simple_spinner_item, modeOfPayments);
                                paymentsAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
                                paymentsModeSpinner.setAdapter(paymentsAdapter);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        }
                );



    }

    public void finishCheckout(View view) {
        String address = addressET.getText().toString();
        if (address.isEmpty()){
            addressInputLayout.setError("Required Field");
            return;
        }

        String modeOfPayment = modeOfPayments[paymentsModeSpinner.getSelectedItemPosition()];

        progressDialog.show();

        new Order.OrderBuilder()
                .setLocality(localities[localitySpinner.getSelectedItemPosition()])
                .setAddress(address)
                .setCart(Cart.getInstance())
                .setModeOfPayment(modeOfPayment)
                .setUserID(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setOrderCompleteListener(new Order.OnOrderCompleteListener() {
                    @Override
                    public void onComplete() {
                        Toast.makeText(CheckoutActivity.this, "Placed Your Order Successfully", Toast.LENGTH_SHORT).show();
                        progressDialog.cancel();
                        Cart.getInstance().clearCart();
                        finish();
                    }

                    @Override
                    public void onError(Order.OrderException e) {
                        Toast.makeText(CheckoutActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
                .build()
                .place();

    }
}
