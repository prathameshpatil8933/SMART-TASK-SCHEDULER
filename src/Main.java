import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

public class Main {
    public static List<Task> tasks = new ArrayList<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        TaskManager tm = new TaskManager();

        while (true) {
            System.out.println("==== Task Manager ====");
            System.out.println("1. Create Task");
            System.out.println("2. Delete Task");
            System.out.println("3. Update Task");
            System.out.println("4. Read Task (by ID)");
            System.out.println("5. Show All Tasks");
            System.out.println("6. Exit");
            System.out.print("7. Enter title to search: ");

            System.out.print(" Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1: // CREATE
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

                    tm.createTask(tasks, id, title, description, priority, deadline);
                    System.out.println(" Task added successfully!");
                    break;

                case 2: // DELETE
                    System.out.print("Enter Task ID to delete: ");
                    int deleteId = sc.nextInt();
                    sc.nextLine();
                    tm.deleteTask(tasks, deleteId);
                    break;

                case 3: // UPDATE
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

                    tm.updateTask(tasks, updateId, newTitle, newDesc, newPriority, newDeadline);
                    break;

                case 4: // READ BY ID
                    System.out.print("Enter Task ID to read: ");
                    int readId = sc.nextInt();
                    sc.nextLine();
                    tm.readTask(tasks, readId);
                    break;

                case 5: // SHOW ALL
                    System.out.println("\n All Tasks:");
                    for (Task t : tasks) {
                        System.out.println(t);
                    }
                    break;

                case 6: // EXIT
                    System.out.println(" Exiting Task Manager. Goodbye!");
                    sc.close();
                    return;
                case 7:
                    System.out.print("Enter title to search: ");
                    String search = sc.nextLine();
                    boolean found = false;
                    for (Task t : tasks) {
                        if (t.getTitle().equalsIgnoreCase(search)) {
                            System.out.println(" Found: " + t);
                            found = true;
                        }
                    }
                    if (!found) System.out.println(" No task with that title.");
                    break;


                default:
                    System.out.println(" Please enter a valid option (1-6).");
            }
        }
    }
}
