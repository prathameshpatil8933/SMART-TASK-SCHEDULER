import java.util.Stack;

class Action {
    enum ActionType { CREATE, DELETE, UPDATE }
    ActionType type;
    int taskId;
    Task oldTask;  // for undo
    Task newTask;  // for redo

    Action(ActionType type, int taskId, Task oldTask, Task newTask) {
        this.type = type;
        this.taskId = taskId;
        this.oldTask = oldTask;
        this.newTask = newTask;
    }


}
