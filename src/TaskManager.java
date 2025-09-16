import java.util.List;

public class TaskManager {
    public void addTask(){

    }
    public List<Task> deleteTask(List<Task> tasks, Integer id) {
        if (id == null) {
            System.out.println("Please enter a valid ID");
            return tasks;
        }

        Task toRemove = null;
        for (Task t : tasks) {
            if (t.getId() == id) {   // ✅ match Task.id with given id
                toRemove = t;
                break;
            }
        }

        if (toRemove != null) {
            tasks.remove(toRemove);
            System.out.println("✅ Task with ID " + id + " removed.");
        } else {
            System.out.println("⚠️ Task with ID " + id + " not found.");
        }

        return tasks;
    }
}
