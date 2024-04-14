package com.example.testapp.api;

import androidx.annotation.NonNull;

import com.example.testapp.middleware.Auth;
import com.example.testapp.model.EventModel;
import com.example.testapp.model.Token;
import com.example.testapp.model.TravelPlan;
import com.example.testapp.model.User;
import com.example.testapp.model.Voting;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.ResponseBody;
import okio.BufferedSource;
import okio.Timeout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Controller {
    private static final String BASEURL = "http://10.0.2.2:8080/";
    private static final Retrofit retrofit = new Retrofit.Builder().baseUrl(BASEURL)
            .addConverterFactory(GsonConverterFactory.create()).build();
    private static final Service apiService = retrofit.create(Service.class);
    private static final Retrofit retrofitScalar = new Retrofit.Builder().baseUrl(BASEURL)
            .addConverterFactory(ScalarsConverterFactory.create()).build();
    private static final Service apiServiceScalar = retrofitScalar.create(Service.class);
    private static final boolean TESTING = false;
    private static boolean OFFLINE = false;

    public static void connect(com.example.testapp.api.Response<String> response) {
        if (!TESTING) {
            API.ping().setOnResponse(res -> {
                response.onResponse("Online");
                OFFLINE = false;
            }).setOnFailure(res -> {
            }).fetch();
        }
    }

    public static Service getService() {
        return OFFLINE ? new TestingService() : apiService;
    }

    public static Service getServiceScalar() {
        return OFFLINE ? new TestingService() : apiServiceScalar;
    }

    static class TestingService implements Service {
        private static final ArrayList<User> users = new ArrayList<>();
        private static final ArrayList<TravelPlan> travelPlans = new ArrayList<>();
        private static final TravelPlan travelPlan;
        private static final String CONTROLLER_TESTING = "Controller:Testing";

        static {
            User user = new User("admin", "password");
            users.add(user);
            LocalDate date = LocalDate.of(2024, 4, 12);
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
            travelPlans.add(new TravelPlan(2, "Singapore", date.plusDays(2), date.plusDays(3), user.getUsername(), "ab", new ArrayList<>()));
            travelPlans.add(new TravelPlan(3, "Taiwan", date.plusDays(3), date.plusDays(4), user.getUsername(), "ab", new ArrayList<>()));
            travelPlans.add(new TravelPlan(4, "Australia", date.plusDays(5), date.plusDays(6), user.getUsername(), "ab", new ArrayList<>()));
            travelPlans.add(new TravelPlan(5, "SG Zoo", date.plusDays(7), date.plusDays(8), user.getUsername(), "ab", new ArrayList<>()));
        }

        @Override
        public Call<Token> register(User user) {
            users.add(user);
            return new ByPassCall<>(new Token("Testing Token"));
        }

        @Override
        public Call<Message> logOut(String token) {
            return new ByPassCall<>(new Message("Logout"));
        }

        @Override
        public Call<Token> authenticate(User user) {
            for (User u : users) {
                if (u.equals(user)) return new ByPassCall<>(new Token("Testing Token"));
            }
            return new ByPassCallGeneral<Token>() {
                @Override
                public void enqueue(@NonNull Callback<Token> callback) {
                    callback.onResponse(this, ResponseType.Error(403));
                }
            };
        }

        @Override
        public Call<String> ping() {
            return Controller.apiServiceScalar.ping();
        }

        @Override
        public Call<ArrayList<TravelPlan>> getTravelPlans(String token) {
            return new AuthByPassCall<>(token, travelPlans);
        }

        @Override
        public Call<TravelPlan> createTravelPlan(String token, TravelPlan.Create travelPlan) {
            String joinCode = "asdsa";
            int id = 100;
            TravelPlan tp = travelPlan.toTravelPlan(id, Auth.getInstance().getUsername(), joinCode);
            return new AuthByPassCall<>(token, tp);
        }

        @Override
        public Call<String> renewJoinlink(String token, String travelPlanId) {
            return null;
        }

        @Override
        public Call<TravelPlan> joinTravelPlan(String token, String joinCode) {
            for (TravelPlan travelplan : travelPlans) {
                if (travelplan.getJoinCode().equals(joinCode)) {
                    return new ByPassCall<>(travelplan);
                }
            }
            return new ByPassCallGeneral<TravelPlan>() {
                @Override
                public void enqueue(@NonNull Callback<TravelPlan> callback) {
                    callback.onResponse(this, ResponseType.Error(404));
                }
            };
        }

        @Override
        public Call<ArrayList<EventModel.GET>> getConcreteEvents(String token, String travelPlanId, String date) {
            return null;
        }

        @Override
        public Call<ArrayList<EventModel.GET>> getSuggestedEvents(String token, String travelPlanId, String date) {
            return null;
        }

        @Override
        public Call<EventModel.GET> getEvent(String token, String travelPlanId, String eventId) {
            return null;
        }

        @Override
        public Call<EventModel> updateEvent(String token, String travelPlanId, String eventId, EventModel event) {
            return null;
        }

        @Override
        public Call<String> deleteEvent(String token, String travelPlanId, String eventId) {
            return null;
        }

        @Override
        public Call<EventModel.GET> createEvent(String token, String travelPlanId, EventModel.Create event) {
            EventModel.GET getEvent = event.toGET(Auth.getInstance().getUsername());
            travelPlan.getEvents().add(getEvent.getEvent());
            return new AuthByPassCall<>(token, getEvent);
        }

        @Override
        public Call<ArrayList<Voting>> getVotings(String token, String travelPlanId) {
            return null;
        }

        @Override
        public Call<Voting> createVoting(String token, String travelPlanId, Voting voting) {
            return null;
        }

        @Override
        public Call<String> createVote(String token, String travelPlanId, String votingId, Voting.Vote vote) {
            return null;
        }

        @Override
        public Call<Voting> getVoting(String token, String travelPlanId, String votingId) {
            return null;
        }

        @Override
        public Call<String> deleteVoting(String token, String travelPlanId, String votingId) {
            return null;
        }

        @Override
        public Call<TravelPlan.GET> getTravelPlan(String token, String travelPlanId) {
            TravelPlan travelPlan = travelPlans.get(Integer.parseInt(travelPlanId) - 1);
            return new ByPassCall<>(new TravelPlan.GET(travelPlan));
        }

        @Override
        public Call<ResponseBody> updateTravelPlan(String token, String travelPlanId, TravelPlan.Create travelPlan) {
            return null;
        }

        @Override
        public Call<ResponseBody> deleteTravelPlan(String token, String travelPlanId) {
            return null;
        }

        static class ResponseType {
            public static <T> Response<T> Error(int code) {
                return Response.error(code, new ResponseBody() {
                    @Override
                    public MediaType contentType() {
                        return null;
                    }

                    @Override
                    public long contentLength() {
                        return 0;
                    }

                    @Override
                    public BufferedSource source() {
                        return null;
                    }
                });
            }
        }

        static abstract class ByPassCallGeneral<T> implements Call<T> {
            @Override
            public retrofit2.Response<T> execute() {
                return null;
            }

            @Override
            public boolean isExecuted() {
                return false;
            }

            @Override
            public void cancel() {
            }

            @Override
            public boolean isCanceled() {
                return false;
            }

            @Override
            public Call<T> clone() {
                return null;
            }

            @Override
            public Request request() {
                return null;
            }

            @Override
            public Timeout timeout() {
                return null;
            }
        }

        static class ByPassCall<T> extends ByPassCallGeneral<T> {
            T response;

            public ByPassCall(T response) {
                this.response = response;
            }

            @Override
            public void enqueue(Callback<T> callback) {
                callback.onResponse(this, Response.success(this.response));
            }
        }

        static class AuthByPassCall<T> extends ByPassCall<T> {
            private String token;

            public AuthByPassCall(T response) {
                super(response);
            }

            public AuthByPassCall(String token, T response) {
                super(response);
                this.token = token;
            }

            @Override
            public void enqueue(Callback<T> callback) {
                if (token == null || token.isEmpty())
                    callback.onFailure(this, new Throwable("Auth Missing"));
                else callback.onResponse(this, Response.success(this.response));
            }
        }
    }
}
