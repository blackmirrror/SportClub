package com.blackmirrror.sportclub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blackmirrror.sportclub.data.SportClubContract;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    TextView dataTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataTv = findViewById(R.id.dataTv);

        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddMemberActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayData();
    }

    void displayData() {
        String[] projection = {
                SportClubContract.MembersEntry._ID,
                SportClubContract.MembersEntry.COLUMN_FIRST_NAME,
                SportClubContract.MembersEntry.COLUMN_LAST_NAME,
                SportClubContract.MembersEntry.COLUMN_GENDER,
                SportClubContract.MembersEntry.COLUMN_SPORT
        };
        Cursor cursor = getContentResolver().query(
                SportClubContract.MembersEntry.CONTENT_URI,
                projection,
                null, null, null);
        dataTv.setText("All members\n\n");
        dataTv.append(
                "Id" + " " +
                "Name" + " " +
                "Gender" + " " +
                "Sport");

        int idIndex = cursor.getColumnIndex(SportClubContract.MembersEntry._ID);
        int fnIndex = cursor.getColumnIndex(SportClubContract.MembersEntry.COLUMN_FIRST_NAME);
        int lnIndex = cursor.getColumnIndex(SportClubContract.MembersEntry.COLUMN_LAST_NAME);
        int genIndex = cursor.getColumnIndex(SportClubContract.MembersEntry.COLUMN_GENDER);
        int spIndex = cursor.getColumnIndex(SportClubContract.MembersEntry.COLUMN_SPORT);

        while (cursor.moveToNext()) {
            int currentId = cursor.getInt(idIndex);
            String currentFn = cursor.getString(fnIndex);
            String currentLn = cursor.getString(lnIndex);
            String currentGen = cursor.getString(genIndex);
            String currentGender = "Unknown";
            if (Objects.equals(currentGen, "1"))
                currentGender = "Male";
            else if (Objects.equals(currentGen, "2"))
                currentGender = "Female";
            String currentSp = cursor.getString(spIndex);

            dataTv.append("\n" +
                    currentId + " " +
                    currentFn + " " +
                    currentLn + " " +
                    currentGender + " " +
                    currentSp);
        }
        cursor.close();
    }

    public void setContentResolver(ContentResolver mockContentResolver) {
    }
}