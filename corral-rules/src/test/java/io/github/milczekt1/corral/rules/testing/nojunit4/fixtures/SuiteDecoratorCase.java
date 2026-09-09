package io.github.milczekt1.corral.rules.testing.nojunit4.fixtures;

import junit.extensions.TestSetup;
import junit.framework.TestCase;

/** MUST FLAG: the JUnit 3 decorator package, which ships in junit:junit alongside the rest. */
public class SuiteDecoratorCase {

    public TestSetup decorateWithOneTimeSetup(TestCase testCase) {
        return new TestSetup(testCase);
    }
}
