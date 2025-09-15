import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter the number of task u want to enter");
        int n=sc.nextInt();
        List<Task> tasks=new ArrayList<>();
        for (int i = 0; i < n; i++) {
            System.out.println("\nEnter details for Task " + i);

            System.out.print("Enter the ID");
            int id=sc.nextInt();
            sc.nextLine();

            System.out.print("Title: ");
            String title = sc.nextLine();

            System.out.print("Description: ");
            String description = sc.nextLine();

            System.out.println("Priority (1-5)");
            int priority=sc.nextInt();
            sc.nextLine();
            System.out.print("Deadline (YYYY-MM-DD): ");
            String deadline = sc.nextLine();
            Task task=new Task(id,title,description,priority,deadline);
            tasks.add(task);



        }
        System.out.println("This are all the tasks");
        for (Task t:tasks){
            System.out.println(t);
        }
    }
}