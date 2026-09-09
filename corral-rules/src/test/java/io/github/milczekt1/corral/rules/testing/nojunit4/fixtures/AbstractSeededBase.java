package io.github.milczekt1.corral.rules.testing.nojunit4.fixtures;

import org.junit.Before;

/** MUST FLAG: no test of its own, pinning scope to test output rather than to declared tests. */
public abstract class AbstractSeededBase {

    @Before
    public void seedTheDatabase() {
    }
}
