package com.berry.groceryapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

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
    // Table name and column names
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

        //user
        String CREATE_TABLE_QUERY = "CREATE TABLE " + TABLE_USER + " (ID INTEGER PRIMARY KEY, USERNAME TEXT NOT NULL," + " EMAIL TEXT NOT NULL," + " PASSWORD TEXT NOT NULL)";
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
                + COLUMN_ITEM_CODE + " INTEGER, "
                + COLUMN_QTY_PURCHASED + " INTEGER, "
                + COLUMN_DATE_OF_PURCHASE + " DATE"
                + ")";
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
        //String INSERT_USER_QUERY = "INSERT INTO " + TABLE_NAME + " VALUES"...
        /*
        String IS_USERNAME_EXISTS_QUERY = "SELECT EXISTS(SELECT 1 FROM" +TABLE_NAME + " WHERE " +
                COL_USERNAME + " = " + userName + ");";
        Cursor existedUserCursor = sqLiteDatabase.rawQuery(IS_USERNAME_EXISTS_QUERY,null);
        if(existedUserCursor.getCount() > 0){
            throw new InsertException();
        }
        */

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

    public boolean isUserValid(String userName, String password) {
        this.sqLiteDatabase = getReadableDatabase();
        String getUserFromDBQuery = "SELECT * FROM " + TABLE_USER + " WHERE USERNAME ='" + userName + "'";

        Cursor cursor = sqLiteDatabase.rawQuery(getUserFromDBQuery, null);
        if (cursor.moveToFirst()) {
            if (cursor.getString(2).equals(password)) {
                sqLiteDatabase.close();
                return true;
            } else {
                sqLiteDatabase.close();
                return false;
            }
        } else {
            sqLiteDatabase.close();
            return false;
        }
    }

    public ArrayList<String> getAllUsers() {
        this.sqLiteDatabase = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USER;
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        ArrayList<String> allUsers = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                allUsers.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        sqLiteDatabase.close();
        return allUsers;
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


    public long insertSalesItem(int orderNum, int itemCode, String customerName, String customerEmail, int qtySold, String dateOfSales) {
        this.sqLiteDatabase = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_ORDER_NUMBER, orderNum);
        values.put(COLUMN_ITEM_CODE_S, itemCode);
        values.put(COLUMN_CUSTOMER_NAME, customerName);
        values.put(COLUMN_CUSTOMER_EMAIL, customerEmail);
        values.put(COLUMN_QTY_SOLD, qtySold);
        values.put(COLUMN_DATE_OF_SALES, dateOfSales);
        long newRowId = sqLiteDatabase.insert(TABLE_SALES, null, values);

        sqLiteDatabase.close();
        return newRowId;
    }


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


}
