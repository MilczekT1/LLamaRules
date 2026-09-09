package io.github.milczekt1.corral.rules.testing.nojunit4.fixtures;

import junit.extensions.TestSetup;
import junit.framework.TestCase;

/** MUST FLAG: {@code junit.extensions}, the JUnit 3 decorator package. */
public class SuiteDecoratorCase {

    public TestSetup decorateWithOneTimeSetup(TestCase testCase) {
        return new TestSetup(testCase);
    }
}
