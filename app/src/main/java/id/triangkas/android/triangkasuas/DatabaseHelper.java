package id.triangkas.android.triangkasuas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.strictmode.SqliteObjectLeakedViolation;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";
    private static final int DATABASE_VERSION = 1;
    protected static final String DATABASE_NAME = "db_uas";
    private static final String TABLE_NAME = "t_ibu_hamil";
    private static final String COL1 = "ID";
    private static final String COL2 = "NAMA";
    private static final String COL3 = "ALAMAT";
    private static final String COL4 = "TENSI";
    private static final String COL5 = "BERAT_BADAN";
    private static final String COL6 = "KONDISI_BAYI";
    private static final String COL7 = "KONDISI_IBU";

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE "+ TABLE_NAME +" "+"("+ COL1 +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                COL2 +" TEXT, "+
                COL3 +" TEXT, "+
                COL4 +" TEXT, "+
                COL5 +" TEXT, "+
                COL6 +" TEXT, "+
                COL7 +" TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }


    public boolean addData(String nama, String alamat, String tensi, String berat_badan, String kondisi_bayi, String kondisi_ibu) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, nama);
        contentValues.put(COL3, alamat);
        contentValues.put(COL4, tensi);
        contentValues.put(COL5, berat_badan);
        contentValues.put(COL6, kondisi_bayi);
        contentValues.put(COL7, kondisi_ibu);

        Log.d(TAG, "addData: Adding " + nama + "to" + TABLE_NAME);
        long result = db.insert(TABLE_NAME, null, contentValues);

        if(result == -1){
            return false;
        } else {
            return true;
        }
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getItemId(String nama){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT "+ COL1 +", "+COL2+", "+COL3+", "+COL4+", "+COL5+", "+COL6+", "+COL7+" FROM " + TABLE_NAME +
                " WHERE "+ COL2 + " = '"+ nama +"'";
        Cursor data = db.rawQuery(query, null);
        return  data;
    }

    public void updateData(String nama, String alamat, String tensi, String berat_badan, String kondisi_bayi, String kondisi_ibu, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE "+TABLE_NAME+" SET "+
                COL2+" = '"+nama+"', " +
                COL3+" = '"+alamat+"', " +
                COL4+" = '"+tensi+"', " +
                COL5+" = '"+berat_badan+"', " +
                COL6+" = '"+kondisi_bayi+"', " +
                COL7+" = '"+kondisi_ibu+"'" +
                " WHERE "+COL1+" = '"+id+"'";
        Log.d(TAG, "updateData: query: "+query);
        db.execSQL(query);
    }

    public void deleteData(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM "+TABLE_NAME+
                " WHERE "+COL1+" = '"+id+"'";
        Log.d(TAG, "deleteData: query: "+query);
        db.execSQL(query);
    }
}
