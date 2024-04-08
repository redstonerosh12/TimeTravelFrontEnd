package com.example.testapp.api;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.testapp.model.EventModel;
import com.example.testapp.model.Token;
import com.example.testapp.model.TravelPlan;
import com.example.testapp.model.User;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class API {
    public static APIBuilder<String> ping() {
        return new APIBuilder<>(Controller.getServiceScalar().ping());
    }

    public static class Auth {
        public static APIBuilder<Token> register(String username, String password, String email) {
            return new APIBuilder<>(Controller.getService().register(new User(username, password, email)));
        }

        public static APIBuilder<Service.Message> logOut(String token) {
            return new APIBuilder<>(Controller.getService().logOut(token));
        }

        public static APIBuilder<Token> authenticate(User user) {
            return new APIBuilder<>(Controller.getService().authenticate(user));
        }
    }

    public static class TravelPlans {
        public static APIBuilder<TravelPlan> create(com.example.testapp.middleware.Auth auth, TravelPlan.Create travelPlan) {
            return new APIBuilder<>(Controller.getService().createTravelPlan(auth.getToken(), travelPlan));
        }

        public static APIBuilder<ArrayList<com.example.testapp.model.TravelPlan>> get(com.example.testapp.middleware.Auth auth) {
            return new APIBuilder<>(Controller.getService().getTravelPlans(auth.getToken()));
        }

        public static APIBuilder<ResponseBody> update(com.example.testapp.middleware.Auth auth, TravelPlan travelPlan) {
            return new APIBuilder<>(Controller.getService().updateTravelPlan(auth.getToken(), travelPlan.getId(), new TravelPlan.Create(travelPlan)));
        }

        public static APIBuilder<ResponseBody> delete(com.example.testapp.middleware.Auth auth, String travelPlanId) {
            return new APIBuilder<>(Controller.getService().deleteTravelPlan(auth.getToken(), travelPlanId));
        }

        public static APIBuilder<String> renewJoinlink(com.example.testapp.middleware.Auth auth, String travelPlanId) {
            return new APIBuilder<>(Controller.getServiceScalar().renewJoinlink(auth.getToken(), travelPlanId));
        }

        public static APIBuilder<TravelPlan> joinTravelPlan(com.example.testapp.middleware.Auth auth, String joinCode) {
            return new APIBuilder<>(Controller.getService().joinTravelPlan(auth.getToken(), joinCode));
        }

        /**
         * Returns an TravelPlan Object populated with the details pertaining to the Travel Plan (id)
         *
         * @param auth         Auth object
         * @param travelPlanId Travel Plan information
         * @return the image at the specified URL
         * @see APIBuilder<TravelPlan>
         */
        public static APIBuilder<TravelPlan> getTravelPlan(com.example.testapp.middleware.Auth auth, String travelPlanId) {
            return new APIBuilder<>(Controller.getService().getTravelPlan(auth.getToken(), travelPlanId));
        }
    }

    public static class Event {
        public static APIBuilder<EventModel.GET> create(com.example.testapp.middleware.Auth auth, String travelPlanId, EventModel.Create event) {
            return new APIBuilder<>(Controller.getService().createEvent(auth.getToken(), travelPlanId, event));
        }

        public static APIBuilder<EventModel> update(com.example.testapp.middleware.Auth auth, String travelPlanId, EventModel event) {
            return new APIBuilder<>(Controller.getService().updateEvent(auth.getToken(), travelPlanId, event.getId(), event));
        }

        public static APIBuilder<EventModel.GET> getEvent(com.example.testapp.middleware.Auth auth, String travelPlanId, String eventId) {
            return new APIBuilder<>(Controller.getService().getEvent(auth.getToken(), travelPlanId, eventId));
        }

        public static APIBuilder<String> delete(com.example.testapp.middleware.Auth auth, String travelPlanId, String eventId) {
            return new APIBuilder<>(Controller.getServiceScalar().deleteEvent(auth.getToken(), travelPlanId, eventId));
        }

    }

    public static class APIBuilder<T> {
        private final Call<T> call;
        private Response<T> response;
        private Failure<T> failure;

        protected APIBuilder(Call<T> call) {
            this.call = call;
        }

        public APIBuilder<T> setOnResponse(Response<T> response) {
            this.response = response;
            return this;
        }

        public APIBuilder<T> setOnFailure(Failure<T> failure) {
            this.failure = failure;
            return this;
        }

        public void fetch() {
            call.enqueue(new retrofit2.Callback<T>() {
                @Override
                public void onResponse(@NonNull Call<T> call, @NonNull retrofit2.Response<T> res) {
                    Log.i("Debugger", res.raw().request().toString());
                    if (res.raw().request().body() != null)
                        Log.i("Debugger", res.raw().request().body().toString());
                    if (res.isSuccessful() && res.body() != null) response.onResponse(res.body());
                    else {
                        Log.e("API:Failure", res.toString());
                        failure.onFailure(res);
                    }
                }

                /**
                 * @see <a href="https://stackoverflow.com/questions/56092243/how-to-fix-this-error-retrofit2-executorcalladapterfactoryexecutorcallbackcall">onFailure</a>
                 * */
                @Override
                public void onFailure(@NonNull Call<T> call, @NonNull Throwable throwable) {
                    //Executing the call failed (this is not an http error)
                    Log.e("API:Critical", throwable.toString());
                    Log.e("API:Critical", call.toString());
                }
            });
        }
    }

    public static abstract class Callback<T> implements Response<T>, Failure<T>{
        public void onFinal() {
        }
    }
}
