package com.marekhakala.mynomadlifeapp.Utilities;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class DBQueryBuilder {
    protected String mTable = null;
    protected Map<String, String> mProjection = new TreeMap<>();

    protected StringBuilder mSelection = new StringBuilder();
    protected ArrayList<String> mSelectionArgs = new ArrayList<>();

    public DBQueryBuilder() {}

    public DBQueryBuilder setTable(String table) {
        mTable = table;
        return this;
    }

    public String getTable() {
        return mTable;
    }

    public DBQueryBuilder setWhere(String selection, String... args) {
        if (TextUtils.isEmpty(selection))
            return this;

        if (mSelection.length() > 0)
            mSelection.append(" AND ");

        mSelection.append("(");
        mSelection.append(selection);
        mSelection.append(")");

        if (args != null)
            Collections.addAll(mSelectionArgs, args);

        return this;
    }

    protected void mapColumns(String[] columns) {
        for (int i = 0; i < columns.length; i++) {
            String target = mProjection.get(columns[i]);

            if (target != null)
                columns[i] = target;
        }
    }

    public String getSelection() {
        return mSelection.toString();
    }

    public String[] getSelectionArgs() {
        return mSelectionArgs.toArray(new String[mSelectionArgs.size()]);
    }

    @Override
    public String toString() {
        return "DBQueryBuilder> Table: " + mTable + ", Selection: " + getSelection()
                + ", SelectionArgs: " + Arrays.toString(getSelectionArgs())
                + "Projection: " + mProjection;
    }

    public Cursor query(SQLiteDatabase db, String[] columns, String orderBy) {
        return query(db, false, columns, orderBy, null);
    }

    public Cursor query(SQLiteDatabase db, boolean distinct, String[] columns, String orderBy) {
        return query(db, distinct, columns, orderBy, null);
    }

    public Cursor query(SQLiteDatabase db, boolean distinct,
                        String[] columns, String orderBy, String limit) {

        if (columns != null)
            mapColumns(columns);
        return db.query(distinct, mTable, columns, getSelection(), getSelectionArgs(), null, null, orderBy, limit);
    }

    public Cursor rawQuery(SQLiteDatabase db, String sqlQuery) {
        return db.rawQuery(sqlQuery, getSelectionArgs());
    }

    public int update(SQLiteDatabase db, ContentValues values) {
        return db.update(mTable, values, getSelection(), getSelectionArgs());
    }

    public int delete(SQLiteDatabase db) {
        return db.delete(mTable, getSelection(), getSelectionArgs());
    }
}

