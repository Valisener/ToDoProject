package todoproject1.acadgild.com.to_do_project;

import android.support.annotation.NonNull;

import java.util.Comparator;

public class TaskModel {
    private int id;
    private String title;
    private String description;
    private String date;
    private int picture;
//mostly getters and setters
    public int getPicture() {
        return picture;
    }

    public void setPicture(int picture) {
        this.picture = picture;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

//compare the objects
    public static Comparator<TaskModel> dateComparator= new Comparator<TaskModel>() {

        @Override
        public int compare(TaskModel o1, TaskModel o2) {
            String o1Date = o1.getDate();
            String o2Date = o2.getDate();
            return o2Date.compareTo(o1Date);
        }

    };

}
