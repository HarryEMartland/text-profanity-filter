package com.gitlab.harryemartland.textprofanityfilter;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SubStringWordProfanityFilter implements MultiWordProfanityFilter {

    private static final char SPACE = ' ';
    private final Set<String> profanitySet;

    public SubStringWordProfanityFilter(List<String> profanityList) {
        this.profanitySet = profanityList.stream().map(String::toUpperCase).collect(Collectors.toSet());
    }

    @Override
    public boolean containsProfanity(String sentence) {
        sentence = sentence.toUpperCase() + SPACE;

        int offset = 0;
        int lastOffSet = 0;

        while (offset != -1) {
            int index = sentence.indexOf(SPACE, offset);
            if (index == -1) {
                return false;
            }
            boolean contains = profanitySet.contains(sentence.substring(offset, index));
            if (contains) {
                return true;
            }

            if (lastOffSet != offset) {
                boolean contains2 = profanitySet.contains(sentence.substring(lastOffSet, index));
                if (contains2) {
                    return true;
                }
            }

            lastOffSet = offset;
            offset = index + 1;
        }

        return false;
    }
}
