package com.gitlab.harryemartland.textprofanityfilter;

import org.junit.Assert;
import org.junit.Test;

public class WordTreeTest {

    private WordTree wordTree = new WordTree();

    @Test
    public void shouldAddWord() {
        wordTree.add("cat");
        Assert.assertTrue(wordTree.contains("cat"));
    }

    @Test
    public void shouldNotContainIfNotAdded() {
        Assert.assertFalse(wordTree.contains("cat"));
    }

    @Test
    public void shouldBeAbleToAddMultipleWords() {
        wordTree.add("cat");
        wordTree.add("bat");
        wordTree.add("sat");
        wordTree.add("can");
        wordTree.add("could");
        Assert.assertTrue(wordTree.contains("cat"));
        Assert.assertTrue(wordTree.contains("bat"));
        Assert.assertTrue(wordTree.contains("sat"));
        Assert.assertTrue(wordTree.contains("can"));
        Assert.assertTrue(wordTree.contains("could"));
        Assert.assertFalse(wordTree.contains("dot"));
        Assert.assertFalse(wordTree.contains("dog"));
    }

    @Test
    public void shouldContainIfWordIsLonger() {
        wordTree.add("butt");
        Assert.assertTrue(wordTree.contains("butt and"));
    }

    @Test
    public void shouldContainSecondWord() {
        wordTree.add("butt");
        Assert.assertTrue(wordTree.contains("like butt", 5));
    }

    @Test
    public void shouldContainMultiWord() {
        wordTree.add("inappropriate word");
        Assert.assertTrue(wordTree.contains("inappropriate word"));
    }
}
