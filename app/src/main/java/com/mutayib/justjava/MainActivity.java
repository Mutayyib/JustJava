package com.mutayib.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private int numberOfCoffees;

    public int getNumberOfCoffees() {
        return numberOfCoffees;
    }

    public void setNumberOfCoffees(int numberOfCoffees) {
        this.numberOfCoffees = numberOfCoffees;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // This method is called when the order button is clicked.
    public void submitOrder(View view) {

        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        displayQuantity();
        displayMessage(hasWhippedCream, hasChocolate);
    }

    // This method is called when the - button is clicked.
    public void decrement(View view) {
        if (getNumberOfCoffees() > 0) {
            setNumberOfCoffees(getNumberOfCoffees() - 1);
        } else {
            return;
        }
        displayQuantity();
    }

    // This method is called when the - button is clicked.
    public void increment(View view) {
        setNumberOfCoffees(getNumberOfCoffees() + 1);
        displayQuantity();
    }

    // This method displays the given quantity value on the screen.
    private void displayQuantity() {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText(String.valueOf(getNumberOfCoffees()));
    }

    private String createOrderSummary(String name, boolean whippedCream, boolean chocolate) {
        String orderSummary = "Name: " + name;
        orderSummary += "\nQuantity: " + getNumberOfCoffees();
        if (whippedCream) {
            orderSummary += "\nAdd Whipped Cream";
        }
        if (chocolate) {
            orderSummary += "\nAdd Chocolate";
        }
        orderSummary += "\nTotal: Rs: " + calculatePrice(whippedCream, chocolate);
        orderSummary += "\nThank You!!!!";

        return orderSummary;
    }

    // This method displays Order Summary.
    private void displayMessage(boolean addWhippedCream, boolean addChocolate) {
        EditText nameEditText = (EditText) findViewById(R.id.enter_name);
        String name = nameEditText.getText().toString();


        TextView summaryTextView = (TextView) findViewById(R.id.summary_text_view);
        summaryTextView.setText(createOrderSummary(name, addWhippedCream, addChocolate));

        String[] mailto = {"mutayyibali@gmail.com"} ;
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, mailto);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java Order by" + name);
        intent.putExtra(Intent.EXTRA_TEXT, createOrderSummary(name, addWhippedCream, addChocolate));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private int calculatePrice(boolean whippedCream, boolean chocolate){

        int bill = getNumberOfCoffees() * 70;
        if (whippedCream)
            bill += getNumberOfCoffees() * 25;
        if (chocolate)
            bill += getNumberOfCoffees() * 35;

        return  bill;
    }

}