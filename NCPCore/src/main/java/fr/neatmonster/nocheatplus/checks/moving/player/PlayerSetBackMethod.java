package fr.neatmonster.nocheatplus.checks.moving.player;

/**
 * Provide something to be able to tell how to set back players technically.
 * 
 * @author asofold
 *
 */
public class PlayerSetBackMethod {

    // TODO: Distinguish (detected/assumed) capabilities from methods to use (e.g. getFrom can be used or not)?
    // TODO: Might use more speaking method names (cancelPlayerMoveEvent)


    // FLAGS (Type might change to long, if so much distinction is necessary).

    /** PlayerMoveEvent.setTo is used (legacy). */
    private static final int SET_TO = 0x01;
    /** PlayerMoveEvent.setCancelled(true) is used. */
    private static final int CANCEL = 0x02;
    /** PlayerMoveEvent.getFrom() is updated with the set back location. */
    private static final int UPDATE_FROM = 0x04;
    /** An additional teleport is scheduled for safety. */
    private static final int SCHEDULE = 0x08;


    // DEFAULTS

    public static final PlayerSetBackMethod LEGACY = new PlayerSetBackMethod("default.legacy", 
            SET_TO);
    public static final PlayerSetBackMethod CAUTIOUS = new PlayerSetBackMethod("default.cautious", 
            CANCEL | UPDATE_FROM | SCHEDULE);
    public static final PlayerSetBackMethod MODERN = new PlayerSetBackMethod("default.modern", 
            CANCEL | UPDATE_FROM);

    public static final PlayerSetBackMethod fromString(String name, String input) {
        // TODO: Perhaps complain for incomplete/wrong content, much later.
        input = input.toLowerCase().replaceAll("_", "");
        int flags = 0;
        if (input.contains("setto")) {
            flags |= SET_TO;
        }
        if (input.contains("cancel")) {
            flags |= CANCEL;
        }
        if (input.contains("updatefrom")) {
            flags |= UPDATE_FROM;
        }
        if (input.contains("schedule")) {
            flags |= SCHEDULE;
        }
        return new PlayerSetBackMethod(name, flags);
    }


    // INSTANCE

    private final String id;
    private final int flags;

    public PlayerSetBackMethod(String id, int flags) {
        this.id = id;
        this.flags = flags;
    }

    public String getId() {
        return id;
    }

    /**
     * ?
     * @return
     */
    public boolean doesThisMakeSense() {
        return (flags & (SET_TO | CANCEL | SCHEDULE)) != 0;
    }

    public boolean shouldSetTo() {
        return (flags & SET_TO) != 0;
    }

    public boolean shouldCancel() {
        return (flags & CANCEL) != 0;
    }

    public boolean shouldUpdateFrom() {
        return (flags & UPDATE_FROM) != 0;
    }

    public boolean shouldSchedule() {
        return (flags & SCHEDULE) != 0;
    }

}
