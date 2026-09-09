package io.github.milczekt1.corral.rules.testing.nojunit4.fixtures;

import junit.framework.TestCase;

/** MUST FLAG: JUnit 3, reached by inheritance rather than by an annotation. */
public class JUnit3StyleCase extends TestCase {

    public void testTheTotal() {
        assertEquals("inherited from junit.framework.Assert", 2, 1 + 1);
    }
}
