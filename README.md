
# Grocery App

The Grocery app is a user-friendly solution showcasing seamless integration of SQLite database management, RecyclerView implementation, Material Design principles, and query functionalities.



## Screenshots

![App Screenshot](https://github.com/ZurichBlade/GroceryApp/blob/master/app/src/main/res/drawable/app_ss.png)


## Features

- SQLite Database Integration: Utilizes SQLite with tables for User, Stock, Sales, and Purchase, demonstrating efficient data management and integrity.
- Material Design Principles: Follows Material Design guidelines for a visually appealing and intuitive user experience.
- Home Page: Displays user info and provides a navigation drawer for Add Stock, Sales, Purchase, Search Stock, and List Stock.
- Add Stock: Enables quick addition of items with validation for Name, Quantity, Price, and Returnable status.
- Sales: Facilitates transactions with real-time inventory updates.
- Purchase: Supports restocking with automatic stock level adjustments.
- Search Stock: Allows quick lookup of items by Item Code with detailed item views.



## Query Usage/Examples

Method to create a table

```bash
  String CREATE_TABLE_QUERY = "CREATE TABLE " + TABLE_USER + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_USERNAME + " TEXT, " +
                COL_EMAIL + " TEXT, " +
                COL_PASSWORD + " TEXT);";
        sqLiteDatabase.execSQL(CREATE_TABLE_QUERY);
```

Method to insert data into the table

```bash
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
```




## Note

This project is developed specifically for practicing SQLite database management, offering a hands-on approach to learning and implementing database workflows in Android applications.


