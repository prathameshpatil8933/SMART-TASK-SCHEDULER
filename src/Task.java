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
