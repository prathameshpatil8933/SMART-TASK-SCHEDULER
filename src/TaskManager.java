import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class TaskManager {
    private Stack<Action> undoStack = new Stack<>();
    private Stack<Action> redoStack = new Stack<>();

    public void createTask(Map<Integer, Task> tasks, int id, String title, String description, int priority, LocalDate deadline, LocalTime time, Scanner sc) {
        while(true) {
            if (tasks.containsKey(id)) {
                System.out.println("Task ID already exists: " + id);
                id = Main.readInt(sc, "Enter the valid ID: ");
            } else {
                break;
            }
        }

        Task task = new Task(id, title, description, priority, deadline,time);
        tasks.put(id, task);

        Task newCopy = new Task(task.getId(), task.getTitle(), task.getDescription(), task.getPriority(), task.getDeadline(),task.getTime());
        undoStack.push(new Action(Action.ActionType.CREATE, id, null, newCopy));
        redoStack.clear();

        System.out.println("Task created: ");
        System.out.println(task);

        if (Main.readYesNo(sc, "Do you want to undo this creation?")) {
            undo(tasks);
        } else {
            System.out.println("Task kept!");
        }

    }

    public Task getTaskById(Map<Integer, Task> tasks, int id, Scanner sc) {
        while (true) {
            Task t = tasks.get(id);
            if (t != null) {
                return t;
            } else {
                System.out.println("No task found with ID=" + id + ". Enter a valid id.");
                id = Main.readInt(sc, "Enter the valid id: ");
            }
        }
    }

    public String deleteTask(Map<Integer, Task> tasks, int id, Scanner sc) {
        Task removed = tasks.remove(id);
        String result;

        if (removed == null) {
            result = "No task found with ID=" + id;
            System.out.println(result);
            return result;
        }

        // record action and clear redo
        Task oldCopy = new Task(removed.getId(), removed.getTitle(), removed.getDescription(), removed.getPriority(), removed.getDeadline(),removed.getTime());
        undoStack.push(new Action(Action.ActionType.DELETE, id, oldCopy, null));
        redoStack.clear();

        result = "Task with ID=" + id + " removed.";
        System.out.println(result);

        if (Main.readYesNo(sc, "Do you want to undo this deletion?")) {
            undo(tasks);
            System.out.println("Task deletion undone! Task restored.");

            if (Main.readYesNo(sc, "Do you want to redo this deletion?")) {
                redo(tasks);
                System.out.println("Task deleted again.");
            } else {
                System.out.println("Task kept.");
            }
        } else {
            System.out.println("Task permanently deleted.");
        }

        return result;
    }

    public void updateTask(Map<Integer, Task> tasks, int id,
                           String newTitle, String newDescription,
                           Integer newPriority, LocalDate newDeadline,
                           LocalTime newTime, Scanner sc) {

        Task t = tasks.get(id);
        if (t == null) {
            System.out.println("The ID you entered is not valid.");
            return;
        }

        Task oldCopy = new Task(t.getId(), t.getTitle(), t.getDescription(), t.getPriority(), t.getDeadline(),t.getTime());

        if (newTitle != null) t.setTitle(newTitle);
        if (newDescription != null) t.setDescription(newDescription);
        if (newPriority != null) t.setPriority(newPriority);
        if (newDeadline != null) t.setDeadline(newDeadline);

        Task newCopy = new Task(t.getId(), t.getTitle(), t.getDescription(), t.getPriority(), t.getDeadline(),t.getTime());

        undoStack.push(new Action(Action.ActionType.UPDATE, id, oldCopy, newCopy));
        redoStack.clear();

        System.out.println("Task with ID " + id + " updated successfully.");
        System.out.println(t);

        if (Main.readYesNo(sc, "Do you want to undo this update?")) {
            undo(tasks);
            System.out.println("Undo done. Task reverted:");
            System.out.println(tasks.get(id));

            if (Main.readYesNo(sc, "Do you want to redo this update?")) {
                redo(tasks);
                System.out.println("Redo done. Task updated again:");
                System.out.println(tasks.get(id));
            } else {
                System.out.println("Redo skipped. Task kept in reverted state.");
            }
        } else {
            System.out.println("Update kept.");
        }
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
            if (t.getTitle().toLowerCase().contains(keyword.toLowerCase())) {
                System.out.println(t);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No tasks found with title containing: " + keyword);
        }
    }

    // Undo: pop from undoStack and push into redoStack
    public void undo(Map<Integer, Task> tasks) {
        if (undoStack.isEmpty()) {
            System.out.println("Nothing to undo.");
            return;
        }
        Action lastAction = undoStack.pop();
        redoStack.push(lastAction);

        switch (lastAction.type) {
            case CREATE:
                tasks.remove(lastAction.taskId);
                System.out.println("Undo: Task creation reverted (ID=" + lastAction.taskId + ").");
                break;
            case UPDATE:
                tasks.put(lastAction.taskId, lastAction.oldTask);
                System.out.println("Undo: Task update reverted (ID=" + lastAction.taskId + ").");
                break;
            case DELETE:
                tasks.put(lastAction.taskId, lastAction.oldTask);
                System.out.println("Undo: Task deletion reverted (ID=" + lastAction.taskId + ").");
                break;
        }
    }

    public void redo(Map<Integer, Task> tasks) {
        if (redoStack.isEmpty()) {
            System.out.println("Nothing to redo.");
            return;
        }

        Action lastUndone = redoStack.pop();
        undoStack.push(lastUndone);

        switch (lastUndone.type) {
            case CREATE:
                tasks.put(lastUndone.taskId, lastUndone.newTask);
                System.out.println("Redo: Task creation redone for ID=" + lastUndone.taskId);
                break;
            case DELETE:
                tasks.remove(lastUndone.taskId);
                System.out.println("Redo: Task deletion redone for ID=" + lastUndone.taskId);
                break;
            case UPDATE:
                tasks.put(lastUndone.taskId, lastUndone.newTask);
                System.out.println("Redo: Task update redone for ID=" + lastUndone.taskId);
                break;
        }
    }
}
