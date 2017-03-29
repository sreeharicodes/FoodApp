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

    String[] localities = {
            "Kalady",
            "Kanjoor",
            "Aluva",
            "Angamaly",
            "Perumbavoor",
            "Malayatoor"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        paymentsModeSpinner = (Spinner) findViewById(R.id.paymentsSpinner);
        localitySpinner = (Spinner) findViewById(R.id.localitiesSpinner);

        addressET = (EditText) findViewById(R.id.addressET);
        addressInputLayout = (TextInputLayout) findViewById(R.id.addressInputLayout);

        ArrayAdapter<String> localitiesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, localities);
        localitiesAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        localitySpinner.setAdapter(localitiesAdapter);

        ArrayAdapter<String> paymentsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, modeOfPayments);
        paymentsAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        paymentsModeSpinner.setAdapter(paymentsAdapter);

    }

    public void finishCheckout(View view) {
        String address = addressET.getText().toString();
        if (address.isEmpty()){
            addressInputLayout.setError("Required Field");
            return;
        }

        String modeOfPayment = modeOfPayments[paymentsModeSpinner.getSelectedItemPosition()];

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        Order order = new Order.OrderBuilder(this)
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
                        finish();
                    }

                    @Override
                    public void onError() {
                        Toast.makeText(CheckoutActivity.this, "Sorry Something Happened. Try again later", Toast.LENGTH_SHORT).show();
                        progressDialog.cancel();
                    }
                })
                .build();
        order.place();

    }
}
