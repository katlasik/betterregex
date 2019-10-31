package io.betterregex;

import java.util.Optional;
import java.util.ServiceLoader;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public interface Regex {

    boolean matches(String text);

    String replaceFirstIn(String text, String replacement);

    String replaceIn(String text, String replacement);

    String replaceIn(String text, Function<String, String> replacer);

    String replaceIn(String text, BiFunction<Integer, String, String> replacer);

    Optional<Match> findFirstInAsMatch(String text);

    Optional<String> findFirstIn(String text);

    Result<Match> findAllInAsMatch(String text);

    Result<String> findAllIn(String text);

    Result<Match> findGroupsInAsMatch(String text);

    Result<String> findGroupsIn(String text);

    Result<String> split(String text);

    Predicate<String> asFindPredicate();

    Predicate<String> asMatchPredicate();

    static Regex of(String pattern) {
        return Regex.of(pattern, 0);
    }

    static Regex of(String pattern, RegexFlag... flags) {
        return ServiceLoader.load(RegexFactory.class).iterator().next().create(pattern, flags);
    }

    static Regex of(String pattern, int flags) {
        return ServiceLoader.load(RegexFactory.class).iterator().next().create(pattern, flags);
    }

}
