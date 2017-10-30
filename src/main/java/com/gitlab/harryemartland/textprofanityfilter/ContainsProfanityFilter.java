package com.gitlab.harryemartland.textprofanityfilter;

import java.util.List;

public class ContainsProfanityFilter implements MultiWordProfanityFilter {

    private final List<String> profanityList;

    public ContainsProfanityFilter(List<String> profanityList) {
        this.profanityList = profanityList;
    }

    @Override
    public boolean containsProfanity(String sentence) {
        for (String profanity : profanityList) {
            if (sentence.contains(profanity)) {
                return true;
            }
        }
        return false;
    }
}
