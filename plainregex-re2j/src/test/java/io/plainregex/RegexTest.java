package io.plainregex;

import io.plainregex.tests.CommonTestSuite;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static io.plainregex.RegexFlag.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RegexTest {

    @TestFactory
    Collection<DynamicTest> testRegex() {
       return new CommonTestSuite().tests();
    }

    @DisplayName("Should correctly handle legal flags.")
    @Test
    void testLegalFlags() {

        List<RegexFlag> regexFlags = Arrays.asList(
                DOTALL,
                CASE_INSENSITIVE,
                DISABLE_UNICODE_GROUPS,
                MULTILINE
        );

        for (RegexFlag flag : regexFlags) {
            Regex regex = Regex.of("[a-z].[a-z]+", flag);
            assertThat(regex.findFirstIn("  A\nBC   ")).isNotNull();
        }

    }

    @Test
    void testIllegalFlags() {
        List<RegexFlag> regexFlags = Arrays.asList(
                LITERAL,
                UNIX_LINES,
                COMMENTS,
                CANON_EQ,
                UNICODE_CASE,
                UNICODE_CHARACTER_CLASS
        );

        for (RegexFlag flag : regexFlags) {
            assertThrows(UnsupportedOperationException.class, () -> Regex.of("[a-z].[a-z]+", flag));
        }
    }

}
