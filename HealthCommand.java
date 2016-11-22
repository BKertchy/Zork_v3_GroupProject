/**
 * A subclass of Command that executes a command to display the user's health to them.
 *
 * @version Zorkv1.0
 * @author Jonathan Samuelsen, Daniel Zamojda, Brendon Kertcher
 */
public class HealthCommand extends Command {
    /**
     * A simple constructor of HealthCommand objects
     */
    public HealthCommand() {
    }

    /**
     * Executes the HealthCommand
     *
     * @return A string value from the GameState class that holds the user's health status.
     */
    public String execute()
    {
        String output = "";
        GameState current = GameState.instance();
        int currentHealth = current.getHealth();
        output = current.getHealthMessage(currentHealth);
        return output;
    }
}
