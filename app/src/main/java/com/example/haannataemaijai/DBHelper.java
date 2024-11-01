package com.example.haannataemaijai;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import android.database.Cursor;

public class DBHelper extends  SQLiteOpenHelper {

    public static final String DB_Person = "person.db";
    public static final String DB_Item = "item.db";

    //constructor
    public DBHelper(@Nullable Context context) {
        super(context, DB_Person, null , 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE person (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, price INTEGER)");
        sqLiteDatabase.execSQL("CREATE TABLE item (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, price REAL)");
        sqLiteDatabase.execSQL("CREATE TABLE selectedItems (id INTEGER PRIMARY KEY AUTOINCREMENT, person_id INTEGER, item_id INTEGER, FOREIGN KEY(person_id) REFERENCES person(id), FOREIGN KEY(item_id) REFERENCES item(id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS person");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS item");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS person_item");
        onCreate(sqLiteDatabase);
    }

    public void insertPerson(String name, int price) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO person (name, price) VALUES ('" + name + "', " + price + ")");
    }

    public void deleteAllPerson(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM person");
        // all way this use function reset autoincrement
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = 'person'");

    }

    public void deletePerson(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM person WHERE id = " + id);
    }

    public boolean insertItem(String name, double price) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO item (name, price) VALUES ('" + name + "', " + price + ")");
        return true;
    }

    public boolean addItem(String name, double price) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO item (name, price) VALUES ('" + name + "', " + price + ")");
        return true;
    }

    public Cursor getAllItem() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res_data = db.rawQuery("SELECT * FROM item", null);
        return res_data;
    }

    public Cursor getSelectedItem(int person_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res_data = db.rawQuery("SELECT * FROM item WHERE id IN (SELECT item_id FROM person_item WHERE person_id = " + person_id + ")", null);
        return res_data;
    }

    public void deleteAllItem(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM item");
        // all way this use function reset autoincrement
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = 'item'");
    }

    public void deleteSelectedItem(int person_id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM person_item WHERE person_id = " + person_id);
    }


    public boolean insertPersonItem(int person_id, int item_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO person_item (person_id, item_id) VALUES (" + person_id + ", " + item_id + ")");
        return true;
    }

    public void RemovePersonItem(int person_id, int item_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM person_item WHERE person_id = " + person_id + " AND item_id = " + item_id);
    }

    public void UpdatePricePerson(int id, int price) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE person SET price = " + price + " WHERE id = " + id);
    }

    public Cursor getAllPerson() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res_data = db.rawQuery("SELECT * FROM person", null);
        return res_data;
    }
}
