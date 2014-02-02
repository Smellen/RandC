package com.example.randc;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;//Will be used in methods not yet written
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;//Again used in methods later on
public class DataBase{
/*Column names - All type string*/
public static final String KEY_ROWID ="_id";//PK
public static final String KEY_INFO ="info_type";

/*Database Layout*/
/*
	|  Whole Database  |
		|Info|
*/
private static final String DATABASE_NAME = "Ellen_database";
private static final String INFO_TABLE = "level_table";

private static final int DATABASE_VERSION = 1;

private DbHelper ourHelper;
private final Context ourContext;
private SQLiteDatabase ourDatabase;

private static class DbHelper extends SQLiteOpenHelper{

	public DbHelper(Context context){

		super(context,DATABASE_NAME, null,DATABASE_VERSION );
	}
/* implement methods from SQLiteOpenHelper */
@Override
/*

KEY_ROWID is the primary key for each table

All insert methods,
create tables
for each entry to each table needed

*/
	public void onCreate(SQLiteDatabase db){
	/*Columns created for Info Table*/
	db.execSQL("CREATE TABLE " + INFO_TABLE + "( " +
			KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +  // adding in columns first is integer thats going to increment automatically
			KEY_INFO + " TEXT NOT NULL);"); 
	Log.i("SQL", "Leaderboard Table created");

	
	/*initialValues will be used to put each value for each column into the
	 	specific table
	 Each value inserted successfully will be confirmed in the log cat	*/
	
	ContentValues initialValues = new ContentValues();
	initialValues.put(KEY_INFO, "Have a Nice Day!");
    db.insert(INFO_TABLE, null, initialValues);
    Log.i("SQL", "Inserting value(pos1)");
 
      
	}
/*Database referenced by variable db*/
@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {	
	}
}
	public DataBase(Context c){
		ourContext = c;
	}

	public DataBase open() throws SQLException{
	ourHelper = new DbHelper(ourContext);
	ourDatabase = ourHelper.getWritableDatabase(); 
	return this;
	}

	public void close(){
	ourHelper.close();
	}
	/* Methods to insert entries into each table*/
	
	public long createEntry( String info ){
	 ContentValues cv = new ContentValues();
	 cv.put(KEY_INFO, info);
	 return ourDatabase.insert(INFO_TABLE, null, cv);
	}
	public String getInfoEntry(){
		//String[] properties = new String[]{KEY_ROWID, KEY_QUANTITY};
		String[] columns = new String[]{KEY_INFO};//Column wanted
		Cursor c = ourDatabase.query(INFO_TABLE, columns, null, null, null, null, null);
		int info = c.getColumnIndex(KEY_INFO);
		int pos = 0;
		String[] return_info = new String[1];
		for( c.moveToFirst(); !c.isAfterLast(); c.moveToNext() ){
			return_info[pos] = c.getString(info);
			pos++;
		}
		String result = return_info[0];
		return result;
		//return c;
	}
	
}