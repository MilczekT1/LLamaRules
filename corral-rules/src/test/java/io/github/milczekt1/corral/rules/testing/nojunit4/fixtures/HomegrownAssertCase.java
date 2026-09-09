package io.github.milczekt1.corral.rules.testing.nojunit4.fixtures;

/** MUST IGNORE: an {@code assertEquals} of its own is not a dependency on anyone else's. */
public class HomegrownAssertCase {

    private static void assertEquals(long expected, long actual) {
        if (expected != actual) {
            throw new AssertionError(expected + " != " + actual);
        }
    }

    public void checksTheTotal() {
        assertEquals(2, 1 + 1);
    }
}
