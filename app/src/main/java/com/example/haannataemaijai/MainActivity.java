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
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
//import  log
import android.util.Log;
import java.util.ArrayList;
public class MainActivity extends AppCompatActivity {

    EditText itemName, itemPrice , Person;
    Button addItemButton, calculateButton , clearButton , addPerson;
    TableLayout itemsTable;
    TextView totalResult , textViewOutput;

    double totalCost = 0;
    private ArrayList<String> inputDataList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        itemName = findViewById(R.id.itemName);
        itemPrice = findViewById(R.id.itemPrice);
        addItemButton = findViewById(R.id.addItemButton);
        calculateButton = findViewById(R.id.calculateButton1);
        itemsTable = findViewById(R.id.itemsTable);
        totalResult = findViewById(R.id.totalResult);
        clearButton = findViewById(R.id.clearbtn);
        Person = findViewById(R.id.Person);
        addPerson = findViewById(R.id.add_person);
        textViewOutput = findViewById(R.id.textViewOutput);
        // Add item button click listener
        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = itemName.getText().toString();
                String priceStr = itemPrice.getText().toString();

                if (!name.isEmpty() && !priceStr.isEmpty()) {
                    double price = Double.parseDouble(priceStr);
                    // create no row
                    TextView no = new TextView(MainActivity.this);
                    no.setText(String.valueOf(itemsTable.getChildCount()));
                    no.setPadding(8, 8, 8, 8); // กำหนด padding

                    // Add a new row to the table
                    TableRow row = new TableRow(MainActivity.this);
                    TextView itemNameView = new TextView(MainActivity.this);
                    TextView itemPriceView = new TextView(MainActivity.this);

                    // กำหนดความยาวสูงสุดของแต่ละบรรทัด
                    int maxLineLength = 15;
                    StringBuilder formattedItemName = new StringBuilder();
                    while (name.length() > maxLineLength) {
                        formattedItemName.append(name, 0, maxLineLength).append("\n");
                        name = name.substring(maxLineLength);
                    }

                    if (!name.isEmpty()) {
                        formattedItemName.append(name);
                    }
                    itemNameView.setText(formattedItemName.toString());
                    itemNameView.setPadding(12, 25, 12, 25); // กำหนด padding

                    itemPriceView.setText(String.format("%.2f", price));
                    itemPriceView.setPadding(12, 25, 12, 25); // กำหนด padding

                    row.addView(no);
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

        // Clear button click listener
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //clear row ตั้งแต่ 1 ถึง สุดท้าย
                itemsTable.removeViews(1, itemsTable.getChildCount() - 1);
                totalCost = 0;
                totalResult.setText("Total cost: 0.00");
            }
        });

        addPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // รับค่าจาก EditText
                String inputText = Person.getText().toString();

                // ตรวจสอบว่ามีการกรอกข้อมูลหรือไม่
                if (!inputText.isEmpty()) {
                    // เพิ่มข้อมูลที่กรอกลงในลิสต์
                    inputDataList.add(inputText);

                    // เรียกฟังก์ชันเพื่ออัพเดตข้อมูลใน TextView
                    updateTextView();

                    // เคลียร์ช่อง EditText หลังกรอกข้อมูล
                    Person.setText("");
                } else {
                    textViewOutput.setText("กรุณากรอกข้อมูลก่อน");
                }
            }
            private void updateTextView() {
                StringBuilder allData = new StringBuilder();
                //log data in allData
//               Log.d("dataPerson",inputDataList);
                //Log data in inputDataList position 0
                Log.d("dataPerson",inputDataList.get(0));
                // วนลูปผ่านข้อมูลทั้งหมดในลิสต์และเพิ่มใน StringBuilder
                for (String data : inputDataList) {
                    allData.append(data).append("\n");  // เพิ่มข้อมูลแต่ละรายการลงไป
                }

                // แสดงข้อมูลใน TextView
                textViewOutput.setText(allData.toString());
            }
        });



        // Calculate button click listener
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totalResult.setText("Total cost: " + String.format("%.2f", totalCost));
            }
        });

        //create function clearData

    }
}