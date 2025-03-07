package com.example.healthcare_app;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class AdminDashboardActivity extends AppCompatActivity {
    ListView userListView;
    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        db = new Database(getApplicationContext(), "healthcare", null, 1);
        userListView = findViewById(R.id.userListView);

        loadUsers();

        // Set click listener to show options when a user is clicked
        userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                HashMap<String, String> selectedUser = (HashMap<String, String>) adapterView.getItemAtPosition(position);
                String username = selectedUser.get("username");
                String email = selectedUser.get("email");

                showUserOptionsDialog(username, email);
            }
        });
    }

    private void loadUsers() {
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        Cursor cursor = db.getUsers();

        while (cursor.moveToNext()) {
            HashMap<String, String> user = new HashMap<>();
            user.put("username", cursor.getString(0));
            user.put("email", cursor.getString(1));
            userList.add(user);
        }

        SimpleAdapter adapter = new SimpleAdapter(this, userList,
                android.R.layout.simple_list_item_2,
                new String[]{"username", "email"},
                new int[]{android.R.id.text1, android.R.id.text2});

        userListView.setAdapter(adapter);
    }

    private void showUserOptionsDialog(final String username, final String email) {
        AlertDialog.Builder builder = new AlertDialog.Builder(AdminDashboardActivity.this);
        builder.setTitle("Choose Action")
                .setItems(new CharSequence[]{"Edit", "Delete"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                showEditUserDialog(username, email);
                                break;
                            case 1:
                                deleteUser(username);
                                break;
                        }
                    }
                })
                .show();
    }


    private void showEditUserDialog(final String username, final String email) {
        // Create a LinearLayout to contain both EditText fields
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(50, 50, 50, 50);  // Optional, to give some padding for better UX

        // Create the EditText fields
        final EditText editUsername = new EditText(this);
        editUsername.setHint("Username");
        editUsername.setText(username);  // Set the current username

        final EditText editEmail = new EditText(this);
        editEmail.setHint("Email");
        editEmail.setText(email);         // Set the current email

        // Add the EditTexts to the layout
        layout.addView(editUsername);
        layout.addView(editEmail);

        // Create the dialog and set its properties
        AlertDialog.Builder builder = new AlertDialog.Builder(AdminDashboardActivity.this);
        builder.setTitle("Edit User")
                .setView(layout)  // Set the LinearLayout that contains both EditText fields
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newUsername = editUsername.getText().toString().trim();
                        String newEmail = editEmail.getText().toString().trim();

                        if (!newUsername.isEmpty() && !newEmail.isEmpty()) {
                            // Call the method to update the username and email
                            db.updateUser(username, newUsername, newEmail);
                            loadUsers();  // Refresh the list after editing
                            Toast.makeText(AdminDashboardActivity.this, "User Updated", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(AdminDashboardActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }



    private void deleteUser(final String username) {
        db.deleteUser(username);
        loadUsers(); // Refresh list after deletion
        Toast.makeText(getApplicationContext(), "User Deleted", Toast.LENGTH_SHORT).show();
    }
}
