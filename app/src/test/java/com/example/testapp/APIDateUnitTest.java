package com.example.testapp;

import static org.junit.Assert.assertEquals;

import com.example.testapp.model.lib.APIDate;

import org.junit.Test;

import java.time.LocalDate;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class APIDateUnitTest {
    @Test
    public void dateToString_isCorrect() {
        String date = APIDate.formatDate(LocalDate.of(2024, 4, 10));
        assertEquals("2024-04-10", date);
    }

    @Test
    public void dateToDate_isCorrect() {
        LocalDate date = APIDate.formatString("2024-04-10");
        assertEquals(LocalDate.of(2024, 4, 10), date);
    }
}