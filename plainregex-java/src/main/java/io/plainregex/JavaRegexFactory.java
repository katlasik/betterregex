package io.plainregex;

import java.util.regex.Pattern;

public class JavaRegexFactory implements RegexFactory {
    @Override
    public RegexFlagResolver resolver() {
        return flag -> {
            switch (flag) {
                case DOTALL:
                    return Pattern.DOTALL;
                case CASE_INSENSITIVE:
                    return Pattern.CASE_INSENSITIVE;
                case LITERAL:
                    return Pattern.LITERAL;
                case UNIX_LINES:
                    return Pattern.UNIX_LINES;
                case MULTILINE:
                    return Pattern.MULTILINE;
                case COMMENTS:
                    return Pattern.COMMENTS;
                case CANON_EQ:
                    return Pattern.CANON_EQ;
                case UNICODE_CASE:
                    return Pattern.UNICODE_CASE;
                case UNICODE_CHARACTER_CLASS:
                    return Pattern.UNICODE_CHARACTER_CLASS;
                default:
                    throw new UnsupportedOperationException(String.format("Java regex can't handle flag: %s.", flag));
            }
        };
    }

    @Override
    public Regex create(String pattern, int flags) {
        return new JavaRegex(pattern, flags);
    }
}
