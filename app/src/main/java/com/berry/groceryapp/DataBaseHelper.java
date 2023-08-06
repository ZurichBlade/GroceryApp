package com.berry.groceryapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.berry.groceryapp.model.StockItemData;

import java.util.ArrayList;

public class DataBaseHelper extends SQLiteOpenHelper {

    public final static String DATABASE_NAME = "Grocery.db";
    //user table
    public final static String TABLE_USER = "USER";
    public final static String COL_ID = "ID";
    public final static String COL_USERNAME = "USERNAME";
    public final static String COL_EMAIL = "EMAIL";
    public final static String COL_PASSWORD = "PASSWORD";


    //stock table
    public static final String TABLE_STOCK = "Stock";
    public static final String COLUMN_ITEM_CODE = "itemCode";
    public static final String COLUMN_ITEM_NAME = "itemName";
    public static final String COLUMN_QTY_STOCK = "qtyStock";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_RETURNABLE = "returnable";


    //Sales table
    public static final String TABLE_SALES = "Sales";
    public static final String COLUMN_ORDER_NUMBER = "orderNumber";
    public static final String COLUMN_ITEM_CODE_S = "itemCode_S";
    public static final String COLUMN_CUSTOMER_NAME = "customerName";
    public static final String COLUMN_CUSTOMER_EMAIL = "customerEmail";
    public static final String COLUMN_QTY_SOLD = "qtySold";
    public static final String COLUMN_DATE_OF_SALES = "dateOfSales";


    // Table purchase
    private static final String TABLE_PURCHASE = "Purchase";
    private static final String COLUMN_INVOICE_NUMBER = "invoiceNumber";
    private static final String COLUMN_ITEM_CODE_P = "itemCode_P";
    private static final String COLUMN_QTY_PURCHASED = "qtyPurchased";
    private static final String COLUMN_DATE_OF_PURCHASE = "dateOfPurchase";

    SQLiteDatabase sqLiteDatabase;


    public DataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        //creating tables.

        // SQL query to create the User table
        String CREATE_TABLE_QUERY = "CREATE TABLE " + TABLE_USER + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_USERNAME + " TEXT, " +
                COL_EMAIL + " TEXT, " +
                COL_PASSWORD + " TEXT);";
        sqLiteDatabase.execSQL(CREATE_TABLE_QUERY);

        //stock
        String CREATE_TABLE_STOCK_QUERY = "CREATE TABLE " + TABLE_STOCK + " (" +
                COLUMN_ITEM_CODE + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ITEM_NAME + " TEXT, " +
                COLUMN_QTY_STOCK + " INTEGER, " +
                COLUMN_PRICE + " REAL, " +
                COLUMN_RETURNABLE + " INTEGER);";
        sqLiteDatabase.execSQL(CREATE_TABLE_STOCK_QUERY);

        //sales
        String CREATE_TABLE_SALES_QUERY = "CREATE TABLE " + TABLE_SALES + " (" +
                COLUMN_ORDER_NUMBER + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ITEM_CODE_S + " INTEGER, " +
                COLUMN_CUSTOMER_NAME + " TEXT, " +
                COLUMN_CUSTOMER_EMAIL + " TEXT, " +
                COLUMN_QTY_SOLD + " INTEGER, " +
                COLUMN_DATE_OF_SALES + " DATE)";
        sqLiteDatabase.execSQL(CREATE_TABLE_SALES_QUERY);

        //purchase
        String CREATE_TABLE_PURCHASE_QUERY = "CREATE TABLE " + TABLE_PURCHASE + " ("
                + COLUMN_INVOICE_NUMBER + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_ITEM_CODE_P + " INTEGER, "
                + COLUMN_QTY_PURCHASED + " INTEGER, "
                + COLUMN_DATE_OF_PURCHASE + " TEXT);";
        sqLiteDatabase.execSQL(CREATE_TABLE_PURCHASE_QUERY);


        this.sqLiteDatabase = sqLiteDatabase;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String UPGRADE_QUERY = "DROP TABLE IF EXISTS " + TABLE_USER;
        sqLiteDatabase.execSQL(UPGRADE_QUERY);

        String UPGRADE_STOCKS_QUERY = "DROP TABLE IF EXISTS " + TABLE_STOCK;
        sqLiteDatabase.execSQL(UPGRADE_STOCKS_QUERY);

        String UPGRADE_SALES_QUERY = "DROP TABLE IF EXISTS " + TABLE_SALES;
        sqLiteDatabase.execSQL(UPGRADE_SALES_QUERY);

        String UPGRADE_PURCHASE_QUERY = "DROP TABLE IF EXISTS " + TABLE_PURCHASE;
        sqLiteDatabase.execSQL(UPGRADE_PURCHASE_QUERY);

        this.onCreate(sqLiteDatabase);
    }

    public boolean insertUser(String userName, String email, String password)/* throws InsertException*/ {
        this.sqLiteDatabase = getWritableDatabase();
        String SELECT_ALL_QUERY = "SELECT * FROM " + TABLE_USER;
        Cursor allUsersCursor = sqLiteDatabase.rawQuery(SELECT_ALL_QUERY, null);
        int id = allUsersCursor.getCount();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_ID, id + 1);
        contentValues.put(COL_USERNAME, userName);
        contentValues.put(COL_EMAIL, email);
        contentValues.put(COL_PASSWORD, password);

        Long result = sqLiteDatabase.insert(TABLE_USER, null, contentValues);
        sqLiteDatabase.close();
        //throw new InsertException();
        return result != -1;

    }


    //to authenticate user.
    // Method to check if a user exists with the given username and password
    public boolean checkUserCredentials(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COL_USERNAME};
        String selection = COL_USERNAME + " = ? AND " + COL_PASSWORD + " = ?";
        String[] selectionArgs = {username, password};
        Cursor cursor = db.query(TABLE_USER, columns, selection, selectionArgs, null, null, null);
        boolean userExists = cursor.moveToFirst();
        cursor.close();
        db.close();
        return userExists;
    }


    // Method to insert data into the Stock table
    public long insertStockItem(String itemName, int qtyStock, int price, boolean returnable) {
        this.sqLiteDatabase = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_ITEM_NAME, itemName);
        values.put(COLUMN_QTY_STOCK, qtyStock);
        values.put(COLUMN_PRICE, price);
        values.put(COLUMN_RETURNABLE, returnable ? 1 : 0); // Convert boolean to integer (1 for true, 0 for false)
        long newRowId = sqLiteDatabase.insert(TABLE_STOCK, null, values);

        sqLiteDatabase.close();
        return newRowId;
    }


    // Method to insert data into the Sales table
    public long insertSalesItem(int itemCode, String customerName, String customerEmail, int qtySold, String dateOfSales) {
        this.sqLiteDatabase = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_ITEM_CODE_S, itemCode);
        values.put(COLUMN_CUSTOMER_NAME, customerName);
        values.put(COLUMN_CUSTOMER_EMAIL, customerEmail);
        values.put(COLUMN_QTY_SOLD, qtySold);
        values.put(COLUMN_DATE_OF_SALES, dateOfSales);
        long newRowId = sqLiteDatabase.insert(TABLE_SALES, null, values);

        sqLiteDatabase.close();
        return newRowId;
    }


    // Method to insert data into the Purchase table
    public long insertPurchaseItem(int itemCode, int qtyPurchased, String dateOfPurchase) {
        this.sqLiteDatabase = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_ITEM_CODE_P, itemCode);
        values.put(COLUMN_QTY_PURCHASED, qtyPurchased);
        values.put(COLUMN_DATE_OF_PURCHASE, dateOfPurchase);
        long newRowId = sqLiteDatabase.insert(TABLE_PURCHASE, null, values);

        sqLiteDatabase.close();
        return newRowId;
    }


    //to get list of all stock.
    public ArrayList<StockItemData> getAllStocks() {
        this.sqLiteDatabase = getReadableDatabase();

        ArrayList<StockItemData> stockList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query("Stock", null, null, null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") int itemCode = cursor.getInt(cursor.getColumnIndex("itemCode"));
                @SuppressLint("Range") String itemName = cursor.getString(cursor.getColumnIndex("itemName"));
                @SuppressLint("Range") int qtyStock = cursor.getInt(cursor.getColumnIndex("qtyStock"));
                @SuppressLint("Range") float price = cursor.getFloat(cursor.getColumnIndex("price"));
                @SuppressLint("Range") boolean returnable = cursor.getInt(cursor.getColumnIndex("returnable")) == 1;

                StockItemData stockItemData = new StockItemData(itemCode, itemName, qtyStock, price, returnable);
                stockList.add(stockItemData);
            }
            cursor.close();
        }

        sqLiteDatabase.close();
        return stockList;
    }


    //to get Stock by id.
    public StockItemData getStockItemById(int itemId) {

        this.sqLiteDatabase = getReadableDatabase();

        StockItemData stockItem = null;
        String[] columns = {COLUMN_ITEM_CODE, COLUMN_ITEM_NAME, COLUMN_QTY_STOCK, COLUMN_PRICE, COLUMN_RETURNABLE};
        String selection = COLUMN_ITEM_CODE + " = ?";
        String[] selectionArgs = {String.valueOf(itemId)};

        Cursor cursor = sqLiteDatabase.query(TABLE_STOCK, columns, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") int itemCode = cursor.getInt(cursor.getColumnIndex(COLUMN_ITEM_CODE));
            @SuppressLint("Range") String itemName = cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_NAME));
            @SuppressLint("Range") int qtyStock = cursor.getInt(cursor.getColumnIndex(COLUMN_QTY_STOCK));
            @SuppressLint("Range") float price = cursor.getFloat(cursor.getColumnIndex(COLUMN_PRICE));
            @SuppressLint("Range") boolean returnable = cursor.getInt(cursor.getColumnIndex(COLUMN_RETURNABLE)) == 1;

            stockItem = new StockItemData(itemCode, itemName, qtyStock, price, returnable);
            cursor.close();
        }

        sqLiteDatabase.close();
        return stockItem;
    }


    // Method to update the qtyStock of an item based on the itemCode and purchased quantity
    public void updateQtyStock(int itemCode, int qtyPurchased) {
        this.sqLiteDatabase = getReadableDatabase();

        // SQL query to update the qtyStock by adding qtyPurchased to the current qtyStock
        String updateQuery = "UPDATE " + TABLE_STOCK +
                " SET " + COLUMN_QTY_STOCK + " = " + COLUMN_QTY_STOCK + " + ?" +
                " WHERE " + COLUMN_ITEM_CODE + " = ?";
        sqLiteDatabase.execSQL(updateQuery, new Object[]{qtyPurchased, itemCode});

        sqLiteDatabase.close();
    }


    // Method to update the qtyStock of an item based on the itemCode and sold quantity
    public void updateSalesQtyStock(int itemCode, int qtyPurchased) {
        this.sqLiteDatabase = getReadableDatabase();

        // SQL query to update the qtyStock by adding qtyPurchased to the current qtyStock
        String updateQuery = "UPDATE " + TABLE_STOCK +
                " SET " + COLUMN_QTY_STOCK + " = " + COLUMN_QTY_STOCK + " - ?" +
                " WHERE " + COLUMN_ITEM_CODE + " = ?";
        sqLiteDatabase.execSQL(updateQuery, new Object[]{qtyPurchased, itemCode});

        sqLiteDatabase.close();
    }


}
