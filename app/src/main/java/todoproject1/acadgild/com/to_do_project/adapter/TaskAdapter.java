package todoproject1.acadgild.com.to_do_project.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.HashMap;

import todoproject1.acadgild.com.to_do_project.R;
import todoproject1.acadgild.com.to_do_project.TaskModel;

public class TaskAdapter extends BaseAdapter {
    private ArrayList<TaskModel> arrayTask;
    private Context context;
    private LayoutInflater layoutInflater;


    public TaskAdapter(Context context, ArrayList<TaskModel> arrayTask) {
//        super(context,R.layout.customlayout);
        this.context = context;
        this.arrayTask = arrayTask;
    }

    @Override
    public int getCount() {
        return arrayTask.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayTask.get(position);
    }

    @Override
    public long getItemId(int position) {
        return (long) position;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewHolder holder = new ViewHolder();


//        arrayTask.get(0).getDate().sort.

//        if (view == null) {
//            LayoutInflater inflater = context.getLayoutInflater();
//            final View v = inflater.inflate(R.layout.customlayout, null);

//            view = layoutInflater.inflate(android.R.layout.simple_expandable_list_item_1, viewGroup,false);
//
//            holder.dueDate = (TextView) view.findViewById(R.id.toDoDueDate);
//            view.setTag(holder);


//            holder= new ViewHolder();
//            HashMap<String, String> hashMap = new HashMap<>();
//            holder.title = (TextView) view.findViewById(R.id.toDoTitle);
//            holder.description= (TextView) view.findViewById(R.id.toDoDescription);
//            holder.dueDate = (TextView) view.findViewById(R.id.toDoDueDate);
//            holder.complete = (ImageView) view.findViewById(R.id.imageView);
//            view.set



//            TextView dueDate = (TextView) view.findViewById(R.id.toDoDueDate);

//            view.setTag(dueDate);


            view = layoutInflater.inflate(R.layout.customlayout, viewGroup, false);

            TextView title = (TextView) view.findViewById(R.id.toDoTitle);
            TextView description = (TextView) view.findViewById(R.id.toDoDescription);
            TextView dueDate = (TextView) view.findViewById(R.id.toDoDueDate);
            ImageView complete = (ImageView) view.findViewById(R.id.imageView);
            TextView topDueDate = (TextView) view.findViewById(R.id.topDueDate);

            topDueDate.setText(arrayTask.get(i).getDate());

            title.setText(arrayTask.get(i).getTitle());
            description.setText(arrayTask.get(i).getDescription());
            dueDate.setText(arrayTask.get(i).getDate());
//            complete.setImageResource(R.drawable.incomplete);
        complete.setImageResource(arrayTask.get(i).getPicture());


//            ImageView imageView = (ImageView) view.findViewById(R.id.imageView);

//            holder.title = title;
//            holder.description = description;
//            holder.dueDate = dueDate;
//            holder.complete = complete;

//            view.setTag(holder);

//            holder.title.getText(arrayTask.get(i));
//        }
//            holder.complete = (ImageView) view.findViewById(R.id.imageView);
//            view.setTag(holder);
//            arrayTask.get(i).g
//            hashMap = arrayTask.get(i);
//            complete.setImageResource(arrayTask.get(i).getCompletePic());
//        TaskModel task = new TaskModel();
//        task.get
//        holder.title.setText();
//            title.setText(arrayTask.get(i).getTitle());
//            description.setText(arrayTask.get(i).getDescription());
//            dueDate.setText(arrayTask.get(i).getDate());
//            imageView.setImageResource(context.getResources().getDrawable(R.mipmap.ic_launcher));
//            imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.complete));







//        else {
//            view = view.getTag();
//        }


//        holder.title.setText(arrayTask.get(i).getTitle());
//        holder.description.setText(arrayTask.get(i).getDescription());
//        holder.dueDate.setText(arrayTask.get(i).getDate());
//        holder.complete.setImageResource(arrayTask.get(i).getCompletePic());
            return view;
        }


    public static class ViewHolder{
        TextView view;
        TextView title;
        TextView description;
        TextView dueDate;
        ImageView complete;
    }

//    TextView title= view.findViewById(R.id.toDoTitle);
//    TextView description = view.findViewById(R.id.toDoDescription);
//    TextView dueDate= view.findViewById(R.id.toDoDueDate);
//    ImageView complete = view.findViewById(R.id.imageView);


    }
