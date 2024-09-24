package com.example.haannataemaijai;
import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
//import checkbox
import android.widget.CheckBox;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
//import  log
import android.util.Log;
import java.util.ArrayList;
import java.util.Objects;
//import dataArray

public class MainActivity extends AppCompatActivity {

    EditText itemName, itemPrice , Person;
    Button addItemButton, calculateButton , clearButton , addPerson;
    TableLayout itemsTable;
    TextView totalResult, textViewOutput, count_money;

    double totalCost = 0;
    private final ArrayList<HashMap<String, Object>> inputDataList = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> data_item = new ArrayList<>();    //create data_item 2 dimention
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
        count_money = findViewById(R.id.count_money);
//        textViewOutput = findViewById(R.id.textViewOutput);
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
                    //add data item { name , price }to data_item
                    HashMap<String, Object> item1 = new HashMap<>();
                    item1.put("product", name);
                    item1.put("price", price);
                    data_item.add(item1);
                    Log.d("MainActivity_data_item", "data_item: " + data_item);
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
            TableLayout tablePerson = findViewById(R.id.tablePerson);

            @Override
            public void onClick(View v) {
                // รับค่าจาก EditText
                String inputText = Person.getText().toString();
                Number count = 0;
                // ตรวจสอบว่ามีการกรอกข้อมูลหรือไม่
                if (!inputText.isEmpty()) {
                    // เพิ่มข้อมูลที่กรอกลงในลิสต์
                    HashMap<String, Object> item1 = new HashMap<>();
                    item1.put("name", inputText);
                    item1.put("price", count);
                    inputDataList.add(item1);

                    // เรียกฟังก์ชันเพื่ออัพเดตข้อมูลใน TextView
                    updateTextView();

                    // เคลียร์ช่อง EditText หลังกรอกข้อมูล
                    Person.setText("");
                } else {
//                    textViewOutput.setText("กรุณากรอกข้อมูลก่อน");
                }
            }

            private void updateTextView() {
                // Clear previous rows in the table
                tablePerson.removeViews(1, tablePerson.getChildCount() - 1);

                for (HashMap<String, Object> data : inputDataList) {
                    // Create a new TableRow
                    TableRow tableRow = new TableRow(MainActivity.this);
                    tableRow.setLayoutParams(new TableRow.LayoutParams(
                            TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.WRAP_CONTENT
                    ));

                    // Create TextView for the data
                    TextView textView = new TextView(MainActivity.this);
                    textView.setText((String) data.get("name"));
                    TableRow.LayoutParams textParams = new TableRow.LayoutParams(
                            0, // Width: 0 for weight distribution
                            TableRow.LayoutParams.WRAP_CONTENT,
                            1.0f // Weight to take up remaining space
                    );
                    textView.setLayoutParams(textParams);
                    //set padding
                    textView.setPadding(20, 25, 12, 25);
                    tableRow.addView(textView);

                    // Create button and set text
                    Button button = new Button(MainActivity.this);
                    button.setText("Setting");

                    // Set click event for the button
                    button.setOnClickListener(view -> showSettingsDialog(data)
                            // Do something when the button is clicked
                    );

                    // Set layout parameters for the button
                    TableRow.LayoutParams buttonParams = new TableRow.LayoutParams(
                            TableRow.LayoutParams.WRAP_CONTENT, // Width based on content
                            TableRow.LayoutParams.WRAP_CONTENT // Height
                    );
                    buttonParams.setMargins(20, 0, 200, 0); // Adjust margin as needed
                    button.setLayoutParams(buttonParams); // Set the layout parameters to the button

                    // Add button to the TableRow
                    tableRow.addView(button);

                    // Add the row to the TableLayout
                    tablePerson.addView(tableRow);
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


        //create function clearData

    }


    private void showSettingsDialog(HashMap<String, Object> data) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Settings");
//        Log.d("MainActivity_user", "data user: " + data.get("select"));
        // สร้าง LinearLayout เพื่อเก็บ CheckBox
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
//        double total = (double);
        // เก็บ CheckBox เพื่อเช็คสถานะตอนกด OK
        ArrayList<CheckBox> checkBoxList = new ArrayList<>();

        Log.d("MainActivity_user_person", "data user: " + inputDataList);

        // สร้าง CheckBox จาก dataItem
        for (HashMap<String, Object> item : data_item) {
            String product = (String) item.get("product");
            double price = (double) item.get("price");
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(product + " - " + price);
            layout.addView(checkBox);
        builder.setView(layout);
        // set Padding
        layout.setPadding(50, 40, 50, 10);

        // ปุ่ม OK
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            double currentPrice = Double.parseDouble(data.get("price").toString());

            @Override
            public void onClick(DialogInterface dialog, int id) {
                // เมื่อผู้ใช้กด OK ตรวจสอบว่า CheckBox ใดถูกเลือก
                for (int i = 0; i < checkBoxList.size(); i++) {
                    ArrayList<HashMap<String, Object>> selectList = new ArrayList<>();
                    ArrayList<HashMap<String, Object>> selectList_food = new ArrayList<>();
                    CheckBox checkBox = checkBoxList.get(i);

                    if( checkBox.isChecked() ){
                    Log.d( "MainActivity_checkBox", "เลือก: " + checkBox );
                    }
                }
            }
        });

        // ปุ่ม Cancel
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        // แสดง dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // ตัวอย่างฟังก์ชันสำหรับเพิ่มเงินให้กับ account
//    private void addMoneyToAccount(String name, double amount) {
//        // ที่นี่คุณสามารถเพิ่มโค้ดการจัดการบัญชีของคุณได้ เช่น เพิ่มเงินในฐานข้อมูล
//        Log.d("MainActivity", "เพิ่มเงินให้กับ: " + name + " จำนวน: " + amount);
//    }
}
}