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
import java.util.Map;
import java.util.ArrayList;


import java.lang.reflect.Array;
import java.util.Arrays;
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
        TableLayout tablePerson = findViewById(R.id.tablePerson);
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
                    item1.put("id", itemsTable.getChildCount());
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
                    // add column price
                    TextView textViewPrice = new TextView(MainActivity.this);
                    textViewPrice.setText(String.valueOf(data.get("price")));
                    TableRow.LayoutParams textParamsPrice = new TableRow.LayoutParams(
                            0, // Width: 0 for weight distribution
                            TableRow.LayoutParams.WRAP_CONTENT,
                            1.0f // Weight to take up remaining space
                    );
                    textViewPrice.setLayoutParams(textParamsPrice);
                    //set padding
                    textViewPrice.setPadding(20, 25, 12, 25);
                    tableRow.addView(textViewPrice);


                    // Create button and set text
                    Button button = new Button(MainActivity.this);
                    button.setText("เลือกสินค้า");

// Set background color and rounded corners
                    button.setBackground(getResources().getDrawable(R.drawable.rounded_button, null));
                    button.setTextColor(getResources().getColor(R.color.white, null)); // Set text color
                    button.setPadding(20, 10, 20, 10); // Add padding for better appearance

// Set click event for the button
                    button.setOnClickListener(view -> showSettingsDialog(data));

// Set layout parameters for the button
                    TableRow.LayoutParams buttonParams = new TableRow.LayoutParams(
                            TableRow.LayoutParams.WRAP_CONTENT, // Width based on content
                            TableRow.LayoutParams.WRAP_CONTENT // Height
                    );
                    buttonParams.setMargins(20, 0, 200, 10); // Adjust margin as needed
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
        builder.setTitle("เลือกสินค้า");
        TableLayout tablePerson = findViewById(R.id.tablePerson);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        ArrayList<CheckBox> checkBoxList = new ArrayList<>();

//        Log.d("MainActivity_user_person", "data user: " + inputDataList);
        Object selectedItems = data.get("select");
        // initialize selectedItems
        if (selectedItems == null) {
            selectedItems = new ArrayList<>();
        }
        Log.d("MainActivity_user_person", "data item: " + selectedItems);
        // สร้าง CheckBox สำหรับแต่ละ item
        for (HashMap<String, Object> item : data_item) {
            String product = (String) item.get("product");
            double price = (double) item.get("price");
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(product + " - " + price);
            for(HashMap<String, Object> item1 : (ArrayList<HashMap<String, Object>>) selectedItems){
                if( item1.get("product").equals(product)){
                    checkBox.setChecked(true);
                }
            }
            checkBoxList.add(checkBox);
            layout.addView(checkBox);
        }
        for (HashMap<String, Object> person : inputDataList) {
            int current_price = 0;
            person.put("price", current_price);
        }

        builder.setView(layout);
        // set Padding
        layout.setPadding(50, 40, 50, 10);

        // ปุ่ม OK
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Log.d("MainActivity_user_person", "checkBoxList: " + checkBoxList);
                // ตรวจสอบว่า CheckBox ใดถูกเลือก
                ArrayList<HashMap<String, Object>> selectedItems = new ArrayList<>();
//                ArrayList<HashMap<String, Object>>  = new ArrayList<>();
                for (int i = 0; i < checkBoxList.size(); i++) {
                    if (checkBoxList.get(i).isChecked()) {
                        // เพิ่มรายการที่ถูกเลือกลงใน ArrayList
                        Log.d("MainActivity_user_person", "Selected items: " + i);
                        selectedItems.add(data_item.get(i));

                     }
                }
                data.put("select", selectedItems);
                //loop in data_item
                // สร้าง Map เพื่อเก็บจำนวนคนที่เลือกแต่ละ product
                Map<String, Integer> productCountMap = new HashMap<>();

// Loop ผ่าน data_item เพื่อรวบรวมจำนวนคนที่เลือกแต่ละ product
                for (HashMap<String, Object> item : data_item) {
                    String productName = (String) item.get("product");
                    Double price = (Double) item.get("price");

                    if (price == null) {
                        Log.d("MainActivity_user_price", "price is null, skipping item.");
                        continue;
                    }

                    Log.d("MainActivity_user_price", "price items: " + price);

                    // Loop ผ่าน inputDataList เพื่อเก็บจำนวนคนที่เลือก product
                    for (HashMap<String, Object> person : inputDataList) {
                        Object selectedItems_person = person.get("select");

                        if (selectedItems_person == null) {
                            selectedItems_person = new ArrayList<>();
                        }

                        // นับจำนวนคนที่เลือก product เดียวกัน
                        for (HashMap<String, Object> item3 : (ArrayList<HashMap<String, Object>>) selectedItems_person) {
                            if (productName.equals(item3.get("product"))) {
                                productCountMap.put(productName, productCountMap.getOrDefault(productName, 0) + 1);
                            }
                        }
                    }
                }

// Loop ผ่าน data_item อีกครั้งเพื่อแจกจ่ายราคา
                for (HashMap<String, Object> item : data_item) {
                    String productName = (String) item.get("product");
                    Double price = (Double) item.get("price");

                    if (price == null) {
                        Log.d("MainActivity_user_price", "price is null, skipping item.");
                        continue;
                    }

                    int count = productCountMap.getOrDefault(productName, 1); // ป้องกัน division by zero

                    // คำนวณราคาต่อคน
                    double price_per_person = price / count;

                    // อัปเดตราคาในแต่ละบุคคล
                    for (HashMap<String, Object> person : inputDataList) {
                        Object selectedItems_person = person.get("select");

                        if (selectedItems_person == null) {
                            selectedItems_person = new ArrayList<>();
                        }

                        // ตรวจสอบว่าบุคคลนี้เลือก product นี้หรือไม่
                        for (HashMap<String, Object> item3 : (ArrayList<HashMap<String, Object>>) selectedItems_person) {
                            if (productName.equals(item3.get("product"))) {
                                Double current_price = person.get("price") instanceof Integer
                                        ? ((Integer) person.get("price")).doubleValue()
                                        : (Double) person.get("price");

                                if (current_price == null) {
                                    Log.d("MainActivity_user_price", "current_price is null, skipping person.");
                                    continue;
                                }

                                // อัปเดตราคาให้กับบุคคลนี้
                                double new_price = current_price + price_per_person;
                                person.put("price", new_price);

                                Log.d("MainActivity_user_price", "Updated price for person: " + new_price);
                            }
                        }
                    }
                }






                updateTextView();
                // แปลง ArrayList เป็น String
                Log.d("MainActivity_user_person", "Selected items: " + selectedItems);
                Log.d("MainActivity_user_person", "Person Selected items: " + inputDataList);

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
                    // add column price
                    TextView textViewPrice = new TextView(MainActivity.this);
                    textViewPrice.setText(String.valueOf(data.get("price")));
                    TableRow.LayoutParams textParamsPrice = new TableRow.LayoutParams(
                            0, // Width: 0 for weight distribution
                            TableRow.LayoutParams.WRAP_CONTENT,
                            1.0f // Weight to take up remaining space
                    );
                    textViewPrice.setLayoutParams(textParamsPrice);
                    //set padding
                    textViewPrice.setPadding(20, 25, 12, 25);
                    tableRow.addView(textViewPrice);


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

}