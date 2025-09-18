import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

class TaskManager {
    Scanner sc=new Scanner(System.in);

    public void createTask(List<Task> tasks) {
        int id;
        while (true) {
            System.out.print("Enter Task ID: ");
            if (sc.hasNextInt()) {
                id = sc.nextInt();
                sc.nextLine();

                boolean duplicate = false;
                for (Task t : tasks) {
                    if (t.getId() == id) {
                        duplicate = true;
                        System.out.println(" Duplicate ID not allowed. Try again.");
                        break;
                    }
                }

                if (!duplicate) break; // valid ID
            } else {
                System.out.println(" Invalid input! Please enter a number.");
                sc.nextLine();
            }
        }

        System.out.print("Enter Title: ");
        String title = sc.nextLine();

        System.out.print("Enter Description: ");
        String description = sc.nextLine();

        int priority;
        while (true) {
            System.out.print("Enter Priority (1-5): ");
            if (sc.hasNextInt()) {
                priority = sc.nextInt();
                sc.nextLine(); // consume newline
                if (priority >= 1 && priority <= 5) break;
                else System.out.println(" Please enter priority between 1 and 5.");
            } else {
                System.out.println(" Invalid input! Please enter a number.");
                sc.nextLine();
            }
        }

        String deadline;
        while (true) {
            System.out.print("Enter Deadline (yyyy-mm-dd): ");
            deadline = sc.nextLine();

            if (deadline == null || deadline.trim().isEmpty()) {
                System.out.println("⚠ Deadline cannot be blank!");
                continue;
            }

            try {
                LocalDate parsedDate = LocalDate.parse(deadline);
                LocalDate today = LocalDate.now();
                if (parsedDate.isBefore(today)) {
                    System.out.println(" Deadline cannot be in the past!");
                } else {
                    break; // valid deadline
                }
            } catch (DateTimeParseException e) {
                System.out.println(" Invalid date format! Please use yyyy-mm-dd.");
            }
        }
        Task tk = new Task(id, title, description, priority, deadline);
        tasks.add(tk);
        System.out.println(" Task added successfully!");
    }

    public void deleteTask(List<Task> tasks, int id) {
        Task dummy = new Task(id, "", "", 0, "");
        boolean removed = tasks.remove(dummy); 
        if (removed) {
            System.out.println(" Task with ID " + id + " removed.");
        } else {
            System.out.println(" Task with ID " + id + " not found.");
        }
    }

    public void readTask(List<Task> tasks, int id) {
        for (Task t : tasks) {
            if (t.getId() == id) {
                System.out.println(" Task found: " + t);
                return;
            }
        }
        System.out.println("Task with ID " + id + " not found.");
    }

    public void updateTask(List<Task> tasks) {
        System.out.print("Enter Task ID to update: ");
        int updateId = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter New Title: ");
        String newTitle = sc.nextLine();

        System.out.print("Enter New Description: ");
        String newDesc = sc.nextLine();

        int newPriority;
        while (true) {
            System.out.print("Enter New Priority (1-5): ");
            if (sc.hasNextInt()) {
                newPriority = sc.nextInt();
                sc.nextLine();
                if (newPriority >= 1 && newPriority <= 5) break;
                else System.out.println(" Please enter priority between 1 and 5.");
            } else {
                System.out.println(" Invalid input! Please enter a number.");
                sc.nextLine();
            }
        }

        System.out.print("Enter New Deadline (yyyy-mm-dd): ");
        String newDeadline = sc.nextLine();
        for (Task t : tasks) {
            if (t.getId() == updateId) {
                t.setTitle(newTitle);
                t.setDescription(newDesc);
                t.setPriority(newPriority);
                t.setDeadline(newDeadline);
                System.out.println(" Task updated: " + t);
                return;
            }
        }
        System.out.println("Task with ID " + updateId + " not found for update.");
    }
}
