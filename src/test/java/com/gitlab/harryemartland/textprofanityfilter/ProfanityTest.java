package com.gitlab.harryemartland.textprofanityfilter;

import static org.hamcrest.core.IsInstanceOf.instanceOf;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class ProfanityTest {

    private static final int REPEAT_COUNT = 10000;

    private static String largeText;
    private static List<String> profanityList;
    private static WordTree profanityWordTree = new WordTree();

    private static void load() throws IOException {

        String url = "https://raw.githubusercontent.com/LDNOOBW/List-of-Dirty-Naughty-Obscene-and-Otherwise-Bad-Words/master/en";
        File tempFile = File.createTempFile("test", "tesmp");
        FileUtils.copyURLToFile(new URL(url), tempFile);

        profanityList = FileUtils.readLines(tempFile, StandardCharsets.UTF_8);

         profanityList.stream()
                .map(String::toUpperCase)
                .forEach(profanityWordTree::add);

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 200; i++) {
            stringBuilder.append("Lorum ipsum ");
        }
        largeText = stringBuilder.toString();


    }

    @Parameterized.Parameters(name = "Function: {1}")
    public static Collection<Object[]> data() {

        try {
            load();
        }catch (IOException e){
            e.printStackTrace();
        }

        SingleWordProfanityFilter containsProfanitySplitWord = new ContainsSplitWordProfanityFilter(profanityList);
        SingleWordProfanityFilter containsProfanitySubString = new SubStringWordProfanityFilter(profanityList);
        SingleWordProfanityFilter containsProfanity = new ContainsProfanityFilter(profanityList);
        SingleWordProfanityFilter containsProfanitySplitWordTree = new SplitWordTreeProfanityFilter(profanityWordTree);
        SingleWordProfanityFilter containsProfanityStartWordTree = new StartWordTreeProfanityFilter(profanityWordTree);

        return Arrays.asList(new Object[][]{
                {containsProfanitySplitWord, "split word"},
                {containsProfanitySubString, "substring"},
                {containsProfanitySplitWordTree, "split word tree"},
                {containsProfanityStartWordTree, "start word tree"},
                {containsProfanity, "contains"}
        });
    }

    @Parameterized.Parameter
    public SingleWordProfanityFilter profanityFilter;

    @Parameterized.Parameter(1)
    public String name;

    @Test
    public void shouldFindProfanity() {
        for (int i = 0; i < REPEAT_COUNT; i++) {
            Assert.assertTrue(profanityFilter.containsProfanity("I like big butt and I cannot lie"));
        }
    }

    @Test
    public void shouldNotFindProfanity() {
        for (int i = 0; i < REPEAT_COUNT; i++) {
            Assert.assertFalse(profanityFilter.containsProfanity("I like big cars and I cannot lie"));
        }
    }

    @Test
    public void shouldFindProfanityAtBeginning() {
        for (int i = 0; i < REPEAT_COUNT; i++) {
            Assert.assertTrue(profanityFilter.containsProfanity("butt I like big cars and I cannot lie"));
        }
    }

    @Test
    public void shouldFindProfanityAtEnd() {
        for (int i = 0; i < REPEAT_COUNT; i++) {
            Assert.assertTrue(profanityFilter.containsProfanity("I like big cars and I cannot lie butt"));
        }
    }

    @Test
    public void shouldNotFindProfanityLargeText() {
        for (int i = 0; i < REPEAT_COUNT; i++) {
            Assert.assertFalse(profanityFilter.containsProfanity(largeText));
        }
    }

    @Test
    public void shouldFindProfanityLargeTextAtEnd() {
        for (int i = 0; i < REPEAT_COUNT; i++) {
            Assert.assertTrue(profanityFilter.containsProfanity(largeText + " butt"));
        }
    }

    @Test
    public void shouldFindProfanityLargeTextAtBeginning() {
        for (int i = 0; i < REPEAT_COUNT; i++) {
            Assert.assertTrue(profanityFilter.containsProfanity("butt " + largeText));
        }
    }

    @Test
    public void shouldFindMultiWordProfanity() {

        Assume.assumeThat(profanityFilter, instanceOf(MultiWordProfanityFilter.class));

        for (int i = 0; i < REPEAT_COUNT; i++) {
            Assert.assertTrue(profanityFilter.containsProfanity("free booty call with booking"));
        }
    }

    @Test
    public void shouldNotFindProfanityEndsWithSpace() {
        for (int i = 0; i < REPEAT_COUNT; i++) {
            Assert.assertFalse(profanityFilter.containsProfanity("a car "));
        }
    }

}
