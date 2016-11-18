
/**
 * A subclass of Command that executes a command to display the user's score to them.
 *
 * @version Zorkv1.0
 * @author Jonathan Samuelsen, Daniel Zamojda, Brendon Kertcher
 */

public class ScoreCommand extends Command {
    /**
     * A simple constructor of ScoreCommand objects
     */
    public ScoreCommand() {
    }

    /**
     * Executes the ScoreCommand
     *
     * @return A string value from the GameState class that holds the user's health status.
     */
    public String execute() { return "You have " + GameState.instance().getScore() + ".\n" + GameState.instance().getRank(); }
}

