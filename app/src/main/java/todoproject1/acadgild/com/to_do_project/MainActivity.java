package todoproject1.acadgild.com.to_do_project;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;

import android.view.View;
import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

import todoproject1.acadgild.com.to_do_project.adapter.TaskAdapter;
import todoproject1.acadgild.com.to_do_project.database.DatabaseHelper;
import todoproject1.acadgild.com.to_do_project.database.Task;

public class MainActivity extends AppCompatActivity  {
    private DatabaseHelper databaseHelper;
    private ListView listview;
    String[] titles = new String[]{"Pay Bill","Bill","Tuesday","reading","Trash"};
    String[] description = new String[]{"Credit Card Bill", "Pay Eletric Bill", "Session at 11am","read books","Take out the trash"};
    String[] date = new String[]{"11/19/2000","5/8/2011","5/2/2012","5/14/2018","5/14/2018"};

    int[] status = new int[]{0,0,0,0,1};
    int[] ids = new int[]{1,2,3,4,5};

    ArrayList<TaskModel> modelArrayList;
    ArrayList<String> array;
    ArrayList<TaskModel> mods;
    TaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar();
//
        mods = new ArrayList<>();
//      reference the listview
        listview = (ListView) findViewById(R.id.listTasks);
//      initialize the array list
        modelArrayList=new ArrayList<>();

//        Code below was for testing purposes of deleting the database and such
        deleteDatabase("task");

//        Create new database helper to deal with database operations
        databaseHelper=new DatabaseHelper(MainActivity.this, Task.TABLE_NAME,null,Task.DATABASE_VERSION);
//        Check count of records if no records insert dummy records.
        int no_of_records = databaseHelper.getRecordCount();
        if (no_of_records ==0){
            insertRecordsInDB();
        }
//        Get the records from the database
        getRecordsFromDB();
//        Load the array list with the values from the database
        loadArrayWithRecords();
//       sort the array list mods by due date
        sortByDueDate();

//        Create an adapter with mods arraylist
        adapter = new TaskAdapter(this, mods);
//      set the adapter on the listview
        listview.setAdapter(adapter);


//      set the item click listener for when they click on the button
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        listItem(position);
                    }
                }
        );
//      Set the item long click listener
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                markItemAsCompleted((int)id);
                return true;
            }
        }
        );
    }
//        Method that loads array list with the values from the database
    private void loadArrayWithRecords() {
        for (int i = 0; i < modelArrayList.size(); i++){
            TaskModel taskModel = new TaskModel();
            taskModel.setDate(modelArrayList.get(i).getDate());
            taskModel.setTitle(modelArrayList.get(i).getTitle());
            taskModel.setDescription(modelArrayList.get(i).getDescription());
            taskModel.setStatus(modelArrayList.get(i).getStatus());
            taskModel.setId(modelArrayList.get(i).getId());
            taskModel.setPicture(R.drawable.incomplete);
            mods.add(taskModel);
        }
    }

    // Method that Edits the item displayed depending on the item clicked
    private void editItemEntry(final int position) {
        final int id;
        LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
        final View view = layoutInflater.inflate(R.layout.add_task_dialog_fragment,null);
        final AlertDialog.Builder editAlertDialogBuilder = new AlertDialog.Builder(this);
        editAlertDialogBuilder.setTitle("Edit Values");
        editAlertDialogBuilder.setView(view);

        id = mods.get(position).getId();

        editAlertDialogBuilder.setCancelable(false).setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText title = view.findViewById(R.id.dialogTitle);
                EditText description = view.findViewById(R.id.dialogDescription);
                if (title.getText().toString().length() <= 0 && description.getText().toString().length() <= 0  ){
                    Toast.makeText(MainActivity.this,"Error! \nTitle and Description can not be left blank",Toast.LENGTH_LONG).show();
                    dialog.cancel();;
                }
                else if (title.getText().toString().length() <= 0  ){
                    Toast.makeText(MainActivity.this,"Error! \nTitle can not be left blank",Toast.LENGTH_LONG).show();
                    dialog.cancel();;
                }
                else if (description.getText().toString().length() <= 0 ){
                    Toast.makeText(MainActivity.this,"Error! \nDescription can not be left blank",Toast.LENGTH_LONG).show();
                    dialog.cancel();;
                }
                else {
                    editAlertDialogBuilder.setView(view);
                    DatePicker editDatePicker = view.findViewById(R.id.datePicker);
                    int day, month, year;
                    day = editDatePicker.getDayOfMonth();
                    month = editDatePicker.getMonth();
                    year = editDatePicker.getYear();
                    ContentValues contentValues = new ContentValues();

                    String date = "" + month + "/" + day + "/" + year;
                    contentValues.put(Task.KEY_TITLE, title.getText().toString());
                    contentValues.put(Task.KEY_DESCRIPTION, description.getText().toString());
                    contentValues.put(Task.KEY_DATE, date);

                    databaseHelper.editDatabaseItem(contentValues, id);
                    TaskModel editTask = new TaskModel();
                    editTask.setTitle(title.getText().toString());
                    editTask.setDescription(description.getText().toString());
                    editTask.setDate(date);
                    mods.set(position, editTask);
                    adapter.notifyDataSetChanged();

                }
            }
        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        editAlertDialogBuilder.show();
    }

// This displays the item that was clicked and asks if you want to edit it
    public void listItem(int id){
        final int itemPosition = id ;

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Edit item?");
        String date = mods.get(id).getDate();
        String title = mods.get(id).getTitle();
        String description = mods.get(id).getDescription();
        String message = title + "\n" + description + " \n" + date;

        alertDialogBuilder.setMessage(message);

        alertDialogBuilder.setCancelable(false).setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                editItemEntry(itemPosition);
            }
        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialogBuilder.show();
    }



    private void markItemAsCompleted(int id) {
        final int itemPosition = id ;

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Set Item as completed?");
        CharSequence ask[] = new CharSequence[]{mods.get(id).getTitle(),mods.get(id).getDescription(),mods.get(id).getDate()};
        String date = mods.get(id).getDate();
        String title = mods.get(id).getTitle();
        String description = mods.get(id).getDescription();
        String message = title + "\n" + description + " \n" + date;
        final int idToSetDone = mods.get(id).getId();
        alertDialogBuilder.setMessage(message);

        alertDialogBuilder.setCancelable(false).setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                databaseHelper.setListItemAsCompleted(idToSetDone);
                removeItemFromArrayList(itemPosition);
                adapter.notifyDataSetChanged();
//                databaseHelper.deleteTaskItem(removeId);


            }
        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
//        alertDialogBuilder.setItems(ask, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//
//                if (which ==0) {
//                    databaseHelper.deleteTaskItem(removeId);
//                    removeItemFromArrayList(removeId);
//                    adapter.notifyDataSetChanged();
//                }
//                else {
//
//                }
//
//            }
//        });


        alertDialogBuilder.show();


    }

    //    Remove the item thats deleted or marked as completed from the array from the adapter
    private void removeItemFromArrayList(long id) {
        for( int i = 0; i < mods.size();i++){
            if (mods.get(i).getId()==(int)id){
                mods.remove(i);
            }
        }
    }

//    Get all unfinished tasks from the database
    private void getRecordsFromDB() {
        modelArrayList=databaseHelper.getAllUnfinishedTasks();
    }

//    Insert new records into the database.
    private void insertRecordsInDB() {
        for(int i=0; i<titles.length;i++){
            TaskModel taskModel = new TaskModel();
            taskModel.setTitle(titles[i]);
            taskModel.setDescription(description[i]);
            taskModel.setDate(date[i]);
            taskModel.setStatus(status[i]);
            modelArrayList.add(taskModel);

            ContentValues contentValues=new ContentValues();
            contentValues.put(Task.KEY_ID,ids[i]);
            contentValues.put(Task.KEY_TITLE,titles[i]);
            contentValues.put(Task.KEY_DESCRIPTION,description[i]);
            contentValues.put(Task.KEY_DATE,date[i]);
            contentValues.put(Task.KEY_STATUS,status[i]);


            databaseHelper.insertRecordDB(contentValues);
        }
    }
    //    Method that ovverides the menu and tells it to inflate and populate
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater= getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }
//    Method that ovverides what to do when item is selected from menu
    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item){
        switch (item.getItemId()) {
            case R.id.menuOptionCompletedTask:
                Intent intent = new Intent(MainActivity.this, FinishedTasks.class);
                startActivity(intent);
                break;

            case R.id.menuOptionNewTask:
                showDialogTask();
                break;

        }
        return true;
    }


    //Pops up the dialog needed to add a new un finished task
    private void showDialogTask() {
        LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
        View view = layoutInflater.inflate(R.layout.add_task_dialog_fragment,null);
        AlertDialog.Builder alertDialogBilder = new AlertDialog.Builder(this);
        alertDialogBilder.setView(view);

        final EditText titleDialog =  view.findViewById(R.id.dialogTitle);
        final EditText descriptionDialog =  view.findViewById(R.id.dialogDescription);
        final DatePicker datePicker = view.findViewById(R.id.datePicker);

        alertDialogBilder.setCancelable(false).setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        final AlertDialog alertDialog = alertDialogBilder.create();
        alertDialog.show();


        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (titleDialog.getText().toString().length() <= 0 && descriptionDialog.getText().toString().length() <= 0  ){
                    Toast.makeText(MainActivity.this,"Error! \nTitle and Description can not be left blank",Toast.LENGTH_LONG).show();
                }
                else if (titleDialog.getText().toString().length() <= 0  ){
                    Toast.makeText(MainActivity.this,"Error! \nTitle can not be left blank",Toast.LENGTH_LONG).show();
                }
                else if (descriptionDialog.getText().toString().length() <= 0 ){
                    Toast.makeText(MainActivity.this,"Error! \nDescription can not be left blank",Toast.LENGTH_LONG).show();
                }
                else {
                    int day, month, year;
                    day = datePicker.getDayOfMonth();
                    month = datePicker.getMonth();
                    year = datePicker.getYear();
                    String title = titleDialog.getText().toString();
                    String description = descriptionDialog.getText().toString();
                    String date = "" + month + "/" + day+ "/" + year;
                    int id = databaseHelper.getRecordCount();

                    TaskModel addThisToList = new TaskModel();
                    addThisToList.setStatus(0);
                    addThisToList.setDescription(description);
                    addThisToList.setTitle(title);
                    addThisToList.setDate(date);
                    addThisToList.setId(id);
                    addThisToList.setPicture(R.drawable.incomplete);
                    mods.add(addThisToList);
                    insertNewRecordIntoDB(title, description, date);
                    sortByDueDate();
                    adapter.notifyDataSetChanged();
                    alertDialog.dismiss();
                }
            }
        });

    }


//    private void menuCreateNewTaskClicked() {
//        final Dialog dialog = new Dialog(MainActivity.this, android.R.style.Theme_Translucent);
//
//        dialog.setContentView(R.layout.add_task_dialog_fragment);
//
////        Button dialogButtonCancel = (Button) dialog.findViewById(R.id.buttonCancel);
////        Button dialogButtonSave = (Button) dialog.findViewById(R.id.buttonSave);
//
//
//
//
//        dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //Dismiss the dialog box get rid of it
//                dialog.dismiss();
//            }
//        });
//
//        dialogButtonSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                showDialogTask();
//
//                EditText titleDialog =  dialog.findViewById(R.id.dialogTitle);
//                EditText descriptionDialog =  dialog.findViewById(R.id.dialogDescription);
//                DatePicker datePicker = dialog.findViewById(R.id.datePicker);
//                int day, month, year;
//                String title, description;
//
//
//                day = datePicker.getDayOfMonth();
//                month = datePicker.getMonth();
//                year = datePicker.getYear();
//
//                final String date = "" + day + "/" + month + "/" + year;
//
//                title = titleDialog.getText().toString();
//                description = descriptionDialog.getText().toString();
//
//
//                Log.i("This is dialog title", title);
//                insertNewRecordIntoDB(title, description, date);
//
////                taskAdapter.notifyDataSetChanged();
////                listview.deferNotifyDataSetChanged();
////                taskAdapter.notifyItemRemoved();
//                //Dismiss the dialog box get rid of it
//                dialog.dismiss();
//
//            }
//        });
//
//        dialog.show();
//    }

// Insert the new task into the database depending on what was entered by the user
    private void insertNewRecordIntoDB(String title, String description, String date ) {
        int id = databaseHelper.getRecordCountLast();
        ContentValues contentValues=new ContentValues();
        contentValues.put(Task.KEY_TITLE, title);
        contentValues.put(Task.KEY_DESCRIPTION, description);
        contentValues.put(Task.KEY_ID,id);
        contentValues.put(Task.KEY_STATUS,0);
        contentValues.put(Task.KEY_DATE,date);
        databaseHelper.insertRecordDB(contentValues);
    }
//  Sort the list by due dates
    private void sortByDueDate(){
        Collections.sort(mods,TaskModel.dateComparator);
    }

}
