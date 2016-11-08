import java.util.Scanner;

public class Exit {

    class NoExitException extends Exception {}

    private String dir;
    private Room src, dest;

    Exit(String dir, Room src, Room dest) {
        init();
        this.dir = dir;
        this.src = src;
        this.dest = dest;
        src.addExit(this);
    }

    /** Given a Scanner object positioned at the beginning of an "exit" file
        entry, read and return an Exit object representing it. 
        @param d The dungeon that contains this exit (so that Room objects 
        may be obtained.)
        @throws NoExitException The reader object is not positioned at the
        start of an exit entry. A side effect of this is the reader's cursor
        is now positioned one line past where it was.
        @throws IllegalDungeonFormatException A structural problem with the
        dungeon file itself, detected when trying to read this room.
     */
    
    /*An Exit shall now take a boolean when reading in the files that defines
    * whether or not the exit can be accessed (is it locked or not?).
    * For now, a locked exit will be determined to be unlocked by an item being 
    * in the adventurer's inventory.
    * Upon being given the command to access an exit, IFF the exit is locked, will
    * a the Exit class call a method that returns a String saying either 1. the 
    * exit is locked (not wanting to disclose the item) or 2. adventurer does not have 
    * required item to access the exit, or 3. it's unlocked and the adventurer
    * can pass.
    *@param HashTable<Item,String> isLocked; Item in order to unlock. String that is used
    *when exit is unlocked.
    *@param Boolean isLocked
    *
    *@author Jonathan Samuelsen
    *@author Daniel Zamojda
    *@author Brendon Kertcher
    */
    Exit(Scanner s, Dungeon d, Boolean isLocked) throws NoExitException,
        Dungeon.IllegalDungeonFormatException {

        init();
        String srcTitle = s.nextLine();
        if (srcTitle.equals(Dungeon.TOP_LEVEL_DELIM)) {
            throw new NoExitException();
        }
        src = d.getRoom(srcTitle);
        dir = s.nextLine();
        dest = d.getRoom(s.nextLine());
        
        // I'm an Exit object. Great. Add me as an exit to my source Room too,
        // though.
        src.addExit(this);

        // throw away delimiter
        if (!s.nextLine().equals(Dungeon.SECOND_LEVEL_DELIM)) {
            throw new Dungeon.IllegalDungeonFormatException("No '" +
                Dungeon.SECOND_LEVEL_DELIM + "' after exit.");
        }
    }

    // Common object initialization tasks.
    private void init() {
    }

    String describe() {
        return "You can go " + dir + " to " + dest.getTitle() + ".";
    }

    String getDir() { return dir; }
    Room getSrc() { return src; }
    Room getDest() { return dest; }
}
