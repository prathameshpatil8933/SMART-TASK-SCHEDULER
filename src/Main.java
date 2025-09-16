import java.util.*;

public class Main {
    public static List<Task> tasks = new ArrayList<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        TaskManager tm = new TaskManager();

        while (true) {
            System.out.println("\n--- Create a new Task ---");
            System.out.print("Enter Task ID: ");
            int id = sc.nextInt();
            sc.nextLine(); // consume newline

            System.out.print("Enter Title: ");
            String title = sc.nextLine();

            System.out.print("Enter Description: ");
            String description = sc.nextLine();

            System.out.print("Enter Priority (1-5): ");
            int priority = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter Deadline (yyyy-mm-dd): ");
            String deadline = sc.nextLine();

            // create and store
            Task t = new Task(id, title, description, priority, deadline);
            tasks.add(t);

            System.out.println("✅ Task added: " + t);

            System.out.println("\nWhat action u would like to do ");
            System.out.println("Press 1 for Create Task");
            System.out.println("Press 2 for Delete Task");
            System.out.println("Press 3 for Update Task");
            System.out.println("Press 4 for Read  Task");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    break;
                case 2:
                    System.out.print("Enter the ID you want to delete: ");
                    int sp = sc.nextInt();
                    sc.nextLine();
                    tm.deleteTask(tasks, sp);
                    System.out.println("📋 Current tasks: " + tasks);
                    break;
                case 3:
                    // future: update logic
                    break;
                case 4:
                    System.out.println("📋 All tasks: " + tasks);
                    break;
                default:
                    System.out.println("⚠️ Please enter a valid input between 1-4");
            }

            // ask if user wants to continue
            System.out.print("\nDo you want to add another task? (yes/no): ");
            String cont = sc.nextLine();
            if (cont.equalsIgnoreCase("no")) {
                break;
            }
        }

        System.out.println("\nAll tasks created:");
        for (Task t : tasks) {
            System.out.println(t);
        }
    }
}
