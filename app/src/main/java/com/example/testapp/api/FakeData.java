package com.example.testapp.api;

import android.util.Log;

import com.example.testapp.middleware.Auth;
import com.example.testapp.model.EventModel;
import com.example.testapp.model.Token;
import com.example.testapp.model.TravelPlan;
import com.example.testapp.model.User;
import com.example.testapp.model.lib.APIDate;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Random;

public class FakeData {
    private static final ArrayList<User> users = new ArrayList<>();
    private static final ArrayList<TravelPlan> travelPlans = new ArrayList<>();
    private static final TravelPlan travelPlan;
    private static final String TAG = "FakeData";

    static {
        User user = new User("admin", "password");
        users.add(user);
        LocalDate date = LocalDate.now();
        ArrayList<EventModel> eventTP1 = new ArrayList<>();
        LocalDateTime dt = LocalDateTime.of(date, LocalTime.of(8, 0));
        eventTP1.add(new EventModel("1", user.getUsername(), "Breakfast", dt.plusHours(0), dt.plusHours(1), "Prata", EventModel.Status.CONCRETE, "Changi Airport"));
        eventTP1.add(new EventModel("2", user.getUsername(), "Stretching", dt.plusHours(1), dt.plusHours(2), "Leg and Arm", EventModel.Status.CONCRETE, "Changi Airport"));
        eventTP1.add(new EventModel("3", user.getUsername(), "Walk in the park", dt.plusHours(2), dt.plusHours(3), "Fast walking speed", EventModel.Status.CONCRETE, "Changi Airport"));
        eventTP1.add(new EventModel("4", user.getUsername(), "Meeting friend", dt.plusHours(3), dt.plusHours(4), "Roshan at home", EventModel.Status.CONCRETE, "Changi Airport"));
        eventTP1.add(new EventModel("5", user.getUsername(), "Lunch", dt.plusHours(4), dt.plusHours(5), "Chicken Rice", EventModel.Status.CONCRETE, "Changi Airport"));
        eventTP1.add(new EventModel("6", user.getUsername(), "Cafe", dt.plusHours(5), dt.plusHours(6), "Latte", EventModel.Status.CONCRETE, "Changi Airport"));
        eventTP1.add(new EventModel("7", user.getUsername(), "Tea break 1", dt.plusHours(6), dt.plusHours(7), "Black Tea", EventModel.Status.CONCRETE, "Changi Airport"));
        eventTP1.add(new EventModel("8", user.getUsername(), "Buy car", dt.plusHours(7), dt.plusHours(8), "Porcho", EventModel.Status.CONCRETE, "Changi Airport"));
        eventTP1.add(new EventModel("9", user.getUsername(), "Watch movie", dt.plusHours(8), dt.plusHours(10), "Panda 1", EventModel.Status.CONCRETE, "Changi Airport"));


        eventTP1.add(new EventModel("10", user.getUsername(), "Breakfast", dt.plusHours(-1), dt.plusHours(0), "Bread", EventModel.Status.SUGGESTED, "Changi Airport"));
        eventTP1.add(new EventModel("11", user.getUsername(), "Movie in the Morning", dt.plusHours(0), dt.plusHours(2), "Kong", EventModel.Status.SUGGESTED, "Changi Airport"));
        eventTP1.add(new EventModel("12", user.getUsername(), "Shopping", dt.plusHours(2), dt.plusHours(3), "Fast walking speed", EventModel.Status.SUGGESTED, "Changi Airport"));
        eventTP1.add(new EventModel("13", user.getUsername(), "Meeting friend", dt.plusHours(3), dt.plusHours(4), "Roshan at home", EventModel.Status.SUGGESTED, "Changi Airport"));
        eventTP1.add(new EventModel("14", user.getUsername(), "Lunch", dt.plusHours(4), dt.plusHours(5), "Chicken Rice", EventModel.Status.SUGGESTED, "Changi Airport"));
        eventTP1.add(new EventModel("15", user.getUsername(), "Cafe", dt.plusHours(5), dt.plusHours(6), "Latte", EventModel.Status.SUGGESTED, "Changi Airport"));
        eventTP1.add(new EventModel("16", user.getUsername(), "Tea break 1", dt.plusHours(6), dt.plusHours(7), "Black Tea", EventModel.Status.SUGGESTED, "Changi Airport"));
        eventTP1.add(new EventModel("17", user.getUsername(), "Buy car", dt.plusHours(7), dt.plusHours(8), "Porcho", EventModel.Status.SUGGESTED, "Changi Airport"));
        eventTP1.add(new EventModel("18", user.getUsername(), "Watch movie", dt.plusHours(8), dt.plusHours(9), "Panda 1", EventModel.Status.SUGGESTED, "Changi Airport"));
        travelPlan = new TravelPlan(1, "Malaysia", date, date.plusDays(1), user.getUsername(), "aa", eventTP1);
        travelPlans.add(travelPlan);
        createTravelPlan(new TravelPlan.Create("Singapore", date.plusDays(3), date.plusDays(4)));
        createTravelPlan(new TravelPlan.Create("Taiwan", date.plusDays(2), date.plusDays(3)));
        createTravelPlan(new TravelPlan.Create("Australia", date.plusDays(5), date.plusDays(6)));
        createTravelPlan(new TravelPlan.Create("SG Zoo", date.plusDays(7), date.plusDays(8)));
        Log.e(TAG, travelPlans.toString());
    }

    private static String generateToken() {
        byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);
        return new String(array, StandardCharsets.UTF_8);
    }

    public static String register(User user) {
        users.add(user);
        return generateToken();
    }

    public static Token authenticate(User user) {
        for (User u : users) {
            if (u.equals(user)) return new Token(generateToken());
        }
        return null;
    }

    public static EventModel.GET createEvent(String travelPlanId, EventModel.Create eventCreate) {
        String username = Auth.getInstance().getUsername();
        EventModel event = eventCreate.toEvent(String.valueOf(travelPlans.size() + 2), username);
        TravelPlan travelPlan = getTravelPlan(travelPlanId);
        if (travelPlan == null) return null;
        else {
            travelPlan.getEvents().add(event);
            return (eventCreate.toGET(username));
        }
    }

    public static TravelPlan getTravelPlan(String travelPlanId) {
        for (TravelPlan travelPlan : travelPlans) {
            if (travelPlan.getId().equals(travelPlanId)) {
                return travelPlan;
            }
        }
        return null;
    }

    public static ArrayList<EventModel.GET> getEvents(String travelPlanId, String date, EventModel.Status status) {
        LocalDate localDate = APIDate.formatString(date);
        Log.e(TAG, localDate.toString());
        for (TravelPlan travelPlan : travelPlans) {
            if (travelPlan.getId().equals(travelPlanId)) {
                ArrayList<EventModel.GET> eventsGet = new ArrayList<>();
                for (EventModel event : travelPlan.getEvents()) {
                    if (event.getDate().equals(localDate) && event.getPlaceStatus() == status) {
                        eventsGet.add(new EventModel.GET(event));
                    }
                }
                return eventsGet;
            }
        }
        return new ArrayList<>();
    }

    public static void deleteEvent(String travelPlanId, String eventId) {
        TravelPlan travelPlan = getTravelPlan(travelPlanId);
        if(travelPlan!=null) {
            ArrayList<EventModel> events = travelPlan.getEvents();
            for(int i = 0; i < events.size(); i ++) {
                if(events.get(i).getId().equals(eventId)) {
                    events.remove(i);
                    return;
                }
            }
        }
    }

    public static TravelPlan createTravelPlan(TravelPlan.Create travelPlan) {
        byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);
        String joinCode = new String(array, StandardCharsets.UTF_8);
        travelPlans.add(travelPlan.toTravelPlan(travelPlans.size() + 2, Auth.getInstance().getUsername(), joinCode));
        return travelPlans.get(travelPlans.size() - 1);
    }

    public static TravelPlan joinTravelPlan(String joinCode) {
        for (TravelPlan travelplan : travelPlans) {
            if (travelplan.getJoinCode().equals(joinCode)) {
                return travelplan;
            }
        }
        return null;
    }

    public static ArrayList<TravelPlan> getTravelPlans() {
        return travelPlans;
    }

    public static void deleteTravelPlan(String travelPlanId) {
        for(int i = 0; i < travelPlans.size(); i ++) {
            if(travelPlans.get(i).getId().equals(travelPlanId)) {
                travelPlans.remove(i);
                return;
            }
        }
    }
}
