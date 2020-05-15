package com.oleksii.ulianov.trpgplanningapplication.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    /**
     * Entire permissions-list.
     */
    public static final String ADMIN = "ROLE_ADMIN";

    /**
     * {@code GAME_MASTER} and can grant (or deny) users {@code GAME_MASTER}; create, edit or delete others characters and games; can deactivate or activate Users.
     */
    public static final String MODERATOR = "ROLE_MODERATOR";

    /**
     * {@code USER} and can create, update or delete (own) games; remove characters from own game; create, update or delete new system; create, update or delete new tag.
     */
    public static final String GAME_MASTER = "ROLE_GAME_MASTER";

    /**
     * {@code ANONYMOUS} and can create, update or delete (own) characters; register for games with existing characters.
     */
    public static final String USER = "ROLE_USER";

    /**
     * Can observe all games, systems, tags and game masters.
     */
    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    private AuthoritiesConstants() {
    }
}
