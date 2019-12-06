package io.plainregex;

import java.util.Optional;
import java.util.ServiceLoader;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public interface Regex {

    /**
     * Returns true if text matches the given regular expression.
     * @param text the text to be matched
     * @return true if text matches regex.
     */
    boolean matches(String text);


    /**
     * Replaces first occurrence in text matching regular expression.
     * @param text text to search
     * @param replacement text which will replace match
     * @return text with replaced matches
     */
    String replaceFirstIn(String text, String replacement);

    /**
     *
     * @param text
     * @param replacement
     * @return
     */
    String replaceIn(String text, String replacement);

    /**
     *
     * @param text
     * @param replacer
     * @return
     */
    String replaceIn(String text, Function<String, String> replacer);

    /**
     *
     * @param text
     * @param replacer
     * @return
     */
    String replaceIn(String text, BiFunction<Integer, String, String> replacer);

    /**
     *
     * @param text
     * @return
     */
    Optional<Match> findFirstInAsMatch(String text);

    /**
     *
     * @param text
     * @return
     */
    Optional<String> findFirstIn(String text);

    /**
     *
     * @param text
     * @return
     */
    Result<Match> findAllInAsMatch(String text);

    /**
     *
     * @param text
     * @return
     */
    Result<String> findAllIn(String text);

    /**
     *
     * @param text
     * @return
     */
    Result<Match> findGroupsInAsMatch(String text);

    /**
     *
     * @param text
     * @return
     */
    Result<String> findGroupsIn(String text);

    /**
     * Splits text using provided regular expression.
     * @param text
     * @return
     */
    Result<String> split(String text);

    /**
     * Returns predicate, which returns true if the regular expression matches any part of string.
     * @return true if the regular expression matches any part of text.
     */
    Predicate<String> asFindPredicate();

    /**
     * Returns predicate, which returns true only if the regular expression matches whole string.
     * @return true if the regular expression matches whole text.
     */
    Predicate<String> asMatchPredicate();

    /**
     * Creates regular expression of the provided pattern.
     * @param pattern the pattern of regulard expression.
     * @return then instance of Regex class
     */
    static Regex of(String pattern) {
        return Regex.of(pattern, 0);
    }

    /**
     *
     * @param pattern
     * @param flags
     * @return
     */
    static Regex of(String pattern, RegexFlag... flags) {
        return ServiceLoader.load(RegexFactory.class).iterator().next().create(pattern, flags);
    }

    /**
     *
     * @param pattern
     * @param flags
     * @return
     */
    static Regex of(String pattern, int flags) {
        return ServiceLoader.load(RegexFactory.class).iterator().next().create(pattern, flags);
    }

}
