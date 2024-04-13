package com.example.testapp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.example.testapp.model.EventModel;
import com.example.testapp.model.User;
import com.example.testapp.model.lib.DateTimeAVL;
import com.example.testapp.model.lib.StartEndDateTime;

import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class AVLTest {
    private static final String TAG = "AVLTest";
    private static User user;
    private static LocalDate date;
    private static ArrayList<EventModel> events;
    private static DateTimeAVL avl;

    @BeforeClass
    public static void setUp() {
        System.out.println("Test");
        user = new User("admin", "password");
        date = LocalDate.of(2024, 4, 12);
        events = new ArrayList<>();
        LocalDateTime dt = LocalDateTime.of(date, LocalTime.of(8, 0));
        events.add(new EventModel("1", user.getUsername(), "Breakfast", dt.plusHours(0), dt.plusHours(1), "Prata", EventModel.Status.CONCRETE, "Changi Airport"));
        events.add(new EventModel("2", user.getUsername(), "Stretching", dt.plusHours(1), dt.plusHours(2), "Leg and Arm", EventModel.Status.CONCRETE, "Changi Airport"));
        events.add(new EventModel("3", user.getUsername(), "Walk in the park", dt.plusHours(2), dt.plusHours(3), "Fast walking speed", EventModel.Status.CONCRETE, "Changi Airport"));
        events.add(new EventModel("4", user.getUsername(), "Meeting friend", dt.plusHours(3), dt.plusHours(4), "Roshan at home", EventModel.Status.CONCRETE, "Changi Airport"));
//        events.add(new EventModel("5", user.getUsername(), "Lunch", dt.plusHours(4), dt.plusHours(5), "Chicken Rice", EventModel.Status.CONCRETE, "Changi Airport"));
        events.add(new EventModel("6", user.getUsername(), "Cafe", dt.plusHours(5), dt.plusHours(6), "Latte", EventModel.Status.CONCRETE, "Changi Airport"));
        events.add(new EventModel("7", user.getUsername(), "Tea break 1", dt.plusHours(6), dt.plusHours(7), "Black Tea", EventModel.Status.CONCRETE, "Changi Airport"));
        events.add(new EventModel("8", user.getUsername(), "Buy car", dt.plusHours(7), dt.plusHours(8), "Porcho", EventModel.Status.CONCRETE, "Changi Airport"));
        events.add(new EventModel("9", user.getUsername(), "Watch movie", dt.plusHours(8), dt.plusHours(9), "Panda 1", EventModel.Status.CONCRETE, "Changi Airport"));

        for (EventModel set : events) {
            System.out.println(set.setToString());
        }
        avl = new DateTimeAVL(events);
    }

    @Test
    public void dateTimeAVL_populated() {
        assertTrue(avl.getNumber_of_datetime() != 0);
    }

    @Test
    public void dateTimeAVL_conflict1() {
        assertTrue(avl.checkConflict(new StartEndDateTime(LocalDateTime.of(date, LocalTime.of(11, 0)), LocalDateTime.of(date, LocalTime.of(12, 0)))));
    }

    @Test
    public void dateTimeAVL_conflict2() {
        assertFalse(avl.checkConflict(new StartEndDateTime(LocalDateTime.of(date, LocalTime.of(12, 0)), LocalDateTime.of(date, LocalTime.of(13, 0)))));
    }

    @Test
    public void dateTimeAVL_conflict3() {
        assertTrue(avl.checkConflict(new StartEndDateTime(LocalDateTime.of(date, LocalTime.of(13, 0)), LocalDateTime.of(date, LocalTime.of(14, 0)))));
    }

    @Test
    public void dateTimeAVL_conflict4() {
        assertTrue(avl.checkConflict(new StartEndDateTime(LocalDateTime.of(date, LocalTime.of(11, 0)), LocalDateTime.of(date, LocalTime.of(13, 0)))));
    }

    @Test
    public void dateTimeAVL_conflict4_1() {
        assertTrue(avl.checkConflict(new StartEndDateTime(LocalDateTime.of(date, LocalTime.of(11, 1)), LocalDateTime.of(date, LocalTime.of(13, 0)))));
    }

    @Test
    public void dateTimeAVL_conflict4_2() {
        assertTrue(avl.checkConflict(new StartEndDateTime(LocalDateTime.of(date, LocalTime.of(11, 0)), LocalDateTime.of(date, LocalTime.of(13, 1)))));
    }
    @Test
    public void dateTimeAVL_conflict4_3() {
        assertTrue(avl.checkConflict(new StartEndDateTime(LocalDateTime.of(date, LocalTime.of(11, 1)), LocalDateTime.of(date, LocalTime.of(13, 1)))));
    }

    @Test
    public void dateTimeAVL_conflict5() {
        assertTrue(avl.checkConflict(new StartEndDateTime(LocalDateTime.of(date, LocalTime.of(8, 0)), LocalDateTime.of(date, LocalTime.of(13, 0)))));
    }

    @Test
    public void dateTimeAVL_conflict6() {
        assertTrue(avl.checkConflict(new StartEndDateTime(LocalDateTime.of(date, LocalTime.of(15, 0)), LocalDateTime.of(date, LocalTime.of(16, 0)))));
    }

    @Test
    public void dateTimeAVL_conflict_after() {
        assertTrue(avl.checkConflict(new StartEndDateTime(LocalDateTime.of(date, LocalTime.of(15, 59)), LocalDateTime.of(date, LocalTime.of(16, 20)))));
    }

    @Test
    public void dateTimeAVL_conflict_before() {
        assertTrue(avl.checkConflict(new StartEndDateTime(LocalDateTime.of(date, LocalTime.of(7, 59)), LocalDateTime.of(date, LocalTime.of(8, 1)))));
    }

    @Test
    public void dateTimeAVL_deconflict_after() {
        assertFalse(avl.checkConflict(new StartEndDateTime(LocalDateTime.of(date, LocalTime.of(16, 0)), LocalDateTime.of(date, LocalTime.of(16, 1)))));
    }

    @Test
    public void dateTimeAVL_deconflict_before() {
        assertFalse(avl.checkConflict(new StartEndDateTime(LocalDateTime.of(date, LocalTime.of(7, 40)), LocalDateTime.of(date, LocalTime.of(8, 0)))));
    }

    @Test
    public void dateTimeAVL_deconflict_between1() {
        assertFalse(avl.checkConflict(new StartEndDateTime(LocalDateTime.of(date, LocalTime.of(12, 40)), LocalDateTime.of(date, LocalTime.of(13, 0)))));
    }

    @Test
    public void dateTimeAVL_deconflict_between2() {
        assertFalse(avl.checkConflict(new StartEndDateTime(LocalDateTime.of(date, LocalTime.of(12, 0)), LocalDateTime.of(date, LocalTime.of(12, 1)))));
    }

    @Test
    public void dateTimeAVL_deconflict_between3() {
        assertFalse(avl.checkConflict(new StartEndDateTime(LocalDateTime.of(date, LocalTime.of(12, 0)), LocalDateTime.of(date, LocalTime.of(13, 0)))));
    }

    @Test
    public void dateTimeAVL_conflict_between4() {
        assertTrue(avl.checkConflict(new StartEndDateTime(LocalDateTime.of(date, LocalTime.of(12, 0)), LocalDateTime.of(date, LocalTime.of(14, 0)))));
    }
}