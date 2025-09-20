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
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate deadline = null;
        LocalDate newDeadlin=null;

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
            sc.nextLine();
            int id,priority=0;
            String desc,title;
            switch (choice) {
                case 1:
                    while (true) {
                        System.out.print("Enter ID: ");
                        if (sc.hasNextInt()) {
                            id = sc.nextInt();
                            sc.nextLine();
                            break;
                        } else {
                            System.out.println("Please enter the ID in Integer format only ");
                        }

                    }
                    System.out.println("ID entered: " + id);


                    while (true) {
                        System.out.print("Enter title: ");
                        title = sc.nextLine();
                        if (title.trim().isEmpty()) {
                            System.out.println("Title cannot be empty!");
                        }
                        else if (!title.matches("[a-zA-Z ]+")) {  // letters (upper/lower) and spaces only
                            System.out.println("Title can contain letters and spaces only!");
                        }
                        else
                            break;
                    }
                    System.out.println("Title entered: " + title);


                    while (true) {
                        System.out.print("Enter description: ");
                        desc = sc.nextLine();
                        if (desc.trim().isEmpty()) {
                            System.out.println("Title cannot be empty!");
                        } else
                            break;
                    }
                    System.out.println("Description entered: " + desc);

                    while (true) {
                        System.out.print("Enter priority (1–5): ");
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
                            sc.nextLine();
                        }
                    }
                    System.out.println("Priority entered: " + priority);




                    while (true) {
                        System.out.print("Enter deadline (dd-MM-yyyy): ");
                        String input = sc.nextLine();

                        try{
                            deadline=LocalDate.parse(input,formatter);
                            break;
                        }
                        catch (DateTimeException e){
                            System.out.println("Invalid date format! Please enter again in dd/MM/yyyy format.");
                        }
                    }
                    System.out.println("Deadline entered: " + deadline);




                    Task newTask = tm.createTask(tasks, id, title, desc, priority, deadline);
                    System.out.println("Task created: " + newTask);
                    break;

                case 2:
                    int delId;
                    while (true) {
                        System.out.print("Enter ID to delete: ");
                        if (sc.hasNextInt()) {
                            delId = sc.nextInt();
                            sc.nextLine();
                            break;
                        }
                        else {
                            System.out.println("The ID values should be an Integer only ");
                        }
                    }
                    System.out.println(tm.deleteTask(tasks, delId) ? "Deleted!" : "Not found!");
                    break;

                case 3:
                    int upId;
                    while (true) {
                        System.out.print("Enter ID to update: ");
                        if (sc.hasNextInt()) {
                            upId = sc.nextInt();
                            sc.nextLine();
                            break;
                        } else {
                            System.out.println("Please enter the ID in integer format only.");
                            sc.nextLine();
                        }
                    }

                    String newTitle;
                    while (true) {
                        System.out.print("Enter new title: ");
                        newTitle = sc.nextLine();
                        if (newTitle.trim().isEmpty()) {
                            System.out.println("Title cannot be empty!");
                        } else if (!newTitle.matches("[a-zA-Z ]+")) {
                            System.out.println("Title can contain letters and spaces only!");
                        } else {
                            break;
                        }
                    }

                    String newDesc;
                    while (true) {
                        System.out.print("Enter new description: ");
                        newDesc = sc.nextLine();
                        if (newDesc.trim().isEmpty()) {
                            System.out.println("Description cannot be empty!");
                        }else if (!newDesc.matches("[a-zA-Z ]+")) {
                            System.out.println("Description can contain letters and spaces only!");
                        } else {
                            break;
                        }
                    }

                    int newPriority;
                    while (true) {
                        System.out.print("Enter new priority (1–5): ");
                        if (sc.hasNextInt()) {
                            newPriority = sc.nextInt();

                            if (newPriority >= 1 && newPriority <= 5) {
                                break;
                            } else {
                                System.out.println("Priority must be between 1 and 5.");
                            }
                        } else {
                            System.out.println("Priority should be an integer only.");
                            sc.nextLine();
                        }
                    }
                    String newDeadline;
                    while(true) {
                        System.out.print("Enter new deadline (dd-mm-yyyy): ");
                        newDeadline= sc.nextLine();
                        try{
                            newDeadlin=LocalDate.parse(newDeadline,formatter);
                            break;
                        }
                        catch (DateTimeParseException e) {
                            System.out.println("Invalid date format! Please enter again in dd/MM/yyyy format.");
                        }

                    }
                    System.out.println(tm.updateTask(tasks, upId, newTitle, newDesc, newPriority, LocalDate.parse(newDeadline))
                            ? "Updated!" : "Not found!");
                    break;

                case 4:
                    System.out.print("Enter ID to read: ");
                    int readId = sc.nextInt();
                    tm.getTaskById(tasks, readId).ifPresentOrElse(
                            System.out::println,
                            () -> System.out.println("Task not found!")
                    );
                    break;

                case 5:
                    tm.showAll(tasks);
                    break;

                case 6:
                    running = false;
                    sc.close();
                    break;

                case 7:
                    System.out.print("Enter title keyword: ");
                    String keyword = sc.nextLine();
                    tm.searchByTitle(tasks, keyword);
                    break;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
