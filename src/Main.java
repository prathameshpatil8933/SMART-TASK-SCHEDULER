import java.util.*;

public class Main {
    public static List<Task> tasks = new ArrayList<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        TaskManager tm = new TaskManager();

        while (true) {
            System.out.println("\n==== Task Manager ====");
            System.out.println("1. Create Task");
            System.out.println("2. Delete Task");
            System.out.println("3. Update Task");
            System.out.println("4. Read Task (by ID)");
            System.out.println("5. Show All Tasks");
            System.out.println("6. Exit");
            System.out.print("👉 Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1: // CREATE
                    System.out.print("Enter Task ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter Title: ");
                    String title = sc.nextLine();

                    System.out.print("Enter Description: ");
                    String description = sc.nextLine();

                    System.out.print("Enter Priority (1-5): ");
                    int priority = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter Deadline (yyyy-mm-dd): ");
                    String deadline = sc.nextLine();

                    tm.createTask(tasks, id, title, description, priority, deadline);
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

                    System.out.print("Enter New Priority (1-5): ");
                    int newPriority = sc.nextInt();
                    sc.nextLine();

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
                    System.out.println("\n📋 All Tasks:");
                    for (Task t : tasks) {
                        System.out.println(t);
                    }
                    break;

                case 6: // EXIT
                    System.out.println("👋 Exiting Task Manager. Goodbye!");
                    sc.close();
                    return;

                default:
                    System.out.println("⚠️ Please enter a valid option (1-6).");
            }
        }
    }
}
