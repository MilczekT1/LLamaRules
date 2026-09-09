package io.github.milczekt1.corral.rules.testing.nojunit4.fixtures;

import org.junit.Rule;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * MUST FLAG: {@code org.junit.runner} and {@code org.junit.rules} are subpackages, so this pins the
 * trailing {@code ..} in {@code org.junit..} — without it neither is matched.
 */
@RunWith(JUnit4.class)
public class RunnerAndRuleCase {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @org.junit.Test
    public void writesAFile() {
    }
}
