package com.example.reminderapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText idEditText, titleEditText, descriptionEditText, dateEditText, timeEditText, locationEditText;
    Button saveButton, viewRemindersButton, updateButton, deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);

        idEditText = findViewById(R.id.idEditText);
        titleEditText = findViewById(R.id.titleEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        dateEditText = findViewById(R.id.dateEditText);
        timeEditText = findViewById(R.id.timeEditText);
        locationEditText = findViewById(R.id.locationEditText);
        saveButton = findViewById(R.id.saveButton);
        viewRemindersButton = findViewById(R.id.viewRemindersButton);
        updateButton = findViewById(R.id.updateButton);
        deleteButton = findViewById(R.id.deleteButton);

        addData();
        viewAll();
        updateData();
        deleteData();
    }

    public void addData() {
        saveButton.setOnClickListener(view -> {
            boolean isInserted = myDb.insertData(
                    titleEditText.getText().toString(),
                    descriptionEditText.getText().toString(),
                    dateEditText.getText().toString(),
                    timeEditText.getText().toString(),
                    locationEditText.getText().toString()
            );

            if (isInserted) {
                Toast.makeText(MainActivity.this, "Reminder Saved", Toast.LENGTH_LONG).show();
                clearFields();
            } else {
                Toast.makeText(MainActivity.this, "Error Saving Reminder", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void viewAll() {
        viewRemindersButton.setOnClickListener(view -> {
            Cursor res = myDb.getAllData();
            if (res.getCount() == 0) {
                showMessage("Error", "No reminders found");
                return;
            }

            StringBuilder buffer = new StringBuilder();
            while (res.moveToNext()) {
                buffer.append("ID: ").append(res.getString(0)).append("\n");
                buffer.append("Title: ").append(res.getString(1)).append("\n");
                buffer.append("Description: ").append(res.getString(2)).append("\n");
                buffer.append("Date: ").append(res.getString(3)).append("\n");
                buffer.append("Time: ").append(res.getString(4)).append("\n");
                buffer.append("Location: ").append(res.getString(5)).append("\n\n");
            }
            showMessage("Reminders", buffer.toString());
        });
    }

    public void updateData() {
        updateButton.setOnClickListener(view -> {
            boolean isUpdated = myDb.updateData(
                    idEditText.getText().toString(),
                    titleEditText.getText().toString(),
                    descriptionEditText.getText().toString(),
                    dateEditText.getText().toString(),
                    timeEditText.getText().toString(),
                    locationEditText.getText().toString()
            );

            if (isUpdated) {
                Toast.makeText(MainActivity.this, "Reminder Updated", Toast.LENGTH_LONG).show();
                clearFields();
            } else {
                Toast.makeText(MainActivity.this, "Error Updating Reminder", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void deleteData() {
        deleteButton.setOnClickListener(view -> {
            Integer deletedRows = myDb.deleteData(idEditText.getText().toString());
            if (deletedRows > 0) {
                Toast.makeText(MainActivity.this, "Reminder Deleted", Toast.LENGTH_LONG).show();
                clearFields();
            } else {
                Toast.makeText(MainActivity.this, "Error Deleting Reminder", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    private void clearFields() {
        idEditText.setText("");
        titleEditText.setText("");
        descriptionEditText.setText("");
        dateEditText.setText("");
        timeEditText.setText("");
        locationEditText.setText("");
    }

}
