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
            System.out.println("8. Undo last action");
            System.out.println("9. Redo last action");
            System.out.print("Enter your choice: ");

            int choice;
            if (sc.hasNextInt()) {
                choice = sc.nextInt();
                sc.nextLine();
            } else {
                System.out.println("Please enter a valid number.");
                sc.nextLine();
                continue;
            }

            switch (choice) {
                case 1:
                    int id = readInt(sc, "Enter ID: ");
                    String title = readTitle(sc, "Enter title: ");
                    String desc = readDescription(sc, "Enter description: ");
                    int priority = readPriority(sc, "Enter priority (1-5): ");
                    LocalDate deadline = readDate(sc, formatter, "Enter deadline (dd/MM/yyyy): ");

                    tm.createTask(tasks, id, title, desc, priority, deadline, sc);
                    pause(sc);
                    break;

                case 2:
                    int delId = readInt(sc, "Enter ID to delete: ");
                    String s = tm.deleteTask(tasks, delId, sc);
                    System.out.println(s);
                    pause(sc);
                    break;

                case 3:
                    int upId = readInt(sc, "Enter ID to update: ");
                    String newTitle = null;
                    String newDesc = null;
                    Integer newPriority = null;
                    LocalDate newDeadline = null;

                    System.out.print("Update title? (y/n): ");
                    if (sc.nextLine().equalsIgnoreCase("y")) {
                        newTitle = readTitle(sc, "Enter new title: ");
                    }

                    System.out.print("Update description? (y/n): ");
                    if (sc.nextLine().equalsIgnoreCase("y")) {
                        newDesc = readDescription(sc, "Enter new description: ");
                    }

                    System.out.print("Update priority? (y/n): ");
                    if (sc.nextLine().equalsIgnoreCase("y")) {
                        newPriority = readPriority(sc, "Enter new priority (1-5): ");
                    }

                    System.out.print("Update deadline? (y/n): ");
                    if (sc.nextLine().equalsIgnoreCase("y")) {
                        newDeadline = readDate(sc, formatter, "Enter new deadline (dd/MM/yyyy): ");
                    }

                    tm.updateTask(tasks, upId, newTitle, newDesc, newPriority, newDeadline, sc);
                    pause(sc);
                    break;

                case 4:
                    int readId = readInt(sc, "Enter ID to read: ");
                    Task t = tm.getTaskById(tasks, readId, sc);
                    System.out.println(t);
                    pause(sc);
                    break;

                case 5:
                    tm.showAll(tasks);
                    pause(sc);
                    break;

                case 6:
                    running = false;
                    sc.close();
                    break;

                case 7:
                    String keyword = readTitle(sc, "Enter title keyword: ");
                    tm.searchByTitle(tasks, keyword);
                    pause(sc);
                    break;

                case 8:
                    tm.undo(tasks);
                    pause(sc);
                    break;

                case 9:
                    tm.redo(tasks);
                    pause(sc);
                    break;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    // helper that asks user to press enter to continue
    private static void pause(Scanner sc) {
        System.out.println("Press only Enter to continue...");
        String input = sc.nextLine();
        while (!input.isEmpty()) {
            System.out.println("Press only Enter to continue...");
            input = sc.nextLine();
        }
    }

    public static int readInt(Scanner sc, String prompt) {
        int value;
        while (true) {
            System.out.print(prompt);
            if (sc.hasNextInt()) {
                value = sc.nextInt();
                sc.nextLine();
                break;
            } else {
                System.out.println("Please enter a valid integer.");
                sc.nextLine();
            }
        }
        return value;
    }

    public static String readTitle(Scanner sc, String prompt) {
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
                sc.nextLine();
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
                if (date.isBefore(LocalDate.now())) {
                    System.out.println("Deadline cannot be in the past. Enter a future date.");
                } else {
                    break;
                }
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format! Please enter again in dd/MM/yyyy format.");
            }
        }
        return date;
    }


    public static boolean readYesNo(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt + " (y/n): ");
            String line = sc.nextLine().trim();
            if (line.isEmpty()) continue;
            char ch = Character.toLowerCase(line.charAt(0));
            if (ch == 'y') return true;
            if (ch == 'n') return false;
            System.out.println("Please enter y or n.");
        }
    }
}
