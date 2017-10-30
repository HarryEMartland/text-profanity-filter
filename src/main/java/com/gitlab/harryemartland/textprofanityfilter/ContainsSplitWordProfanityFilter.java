package com.gitlab.harryemartland.textprofanityfilter;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ContainsSplitWordProfanityFilter implements SingleWordProfanityFilter {

    private final Set<String> profanitySet;

    public ContainsSplitWordProfanityFilter(List<String> profanityList) {
        this.profanitySet = profanityList.stream().map(String::toUpperCase).collect(Collectors.toSet());
    }

    @Override
    public boolean containsProfanity(String sentence) {
        String[] split = sentence.toUpperCase().split(" ");
        for (String word : split) {
            if (profanitySet.contains(word)) {
                return true;
            }
        }
        return false;
    }
}
