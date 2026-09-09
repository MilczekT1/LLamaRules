package io.github.milczekt1.corral.rules.testing.nojunit4.fixtures;

import org.junit.Before;

/**
 * MUST FLAG: declares no test of its own, so a rule scoped to classes-declaring-a-test would report
 * nothing while the fixture silently never ran for any Jupiter subclass extending this.
 */
public abstract class AbstractSeededBase {

    @Before
    public void seedTheDatabase() {
    }
}
