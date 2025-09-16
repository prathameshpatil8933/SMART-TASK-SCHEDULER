import java.util.List;

class TaskManager {

    public void deleteTask(List<Task> tasks, int id) {
        // Dummy task with only id used for equals()
        Task dummy = new Task(id, "", "", 0, "");

        boolean removed = tasks.remove(dummy); // internally calls equals()

        if (removed) {
            System.out.println("✅ Task with ID " + id + " removed.");
        } else {
            System.out.println("⚠️ Task with ID " + id + " not found.");
        }

    }

    public void createTask(List<Task>take,int id, String title, String description, int priority, String deadline){
        Task tk=new Task(id, title,  description, priority, deadline);
        take.add(tk);
    }
    // Read
    public void readTask(List<Task> tasks, int id) {
        for (Task t : tasks) {
            if (t.getId() == id) {
                System.out.println("📋 Task found: " + t);
                return;
            }
        }
        System.out.println("⚠️ Task with ID " + id + " not found.");
    }

    // Update
    public void updateTask(List<Task> tasks, int id, String newTitle, String newDesc, int newPriority, String newDeadline) {
        for (Task t : tasks) {
            if (t.getId() == id) {
                t.setTitle(newTitle);
                t.setDescription(newDesc);
                t.setPriority(newPriority);
                t.setDeadline(newDeadline);
                System.out.println("✏️ Task updated: " + t);
                return;
            }
        }
        System.out.println("⚠️ Task with ID " + id + " not found for update.");
    }
}
