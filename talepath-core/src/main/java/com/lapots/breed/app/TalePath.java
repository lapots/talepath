package com.lapots.breed.app;

import com.lapots.breed.app.apps.CharacterPath;

import org.jooby.Jooby;

/**
 * Instantiate modules required for TalePath application.
 * Implement Jooby application and inject it into this class.
 */
public class TalePath extends Jooby {
    {
        use("characters", new CharacterPath());
    }
}
