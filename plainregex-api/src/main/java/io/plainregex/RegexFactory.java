package io.plainregex;

interface RegexFactory {

    default Regex create(String pattern, RegexFlag... flags) {
        return create(pattern, RegexFlag.resolve(resolver(), flags));
    }

    RegexFlagResolver resolver();

    Regex create(String pattern, int flags);

}
