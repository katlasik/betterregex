package io.betterregex.tests;

import io.betterregex.Match;
import io.betterregex.Regex;
import io.betterregex.RegexFlag;
import org.junit.jupiter.api.DynamicTest;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

import static io.betterregex.RegexFlag.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.DynamicTest.*;

public class CommonTestSuite {

    public Collection<DynamicTest> tests() {

        return Arrays.asList(
                dynamicTest("Should correctly match regex.", this::testMatches),
                dynamicTest("Should correctly match regex as predicate.", this::testAsMatchPredicate),
                dynamicTest("Should correctly find with regex as predicate.", this::testAsFindPredicate),
                dynamicTest("Should correctly replace first occurrence with regex.", this::testReplaceFirstIn),
                dynamicTest("Should correctly replace all occurrences with regex.", this::testReplaceIn),
                dynamicTest("Should correctly replace all occurrences with indexed function.", this::testReplaceInWithIndexedFunction),
                dynamicTest("Should correctly replace all occurrences with function.", this::testReplaceInWithFunction),
                dynamicTest("Should correctly find first with regex.", this::testFindFirstIn),
                dynamicTest("Should correctly find first as match with regex.", this::testFindFirstMatchIn),
                dynamicTest("Should correctly find all with regex.", this::testFindAllIn),
                dynamicTest("Should correctly find all as match with regex.", this::testFindAllMatchIn),
                dynamicTest("Should correctly find all as groups with regex.", this::testFindAllGroupsIn),
                dynamicTest("Should correctly find all as groups as match with regex.", this::testFindAllGroupsMatchIn),
                dynamicTest("Should correctly do split with regex.", this::testSplit)
        );

    }

    void testMatches() {
        Regex regex = Regex.of("[ABC]+");
        assertThat(regex.matches("A")).isTrue();
        assertThat(regex.matches("AA")).isTrue();
        assertThat(regex.matches("ABC")).isTrue();
        assertThat(regex.matches("CCC")).isTrue();
        assertThat(regex.matches("YYY")).isFalse();
    }

    void testAsMatchPredicate() {
        Regex regex = Regex.of("[ABC]+");
        assertThat(regex.asMatchPredicate().test("A")).isTrue();
        assertThat(regex.asMatchPredicate().test("AA")).isTrue();
        assertThat(regex.asMatchPredicate().test("ABC")).isTrue();
        assertThat(regex.asMatchPredicate().test("CCC")).isTrue();
        assertThat(regex.asMatchPredicate().test("XXX")).isFalse();
    }

    void testAsFindPredicate() {
        Regex regex = Regex.of("[ABC]+");
        assertThat(regex.asFindPredicate().test("xAX")).isTrue();
        assertThat(regex.asFindPredicate().test("xxAAxx")).isTrue();
        assertThat(regex.asFindPredicate().test("xxABCtt")).isTrue();
        assertThat(regex.asFindPredicate().test("yyyCCCyy")).isTrue();
        assertThat(regex.asFindPredicate().test("yyyXXXyy")).isFalse();
    }

    void testReplaceFirstIn() {
        Regex regex = Regex.of("[ABC]+");
        assertThat(regex.replaceFirstIn("XXXXXXXXXXXAAAAAaXXXXXXXXXAAAAxxx", "!!!")).isEqualTo("XXXXXXXXXXX!!!aXXXXXXXXXAAAAxxx");
        assertThat(regex.replaceFirstIn("XXXXXXXXXXXAAAAAaXXXXXXXXXAxxx", "!!!")).isEqualTo("XXXXXXXXXXX!!!aXXXXXXXXXAxxx");
        assertThat(regex.replaceFirstIn("XXXXXXXXXXXABCaXXXXXXXXXAxxx", "!!!")).isEqualTo("XXXXXXXXXXX!!!aXXXXXXXXXAxxx");
        assertThat(regex.replaceFirstIn("XXXXXXXXXXXCCCaXXXXXXXXXAxxx", "!!!")).isEqualTo("XXXXXXXXXXX!!!aXXXXXXXXXAxxx");
    }

    void testReplaceIn() {
        Regex regex = Regex.of("[ABC]+");
        assertThat(regex.replaceIn("XXXXXXXXXXXAAAAAaXXXXXXXXXAAAAxxx", "!!!")).isEqualTo("XXXXXXXXXXX!!!aXXXXXXXXX!!!xxx");
        assertThat(regex.replaceIn("XXXXXXXXXXXAAAAAaXXXXXXXXXAxxx", "!!!")).isEqualTo("XXXXXXXXXXX!!!aXXXXXXXXX!!!xxx");
        assertThat(regex.replaceIn("XXXXXXXXXXXABCaXXXXXXXXXAxxx", "!!!")).isEqualTo("XXXXXXXXXXX!!!aXXXXXXXXX!!!xxx");
        assertThat(regex.replaceIn("XXXXXXXXXXXCCCaXXXXXXXXXCxxx", "!!!")).isEqualTo("XXXXXXXXXXX!!!aXXXXXXXXX!!!xxx");
    }

    void testReplaceInWithIndexedFunction() {
        Regex regex = Regex.of("[ABC]+");
        assertThat(regex.replaceIn("___A___B____C____D____E", (i, s) -> i.toString())).isEqualTo("___0___1____2____D____E");
        assertThat(regex.replaceIn("AAAXBBBXCCC", (i, s) -> s.toLowerCase() + i)).isEqualTo("aaa0Xbbb1Xccc2");
    }

    void testReplaceInWithFunction() {
        Regex regex = Regex.of("[ABC]+");
        assertThat(regex.replaceIn("___A___B____C____D____E", s -> s + s)).isEqualTo("___AA___BB____CC____D____E");
        assertThat(regex.replaceIn("AAABBBCCC", (Function<String, String>) String::toLowerCase)).isEqualTo("aaabbbccc");
    }

    void testFindFirstIn() {
        Regex regex = Regex.of("[ABC]+");
        assertThat(regex.findFirstIn("___X___Y____C____D____E")).contains("C");
        assertThat(regex.findFirstIn("WWWBBBCCC")).contains("BBBCCC");
    }


    void testFindFirstMatchIn() {
        Regex regex = Regex.of("[ABC]+");
        assertThat(regex.findFirstInAsMatch("___X___Y____C____D____E")).contains(new Match("C", 12, 13));
        assertThat(regex.findFirstInAsMatch("WWWBBBCCC")).contains(new Match("BBBCCC", 3, 9));
    }

    void testFindAllIn() {
        Regex regex = Regex.of("[ABC]+");
        assertThat(regex.findAllIn("___X___Y____C____D____E_A").list()).containsExactly("C", "A");
        assertThat(regex.findAllIn("WWWBBBWCCC").list()).containsExactly("BBB", "CCC");
    }

    void testFindAllMatchIn() {
        Regex regex = Regex.of("[ABC]+");
        assertThat(regex.findAllInAsMatch("___X___Y____C____D____E_A").list()).containsExactly(new Match("C", 12, 13), new Match("A", 24, 25));
        assertThat(regex.findAllInAsMatch("WWWBBBWCCC").list()).containsExactly(new Match("BBB", 3, 6), new Match("CCC", 7, 10));
    }

    void testFindAllGroupsIn() {
        Regex regex = Regex.of("([ABCabc]+)XXX([ABCabc]+)");
        assertThat(regex.findGroupsIn("aaaXXXbbb").list()).containsExactly("aaa", "bbb");
        assertThat(regex.findGroupsIn("aXXXc").list()).containsExactly("a", "c");
    }

    void testFindAllGroupsMatchIn() {
        Regex regex = Regex.of("([ABCabc]+)XXX([ABCabc]+)");
        assertThat(regex.findGroupsInAsMatch("aaaXXXbbb").list()).containsExactly(new Match("aaa", 0, 3), new Match("bbb", 6, 9));
        assertThat(regex.findGroupsInAsMatch("aXXXcB").list()).containsExactly(new Match("a", 0, 1), new Match("cB", 4, 6));
        assertThat(regex.findGroupsInAsMatch("aaa").list()).isEmpty();
    }

    void testSplit() {
        Regex regex = Regex.of("[ABC]+");
        assertThat(regex.split("___A___AA__X_AB____C____CAB__W").list()).containsExactly("___", "___", "__X_", "____", "____", "__W");
        assertThat(regex.split("WWWBBBZZZCCC").list()).contains("WWW", "ZZZ");
    }

}
