package in.ed.poonacollege.androidassignments.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import in.ed.poonacollege.androidassignments.model.Customer;

public class Database extends SQLiteOpenHelper {

    private static final String DB_NAME = "assignment13";
    private static final int VERSION = 1;

    private static final String CUSTOMER_TABLE = "customer";
    private static final String CUSTOMER_ID = "customer_id";
    private static final String CUSTOMER_NAME = "customer_name";
    private static final String CUSTOMER_ADDRESS = "customer_address";
    private static final String CUSTOMER_PHONE = "customer_phone";

    public Database(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + CUSTOMER_TABLE + "(" +
                CUSTOMER_ID +" INTEGER PRIMARY KEY NOT NULL,"+
                CUSTOMER_NAME +" TEXT,"+
                CUSTOMER_ADDRESS + " TEXT," +
                CUSTOMER_PHONE + " TEXT" +
                ")");
    }

    public void insertCustomer(Customer customer){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CUSTOMER_ID, customer.getId());
        contentValues.put(CUSTOMER_NAME, customer.getCustomerName());
        contentValues.put(CUSTOMER_ADDRESS, customer.getCustomerAddress());
        contentValues.put(CUSTOMER_PHONE, customer.getCustomerPhone());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(CUSTOMER_TABLE, null, contentValues);
    }

    public void deleteCustomer(int id){
        SQLiteDatabase db = getWritableDatabase();
        int deleted = db.delete(CUSTOMER_TABLE, CUSTOMER_ID + "=" + id,null);
        Log.d("Database", "Customer deleted : " + deleted);
    }

    public ArrayList<Customer> getAllCustomers(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + CUSTOMER_TABLE, null);
        ArrayList<Customer> customers = new ArrayList<>();

        if(cursor != null && cursor.getCount() > 0){
            while (cursor.moveToNext()){
                Customer customer = new Customer();
                customer.setId(cursor.getInt(cursor.getColumnIndex(CUSTOMER_ID)));
                customer.setCustomerName(cursor.getString(cursor.getColumnIndex(CUSTOMER_NAME)));
                customer.setCustomerAddress(cursor.getString(cursor.getColumnIndex(CUSTOMER_ADDRESS)));
                customer.setCustomerPhone(cursor.getString(cursor.getColumnIndex(CUSTOMER_PHONE)));
                customers.add(customer);
            }
            cursor.close();
        }
        return customers;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
