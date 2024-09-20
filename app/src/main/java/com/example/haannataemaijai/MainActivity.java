package com.example.haannataemaijai;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText itemName, itemPrice;
    Button addItemButton, calculateButton;
    TableLayout itemsTable;
    TextView totalResult;

    double totalCost = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        itemName = findViewById(R.id.itemName);
        itemPrice = findViewById(R.id.itemPrice);
        addItemButton = findViewById(R.id.addItemButton);
        calculateButton = findViewById(R.id.calculateButton);
        itemsTable = findViewById(R.id.itemsTable);
        totalResult = findViewById(R.id.totalResult);

        // Add item button click listener
        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = itemName.getText().toString();
                String priceStr = itemPrice.getText().toString();

                if (!name.isEmpty() && !priceStr.isEmpty()) {
                    double price = Double.parseDouble(priceStr);

                    // Add a new row to the table
                    TableRow row = new TableRow(MainActivity.this);
                    TextView itemNameView = new TextView(MainActivity.this);
                    TextView itemPriceView = new TextView(MainActivity.this);

                    itemNameView.setText(name);
                    itemPriceView.setText(String.format("%.2f", price));

                    row.addView(itemNameView);
                    row.addView(itemPriceView);

                    itemsTable.addView(row);

                    // Add to total cost
                    totalCost += price;

                    // Clear input fields after adding
                    itemName.setText("");
                    itemPrice.setText("");

                } else {
                    Toast.makeText(MainActivity.this, "Please fill both fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Calculate button click listener
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totalResult.setText("Total cost: " + String.format("%.2f", totalCost));
            }
        });
    }
}