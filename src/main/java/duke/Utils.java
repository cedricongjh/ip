package duke;

import java.util.StringJoiner;

public class Utils {

    public static String[] splitBetween(String str, String separator) throws DukeException {
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

        return new String[]{String.valueOf(start), String.valueOf(end)};
    }

    public static int getInputNumber(String userInput) throws DukeException {

        assert userInput != null;

        try {
            return Integer.parseInt(userInput) - 1;
        } catch (NumberFormatException exception) {
            throw new DukeException("Please enter a number after the command.");
        }
    }

    public static String showTasks(TaskList tasks) {

        assert tasks != null;

        StringBuilder res = new StringBuilder();
        for (int i = 0; i < tasks.numberOfTasks(); i++) {
            res.append(String.format("%d. %s%n\n", i + 1, tasks.getTask(i)));
        }
        return res.toString();
    }


}
