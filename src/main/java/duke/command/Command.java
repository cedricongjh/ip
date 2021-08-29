package duke.command;

import duke.DukeException;
import duke.Storage;
import duke.TaskList;
import duke.UI;

public abstract class Command {

    protected final String userCommand;
    protected final String userArgument;


    public Command(String userCommand, String userArgument) {
        this.userArgument = userArgument;
        this.userCommand = userCommand;
    }

    public abstract void execute(TaskList tasks, UI ui, Storage storage) throws DukeException;

    public abstract boolean isExit();

}
