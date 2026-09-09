package io.github.milczekt1.corral.rules.testing.nojunit4.fixtures;

import org.junit.Before;
import org.junit.jupiter.api.Test;

/**
 * MUST FLAG on {@code @Before} only. The Jupiter {@code @Test} in the same class is the must-not-match
 * half: an over-broad predicate that dropped the Jupiter exclusion finds it too.
 *
 * <p>This is the shape that runs but never sets up — Jupiter executes the test and ignores the JUnit 4
 * fixture, so the method runs against whatever {@code seedTheOrder} was supposed to populate.
 */
public class HalfMigratedCase {

    @Before
    public void seedTheOrder() {
    }

    @Test
    void placesTheOrder() {
    }
}
