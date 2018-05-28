package todoproject1.acadgild.com.to_do_project.OldCopies;

public class TaskModel {
    private int id;
    private String title;
    private String description;
    private String date;
    private int completePic;

    public int getCompletePic() {
        return completePic;
    }

    public void setCompletePic(int completePic) {
        this.completePic = completePic;
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


}
