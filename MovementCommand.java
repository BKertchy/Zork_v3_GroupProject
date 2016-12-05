class MovementCommand extends Command {

    private String dir;
                       

    MovementCommand(String dir) {
        this.dir = dir;
        GameState.instance().setGoBack(dir);
    }

    public String execute() {
        Room currentRoom = GameState.instance().getAdventurersCurrentRoom();
        Room nextRoom = currentRoom.leaveBy(dir);
        if(currentRoom.isDark && (dir != GameState.instance().goBack()))
            return "\n You can't see anything and therefore cannot go anywhere except from where you entered. You need a light source. \n";
        else{
            
            if(GameState.instance().hasLight())
                nextRoom.setIsDark(false);

            if (nextRoom != null) {  // could try/catch here.
                GameState.instance().setAdventurersCurrentRoom(nextRoom);
                return "\n" + nextRoom.describe() + "\n";
            } else {
                return "You can't go " + dir + ".\n";
            }
        }
    }
}
