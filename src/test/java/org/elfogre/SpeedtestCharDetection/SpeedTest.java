package org.elfogre.SpeedtestCharDetection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.IntPredicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import static org.apache.commons.lang3.ArrayUtils.toObject;
import static java.util.Arrays.asList;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class SpeedTest {

	private static List<String> testingStrings;

	private static final int LISTS_SIZE = 200000;
	private static final int LONG_STRINGS_LENGTH = 4000;
	private static final int SHORT_STRINGS_LENGTH = 4000;

	private static final String OK_CHARS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ.-_";
	private static final String SOME_NOT_OK_CHARS = OK_CHARS + "ñ()^";
	private static final String RESULT_FORMAT = "OK: %d, not OK: %d";

	private static final char[] VALID_CHARS_ARRAY = OK_CHARS.toCharArray();
	private static final Set<Character> VALID_CHARS_SET = Collections
			.unmodifiableSet(new HashSet<>(asList(toObject(VALID_CHARS_ARRAY))));

	@BeforeClass
	public static void createTestLists() {
		testingStrings = new ArrayList<>();
		for (int i = 0; i < LISTS_SIZE; i++) {
			if ((i % 2) == 1) {
				testingStrings.add(RandomStringUtils.random(LONG_STRINGS_LENGTH, SOME_NOT_OK_CHARS));
				testingStrings.add(RandomStringUtils.random(SHORT_STRINGS_LENGTH, SOME_NOT_OK_CHARS));
			} else {
				testingStrings.add(RandomStringUtils.random(LONG_STRINGS_LENGTH, OK_CHARS));
				testingStrings.add(RandomStringUtils.random(SHORT_STRINGS_LENGTH, OK_CHARS));
			}
		}
		Collections.shuffle(testingStrings);
	}

	@Test
	public void checkWithRegex() {
		String regex = "^[a-zA-Z0-9-_\\.]+$";
		int totalOK = 0;
		int totalNOK = 0;
		for (String string : testingStrings) {
			if (string.matches(regex)) {
				totalOK++;
			} else {
				totalNOK++;
			}
		}
		System.out.println(String.format(RESULT_FORMAT, totalOK, totalNOK));
	}

	@Test
	public void checkWithCompiledRegex() {
		String regex = "^[a-zA-Z0-9-_\\.]+$";
		int totalOK = 0;
		int totalNOK = 0;
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher;
		for (String string : testingStrings) {
			matcher = pattern.matcher(string);
			if (matcher.matches()) {
				totalOK++;
			} else {
				totalNOK++;
			}
		}
		System.out.println(String.format(RESULT_FORMAT, totalOK, totalNOK));
	}

	@Test
	public void checkWithCharBucle() {
		int totalOK = 0;
		int totalNOK = 0;
		for (String string : testingStrings) {
			char[] chars = string.toCharArray();
			if (containsOnlyOkChars(chars)) {
				totalOK++;
			} else {
				totalNOK++;
			}
		}
		System.out.println(String.format(RESULT_FORMAT, totalOK, totalNOK));
	}

	private boolean containsOnlyOkChars(char[] chars) {
		for (char c : chars) {
			if (!OK_CHARS.contains(Character.toString(c))) {
				return false;
			}
		}
		return true;
	}

	@Test
	public void checkWithCharBucleOptimized() {
		int totalOK = 0;
		int totalNOK = 0;
		for (String string : testingStrings) {
			char[] chars = string.toCharArray();
			Set<Character> uniqueChars = new HashSet<>();
			for (char character : chars) {
				uniqueChars.add(character);
			}
			if (containsOnlyOkChars(uniqueChars)) {
				totalOK++;
			} else {
				totalNOK++;
			}
		}
		System.out.println(String.format(RESULT_FORMAT, totalOK, totalNOK));
	}

	private boolean containsOnlyOkChars(Set<Character> uniqueChars) {
		for (Character character : uniqueChars) {
			if (!OK_CHARS.contains(Character.toString(character))) {
				return false;
			}
		}
		return true;
	}

	@Test
	public void checkWithJava8() {
		int totalOK = 0;
		int totalNOK = 0;
		IntPredicate p1 = s -> OK_CHARS.contains(Character.toString(new Character((char) s)));
		for (String string : testingStrings) {
			boolean allLetters = string.chars().allMatch(p1);
			if (allLetters) {
				totalOK++;
			} else {
				totalNOK++;
			}
		}
		System.out.println(String.format(RESULT_FORMAT, totalOK, totalNOK));
	}
	
	@Test
	public void testWithStringUtilsContainsOnly() {
		int totalOK = 0;
		int totalNOK = 0;
		for (String string : testingStrings) {
			boolean allLetters = StringUtils.containsOnly(string, VALID_CHARS_ARRAY);
			if (allLetters) {
				totalOK++;
			} else {
				totalNOK++;
			}
		}
		System.out.println(String.format(RESULT_FORMAT, totalOK, totalNOK));
	}
}
