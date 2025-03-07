package com.example.healthcare_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {

    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Create users table
        String qry1 = "CREATE TABLE users(username TEXT PRIMARY KEY, email TEXT, password TEXT, role TEXT)";
        sqLiteDatabase.execSQL(qry1);

        // Create cart table
        String qry2 = "CREATE TABLE cart(username TEXT, product TEXT, price FLOAT, otype TEXT)";
        sqLiteDatabase.execSQL(qry2);

        // Create order table
        String qry3 = "CREATE TABLE orderPlace(username TEXT, fullname TEXT, address TEXT, contact TEXT, pincode INT, date TEXT, time TEXT, amount FLOAT, otype TEXT)";
        sqLiteDatabase.execSQL(qry3);

        // Insert default admin
        String adminInsert = "INSERT INTO users (username, email, password, role) VALUES ('admin', 'admin@example.com', 'admin123', 'admin')";
        sqLiteDatabase.execSQL(adminInsert);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {
            db.execSQL("ALTER TABLE users ADD COLUMN role TEXT DEFAULT 'user'");
        }
    }

    // Register a new user with a default role "user"
    public void register(String username, String email, String password) {
        ContentValues cv = new ContentValues();
        cv.put("username", username);
        cv.put("email", email);
        cv.put("password", password);
        cv.put("role", "user"); // Default role for new users

        SQLiteDatabase db = getWritableDatabase();
        db.insert("users", null, cv);
        db.close();
    }

    // Login method that returns role
    public String login(String username, String password) {
        String role = "";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT role FROM users WHERE username=? AND password=?", new String[]{username, password});

        if (c.moveToFirst()) {
            role = c.getString(0);
        }

        c.close(); // Close cursor
        db.close(); // Close database connection
        return role;
    }

    // Add item to cart
    public void addCart(String username, String product, float price, String otype) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("username", username);
        cv.put("product", product);
        cv.put("price", price);
        cv.put("otype", otype);

        db.insert("cart", null, cv);
        db.close();
    }

    // Check if item exists in cart
    public int checkCart(String username, String product) {
        int result = 0;
        String[] str = {username, product};
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM cart WHERE username=? AND product=?", str);

        if (c.moveToFirst()) {
            result = 1; // Product is already in cart
        }

        c.close(); // Close cursor
        db.close(); // Close database connection
        return result;
    }

    // Remove items from cart
    public void removeCart(String username, String otype) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("cart", "username = ? AND otype = ?", new String[]{username, otype});
        db.close();
    }

    // Retrieve cart data
    public ArrayList<String> getCartData(String username, String otype) {
        ArrayList<String> arr = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String[] str = {username, otype};

        Cursor c = db.rawQuery("SELECT * FROM cart WHERE username = ? AND otype = ?", str);
        if (c.moveToFirst()) {
            do {
                String product = c.getString(1);
                String price = c.getString(2);
                arr.add(product + " $" + price);
            } while (c.moveToNext());
        }

        c.close(); // Close cursor
        db.close(); // Close database connection
        return arr;
    }

    // Add new order
    public void addOrder(String username, String fullname, String address, String contact, int pincode, String date, String time, float price, String otype) {
        ContentValues cv = new ContentValues();
        cv.put("username", username);
        cv.put("fullname", fullname);
        cv.put("address", address);
        cv.put("contact", contact);
        cv.put("pincode", pincode);
        cv.put("date", date);
        cv.put("time", time);
        cv.put("amount", price);
        cv.put("otype", otype);

        SQLiteDatabase db = getWritableDatabase();
        db.insert("orderPlace", null, cv);
        db.close();
    }

    // Retrieve orders of a user
    public ArrayList<String> getOrderData(String username) {
        ArrayList<String> arr = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String[] str = {username};

        Cursor c = db.rawQuery("SELECT * FROM orderPlace WHERE username = ?", str);
        if (c.moveToFirst()) {
            do {
                arr.add(c.getString(1) + "$" + c.getString(2) + "$" + c.getString(3) + "$" + c.getString(4) + "$" +
                        c.getString(5) + "$" + c.getString(6) + "$" + c.getString(7) + "$" + c.getString(8));
            } while (c.moveToNext());
        }

        c.close(); // Close cursor
        db.close(); // Close database connection
        return arr;
    }

    // Check if an appointment already exists
    public int checkAppointmentExists(String username, String fullname, String address, String contact, String date, String time) {
        int result = 0;
        String[] str = {username, fullname, address, contact, date, time};

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM orderPlace WHERE username = ? AND fullname = ? AND address = ? AND contact = ? AND date = ? AND time = ?", str);

        if (c.moveToFirst()) {
            result = 1; // Appointment already exists
        }

        c.close(); // Close cursor
        db.close(); // Close database connection
        return result;
    }

    // Retrieve all users (for admin panel)
    public Cursor getUsers() {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT username, email FROM users WHERE role = 'user'", null);
    }

    // Delete user (for admin panel)
    public void deleteUser(String username) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("users", "username=?", new String[]{username});
        db.close();
    }
//    public void updateUser(String username, String email, String password) {
//        SQLiteDatabase db = getWritableDatabase();
//        ContentValues cv = new ContentValues();
//        cv.put("email", email);
//        cv.put("password", password);
//
//        db.update("users", cv, "username=?", new String[]{username});
//        db.close();
//    }
//    public Cursor getUserDetails(String username) {
//        SQLiteDatabase db = getReadableDatabase();
//        return db.rawQuery("SELECT username, email, password FROM users WHERE username=?", new String[]{username});
//    }
public void updateUser(String oldUsername, String newUsername, String newEmail) {
    ContentValues cv = new ContentValues();
    cv.put("username", newUsername);
    cv.put("email", newEmail);

    SQLiteDatabase db = getWritableDatabase();
    db.update("users", cv, "username = ?", new String[]{oldUsername});
    db.close();
}
}
