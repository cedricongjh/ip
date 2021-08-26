import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringJoiner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Duke {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Storage storage = new Storage("./data/tasks.txt");
        TaskList tasks;

        try {
            tasks = new TaskList(storage.loadTasks());
        } catch (DukeException e) {
            System.out.println(e.getMessage());
            tasks = new TaskList(new ArrayList<>());
            // TODO: Create new file when cannot load tasks from current file
        }

        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);
        System.out.println("What can I do for you today?");

        String userCommand;
        String userInput;

        mainLoop:
        while (true) {
            try {
                userCommand = scanner.next();
                userInput = scanner.nextLine().trim();

                switch (userCommand) {
                    case "list":
                        if (tasks.isEmpty()) {
                            System.out.println("Currently no tasks!");
                        }
                        for (int i = 0; i < tasks.numberOfTasks(); i++) {
                            System.out.printf("%d. %s%n", i + 1, tasks.getTask(i));
                        }
                        break;
                    case "bye":
                        System.out.println("Good bye.");
                        break mainLoop;
                    case "done":
                        int done = getInputNumber(userInput);

                        if (done >= tasks.numberOfTasks()|| done < 0) {
                            System.out.println("Task does not exist!");
                            continue;
                        }

                        Task doneTask = tasks.getTask(done);
                        doneTask.markAsDone();
                        storage.editTaskFromFile(done, tasks);

                        System.out.printf("I've marked this task as done:\n" +
                                "%s\n", doneTask.toString());

                        break;
                    case ("todo"):
                    case("deadline"):
                    case("event"):
                        if (userInput.equals("")) {
                            throw new DukeException("The description of a Task cannot be empty.");
                        }

                        if (userCommand.equals("todo")) {
                            tasks.addTask(new Todo(userInput, false));
                        } else if (userCommand.equals("deadline")) {
                            String[] deadlineInfo = splitBetween(userInput, "/by");
                            tasks.addTask(new Deadline(deadlineInfo[0], deadlineInfo[1], false));
                        } else {
                            String[] eventInfo = splitBetween(userInput, "/at");
                            tasks.addTask(new Event(eventInfo[0], eventInfo[1], false));
                        }
                        addTask(tasks.getTask(tasks.numberOfTasks() - 1), storage, tasks);
                        break;
                    case("delete"):
                        int delete = getInputNumber(userInput);
                        if (delete >= tasks.numberOfTasks() || delete < 0) {
                            System.out.println("Task does not exist!");
                            continue;
                        }

                        Task removedTask = tasks.getTask(delete);
                        storage.deleteTaskFromFile(delete, tasks);
                        tasks.removeTask(delete);

                        System.out.printf("I've removed this task:\n%s\n", removedTask.toString());
                        System.out.printf("Now you have %d tasks in your list.\n", tasks.numberOfTasks());
                        break;
                    default:
                        throw new DukeException("Sorry I do not understand this directive.");
                }}
            catch (DukeException exception) {
                System.out.println(exception.getMessage());
            }
        }
    }

    private static void addTask(Task newTask, Storage storage, TaskList tasks) {
        storage.saveTaskToFile(newTask);
        System.out.printf("Got it, I've added this task:\n %s\n", newTask.toString());
        System.out.printf("Now you have %d tasks in your list.\n", tasks.numberOfTasks());
    }

    private static String[] splitBetween(String str, String separator) throws DukeException {
        StringJoiner start = new StringJoiner(" ");
        StringJoiner end = new StringJoiner(" ");

        int i = 0;
        boolean after = false;

        String[] strArray = str.split(" ");

        while (i < strArray.length) {
            String currentString = strArray[i];
            if (after) {
                end.add(currentString);
            } else if (currentString.equals(separator)) {
                after = true;
            } else {
                start.add(currentString);
            }
            i++;
        }

        if (!after) {
            throw new DukeException("Incorrect format for command.");
        }

        if (String.valueOf(end).equals("")) {
            throw new DukeException("Please specify a time for the task.");
        }

        return new String[] {String.valueOf(start), String.valueOf(end)};
    }

    private static int getInputNumber(String userInput) throws DukeException {
        try {
           return Integer.parseInt(userInput) - 1;
        } catch (NumberFormatException exception) {
            throw new DukeException("Please enter a number after the command.");
        }
    }

}
