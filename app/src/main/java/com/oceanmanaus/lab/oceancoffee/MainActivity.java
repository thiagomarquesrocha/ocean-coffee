package com.oceanmanaus.lab.oceancoffee;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void submitOrder(View view){

        EditText nameView = (EditText) findViewById(R.id.name);
        String name = nameView.getText().toString();

        Log.d("Debug", "Name : " + name);

        CheckBox whippedCreamView = (CheckBox) findViewById(R.id.whippedCream);
        boolean hasWhippedCream = whippedCreamView.isChecked();

        CheckBox chocolateView = (CheckBox) findViewById(R.id.chocolate);
        boolean hasChocolate = chocolateView.isChecked();

        Log.d("Debug", "Add hasWhippedCream? " + hasWhippedCream);
        Log.d("Debug", "Add hasChocolate? " + hasChocolate);

        int price = calculatePrice(quantity, 5, hasWhippedCream, hasChocolate);
        String message = createOrderSummary(quantity, price, hasWhippedCream, hasChocolate, name);

        Log.d("Debug", "Price : " + price);

        Log.i("Info", message);

        TextView summaryView = (TextView) findViewById(R.id.summaryOrder);
        summaryView.setText(message);


        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("malito:"));// Only email
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java " + name);
        intent.putExtra(Intent.EXTRA_TEXT, message);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }
    public void increment(View view){
        if(quantity >= 100){
            displayMessage("Só é permitido copos de 1-100");
            return;
        }
        quantity++;
        displayQuantity();
    }
    public void decrement(View view){
        if(quantity <= 1){
            displayMessage("Só é permitido copos de 1-100");
            return;
        }
        quantity--;
        displayQuantity();
    }

    private void displayMessage(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    private void displayPrice(){

    }

    private void displayQuantity(){
        TextView quantityView = (TextView) findViewById(R.id.quantity_text_view);
        quantityView.setText(quantity + "");
    }

    private String createOrderSummary(int quantity, int price, boolean hasWhippedCream, boolean hasChocolate, String name){
        String message = getString(R.string.name) + name;
        message += "\n" + getString(R.string.hasWhippedCream) + hasWhippedCream;
        message += "\n" + getString(R.string.hasChocolate) + hasChocolate;
        message += "\n" + getString(R.string.quantity) + quantity; // Message = message + ""
        message += "\n" + getString(R.string.price) + price;
        message += "\n" + getString(R.string.thank_you);

        return message;
    }

    private int calculatePrice(int quantity, int priceByCup, boolean hasWhippedCream, boolean hasChocolate){

        if(hasWhippedCream){
            priceByCup += 1; // priceByCup = priceByCup + 1
        }

        if(hasChocolate){
            priceByCup += 2;
        }

        return quantity * priceByCup;
    }
}
