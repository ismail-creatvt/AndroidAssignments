package in.ed.poonacollege.androidassignments.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import in.ed.poonacollege.androidassignments.model.Department;
import in.ed.poonacollege.androidassignments.model.Employee;

public class DatabaseEmployee extends SQLiteOpenHelper {
    private static final String DB_NAME = "assignment15";
    private static final int VERSION = 1;

    private static final String EMP_TABLE = "Emp";
    private static final String EMP_NO = "emp_no";
    private static final String EMP_NAME = "emp_name";
    private static final String EMP_ADDRESS = "emp_address";
    private static final String EMP_PHONE = "emp_phone";
    private static final String EMP_SALARY = "emp_salary";

    private static final String DEPT_TABLE = "Dept";
    private static final String DEPT_NO = "dept_no";
    private static final String DEPT_NAME = "dept_name";
    private static final String DEPT_LOCATION = "dept_location";

    private static final String DNO = "dno";

    public DatabaseEmployee(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " +
                        DEPT_TABLE + "(" +
                        DEPT_NO + " INTEGER PRIMARY KEY," +
                        DEPT_NAME + " TEXT," +
                        DEPT_LOCATION + " TEXT"+
                ")");
        db.execSQL("CREATE TABLE " +
                        EMP_TABLE + "(" +
                        EMP_NO + " INTEGER PRIMARY KEY," +
                        EMP_NAME + " TEXT," +
                        EMP_ADDRESS + " TEXT," +
                        EMP_PHONE + " TEXT," +
                        EMP_SALARY + " REAL," +
                        DNO + " INTEGER REFERENCES " + DEPT_TABLE + "(" + DEPT_NO + ")" +
                        " ON UPDATE CASCADE ON DELETE CASCADE" +
                        ")"
                );
        insertDepartmentValues(db);
        insertEmployeeValues(db);
    }

    private void insertEmployeeValues(SQLiteDatabase db) {
        Employee employee = new Employee();

        employee.setName("Shahrukh Khan");
        employee.setSalary(400000f);
        employee.setPhone("8493489834");
        employee.setAddress("Mumbai");
        employee.setDno(1);
        employee.setNo(1);
        insertEmployee(db, employee);

        employee.setName("Chris Hemsworth");
        employee.setSalary(300000f);
        employee.setPhone("8493342983");
        employee.setAddress("Los Angeles");
        employee.setDno(1);
        employee.setNo(2);
        insertEmployee(db, employee);

        employee.setName("Robert Downey Jr.");
        employee.setSalary(500000f);
        employee.setPhone("849344343");
        employee.setAddress("Planet Mars, Solar System, Milky Way Galaxy");
        employee.setDno(1);
        employee.setNo(3);
        insertEmployee(db, employee);

        employee.setName("Salman Khan");
        employee.setSalary(400000f);
        employee.setPhone("8493489834");
        employee.setAddress("Mumbai");
        employee.setDno(2);
        employee.setNo(4);
        insertEmployee(db, employee);

        employee.setName("Shradha Kapoor");
        employee.setSalary(400000f);
        employee.setPhone("8493489834");
        employee.setAddress("");
        employee.setDno(2);
        employee.setNo(5);
        insertEmployee(db, employee);

        employee.setName("Scarlett Johansonn");
        employee.setSalary(400000f);
        employee.setPhone("8493423834");
        employee.setAddress("California");
        employee.setDno(2);
        employee.setNo(6);
        insertEmployee(db, employee);

        employee.setName("Chris Evans");
        employee.setSalary(400000f);
        employee.setPhone("8493489834");
        employee.setAddress("Australia");
        employee.setDno(3);
        employee.setNo(7);
        insertEmployee(db, employee);

        employee.setName("Will Smith");
        employee.setSalary(400000f);
        employee.setPhone("8493489834");
        employee.setAddress("South Africa");
        employee.setDno(3);
        employee.setNo(8);
        insertEmployee(db, employee);

        employee.setName("Keanu Reeves");
        employee.setSalary(400000f);
        employee.setPhone("8493489834");
        employee.setAddress("Canada");
        employee.setDno(3);
        employee.setNo(9);
        insertEmployee(db, employee);
    }

    private void insertDepartmentValues(SQLiteDatabase db) {
        Department department = new Department();

        department.setName("Computer Science");
        department.setNo(1);
        department.setLocation("Building no 12");
        insertDepartment(db, department);

        department.setName("Biology");
        department.setNo(2);
        department.setLocation("Building no 14");
        insertDepartment(db, department);

        department.setName("Chemistry");
        department.setNo(3);
        department.setLocation("Building no 2");
        insertDepartment(db, department);

    }

    public void insertDepartment(SQLiteDatabase db, Department department){
        if(db == null){
            db = getWritableDatabase();
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put(DEPT_NO, department.getNo());
        contentValues.put(DEPT_NAME, department.getName());
        contentValues.put(DEPT_LOCATION, department.getLocation());
        db.insert(DEPT_TABLE, null, contentValues);
    }

    public ArrayList<Department> getAllDepartments(){
        ArrayList<Department> departments = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DEPT_TABLE,null);

        if(cursor != null && cursor.getCount() > 0){
            while(cursor.moveToNext()){
                Department department = new Department();
                department.setNo(cursor.getInt(cursor.getColumnIndex(DEPT_NO)));
                department.setName(cursor.getString(cursor.getColumnIndex(DEPT_NAME)));
                department.setLocation(cursor.getString(cursor.getColumnIndex(DEPT_LOCATION)));
                departments.add(department);
            }
            cursor.close();
        }
        return departments;
    }

    public void insertEmployee(SQLiteDatabase db, Employee employee){
        if(db == null){
            db = getWritableDatabase();
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put(EMP_NO, employee.getNo());
        contentValues.put(EMP_NAME, employee.getName());
        contentValues.put(EMP_ADDRESS, employee.getAddress());
        contentValues.put(EMP_PHONE, employee.getPhone());
        contentValues.put(EMP_SALARY, employee.getSalary());
        contentValues.put(DNO, employee.getDno());
        db.insert(EMP_TABLE, null, contentValues);
    }

    public ArrayList<Employee> getAllEmployees(int deptno){
        ArrayList<Employee> employees = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + EMP_TABLE + " where " + DNO + "= ?",new String[]{String.valueOf(deptno)});

        if(cursor != null && cursor.getCount() > 0){
            while(cursor.moveToNext()){
                Employee employee = new Employee();
                employee.setNo(cursor.getInt(cursor.getColumnIndex(EMP_NO)));
                employee.setName(cursor.getString(cursor.getColumnIndex(EMP_NAME)));
                employee.setAddress(cursor.getString(cursor.getColumnIndex(EMP_ADDRESS)));
                employee.setPhone(cursor.getString(cursor.getColumnIndex(EMP_PHONE)));
                employee.setSalary(cursor.getFloat(cursor.getColumnIndex(EMP_SALARY)));
                employee.setDno(cursor.getInt(cursor.getColumnIndex(DNO)));
                employees.add(employee);
            }
            cursor.close();
        }
        return employees;
    }

    public void deleteEmployee(int empno){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from " + EMP_TABLE + " where " + EMP_NO + " = " + empno);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
