package io.github.milczekt1.corral.rules.testing.nojunit4.fixtures;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.platform.commons.annotation.Testable;

/** MUST IGNORE: Jupiter, its params module and the Platform are all separately excluded packages. */
@Testable
public class JupiterOnlyCase {

    @Test
    void chargesTheCard() {
        Assertions.assertEquals(2, 1 + 1, "card should be charged");
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2})
    void chargesEachCard(int amount) {
        Assertions.assertTrue(amount > 0);
    }
}
