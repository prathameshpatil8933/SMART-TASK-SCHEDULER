import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

public class TaskManager {


    public Task createTask(Map<Integer, Task> tasks, int id, String title, String description, int priority, LocalDate deadline) {
        int choice=0;
        if (tasks.containsKey(id)) {
            System.out.println("Task ID already exists: " + id);
            choice=1;
        }
        if (priority < 1 || priority > 5) {
            System.out.println("Priority must be between 1 and 5.");
            choice=2;
        }
        while(true) {
            break;

        }
        Task task = new Task(id, title, description, priority, deadline);
        tasks.put(id, task);
        return task;
    }


    public Optional<Task> getTaskById(Map<Integer, Task> tasks, int id) {
        return Optional.ofNullable(tasks.get(id));
    }


    public boolean deleteTask(Map<Integer, Task> tasks, int id) {
        return tasks.remove(id) != null;
    }


    public boolean updateTask(Map<Integer, Task> tasks, int id, String newTitle, String newDescription, int newPriority, LocalDate newDeadline) {
        Task t = tasks.get(id);
        if (t == null) return false;
        t.setTitle(newTitle);
        t.setDescription(newDescription);
        t.setPriority(newPriority);
        t.setDeadline(newDeadline);
        return true;
    }


    public void showAll(Map<Integer, Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("No tasks available.");
        } else {
            tasks.values().forEach(System.out::println);
        }
    }

    public void searchByTitle(Map<Integer, Task> tasks, String keyword) {
        boolean found = false;
        for (Task t : tasks.values()) {
            if (t.getTitle().toLowerCase().contains(keyword.toLowerCase())) {
                System.out.println(t);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No tasks found with title containing: " + keyword);
        }
    }
}
