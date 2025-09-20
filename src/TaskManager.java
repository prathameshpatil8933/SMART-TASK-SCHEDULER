import java.time.LocalDate;
import java.util.*;

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
        System.out.println("Task created: ");
        System.out.println(task);

        System.out.println("Do you want to undo this creation? (y/n)");
        char ip = sc.next().charAt(0);
        if (ip == 'y' || ip == 'Y') {
            tasks.remove(id);
            System.out.println("Task creation undone!");
        } else {
            System.out.println("Task kept!");
        }


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


    public String deleteTask(Map<Integer, Task> tasks, int id, Scanner sc) {
        Task deletedTask = tasks.get(id); // store the task for undo
        String result;

        if (deletedTask != null) {
            tasks.remove(id);
            result = "Task with ID=" + id + " removed.";
            System.out.println(result);


            while (true) {
                System.out.print("Do you want to undo this deletion? (y/n): ");
                char undoChoice = sc.next().charAt(0);
                sc.nextLine();

                if (undoChoice == 'y' || undoChoice == 'Y') {
                    tasks.put(id, deletedTask);
                    System.out.println("Task deletion undone! Task restored.");

                    // Redo option when undo
                    while (true) {
                        System.out.print("Do you want to redo this deletion? (y/n): ");
                        char redoChoice = sc.next().charAt(0);
                        sc.nextLine();

                        if (redoChoice == 'y' || redoChoice == 'Y') {
                            tasks.remove(id);
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

        } else {
            result = "No task found with ID=" + id;
            System.out.println(result);
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

        // Store old values for undo
        String oldTitle = t.getTitle();
        String oldDescription = t.getDescription();
        int oldPriority = t.getPriority();
        LocalDate oldDeadline = t.getDeadline();

        // Apply new values
        if (newTitle != null) t.setTitle(newTitle);
        if (newDescription != null) t.setDescription(newDescription);
        if (newPriority != null) t.setPriority(newPriority);
        if (newDeadline != null) t.setDeadline(newDeadline);

        System.out.println("Task with ID " + id + " updated successfully.");
        System.out.println(t);

        // Ask for undo
        while (true) {
            System.out.print("Do you want to undo this update? (y/n): ");
            char undo = sc.next().charAt(0);
            sc.nextLine();

            if (undo == 'y' || undo == 'Y') {

                t.setTitle(oldTitle);
                t.setDescription(oldDescription);
                t.setPriority(oldPriority);
                t.setDeadline(oldDeadline);

                System.out.println("Undo done. Task reverted:");
                System.out.println(t);


                while (true) {
                    System.out.print("Do you want to redo this update? (y/n): ");
                    char redo = sc.next().charAt(0);
                    sc.nextLine();

                    if (redo == 'y' || redo == 'Y') {
                        t.setTitle(newTitle);
                        t.setDescription(newDescription);
                        t.setPriority(newPriority);
                        t.setDeadline(newDeadline);

                        System.out.println("Redo done. Task updated again:");
                        System.out.println(t);
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

