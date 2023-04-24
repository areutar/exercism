import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class PhoneNumberNewTest {

    @Test
    public void cleansTheNumber() {
        String expectedNumber = "2234567890";
        String actualNumber = new PhoneNumberNew("(223) 456-7890").getNumber();

        assertEquals(
                expectedNumber, actualNumber
        );
    }

    
    @Test
    public void cleansNumbersWithDots() {
        String expectedNumber = "2234567890";
        String actualNumber = new PhoneNumberNew("223.456.7890").getNumber();

        assertEquals(
                expectedNumber, actualNumber
        );
    }

    
    @Test
    public void cleansNumbersWithMultipleSpaces() {
        String expectedNumber = "2234567890";
        String actualNumber = new PhoneNumberNew("223 456   7890   ").getNumber();

        assertEquals(
                expectedNumber, actualNumber
        );
    }

    
    @Test
    public void invalidWhen9Digits() {
        IllegalArgumentException expected =
            assertThrows(
                IllegalArgumentException.class,
                () -> new PhoneNumberNew("123456789"));

        assertThat(expected)
            .hasMessage("incorrect number of digits");
    }

    
    @Test
    public void invalidWhen11DigitsDoesNotStartWith1() {
        IllegalArgumentException expected =
            assertThrows(
                IllegalArgumentException.class,
                () -> new PhoneNumberNew("22234567890"));

        assertThat(expected)
            .hasMessage("11 digits must start with 1");
    }

    
    @Test
    public void validWhen11DigitsAndStartingWith1() {
        String expectedNumber = "2234567890";
        String actualNumber = new PhoneNumberNew("12234567890").getNumber();

        assertEquals(
                expectedNumber, actualNumber
        );
    }
    
    
    @Test
    public void validWhen11DigitsAndStartingWith1EvenWithPunctuation() {
        String expectedNumber = "2234567890";
        String actualNumber = new PhoneNumberNew("+1 (223) 456-7890").getNumber();

        assertEquals(
                expectedNumber, actualNumber
        );
    }

    
    @Test
    public void invalidWhenMoreThan11Digits() {
        IllegalArgumentException expected =
            assertThrows(
                IllegalArgumentException.class,
                () -> new PhoneNumberNew("321234567890"));

        assertThat(expected)
            .hasMessage("more than 11 digits");
    }

    
    @Test
    public void invalidWithLetters() {
        IllegalArgumentException expected =
            assertThrows(
                IllegalArgumentException.class,
                () -> new PhoneNumberNew("123-abc-7890"));

        assertThat(expected)
            .hasMessage("letters not permitted");
    }

    
    @Test
    public void invalidWithPunctuations() {
        IllegalArgumentException expected =
            assertThrows(
                IllegalArgumentException.class,
                () -> new PhoneNumberNew("123-@:!-7890"));

        assertThat(expected)
            .hasMessage("punctuations not permitted");
    }
    
    
    @Test
    public void invalidIfAreaCodeStartsWith0() {
        IllegalArgumentException expected =
            assertThrows(
                IllegalArgumentException.class,
                () -> new PhoneNumberNew("(023) 456-7890"));

        assertThat(expected)
            .hasMessage("area code cannot start with zero");
    }
    
    
    @Test
    public void invalidIfAreaCodeStartsWith1() {
        IllegalArgumentException expected =
            assertThrows(
                IllegalArgumentException.class,
                () -> new PhoneNumberNew("(123) 456-7890"));

        assertThat(expected)
            .hasMessage("area code cannot start with one");
    }
    
    
    @Test
    public void invalidIfExchangeCodeStartsWith0() {
        IllegalArgumentException expected =
            assertThrows(
                IllegalArgumentException.class,
                () -> new PhoneNumberNew("(223) 056-7890"));

        assertThat(expected)
            .hasMessage("exchange code cannot start with zero");
    }

    
    @Test
    public void invalidIfExchangeCodeStartsWith1() {
        IllegalArgumentException expected =
            assertThrows(
                IllegalArgumentException.class,
                () -> new PhoneNumberNew("(223) 156-7890"));

        assertThat(expected)
            .hasMessage("exchange code cannot start with one");
    }
    
    
    @Test
    public void invalidIfAreaCodeStartsWith0OnValid11DigitNumber() {
        IllegalArgumentException expected =
            assertThrows(
                IllegalArgumentException.class,
                () -> new PhoneNumberNew("1 (023) 456-7890"));

        assertThat(expected)
            .hasMessage("area code cannot start with zero");
    }
    
    
    @Test
    public void invalidIfAreaCodeStartsWith1OnValid11DigitNumber() {
        IllegalArgumentException expected =
            assertThrows(
                IllegalArgumentException.class,
                () -> new PhoneNumberNew("1 (123) 456-7890"));

        assertThat(expected)
            .hasMessage("area code cannot start with one");
    }
    
    
    @Test
    public void invalidIfExchangeCodeStartsWith0OnValid11DigitNumber() {
        IllegalArgumentException expected =
            assertThrows(
                IllegalArgumentException.class,
                () -> new PhoneNumberNew("1 (223) 056-7890"));

        assertThat(expected)
            .hasMessage("exchange code cannot start with zero");
    }
    
    
    @Test
    public void invalidIfExchangeCodeStartsWith1OnValid11DigitNumber() {
        IllegalArgumentException expected =
            assertThrows(
                IllegalArgumentException.class,
                () -> new PhoneNumberNew("1 (223) 156-7890"));

        assertThat(expected)
            .hasMessage("exchange code cannot start with one");
    }
}
