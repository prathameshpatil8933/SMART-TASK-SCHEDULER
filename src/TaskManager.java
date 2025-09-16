import java.util.List;

class TaskManager {
    public List<Task> deleteTask(List<Task> tasks, int id) {
        // Dummy task with only id used for equals()
        Task dummy = new Task(id, "", "", 0, "");

        boolean removed = tasks.remove(dummy); // internally calls equals()

        if (removed) {
            System.out.println("✅ Task with ID " + id + " removed.");
        } else {
            System.out.println("⚠️ Task with ID " + id + " not found.");
        }
        return tasks;
    }
}
