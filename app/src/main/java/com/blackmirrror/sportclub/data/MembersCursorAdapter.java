package com.blackmirrror.sportclub.data;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cursoradapter.widget.CursorAdapter;

import com.blackmirrror.sportclub.R;

public class MembersCursorAdapter extends CursorAdapter {

    public MembersCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.item_member, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvId = (TextView) view.findViewById(R.id.member_id);
        TextView tvFirstName = (TextView) view.findViewById(R.id.member_first_name);
        TextView tvLastName = (TextView) view.findViewById(R.id.member_last_name);
        TextView tvGender = (TextView) view.findViewById(R.id.member_gender);
        TextView tvSport = (TextView) view.findViewById(R.id.member_sport);

        String id = cursor.getString(cursor.getColumnIndexOrThrow(SportClubContract.MembersEntry._ID));
    }
}
