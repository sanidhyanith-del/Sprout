package org.AmineSidki;

import org.AmineSidki.util.ParserUtil;
import org.junit.Test;
import static org.junit.Assert.*;

public class ParserUtilTest {
    @Test
    public void getPackageName_ShouldStripEntity() {
        String input = "com.amine.sprout.entity";
        String expected = "com.amine.sprout"; // No dot here!
        assertEquals("Failed to extract base package", expected, ParserUtil.getPackageName(input));
    }

    @Test
    public void getPackageName_ShouldHandleDeepSubPackages() {
        String input = "org.example.app.module.entity";
        String expected = "org.example.app.module"; // No dot here!
        assertEquals(expected, ParserUtil.getPackageName(input));
    }
}