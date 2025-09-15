import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Task> tasks = new ArrayList<>();

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

            Task t = new Task(id, title, description, priority, deadline);
            tasks.add(t);

            System.out.println("✅ Task added: " + t);

            System.out.print("Do you want to add another task? (yes/no): ");
            String choice = sc.nextLine();
            if (choice.equalsIgnoreCase("no")) {
                break;
            }
        }

        System.out.println("\nAll tasks created:");
        for (Task t : tasks) {
            System.out.println(t);
        }
    }
}