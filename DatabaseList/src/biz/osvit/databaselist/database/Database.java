package biz.osvit.databaselist.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import biz.osvit.databaselist.models.UserModel;
import biz.osvit.databaselist.models.UserModelWrapper;
import biz.osvit.databaselist.utils.C;

/**
 * Title: Database List <br />
 * Copyright: Copyright @ 2014 <br />
 * 
 * @author Zoran Veres
 * @version 1.0.0
 */

public class Database {

	private DatabaseHelper mDatabaseHelper;
	public SQLiteDatabase mSqlDatabase;

	public Database(Context context) {
		mDatabaseHelper = new DatabaseHelper(context);
	}

	public class DatabaseHelper extends SQLiteOpenHelper {

		public DatabaseHelper(Context context) {
			super(context, C.DatabaseConstants.DATABASE_NAME, null, C.DatabaseConstants.DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			/* Used formatter off/on tag, so that eclipse format doesn't mess my look of creator */
			/* @formatter:off */
			String createDatabaseCmd = "create table " + C.DatabaseConstants.TABLE_USER_NAME + " ("
					+ C.DatabaseConstants.TABLE_USER_ID + " integer primary key autoincrement, "
					+ C.DatabaseConstants.TABLE_USER_FIRST_NAME + " text, "
					+ C.DatabaseConstants.TABLE_USER_LAST_NAME + " text, "
					+ C.DatabaseConstants.TABLE_USER_GENDER + " text, "
					+ C.DatabaseConstants.TABLE_USER_PHONE_NUMBER + " text);";
			db.execSQL(createDatabaseCmd);
			/* @formatter:on */
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			onCreate(db);
		}
	}

	public void insertUser(UserModel user) {

		mSqlDatabase = mDatabaseHelper.getWritableDatabase();

		ContentValues contentValues = new ContentValues();
		contentValues.put(C.DatabaseConstants.TABLE_USER_FIRST_NAME, user.getFirstName());
		contentValues.put(C.DatabaseConstants.TABLE_USER_LAST_NAME, user.getLastName());
		contentValues.put(C.DatabaseConstants.TABLE_USER_GENDER, user.getGender());
		contentValues.put(C.DatabaseConstants.TABLE_USER_PHONE_NUMBER, user.getPhoneNumber());
		mSqlDatabase.insertWithOnConflict(C.DatabaseConstants.TABLE_USER_NAME, null, contentValues, 0);

		mDatabaseHelper.close();
	}

	public void updateUser(long rowId, UserModel user) {

		mSqlDatabase = mDatabaseHelper.getWritableDatabase();

		String where = C.DatabaseConstants.TABLE_USER_ID + " = " + rowId;

		ContentValues updateValues = new ContentValues();
		updateValues.put(C.DatabaseConstants.TABLE_USER_FIRST_NAME, user.getFirstName());
		updateValues.put(C.DatabaseConstants.TABLE_USER_LAST_NAME, user.getLastName());
		updateValues.put(C.DatabaseConstants.TABLE_USER_GENDER, user.getGender());
		updateValues.put(C.DatabaseConstants.TABLE_USER_PHONE_NUMBER, user.getPhoneNumber());
		mSqlDatabase.update(C.DatabaseConstants.TABLE_USER_NAME, updateValues, where, null);

		mDatabaseHelper.close();
	}

	public void deleteUser(long rowId) {

		mSqlDatabase = mDatabaseHelper.getWritableDatabase();

		mSqlDatabase.delete(C.DatabaseConstants.TABLE_USER_NAME, C.DatabaseConstants.TABLE_USER_ID + " = " + rowId, null);

		mDatabaseHelper.close();
	}

	public UserModelWrapper getAllUsers() {

		UserModelWrapper users = new UserModelWrapper();
		mSqlDatabase = mDatabaseHelper.getReadableDatabase();
		Cursor cursor = mSqlDatabase.query(C.DatabaseConstants.TABLE_USER_NAME, null, null, null, null, null, null);
		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
			int id = cursor.getInt(cursor.getColumnIndex(C.DatabaseConstants.TABLE_USER_ID));
			String firstName = cursor.getString(cursor.getColumnIndex(C.DatabaseConstants.TABLE_USER_FIRST_NAME));
			String lastName = cursor.getString(cursor.getColumnIndex(C.DatabaseConstants.TABLE_USER_LAST_NAME));
			String gender = cursor.getString(cursor.getColumnIndex(C.DatabaseConstants.TABLE_USER_GENDER));
			String phoneNumber = cursor.getString(cursor.getColumnIndex(C.DatabaseConstants.TABLE_USER_PHONE_NUMBER));

			UserModel user = new UserModel();
			user.setId(id);
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setGender(gender);
			user.setPhoneNumber(phoneNumber);
			users.addUser(user);
		}

		mDatabaseHelper.close();
		cursor.close();
		return users;
	}

	public UserModel getUser(long rowId) {

		UserModel user = new UserModel();
		mSqlDatabase = mDatabaseHelper.getReadableDatabase();
		Cursor cursor = mSqlDatabase
				.query(C.DatabaseConstants.TABLE_USER_NAME, null, C.DatabaseConstants.TABLE_USER_ID + " = " + rowId, null, null, null, null);

		cursor.moveToFirst();
		{
			int id = cursor.getInt(cursor.getColumnIndex(C.DatabaseConstants.TABLE_USER_ID));
			String firstName = cursor.getString(cursor.getColumnIndex(C.DatabaseConstants.TABLE_USER_FIRST_NAME));
			String lastName = cursor.getString(cursor.getColumnIndex(C.DatabaseConstants.TABLE_USER_LAST_NAME));
			String gender = cursor.getString(cursor.getColumnIndex(C.DatabaseConstants.TABLE_USER_GENDER));
			String phoneNumber = cursor.getString(cursor.getColumnIndex(C.DatabaseConstants.TABLE_USER_PHONE_NUMBER));

			user.setId(id);
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setGender(gender);
			user.setPhoneNumber(phoneNumber);
		}

		mDatabaseHelper.close();
		cursor.close();
		return user;
	}
}
