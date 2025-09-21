import java.time.LocalDate;
import java.util.*;

public class TaskManager {
    private Stack<Action> undoStack = new Stack<>();
    private Stack<Action> redoStack = new Stack<>();

    public Task createTask(Map<Integer, Task> tasks, int id, String title, String description, int priority, LocalDate deadline, Scanner sc) {
        while(true) {
            if (tasks.containsKey(id)) {
                System.out.println("Task ID already exists: " + id);
                id = Main.readInt(sc, "Enter the valid ID: ");
            } else {
                break;
            }
        }

        Task task = new Task(id, title, description, priority, deadline);
        tasks.put(id, task);

        // record action and clear redo history
        undoStack.push(new Action(Action.ActionType.CREATE, id, null, new Task(task.getId(), task.getTitle(), task.getDescription(), task.getPriority(), task.getDeadline())));
        redoStack.clear();

        System.out.println("Task created: ");
        System.out.println(task);

        // immediate undo prompt (reads input repeatedly until valid)
        while (true) {
            System.out.print("Do you want to undo this creation? (y/n): ");
            String line = sc.nextLine().trim();
            if (line.isEmpty()) continue;
            char ip = line.charAt(0);
            if (ip == 'y' || ip == 'Y') {
                undo(tasks);
                break;
            } else if (ip == 'n' || ip == 'N') {
                System.out.println("Task kept!");
                break;
            } else {
                System.out.println("Enter valid option from (y/n).");
            }
        }

        return task;
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
        Task oldCopy = new Task(removed.getId(), removed.getTitle(), removed.getDescription(), removed.getPriority(), removed.getDeadline());
        undoStack.push(new Action(Action.ActionType.DELETE, id, oldCopy, null));
        redoStack.clear();

        result = "Task with ID=" + id + " removed.";
        System.out.println(result);

        // immediate undo prompt
        while (true) {
            System.out.print("Do you want to undo this deletion? (y/n): ");
            String line = sc.nextLine().trim();
            if (line.isEmpty()) continue;
            char undoChoice = line.charAt(0);

            if (undoChoice == 'y' || undoChoice == 'Y') {
                undo(tasks);
                System.out.println("Task deletion undone! Task restored.");

                // offer redo after undo
                while (true) {
                    System.out.print("Do you want to redo this deletion? (y/n): ");
                    String r = sc.nextLine().trim();
                    if (r.isEmpty()) continue;
                    char redoChoice = r.charAt(0);

                    if (redoChoice == 'y' || redoChoice == 'Y') {
                        redo(tasks);
                        System.out.println("Task deleted again.");
                        break;
                    } else if (redoChoice == 'n' || redoChoice == 'N') {
                        System.out.println("Task kept.");
                        break;
                    } else {
                        System.out.println("Invalid input! Please enter y or n.");
                    }
                }
                break;
            } else if (undoChoice == 'n' || undoChoice == 'N') {
                System.out.println("Task permanently deleted.");
                break;
            } else {
                System.out.println("Invalid input! Please enter y or n.");
            }
        }

        return result;
    }

    public void updateTask(Map<Integer, Task> tasks, int id,
                           String newTitle, String newDescription,
                           Integer newPriority, LocalDate newDeadline,
                           Scanner sc) {

        Task t = tasks.get(id);
        if (t == null) {
            System.out.println("The ID you entered is not valid.");
            return;
        }

        // old snapshot and apply new values
        Task oldCopy = new Task(t.getId(), t.getTitle(), t.getDescription(), t.getPriority(), t.getDeadline());

        if (newTitle != null) t.setTitle(newTitle);
        if (newDescription != null) t.setDescription(newDescription);
        if (newPriority != null) t.setPriority(newPriority);
        if (newDeadline != null) t.setDeadline(newDeadline);

        Task newCopy = new Task(t.getId(), t.getTitle(), t.getDescription(), t.getPriority(), t.getDeadline());

        // push action & clear redo
        undoStack.push(new Action(Action.ActionType.UPDATE, id, oldCopy, newCopy));
        redoStack.clear();

        System.out.println("Task with ID " + id + " updated successfully.");
        System.out.println(t);

        // Ask for immediate undo (reads input safely)
        while (true) {
            System.out.print("Do you want to undo this update? (y/n): ");
            String line = sc.nextLine().trim();
            if (line.isEmpty()) continue;
            char undo = line.charAt(0);

            if (undo == 'y' || undo == 'Y') {
                undo(tasks);
                System.out.println("Undo done. Task reverted:");
                System.out.println(tasks.get(id));

                // redo prompt after undo
                while (true) {
                    System.out.print("Do you want to redo this update? (y/n): ");
                    String r = sc.nextLine().trim();
                    if (r.isEmpty()) continue;
                    char redo = r.charAt(0);

                    if (redo == 'y' || redo == 'Y') {
                        redo(tasks);
                        System.out.println("Redo done. Task updated again:");
                        System.out.println(tasks.get(id));
                        break;
                    } else if (redo == 'n' || redo == 'N') {
                        System.out.println("Redo skipped. Task kept in reverted state.");
                        break;
                    } else {
                        System.out.println("Please enter valid option (y/n).");
                    }
                }
                break;
            } else if (undo == 'n' || undo == 'N') {
                System.out.println("Update kept.");
                break;
            } else {
                System.out.println("Please enter valid option (y/n).");
            }
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

    // Correct undo: pop from undoStack and push into redoStack
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

    // Redo pops from redoStack and pushes into undoStack (keeps history)
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
