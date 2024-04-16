package com.example.cartyproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ProductDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "products.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_PRODUCTS = "product"; // Table name should match
    private static final String COLUMN_ID = "prod_id"; // Matches the primary key column name
    private static final String COLUMN_NAME = "prod_name"; // Matches the column name for product name
    private static final String COLUMN_DESCRIPTION = "description"; // Matches the column name for description
    private static final String COLUMN_PRICE = "price"; // You might need to add this column if you have a price in your database
    private static final String COLUMN_CATEGORY = "category"; // Matches the column name for category
    private static final String COLUMN_IMAGE = "prod_pic"; // Matches the column name for product image


    public ProductDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_PRODUCTS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_PRICE + " REAL, " +
                COLUMN_CATEGORY + " TEXT, " +
                COLUMN_IMAGE + " BLOB)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        onCreate(db);
    }

    public boolean addProduct(String name, String description, double price, String category, byte[] image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_DESCRIPTION, description);
        contentValues.put(COLUMN_PRICE, price);
        contentValues.put(COLUMN_CATEGORY, category);
        contentValues.put(COLUMN_IMAGE, image);
        long result = db.insert(TABLE_PRODUCTS, null, contentValues);
        return result != -1;
    }
}
