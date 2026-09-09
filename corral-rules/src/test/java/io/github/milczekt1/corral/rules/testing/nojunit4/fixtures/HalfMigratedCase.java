package io.github.milczekt1.corral.rules.testing.nojunit4.fixtures;

import org.junit.Before;
import org.junit.jupiter.api.Test;

/**
 * MUST FLAG on {@code @Before} only. The Jupiter {@code @Test} beside it is the must-not-match half:
 * an over-broad predicate finds that too.
 */
public class HalfMigratedCase {

    @Before
    public void seedTheOrder() {
    }

    @Test
    void placesTheOrder() {
    }
}
