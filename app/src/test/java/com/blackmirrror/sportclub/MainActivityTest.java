package com.blackmirrror.sportclub;

import android.content.ContentResolver;
import android.database.Cursor;

import com.blackmirrror.sportclub.data.SportClubContract;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MainActivityTest {

    @Mock
    private ContentResolver mockContentResolver;

    private MainActivity activity;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        activity = new MainActivity();
        activity.setContentResolver(mockContentResolver);
    }

    @Test
    public void testDisplayData() {
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
        activity.displayData();
    }
}

