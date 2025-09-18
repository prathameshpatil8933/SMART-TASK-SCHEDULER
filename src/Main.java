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
                    tm.createTask(tasks);
                    break;

                case 2: // DELETE
                    System.out.print("Enter Task ID to delete: ");
                    int deleteId = sc.nextInt();
                    sc.nextLine();
                    tm.deleteTask(tasks, deleteId);
                    break;

                case 3: // UPDATE
                    tm.updateTask(tasks);
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
                    if (!found) System.out.println("  No task with that title.");
                    break;


                default:
                    System.out.println(" Please enter a valid option (1-6).");
            }
        }
    }
}
