package io.github.milczekt1.corral.rules.testing.nojunit4.fixtures;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * MUST FLAG on {@link Assert} only — the Jupiter assertion beside it must stay silent.
 *
 * <p>The two calls are the same comparison written both ways round: JUnit 4 takes the message first,
 * Jupiter takes it last, which is why switching only the static import still compiles.
 */
public class MessageFirstAssertCase {

    private final long total = 2;

    @Test
    void comparesTwoWays() {
        Assert.assertEquals("message first, JUnit 4", 2, total);
        Assertions.assertEquals(2, total, "message last, Jupiter");
    }
}
