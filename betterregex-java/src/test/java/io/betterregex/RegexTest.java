package io.betterregex;

import io.betterregex.tests.CommonTestSuite;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.Collection;

class RegexTest {

    @TestFactory
    Collection<DynamicTest> testRegex() {
       return new CommonTestSuite().tests();
    }

}
