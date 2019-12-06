package io.plainregex;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

class JavaRegex implements Regex {

    private final Pattern pattern;


    public JavaRegex(String pattern, int flags) {
        this.pattern = Pattern.compile(pattern, flags);
    }

    @Override
    public boolean matches(String text) {
        return pattern.matcher(text).matches();
    }

    @Override
    public String replaceFirstIn(String text, String replacement) {
        return pattern.matcher(text).replaceFirst(replacement);
    }

    @Override
    public String replaceIn(String text, String replacement) {
        return pattern.matcher(text).replaceAll(replacement);
    }

    @Override
    public String replaceIn(String text, BiFunction<Integer, String, String> replacer) {
        StringBuilder sb = new StringBuilder();

        Matcher matcher = pattern.matcher(text);
        int index = 0;
        for (int i = 0; matcher.find(); i++) {
            sb.append(text, index, matcher.start());
            sb.append(replacer.apply(i, matcher.group()));
            index = matcher.end();
        }
        sb.append(text, index, text.length());

        return sb.toString();
    }

    @Override
    public String replaceIn(String text, Function<String, String> replacer) {
        return replaceIn(text, (i, s) -> replacer.apply(s));
    }

    @Override
    public Optional<String> findFirstIn(String text) {
        return findFirstInAsMatch(text).map(Match::text);
    }

    @Override
    public Optional<Match> findFirstInAsMatch(String text) {
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return Optional.of(new Match(matcher.group(), matcher.start(), matcher.end()));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Result<String> findAllIn(String text) {
        Matcher matcher = pattern.matcher(text);

        Stream<Match> stream = StreamSupport.stream(new FindingSpliterator(matcher), false);

        return new MatchedResult<>(stream.map(Match::text));
    }

    @Override
    public Result<Match> findAllInAsMatch(String text) {
        Matcher matcher = pattern.matcher(text);

        Stream<Match> stream = StreamSupport.stream(new FindingSpliterator(matcher), false);

        return new MatchedResult<>(stream);
    }

    @Override
    public Result<String> findGroupsIn(String text) {
        return new MatchedResult<>(findGroupsInAsMatch(text).stream().map(Match::text));
    }

    @Override
    public Result<Match> findGroupsInAsMatch(String text) {
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return new MatchedResult<>(IntStream.range(1, matcher.groupCount() + 1).mapToObj(i -> new Match(matcher.group(i), matcher.start(i), matcher.end(i))));
        } else {
            return MatchedResult.empty();
        }
    }

    @Override
    public Result<String> split(String text) {
        Matcher matcher = pattern.matcher(text);

        Stream<String> stream = StreamSupport.stream(new SplittingSpliterator(matcher, text), false);

        return new MatchedResult<>(stream);
    }

    @Override
    public Predicate<String> asFindPredicate() {
        return string -> pattern.matcher(string).find();
    }

    @Override
    public Predicate<String> asMatchPredicate() {
        return string -> pattern.matcher(string).matches();
    }

}
