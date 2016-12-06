import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * This class represents Non-Playable Characters (NPC's) that exist throughout the dungeon. NPC's can be interacted with
 * and will have items to give to the adventurer throughout his/her journey.
 *
 * @version Zorkv1.0
 * @author Jonathan Samuelsen
 * @author Daniel Zamojda
 * @author Brendon Kertcher
 */
public class NPC {

    class NoNPCException extends Exception {}

    /**
     * A String containing the name of the NPC
     */
    private String name;
    /**
     * A String containing some type of greeting message from the NPC
     */
    private String initialMessage;
    /**
     * A boolean that is true if the NPC has been encountered before, false otherwise
     */
    private boolean beenSeen;
    /**
     * A Room object that stores the room the NPC is currently located
     */
    private Room location;
    /**
     * A hashmap of messages that the NPC can say to the user. The keys are the statement given from the user, the
     * values are the NPC's responses.
     */
    private HashMap<String, String> messages;
    /**
     * An ArrayList of Items that represents the NPC's inventory
     */
    private ArrayList<Item> inventory;

    /**
     * A constructor for NPC objects. This method calls the init() method and then reads through a Scanner object
     * containing the NPC's name, location, items they are holding, and a list of responses to specific statements. This
     * file holds this information on separate lines in the order they are listed.
     *
     * @param s A scanner object containing the information required to construct a NPC
     * @param d The dungeon object that the NPC will be stored in. Necessary for retrieving Item objects for inventory.
     * @param initState True if the game is being started from scratch, false if it is being loaded from a .sav file
     */
    public NPC (Scanner s, Dungeon d, boolean initState) throws NoNPCException {
        init();

        name = s.nextLine();

        if(name.equals(Dungeon.TOP_LEVEL_DELIM)) {
            throw new NoNPCException();
        }

        location = d.getRoom(s.nextLine());



        String temp = s.nextLine();
        if(temp.startsWith("inventory: ")) {
            temp = temp.substring("inventory: ".length());
            String[] itemNames = temp.split(",");
            for(String itemName : itemNames) {
                try {
                    addItemToInventory(d.getItem(itemName));
                } catch(Item.NoItemException e) {
                    System.out.println("There is no item named " + s + ".");
                }
            }
            initialMessage = s.nextLine();
            temp = s.nextLine();
        } else {
            initialMessage = temp;
            temp = s.nextLine();
        }

        while(!temp.equals("---")) {
            String statement = temp.substring(0, temp.indexOf(':'));
            String response = temp.substring(temp.indexOf(':')+1);
            addMessage(statement, response);
            temp = s.nextLine();
        }
    }

    /**
     * Initializes all of the necessary field for the objects. Creates New Hashmaps/ArrayLists and sets booleans.
     */
    public void init() {
        beenSeen = false;
        messages = new HashMap<>();
        inventory = new ArrayList();
    }

    /**
     * Adds a message to the messages ArrayList
     *
     * @param statement The statement from the user that activates the response
     * @param response The String that the NPC will respond with
     */
    public void addMessage(String statement, String response) { messages.put(statement,response); }

    /**
     * Returns a message from the Hashmap to be displayed to the user. If the statement given does not have a
     * corresponding response, it will return an appropriate message.
     *
     * @param statement The statement given from the user
     * @return The corresponding message from the messages HashMap. If the statement is not in messages, it will return that it does not understand.
     */
    public String getMessage(String statement) { return messages.containsKey(statement) ? messages.get(statement) : "I do not understant what you are saying."; }

    /**
     * Sets the location of the NPC
     *
     * @param location The room that the NPC will be placed into.
     */
    public void setLocation(Room location) { this.location = location; }

    /**
     * Retrieves the location of the NPC.
     *
     * @return location field
     */
    public Room getLocation() { return this.location; }

    /**
     * Returns a String about the NPC to be displayed for the user and sets beenSeen to true.
     *
     * @return if the NPC has been encountered before, it returns the name of the NPC in the room, if the NPC has not been encountered, it also returns initialMessage
     */
    public String describe() {
        if(beenSeen) {
            return name + " is in the room.";
        }
        beenSeen = true;
        return name + " is in the room. " + initialMessage;
    }

    /**
     * Adds an item to the NPC's inventory ArrayList
     *
     * @param item the item to be added to the inventory
     */
    public void addItemToInventory(Item item) { inventory.add(item); }

    /**
     * Returns an item from the NPC's inventory to give to the user and removes it from the NPC's inventory.
     *
     * @param itemName the name of the item
     * @return the item from the NPC's inventory
     */
    public Item getItemFromInventory(String itemName) {
        for(Item i : inventory) {
            if (i.getPrimaryName().equals(itemName)) {
                inventory.remove(i);
                return i;
            }
        }
        return null;
    }

    public String getName() { return this.name; }
}