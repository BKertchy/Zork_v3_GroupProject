class MovementCommand extends Command {

    private String dir;
                       

    MovementCommand(String dir) {
        this.dir = dir;
        GameState.instance().setGoBack(dir);
    }

    public String execute() {
        Room currentRoom = GameState.instance().getAdventurersCurrentRoom();
        Room nextRoom = currentRoom.leaveBy(dir);
        if(currentRoom.isDark() && (dir != GameState.instance().goBack()) && !GameState.instance().hasLight())
            return "\n You can't see anything and therefore cannot go anywhere except from where you entered. You need a light source. \n";
        else{
            Exit goingTo = currentRoom.getExit(dir);
            String unlocked = "";
            if(goingTo.isLocked()){
                try
                {
                    GameState.instance().getItemFromInventoryNamed(goingTo.getKey().getPrimaryName());
                    unlocked = "You unlocked the room using the " + goingTo.getKey().getPrimaryName();
                    goingTo.setLock(false);
                }
                catch (Item.NoItemException e)
                {
                    goingTo.setLock(true);
                    return "\n You don't have the right item necessary to unlock this room. \n";
                }
            }
            if (nextRoom != null) {  // could try/catch here.
                GameState.instance().setAdventurersCurrentRoom(nextRoom);
                return "\n" + unlocked + "\n" + nextRoom.describe() + "\n";
            } else {
                return "You can't go " + dir + ".\n";
            }
        }
    }
}
