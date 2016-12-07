class UnknownCommand extends Command {

    private String bogusCommand;

    UnknownCommand(String bogusCommand) {
        this.bogusCommand = bogusCommand;
    }

    String execute() {
        if(GameState.instance().getAdventurersCurrentRoom().containsNPC()) {
            for(NPC npc : GameState.instance().getAdventurersCurrentRoom().getNPCs()) {
                String msg = npc.getMessage(bogusCommand);
                if(msg.startsWith("[")) {
                    String events[] = msg.substring(1,msg.indexOf(']')).split(",");
                    msg = msg.substring(msg.indexOf("]")+1) + "\n";
                    for(String event : events) {
                        if(event.equals("Win")) {
                            System.out.println(msg);
                            GameState.instance().win();
                        } else if(event.equals("Die")) {
                            System.out.println(msg);
                            GameState.instance().die();
                        } else if(event.startsWith("Wound")) {
                            msg += npc.wound(Integer.valueOf(event.substring(6,event.indexOf(")"))));
                        } else if(event.startsWith("Give")) {
                            try {
                                msg += npc.give(GameState.instance().getDungeon().getItem(event.substring(5, event.indexOf(")"))));
                            } catch (Item.NoItemException e) { }
                        }
                        else if(event.equals("Teleport")){
                            msg += npc.teleport();
                        }
                    }
                    return msg;
                }
            }
        }

        return "I'm not sure what you mean by \"" + bogusCommand + "\".\n";
    }
}
