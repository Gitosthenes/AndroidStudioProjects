package com.example.android.justjava;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int quantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void increment(View view) {
        if (quantity < 100) {
            quantity += 1;
            displayQuantity();
        }
    }

    public void decrement(View view) {
        if (quantity > 0) {
            quantity -= 1;
            displayQuantity();
        }
    }

    public void submitOrder(View view) {
        if (quantity == 0) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.zero_quantity_toast_message),
                    Toast.LENGTH_SHORT).show();
        } else {
            sendIntent();
        }
    }

    private void displayQuantity() {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        String quantityToString = Integer.toString(quantity);
        quantityTextView.setText(quantityToString);
    }

    private String getOrderSummary() {
        EditText nameEditText = findViewById(R.id.name_text_field);
        CheckBox whippedCreamCheckBox = findViewById(R.id.whipped_cream_check_box);
        CheckBox chocolateCheckBox = findViewById(R.id.chocolate_check_box);

        int basePrice = 5;
        if (whippedCreamCheckBox.isChecked()) {
            basePrice += 1;
        }
        if (chocolateCheckBox.isChecked()) {
            basePrice += 2;
        }

        String name = nameEditText.getText().toString();
        String price ="$" + (quantity * basePrice);
        boolean addWhippedCream = whippedCreamCheckBox.isChecked();
        boolean addChocolate = chocolateCheckBox.isChecked();

        return getString(R.string.summary_name, name) + "\n" +
               getString(R.string.summary_whipped_cream, addWhippedCream) + "\n" +
               getString(R.string.summary_chocolate, addChocolate) + "\n" +
               getString(R.string.summary_quantity, quantity) + "\n" +
               getString(R.string.summary_total, price) + "\n" +
               getString(R.string.thank_you);
    }

    private String getSubjectLine() {
        EditText nameEditText = findViewById(R.id.name_text_field);
        String name = nameEditText.getText().toString();
        return getString(R.string.email_subject, name);
    }

    private void sendIntent() {
        String subject = getSubjectLine();
        String message = getOrderSummary();
        Intent intent = new Intent(Intent.ACTION_SEND);

        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);

        startActivity(Intent.createChooser(intent, getString(R.string.intent_email) + "..."));
    }
}