package com.sliit.crud;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.strictmode.WebViewMethodCalledOnWrongThreadViolation;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.sliit.crud.database.DBHelper;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText et_username, et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
    }

    public void saveUser(View view){
        String username = et_username.getText().toString();
        String password = et_password.getText().toString();
        DBHelper dbHelper = new DBHelper(this);

        if(username.isEmpty()||password.isEmpty()){
            Toast.makeText(this, "Enter Values", Toast.LENGTH_SHORT).show();
        }
        else{
            long inserted = dbHelper.addInfo(username, password);

            if(inserted > 0){
                Toast.makeText(this, "Data inserted successful", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, "Something wend wrong", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public  void viewAll(View view){
        DBHelper dbHelper = new DBHelper(this);

        List info = dbHelper.readAll();

        String[] infoArray = (String[]) info.toArray(new String[0]);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("User Details");

        builder.setItems(infoArray, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String username = infoArray[which].split(" :")[0];
                et_username.setText(username);
                et_password.setText("**************");
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    public void deleteUser(View view){
        DBHelper dbHelper = new DBHelper(this);
        String username = et_username.getText().toString();

        if(username.isEmpty()){
            Toast.makeText(this, "Select a user", Toast.LENGTH_SHORT).show();
        }
        else{
            dbHelper.deleteInfo(username);
            Toast.makeText(this, username + " is deleted", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateUser(View view){
        DBHelper dbHelper = new DBHelper(this);
        String username = et_username.getText().toString();
        String password = et_password.getText().toString();

        if(username.isEmpty()||password.isEmpty()){
            Toast.makeText(this, "Select or type user", Toast.LENGTH_SHORT).show();
        }
        else{
            dbHelper.updateInfo(view, username, password);
        }
    }
}