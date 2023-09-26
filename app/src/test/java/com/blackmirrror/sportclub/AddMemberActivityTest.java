package com.blackmirrror.sportclub;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.widget.EditText;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.blackmirrror.sportclub.data.SportClubContract;

public class AddMemberActivityTest {

    @Mock
    private ContentResolver mockContentResolver;

    @Mock
    private EditText mockFirstNameEt, mockLastNameEt, mockSportEt;

    private AddMemberActivity activity;
    private MainActivity mainActivity;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        activity = new AddMemberActivity();
        activity.setContentResolver(mockContentResolver);
        activity.firstNameEt = mockFirstNameEt;
        activity.lastNameEt = mockLastNameEt;
        activity.sportEt = mockSportEt;

        when(mockContentResolver.insert(Mockito.any(Uri.class), Mockito.any(ContentValues.class)))
                .thenReturn(Uri.parse("content://example.com/members/1"));
    }

    @Test
    public void testInsertMember_Successful() {
        // Устанавливаем значения полей
        Mockito.when(mockFirstNameEt.getText()).thenReturn(new SpannableStringBuilder("John"));
        Mockito.when(mockLastNameEt.getText()).thenReturn(new SpannableStringBuilder("Doe"));
        Mockito.when(mockSportEt.getText()).thenReturn(new SpannableStringBuilder("Tennis"));
        // Вызываем метод insertMember()
        activity.insertMember();
        // Проверяем, что был выполнен insert с корректными данными
        ContentValues expectedValues = new ContentValues();
        expectedValues.put(SportClubContract.MembersEntry.COLUMN_FIRST_NAME, "John");
        expectedValues.put(SportClubContract.MembersEntry.COLUMN_LAST_NAME, "Doe");
        expectedValues.put(SportClubContract.MembersEntry.COLUMN_SPORT, "Tennis");
        expectedValues.put(SportClubContract.MembersEntry.COLUMN_GENDER, 1); // Устанавливаем ожидаемое значение для пола
        verify(mockContentResolver).insert(SportClubContract.MembersEntry.CONTENT_URI, expectedValues);
    }

    @Test
    public void testInsertMember_Failed() {
        // Устанавливаем значения полей
        Mockito.when(mockFirstNameEt.getText()).thenReturn(new SpannableStringBuilder("John"));
        Mockito.when(mockLastNameEt.getText()).thenReturn(new SpannableStringBuilder("Doe"));
        Mockito.when(mockSportEt.getText()).thenReturn(new SpannableStringBuilder("Tennis"));
        // Меняем поведение mockContentResolver, чтобы вернуть null (неудачная вставка)
        when(mockContentResolver.insert(Mockito.any(Uri.class), Mockito.any(ContentValues.class))).thenReturn(null);
        // Вызываем метод insertMember()
        activity.insertMember();
    }

    @Test
    public void testDeleteMember_Successful() {
        // Устанавливаем значения полей
        Mockito.when(mockFirstNameEt.getText()).thenReturn(new SpannableStringBuilder("John"));
        Mockito.when(mockLastNameEt.getText()).thenReturn(new SpannableStringBuilder("Doe"));
        Mockito.when(mockSportEt.getText()).thenReturn(new SpannableStringBuilder("Tennis"));

        // Вызываем метод deleteMember()
        activity.deleteMember();

        // Проверяем, что был выполнен delete
        Bundle extras = activity.firstNameEt.getInputExtras(true);
        verify(mockContentResolver).delete(SportClubContract.MembersEntry.CONTENT_URI, extras);
    }

    @Test
    public void testDisplayDataMainActivity() {
        // Создаем фиктивный Cursor с данными, которые вы ожидаете получить из базы данных
        Cursor mockCursor = mock(Cursor.class);
        when(mockCursor.getColumnIndex(SportClubContract.MembersEntry._ID)).thenReturn(0);
        when(mockCursor.getColumnIndex(SportClubContract.MembersEntry.COLUMN_FIRST_NAME)).thenReturn(1);
        when(mockCursor.getColumnIndex(SportClubContract.MembersEntry.COLUMN_LAST_NAME)).thenReturn(2);
        when(mockCursor.getColumnIndex(SportClubContract.MembersEntry.COLUMN_GENDER)).thenReturn(3);
        when(mockCursor.getColumnIndex(SportClubContract.MembersEntry.COLUMN_SPORT)).thenReturn(4);

        when(mockCursor.moveToNext()).thenReturn(true, false); // Симулируем две записи в Cursor
        when(mockCursor.getInt(0)).thenReturn(1);
        when(mockCursor.getString(1)).thenReturn("John");
        when(mockCursor.getString(2)).thenReturn("Doe");
        when(mockCursor.getString(3)).thenReturn("1");
        when(mockCursor.getString(4)).thenReturn("Tennis");

        // Устанавливаем фиктивный Cursor в getContentResolver()
        when(mockContentResolver.query(
                SportClubContract.MembersEntry.CONTENT_URI,
                null, null, null, null)).thenReturn(mockCursor);

        // Вызываем метод displayData()
        mainActivity.displayData();
    }
}
