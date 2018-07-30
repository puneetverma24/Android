package in.codebucket.sqlite3level;

import android.content.ContentValues;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;



/**
 * Created by Alchemy on 10-03-2018.
 */

public class DbAdapter  {

     DbHelper helper;
     public DbAdapter(Context context)
     {
         helper=new DbHelper(context);

     }


    public long insertRow(String name,int roll)
    {

        SQLiteDatabase db= helper.getWritableDatabase();

        ContentValues contentValues=new ContentValues();

        contentValues.put(DbHelper.COL_NAME,name);
        contentValues.put(DbHelper.COL_ROLL,roll);
        long id=db.insert(DbHelper.TABLE_NAME,null,contentValues);

        db.close();
        return id;
    }


    ////////////////////////////////////////////////
    // sql witjout : see video 17 notedown the theory
//   see again
    //////////////////////////////////////////////


public String display()
{
    SQLiteDatabase db=helper.getWritableDatabase();
    String[] columns={DbHelper.COL_NAME,DbHelper.COL_ROLL};

    Cursor cursor=db.query(DbHelper.TABLE_NAME,columns,null,null,null,null,null);

    StringBuffer buffer=new StringBuffer();
    while(cursor.moveToNext())
{
int index=cursor.getColumnIndex(DbHelper.COL_ROLL);
int roll=cursor.getInt(index);
    int index2=cursor.getColumnIndex(DbHelper.COL_NAME);

String name=cursor.getString(index2);
buffer.append(roll +" "+ name+"\n");
}
    db.close();
return buffer.toString();

}


public  String getData(int roll)
{

SQLiteDatabase db=helper.getWritableDatabase();
String[] columns={DbHelper.COL_NAME};
///////////////////////////////////////////////////////////
  //  two ways
//Cursor cursor=db.query(DbHelper.TABLE_NAME,columns,DbHelper.COL_ROLL+"='"+roll+"'",null,null,null,null);
// or use ike this

    String[] selectionArgs={String.valueOf(roll)};
    Cursor cursor=db.query(DbHelper.TABLE_NAME,columns,DbHelper.COL_ROLL+"=?",selectionArgs,null,null,null);


StringBuffer buffer=new StringBuffer();

while (cursor.moveToNext())
{
    String name=cursor.getString(cursor.getColumnIndex(DbHelper.COL_NAME));
    buffer.append("Name= "+name);

}
   db.close();

    return buffer.toString();
}


public void deleteRow(int roll)
{
SQLiteDatabase db=helper.getWritableDatabase();
String[] selectionArgs={String.valueOf(roll)};

db.delete(DbHelper.TABLE_NAME,DbHelper.COL_ROLL+"=?",selectionArgs );
    db.close();

}



    public long updateRow(int roll, String name)
    {
        SQLiteDatabase db=helper.getWritableDatabase();

        ContentValues contentValues=new ContentValues();
        contentValues.put(DbHelper.COL_NAME,name);

    String[] selectionArgs={String.valueOf(roll)};
 long id= db.update(DbHelper.TABLE_NAME,contentValues,
         DbHelper.COL_ROLL+"=?",selectionArgs);
       db.close();
return id;
    }


    public void deleteAll() {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("delete from " + DbHelper.TABLE_NAME);
        db.close();

    }




    public class DbHelper extends SQLiteOpenHelper
{

    private static final String DATABASE_NAME="school";
    private static final String TABLE_NAME="student";
    private static final int DATABASE_VERSION=1;
    private static final String COL_UID="_uid";
    private static final String COL_NAME="name";
    private static final String COL_ROLL="roll";
    private static final String CREATE_TABLE="CREATE TABLE "+TABLE_NAME +"("+COL_UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+COL_NAME+" VARCHAR(255), "+COL_ROLL+" INTEGER) ;";

    private Context context;


    public DbHelper(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{


            db.execSQL(CREATE_TABLE);

        }catch(SQLException e)
        {e.printStackTrace();

            Log.v("check",""+e.toString());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }






}


}
