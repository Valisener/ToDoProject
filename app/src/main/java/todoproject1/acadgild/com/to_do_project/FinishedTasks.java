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

public class FinishedTasks extends AppCompatActivity {
    private DatabaseHelper databaseHelper;
    private ListView listview;
    ArrayList<TaskModel> modelArrayList;
    ArrayList<String> array;
    ArrayList<TaskModel> finishedTasks;
    TaskAdapter adapter;
    String[] titles = new String[]{};
    String[] description = new String[]{};
    String[] date = new String[]{};
    int[] status = new int[]{};
    int[] ids = new int[]{};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finished_tasks);

        getSupportActionBar();
//        make array list of finished tasks
        finishedTasks = new ArrayList<>();
//reference listview
        listview = (ListView) findViewById(R.id.listCompletedTasks);
//        make a model array list
        modelArrayList = new ArrayList<>();
//      Create a new database helper that handles database operations
        databaseHelper = new DatabaseHelper(FinishedTasks.this, Task.TABLE_NAME, null, Task.DATABASE_VERSION);

//        deleteDatabase("task");


//      Get the finished tasks from the database
        getRecordsFromDB();
//        Load the array list with the values from the database
        loadArrayWithRecords();
//Sort the finished tasks by due date
        sortByDueDate();
//      Create an adapter with finished tasks arraylist
        adapter = new TaskAdapter(this, finishedTasks);
//      Sets the adapter on the listview
        listview.setAdapter(adapter);


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                listItem(position);

                                            }
                                        }
        );
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                                                @Override
                                                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                                                    showAsDialogLongClick( position);
                                                    adapter.notifyDataSetChanged();
                                                    return true;
                                                }
                                            }
        );
    }
    //        Method that loads array list with the values from the database
    private void loadArrayWithRecords() {
        for (int i = 0; i < modelArrayList.size(); i++) {
            TaskModel taskModel = new TaskModel();
            taskModel.setDate(modelArrayList.get(i).getDate());
            taskModel.setTitle(modelArrayList.get(i).getTitle());
            taskModel.setDescription(modelArrayList.get(i).getDescription());
            taskModel.setStatus(modelArrayList.get(i).getStatus());
            taskModel.setId(modelArrayList.get(i).getId());
            taskModel.setPicture(R.drawable.complete);
            finishedTasks.add(taskModel);
        }
    }

    //    Remove the item thats deleted from the array from the adapter
    private void removeItemFromArrayList(int itemPosition) {
        finishedTasks.remove(itemPosition);
    }

    // This displays the item that was clicked and asks if you want to edit it
    public void listItem(final int id){
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Edit item?");
        String date = finishedTasks.get(id).getDate();
        String title = finishedTasks.get(id).getTitle();
        String description = finishedTasks.get(id).getDescription();
        String message = title + "\n" + description + " \n" + date;

        alertDialogBuilder.setMessage(message);

        alertDialogBuilder.setCancelable(false).setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                editItemEntry(id);
            }
        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialogBuilder.show();
    }

//  Method that fills up the arraylist with all finished tasks from the database
    private void getRecordsFromDB() {
        modelArrayList = databaseHelper.getAllFinishedTasks();

    }
//  Method that inserts the records into the database
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menucompleted, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuOptionIncompleteTask:
                Intent intent = new Intent(FinishedTasks.this, MainActivity.class);
                startActivity(intent);
                break;

            case R.id.menuOptionAddNewFinishedTask:
                showDialogTask();
                adapter.notifyDataSetChanged();
                break;
        }
        return true;
    }

//    Method that asks if you want to delete the selected task
    private void showAsDialogLongClick(final int id) {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Do you want to delete this task?");
        CharSequence ask[] = new CharSequence[]{finishedTasks.get(id).getTitle(), finishedTasks.get(id).getDescription(), finishedTasks.get(id).getDate()};
        String date = finishedTasks.get(id).getDate();
        String title = finishedTasks.get(id).getTitle();
        String description = finishedTasks.get(id).getDescription();
        String message = title + "\n" + description + " \n" + date;
        final int itemId = finishedTasks.get(id).getId();
        alertDialogBuilder.setMessage(message);

        alertDialogBuilder.setCancelable(false).setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                removeItemFromArrayList(id);
                databaseHelper.deleteTaskItem(itemId);
                adapter.notifyDataSetChanged();

            }
        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialogBuilder.show();
    }

// Method that Edits the item displayed depending on the item clicked
    private void editItemEntry(final int position) {
        final int id;
        LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
        final View view = layoutInflater.inflate(R.layout.add_task_dialog_fragment,null);
        final AlertDialog.Builder editAlertDialogBuilder = new AlertDialog.Builder(this);
        editAlertDialogBuilder.setTitle("Edit Values");
        editAlertDialogBuilder.setView(view);

        id = finishedTasks.get(position).getId();

        editAlertDialogBuilder.setCancelable(false).setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText title = view.findViewById(R.id.dialogTitle);
                EditText description = view.findViewById(R.id.dialogDescription);
                if (title.getText().toString().length() <= 0 && description.getText().toString().length() <= 0  ){
                    Toast.makeText(FinishedTasks.this,"Error! \nTitle and Description can not be left blank",Toast.LENGTH_LONG).show();
                    dialog.cancel();;
                }
                else if (title.getText().toString().length() <= 0  ){
                    Toast.makeText(FinishedTasks.this,"Error! \nTitle can not be left blank",Toast.LENGTH_LONG).show();
                    dialog.cancel();;
                }
                else if (description.getText().toString().length() <= 0 ){
                    Toast.makeText(FinishedTasks.this,"Error! \nDescription can not be left blank",Toast.LENGTH_LONG).show();
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

                    String date = "" + day + "/" + month + "/" + year;
                    contentValues.put(Task.KEY_TITLE, title.getText().toString());
                    contentValues.put(Task.KEY_DESCRIPTION, description.getText().toString());
                    contentValues.put(Task.KEY_DATE, date);

                    databaseHelper.editDatabaseItem(contentValues, id);
                    TaskModel editTask = new TaskModel();
                    editTask.setTitle(title.getText().toString());
                    editTask.setDescription(description.getText().toString());
                    editTask.setDate(date);
                    finishedTasks.set(position, editTask);
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


    //Pops up the dialog needed to add a new finished task
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
                    Toast.makeText(FinishedTasks.this,"Error! \nTitle and Description can not be left blank",Toast.LENGTH_LONG).show();
                }
                else if (titleDialog.getText().toString().length() <= 0  ){
                    Toast.makeText(FinishedTasks.this,"Error! \nTitle can not be left blank",Toast.LENGTH_LONG).show();
                }
                else if (descriptionDialog.getText().toString().length() <= 0 ){
                    Toast.makeText(FinishedTasks.this,"Error! \nDescription can not be left blank",Toast.LENGTH_LONG).show();
                }
                else {
                    int day, month, year;
                    day = datePicker.getDayOfMonth();
                    month = datePicker.getMonth();
                    year = datePicker.getYear();
                    String title = titleDialog.getText().toString();
                    String description = descriptionDialog.getText().toString();
                    String date = "" + month + "/" + day + "/" + year;
                    int id = databaseHelper.getRecordCountLast();

                    TaskModel addThisToList = new TaskModel();
                    addThisToList.setStatus(1);
                    addThisToList.setDescription(description);
                    addThisToList.setTitle(title);
                    addThisToList.setDate(date);
                    addThisToList.setId(id);
                    addThisToList.setPicture(R.drawable.complete);
                    finishedTasks.add(addThisToList);
                    insertNewRecordIntoDB(title, description, date);
                    //Sort the finished tasks by due date
                    sortByDueDate();
                    adapter.notifyDataSetChanged();
                    alertDialog.dismiss();
                }
            }
        });

    }

    // Insert the new task into the database depending on what was entered by the user
    private void insertNewRecordIntoDB(String title, String description, String date ) {
        int id = databaseHelper.getRecordCount();
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
        Collections.sort(finishedTasks,TaskModel.dateComparator);
    }

}