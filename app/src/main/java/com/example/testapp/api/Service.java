package com.example.testapp.api;

import com.example.testapp.model.EventModel;
import com.example.testapp.model.Token;
import com.example.testapp.model.TravelPlan;
import com.example.testapp.model.User;
import com.example.testapp.model.Voting;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface Service {
    /**
     * Controller: auth-controller
     * Do not require Authorization except logout
     */
    @POST("/api/v1/auth/register")
    Call<Token> register(@Body User user);

    @POST("/api/v1/auth/log-out")
    Call<Message> logOut(@Header("Authorization") String token);

    @POST("/api/v1/auth/authenticate")
    Call<Token> authenticate(@Body User user);

    /**
     * Controller: Others
     */
    @POST("/api/v1/connection/ping")
    Call<String> ping(); //TODO: Test

    /**
     * Controller: travel-plan-controller
     */
    @GET("/api/v1/travelplans/{travelPlanId}")
    Call<TravelPlan> getTravelPlan(@Header("Authorization") String token, @Path("travelPlanId") String travelPlanId);

    @PUT("/api/v1/travelplans/{travelPlanId}")
    Call<ResponseBody> updateTravelPlan(@Header("Authorization") String token, @Path("travelPlanId") String travelPlanId, @Body TravelPlan.Create travelPlan);

    @DELETE("/api/v1/travelplans/{travelPlanId}") //FIXME: Unable to delete plan
    Call<ResponseBody> deleteTravelPlan(@Header("Authorization") String token, @Path("travelPlanId") String travelPlanId);

    @GET("/api/v1/travelplans")
    Call<ArrayList<TravelPlan>> getTravelPlans(@Header("Authorization") String token);

    @POST("/api/v1/travelplans")
    Call<TravelPlan> createTravelPlan(@Header("Authorization") String token, @Body TravelPlan.Create travelPlan);

    @POST("/api/v1/travelplans/{travelPlanId}/joinlink")
    Call<String> renewJoinlink(@Header("Authorization") String token, @Path("travelPlanId") String travelPlanId);

    @POST("/api/v1/travelplans/join/{joinCode}") //FIXME: Unable to join
    Call<TravelPlan> joinTravelPlan(@Header("Authorization") String token, @Path("joinCode") String joinCode);

    /**
     * Controller: event-controller
     */
    @GET("/api/v1/travelplans/{travelPlanId}/events/{eventId}")
    Call<EventModel.GET> getEvent(@Header("Authorization") String token, @Path("travelPlanId") String travelPlanId, @Path("eventId") String eventId);

    @PUT("/api/v1/travelplans/{travelPlanId}/events/{eventId}") //TODO: Test
    Call<EventModel> updateEvent(@Header("Authorization") String token, @Path("travelPlanId") String travelPlanId, @Path("eventId") String eventId, @Body EventModel event);

    @DELETE("/api/v1/travelplans/{travelPlanId}/events/{eventId}") //TODO: Test
    Call<String> deleteEvent(@Header("Authorization") String token, @Path("travelPlanId") String travelPlanId, @Path("eventId") String eventId);

    @POST("/api/v1/travelplans/{travelPlanId}/events")
    Call<EventModel.GET> createEvent(@Header("Authorization") String token, @Path("travelPlanId") String travelPlanId, @Body EventModel.Create event);

    /**
     * Controller: voting-controller
     */
    @GET("/api/v1/travelplans/{travelPlanId}/voting") //TODO: Test
    Call<ArrayList<Voting>> getVotings(@Header("Authorization") String token, @Path("travelPlanId") String travelPlanId);

    @POST("/api/v1/travelplans/{travelPlanId}/voting") //TODO: Test
    Call<Voting> createVoting(@Header("Authorization") String token, @Path("travelPlanId") String travelPlanId, @Body Voting voting);

    @POST("/api/v1/travelplans/{travelPlanId}/voting/{votingId}/vote") //TODO: Test
    Call<String> createVote(@Header("Authorization") String token, @Path("travelPlanId") String travelPlanId, @Path("votingId") String votingId, @Body Voting.Vote vote);

    @GET("/api/v1/travelplans/{travelPlanId}/voting/{votingId}") //TODO: Test
    Call<Voting> getVoting(@Header("Authorization") String token, @Path("travelPlanId") String travelPlanId, @Path("votingId") String votingId);

    @DELETE("/api/v1/travelplans/{travelPlanId}/voting/{votingId}") //TODO: Test
    Call<String> deleteVoting(@Header("Authorization") String token, @Path("travelPlanId") String travelPlanId, @Path("votingId") String votingId);

     class Message {
        private String message;

         Message(String message) {
             this.message = message;
         }
    }
}