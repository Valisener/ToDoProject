package todoproject1.acadgild.com.to_do_project.OldCopies;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import todoproject1.acadgild.com.to_do_project.R;
import todoproject1.acadgild.com.to_do_project.database.DatabaseHelper;
import todoproject1.acadgild.com.to_do_project.database.Task;

public class MainActivity extends AppCompatActivity {
    private DatabaseHelper databaseHelper;
    private ListView listview;
    String[] titles = new String[]{"Pay Bill","Bill","Tuesday","reading"};
    String[] description = new String[]{"Credit Card Bill", "Pay Eletric Bill", "Session at 11am","read books"};
    String[] date = new String[]{"19/11/1914","5/8/2011","5/2/2012","5/14/2018"};
    int[] status = new int[]{0,0,0,0};
    int[] ids = new int[]{1,2,3,4};

    private ArrayList<TaskModel> modelArrayList;
    ArrayList<String> array;
    private ArrayList<String> taskDueDate;
    ArrayList<TaskModel> arrayTask;
    ArrayList<TaskModel> mods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listview = (ListView) findViewById(R.id.listTasks);
        modelArrayList=new ArrayList<>();
        taskDueDate = new ArrayList<>();
        arrayTask = new ArrayList<>();
        TaskAdapter taskAdapter = new TaskAdapter(MainActivity.this,arrayTask);


        databaseHelper=new DatabaseHelper(MainActivity.this, Task.TABLE_NAME,null,Task.DATABASE_VERSION);
        int no_of_records = databaseHelper.getRecordCount();
        if (no_of_records ==0){
            insertRecordsInDB();
        }
        getRecordsFromDB();
//        Getting task titles
        for (int i = 0; i < modelArrayList.size(); i++){
            TaskModel taskModel = new TaskModel();
            taskModel.setDate(modelArrayList.get(i).getDate());
            taskModel.setTitle(modelArrayList.get(i).getTitle());
            taskModel.setDescription(modelArrayList.get(i).getDescription());
//            taskDueDate.add(modelArrayList.get(i).getDate());
            mods.add(taskModel);
        }

        TaskAdapter adapter = new TaskAdapter(this, mods);
//        arrayAdapter.notifyDataSetChanged();
//        android.R.layout.simple_expandable_list_item_1
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                }
        );
        listview.setOnItemLongClickListener(null);


//        adapter = new ArrayAdapter<String>(MainActivity.this,R.layout.customlayout, array);

    }

    private void getRecordsFromDB() {
        modelArrayList=databaseHelper.getAllTasks();
    }

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

    protected boolean onCreateOpttionsMenu(Menu menu) {
        MenuInflater menuInflater= getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);

        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.menuOptionIncompleteTask:


                break;

            case R.id.menuOptionNewTask:


                break;

        }
        return true;
    }

}
