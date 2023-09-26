package com.blackmirrror.sportclub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.blackmirrror.sportclub.data.SportClubContract;
import com.blackmirrror.sportclub.data.SportClubContract.MembersEntry;

import java.util.ArrayList;

public class AddMemberActivity extends AppCompatActivity {

    public EditText firstNameEt;
    public EditText lastNameEt;
    public EditText sportEt;
    Spinner genderSp;
    int gender = 0;

    private ArrayAdapter spinnerArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);

        firstNameEt = findViewById(R.id.firstNameEt);
        lastNameEt = findViewById(R.id.lastNameEt);
        sportEt = findViewById(R.id.sportEt);
        genderSp = findViewById(R.id.genderSp);

        spinnerArrayAdapter = ArrayAdapter.createFromResource(this, R.array.array_gender, android.R.layout.simple_spinner_item);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        genderSp.setAdapter(spinnerArrayAdapter);
        genderSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectGender = (String) adapterView.getItemAtPosition(i);
                if (!selectGender.isEmpty()) {
                    if (selectGender.equals("Male"))
                        gender = MembersEntry.GENDER_MALE;
                    else if (selectGender.equals("Female"))
                        gender = MembersEntry.GENDER_FEMALE;
                    else
                        gender = MembersEntry.GENDER_UNKNOWN;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                gender = MembersEntry.GENDER_UNKNOWN;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_member, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.saveMember:
                insertMember();
                return true;
            case R.id.deleteMember:
                deleteMember();
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void deleteMember() {
        // агаа не проходит тесты
    }

    void insertMember() {
        String firstName = firstNameEt.getText().toString().trim();
        String lastName = lastNameEt.getText().toString().trim();
        String sport = sportEt.getText().toString().trim();

        ContentValues contentValues = new ContentValues();
        contentValues.put(MembersEntry.COLUMN_FIRST_NAME, firstName);
        contentValues.put(MembersEntry.COLUMN_LAST_NAME, lastName);
        contentValues.put(MembersEntry.COLUMN_SPORT, sport);
        contentValues.put(MembersEntry.COLUMN_GENDER, gender);

        ContentResolver contentResolver = getContentResolver();
        Uri uri = contentResolver.insert(MembersEntry.CONTENT_URI, contentValues);
        if (uri == null) {
            Toast.makeText(this, "Not insert", Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(this, "Successful insert", Toast.LENGTH_SHORT).show();
    }

    public void setContentResolver(ContentResolver mockContentResolver) {
        this.setContentResolver(mockContentResolver);
    }
}