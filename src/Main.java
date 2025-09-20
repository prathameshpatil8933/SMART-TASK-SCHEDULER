import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        TaskManager tm = new TaskManager();
        Map<Integer, Task> tasks = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        boolean running = true;
        while (running) {
            System.out.println("\n==== Task Manager ====");
            System.out.println("1. Create Task");
            System.out.println("2. Delete Task");
            System.out.println("3. Update Task");
            System.out.println("4. Read Task (by ID)");
            System.out.println("5. Show All Tasks");
            System.out.println("6. Exit");
            System.out.println("7. Search by Title");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1: // Create Task
                    int id = readInt(sc, "Enter ID: ");
                    String title = readTitle(sc, "Enter title: ");
                    String desc = readDescription(sc, "Enter description: ");
                    int priority = readPriority(sc, "Enter priority (1-5): ");
                    LocalDate deadline = readDate(sc, formatter, "Enter deadline (dd/MM/yyyy): ");

                    Task newTask = tm.createTask(tasks, id, title, desc, priority, deadline);
                    System.out.println("Task created: " + newTask);
                    break;

                case 2: // Delete Task
                    int delId = readInt(sc, "Enter ID to delete: ");
                    System.out.println(tm.deleteTask(tasks, delId) ? "Deleted!" : "Not found!");
                    break;

                case 3: // Update Task
                    int upId = readInt(sc, "Enter ID to update: ");
                    String newTitle = readTitle(sc, "Enter new title: ");
                    String newDesc = readDescription(sc, "Enter new description: ");
                    int newPriority = readPriority(sc, "Enter new priority (1-5): ");
                    LocalDate newDeadline = readDate(sc, formatter, "Enter new deadline (dd/MM/yyyy): ");

                    System.out.println(tm.updateTask(tasks, upId, newTitle, newDesc, newPriority, newDeadline)
                            ? "Updated!" : "Not found!");
                    break;

                case 4: // Read Task
                    int readId = readInt(sc, "Enter ID to read: ");
                    tm.getTaskById(tasks, readId).ifPresentOrElse(
                            System.out::println,
                            () -> System.out.println("Task not found!")
                    );
                    break;

                case 5: // Show all
                    tm.showAll(tasks);
                    break;

                case 6: // Exit
                    running = false;
                    sc.close();
                    break;

                case 7: // Search
                    System.out.print("Enter title keyword: ");
                    String keyword = sc.nextLine();
                    tm.searchByTitle(tasks, keyword);
                    break;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }


    private static int readInt(Scanner sc, String prompt) {
        int value;
        while (true) {
            System.out.print(prompt);
            if (sc.hasNextInt()) {
                value = sc.nextInt();
                sc.nextLine(); // consume newline
                break;
            } else {
                System.out.println("Please enter a valid integer.");
                sc.nextLine(); // discard invalid input
            }
        }
        return value;
    }

    private static String readTitle(Scanner sc, String prompt) {
        String title;
        while (true) {
            System.out.print(prompt);
            title = sc.nextLine();
            if (title.trim().isEmpty()) {
                System.out.println("Title cannot be empty!");
            } else if (!title.matches("[a-zA-Z ]+")) {
                System.out.println("Title can contain letters and spaces only!");
            } else {
                break;
            }
        }
        return title;
    }

    private static String readDescription(Scanner sc, String prompt) {
        String desc;
        while (true) {
            System.out.print(prompt);
            desc = sc.nextLine();
            if (desc.trim().isEmpty()) {
                System.out.println("Description cannot be empty!");
            } else {
                break;
            }
        }
        return desc;
    }

    private static int readPriority(Scanner sc, String prompt) {
        int priority;
        while (true) {
            System.out.print(prompt);
            if (sc.hasNextInt()) {
                priority = sc.nextInt();
                sc.nextLine(); // consume newline
                if (priority >= 1 && priority <= 5) {
                    break;
                } else {
                    System.out.println("Priority must be between 1 and 5.");
                }
            } else {
                System.out.println("Priority should be an integer only.");
                sc.nextLine(); // discard invalid input
            }
        }
        return priority;
    }

    private static LocalDate readDate(Scanner sc, DateTimeFormatter formatter, String prompt) {
        LocalDate date;
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine();
            try {
                date = LocalDate.parse(input, formatter);
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format! Please enter again in dd/MM/yyyy format.");
            }
        }
        return date;
    }
}
