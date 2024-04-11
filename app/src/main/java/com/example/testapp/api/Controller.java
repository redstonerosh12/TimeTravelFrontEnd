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
    static private final String BASEURL = "http://10.0.2.2:8080/";
    static private final Retrofit retrofit = new Retrofit.Builder().baseUrl(BASEURL)
            .addConverterFactory(GsonConverterFactory.create()).build();
    static private final Service apiService = retrofit.create(Service.class);
    static private final Retrofit retrofitScalar = new Retrofit.Builder().baseUrl(BASEURL)
            .addConverterFactory(ScalarsConverterFactory.create()).build();
    static private final Service apiServiceScalar = retrofitScalar.create(Service.class);
    static private boolean TESTING = true;

    public static void connect(com.example.testapp.api.Response<String> response) {
        API.ping().setOnResponse(res -> {
            response.onResponse("Online");
            TESTING = false;
        }).setOnFailure(res -> {
        }).fetch();
    }

    public static Service getService() {
        return TESTING ? new TestingService() : apiService;
    }

    public static Service getServiceScalar() {
        return TESTING ? new TestingService() : apiServiceScalar;
    }

    static class TestingService implements Service {
        private static final ArrayList<User> users = new ArrayList<>();
        private static final ArrayList<TravelPlan> travelPlans = new ArrayList<>();

        static {
            User user = new User("admin", "password");
            users.add(user);
            LocalDate date = LocalDate.of(2024,4,12);
            ArrayList<EventModel> eventTP1 = new ArrayList<>();
            LocalDateTime dt = LocalDateTime.of(date, LocalTime.of(8,0));
            eventTP1.add(new EventModel("1", user.getUsername(),"Breakfast",dt.plusHours(0), dt.plusHours(1), "Prata", EventModel.Status.CONCRETE, "Changi Airport"));
            eventTP1.add(new EventModel("2", user.getUsername(),"Stretching",dt.plusHours(1), dt.plusHours(2), "Leg and Arm", EventModel.Status.CONCRETE, "Changi Airport"));
            eventTP1.add(new EventModel("3", user.getUsername(),"Walk in the park",dt.plusHours(2), dt.plusHours(3), "Fast walking speed", EventModel.Status.CONCRETE, "Changi Airport"));
            eventTP1.add(new EventModel("4", user.getUsername(),"Meeting friend",dt.plusHours(3), dt.plusHours(4), "Roshan at home", EventModel.Status.CONCRETE, "Changi Airport"));
            eventTP1.add(new EventModel("5", user.getUsername(),"Lunch",dt.plusHours(4), dt.plusHours(5), "Chicken Rice", EventModel.Status.CONCRETE, "Changi Airport"));
            eventTP1.add(new EventModel("6", user.getUsername(),"Cafe",dt.plusHours(5), dt.plusHours(6), "Latte", EventModel.Status.CONCRETE, "Changi Airport"));
            eventTP1.add(new EventModel("7", user.getUsername(),"Tea break 1",dt.plusHours(6), dt.plusHours(7), "Black Tea", EventModel.Status.CONCRETE, "Changi Airport"));
            eventTP1.add(new EventModel("8", user.getUsername(),"Buy car",dt.plusHours(7), dt.plusHours(8), "Porcho", EventModel.Status.CONCRETE, "Changi Airport"));
            eventTP1.add(new EventModel("9", user.getUsername(),"Watch movie",dt.plusHours(8), dt.plusHours(9), "Panda 1", EventModel.Status.CONCRETE, "Changi Airport"));


            eventTP1.add(new EventModel("10", user.getUsername(),"Breakfast",dt.plusHours(-1), dt.plusHours(0), "Bread", EventModel.Status.SUGGESTED, "Changi Airport"));
            eventTP1.add(new EventModel("11", user.getUsername(),"Movie in the Morning",dt.plusHours(0), dt.plusHours(2), "Kong", EventModel.Status.SUGGESTED, "Changi Airport"));
            eventTP1.add(new EventModel("12", user.getUsername(),"Shopping",dt.plusHours(2), dt.plusHours(3), "Fast walking speed", EventModel.Status.SUGGESTED, "Changi Airport"));
            eventTP1.add(new EventModel("13", user.getUsername(),"Meeting friend",dt.plusHours(3), dt.plusHours(4), "Roshan at home", EventModel.Status.SUGGESTED, "Changi Airport"));
            eventTP1.add(new EventModel("14", user.getUsername(),"Lunch",dt.plusHours(4), dt.plusHours(5), "Chicken Rice", EventModel.Status.SUGGESTED, "Changi Airport"));
            eventTP1.add(new EventModel("15", user.getUsername(),"Cafe",dt.plusHours(5), dt.plusHours(6), "Latte", EventModel.Status.SUGGESTED, "Changi Airport"));
            eventTP1.add(new EventModel("16", user.getUsername(),"Tea break 1",dt.plusHours(6), dt.plusHours(7), "Black Tea", EventModel.Status.SUGGESTED, "Changi Airport"));
            eventTP1.add(new EventModel("17", user.getUsername(),"Buy car",dt.plusHours(7), dt.plusHours(8), "Porcho", EventModel.Status.SUGGESTED, "Changi Airport"));
            eventTP1.add(new EventModel("18", user.getUsername(),"Watch movie",dt.plusHours(8), dt.plusHours(9), "Panda 1", EventModel.Status.SUGGESTED, "Changi Airport"));

            travelPlans.add(new TravelPlan(1, "Malaysia", date, date.plusDays(1),user.getUsername(),"asdawq",eventTP1));
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
            ArrayList<TravelPlan> fakeData = new ArrayList<>();
            LocalDate date = LocalDate.of(1999, 1, 1);
            fakeData.add(new TravelPlan(1, "Testing Plan", date, date));
            fakeData.add(new TravelPlan(2, "Testing Plan2", date, date));
            fakeData.add(new TravelPlan(3, "Testing Plan3", date, date));
            return new ByPassCall<>(fakeData);
        }

        @Override
        public Call<TravelPlan> createTravelPlan(String token, TravelPlan.Create travelPlan) {
            return null;
        }

        @Override
        public Call<String> renewJoinlink(String token, String travelPlanId) {
            return null;
        }

        @Override
        public Call<TravelPlan> joinTravelPlan(String token, String joinlink) {
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
            return new ByPassCall<>(event.toGET(Auth.getInstance().getUsername()));
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
        public Call<TravelPlan> getTravelPlan(String token, String travelPlanId) {
            return new ByPassCall<>(travelPlans.get(Integer.parseInt(travelPlanId) - 1));
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
    }
}
