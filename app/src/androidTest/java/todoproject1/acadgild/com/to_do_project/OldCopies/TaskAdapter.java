package todoproject1.acadgild.com.to_do_project.OldCopies;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import todoproject1.acadgild.com.to_do_project.R;

public class TaskAdapter extends BaseAdapter {
    private ArrayList<TaskModel> arrayTask;
    private Context context;
    private LayoutInflater layoutInflater;

    public TaskAdapter(Context context, ArrayList<TaskModel> arrayTask) {
        this.context = context;
        this.arrayTask = arrayTask;
        layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
    public View getView(int i, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null) {
            view = layoutInflater.inflate(android.R.layout.simple_expandable_list_item_1, parent, false);
            holder= new ViewHolder();

            holder.title = (TextView) view.findViewById(R.id.toDoTitle);
            holder.description = (TextView) view.findViewById(R.id.toDoDescription);
            holder.dueDate = (TextView)  view.findViewById(R.id.toDoDueDate);
//            ImageView complete = (ImageView) view.findViewById(R.id.imageView);
//            holder.complete = (ImageView) view.findViewById(R.id.imageView);
            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.title.setText(arrayTask.get(i).getTitle());
        holder.description.setText(arrayTask.get(i).getDescription());
        holder.dueDate.setText(arrayTask.get(i).getDate());
        holder.complete.setImageResource(arrayTask.get(i).getCompletePic());
        return view;
    }


    public class ViewHolder{
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
