import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

public class TaskManager {


    public Task createTask(Map<Integer, Task> tasks, int id, String title, String description, int priority, LocalDate deadline, Scanner sc) {

        while(true) {
            if (tasks.containsKey(id)) {
                System.out.println("Task ID already exists: " + id);
                System.out.println("Enter the valid ID");
                id=Main.readInt(sc,"The new ID u entered");
            }
            else {
                break;
            }
        }
        Task task = new Task(id, title, description, priority, deadline);
        tasks.put(id, task);
        return task;
    }


    public Task getTaskById(Map<Integer, Task> tasks, int id,Scanner sc) {
        while(true) {
            if (tasks.get(id) != null) {
                 return tasks.get(id);
            } else {
                System.out.println("Enter the valid id");
                Main.readInt(sc,"Enter the valid id");
            }
        }
    }


    public String deleteTask(Map<Integer, Task> tasks, int id) {
        String s;
        if(tasks.remove(id)!=null){
            s="Task with id="+id +" removed ";
        }
        else {
            s="No such task with this ID=" +id ;
        }
        return s;

    }


    public String updateTask(Map<Integer, Task> tasks, int id,
                              String newTitle, String newDescription,
                              Integer newPriority, LocalDate newDeadline)
    {
        Task t = tasks.get(id);
        if (t == null) return "The ID u entered is not valid ";

        if (newTitle != null) t.setTitle(newTitle);
        if (newDescription != null) t.setDescription(newDescription);
        if (newPriority != null) t.setPriority(newPriority);
        if (newDeadline != null) t.setDeadline(newDeadline);
        return "The task with "+id+" is updated succesfully";

    }


    public void showAll(Map<Integer, Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("No tasks available.");
        } else {
            for (Map.Entry<Integer, Task> entry : tasks.entrySet()) {
                System.out.println("ID=" + entry.getKey() + " → " + entry.getValue());
            }
        }
    }

    public void searchByTitle(Map<Integer, Task> tasks, String keyword) {
        boolean found = false;
        for (Task t : tasks.values()) {
            if (t.getTitle().toLowerCase().contains(keyword)) {
                System.out.println(t);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No tasks found with title containing: " + keyword);
        }
    }



}

