package site.metacoding.firstapp.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MyParseTest {

    @Test
    public void changeStringToInt_test() {
        String value = "1";

        int result = Integer.parseInt(value);

        assertEquals(1, result);
    }
}
