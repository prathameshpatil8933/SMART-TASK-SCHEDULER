class Task {
    private int id;
    private String title;
    private String description;
    private int priority;
    private String deadline;

    public Task(int id, String title, String description, int priority, String deadline) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.deadline = deadline;
    }

    // Getters & Setters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public int getPriority() { return priority; }
    public String getDeadline() { return deadline; }

    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setPriority(int priority) { this.priority = priority; }
    public void setDeadline(String deadline) { this.deadline = deadline; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;
        Task task = (Task) o;
        return this.id == task.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }


    @Override
    public String toString() {
        return "[ID=" + id + ", Title=" + title + ", Desc=" + description +
                ", Priority=" + priority + ", Deadline=" + deadline + "]";
    }
}
