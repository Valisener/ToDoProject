package todoproject1.acadgild.com.to_do_project.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import todoproject1.acadgild.com.to_do_project.R;
import todoproject1.acadgild.com.to_do_project.TaskModel;

public class DatabaseHelper extends SQLiteOpenHelper{

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//Creates the table
        String CREATE_TABLE = "CREATE TABLE " +
                Task.TABLE_NAME + "(" +
                Task.KEY_ID + " INTEGER PRIMARY KEY," +
                Task.KEY_TITLE + " TEXT," +
                Task.KEY_DESCRIPTION + " TEXT," +
                Task.KEY_DATE + " TEXT," +
                Task.KEY_STATUS + " INTEGER" +
                ")";

//executes the database create table
        db.execSQL(CREATE_TABLE);
    }
// Not needed
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
//Gets all the counted tasks from the database
    public int getRecordCount() {
        int row_count = 0;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(false,Task.TABLE_NAME,null,null,null,null,null,null,null) ;
        if (cursor != null){
            cursor.moveToFirst();
            row_count = cursor.getCount();
        }
        return row_count;
    }
//method that inserts records into the database
    public void insertRecordDB(ContentValues contentValues) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            sqLiteDatabase.insert(Task.TABLE_NAME, null, contentValues);
        } catch(Exception e){
            e.printStackTrace();
        }
    }
//Method that edits the database item that needs to be edited with whatever information
    public void editDatabaseItem(ContentValues contentValues, int id){
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            sqLiteDatabase.update(Task.TABLE_NAME, contentValues, Task.KEY_ID + " = ?", new String[]{String.valueOf(id)});
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
//method that gets all finished tasks
    public ArrayList<TaskModel> getAllFinishedTasks() {
        ArrayList<TaskModel> arrayList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "select * from "+ Task.TABLE_NAME + " where " + Task.KEY_STATUS + "=1";
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);
        if (cursor != null){
            cursor.moveToFirst();
            do {
                TaskModel taskModel = new TaskModel();
                taskModel.setId(cursor.getInt(0));
                taskModel.setTitle(cursor.getString(1));
                taskModel.setDescription(cursor.getString(2));
                taskModel.setDate(cursor.getString(3));
                taskModel.setStatus(cursor.getInt(4));
//                taskModel.setPicture((R.drawable.incomplete));
                arrayList.add(taskModel);
            } while (cursor.moveToNext()) ;
        }
        return arrayList;
    }
//Method that gets all unfinished tasks
    public ArrayList<TaskModel> getAllUnfinishedTasks() {
        ArrayList<TaskModel> arrayList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "select * from "+ Task.TABLE_NAME + " where " + Task.KEY_STATUS + "=0";
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);
        if (cursor != null){
            cursor.moveToFirst();
            do {
                TaskModel taskModel = new TaskModel();
                taskModel.setId(cursor.getInt(0));
                taskModel.setTitle(cursor.getString(1));
                taskModel.setDescription(cursor.getString(2));
                taskModel.setDate(cursor.getString(3));
                taskModel.setStatus(cursor.getInt(4));
//                taskModel.setPicture((R.drawable.complete));
                arrayList.add(taskModel);
            } while (cursor.moveToNext()) ;
        }
        return arrayList;
    }

// get all the tasks from the database
    public ArrayList<TaskModel> getAllTasks() {
        ArrayList<TaskModel> arrayList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "select * from "+ Task.TABLE_NAME;
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);
        if (cursor != null){
            cursor.moveToFirst();
            do {
                TaskModel taskModel = new TaskModel();
                taskModel.setId(cursor.getInt(0));
                taskModel.setTitle(cursor.getString(1));
                taskModel.setDescription(cursor.getString(2));
                taskModel.setDate(cursor.getString(3));
                taskModel.setStatus(cursor.getInt(4));
                arrayList.add(taskModel);

            } while (cursor.moveToNext()) ;
        }
        return arrayList;
    }
//Method that deletes the task item
    public void deleteTaskItem(long itemId) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        try {
        String number = String.valueOf(itemId);
        String delete = "DELETE FROM " + Task.TABLE_NAME + " WHERE " + Task.KEY_ID + "=" + number + "";
        sqLiteDatabase.execSQL(delete);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
//method that sets the item clicked as completed
    public void setListItemAsCompleted(int idMarkAsComplete) {
        String query = "UPDATE " +
                Task.TABLE_NAME + " SET " +
                Task.KEY_STATUS + "= '1'" + " WHERE " +
                Task.KEY_ID + " = " + idMarkAsComplete + ";";
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            sqLiteDatabase.execSQL(query);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
//Gets the highest id from the table and adds one to it so when its added to the table its not adding the same id
    public int getRecordCountLast() {
        ArrayList<TaskModel> arrayList = new ArrayList<>();
        int idToUse = 0;
        arrayList = getAllTasks();
        for (TaskModel task: arrayList){
            if (idToUse < task.getId()){
                idToUse = task.getId() + 1;
            }
        }
        return idToUse;
    }
}
