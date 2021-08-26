package duke;

import duke.task.Task;

import java.util.ArrayList;

public class TaskList {

    private final ArrayList<Task> taskList;

    public TaskList(ArrayList<Task> taskList) {
        this.taskList = taskList;
    }

    public boolean isEmpty() {
        return taskList.isEmpty();
    }

    public int numberOfTasks() {
        return taskList.size();
    }

    public void addTask(Task task) {
        taskList.add(task);
    }

    public Task getTask(int taskIndex) {
        return taskList.get(taskIndex);
    }

    public void removeTask (int delete) {
        taskList.remove(delete);
    }

    public TaskList searchTasks(String searchQuery) {
        ArrayList<Task> res = new ArrayList<>();
        for (Task task : taskList) {
            if (task.toString().contains(searchQuery)) {
                res.add(task);
            }
        }
        return new TaskList(res);
    }
}
