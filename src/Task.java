import java.util.Objects;

class Task {
    private int id;
    private String title;
    private String description;
    private int priority;  // Higher = more urgent
    private String deadline;

    public Task(int id, String title, String description, int priority, String deadline) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.deadline = deadline;
    }

    public Task() {}

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;
        Task task = (Task) o;
        return id == task.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", priority=" + priority +
                ", deadline='" + deadline + '\'' +
                '}';
    }
}
