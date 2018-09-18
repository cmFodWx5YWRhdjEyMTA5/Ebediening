package com.ebediening.Utilites;

import com.ebediening.Response.BookingResponse;
import com.ebediening.Response.FavResponse;
import com.ebediening.Response.LoginResponse;
import com.ebediening.Response.NotifyResponse;
import com.ebediening.Response.OdrHistoryResponse;
import com.ebediening.Response.OrdrResponse;
import com.ebediening.Response.TrackResponse;
import com.ebediening.Response.UserResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface EbedieningService {
    @FormUrlEncoded
    @POST("register")
    Call<UserResponse> register(@Field("email") String email,
                                @Field("password") String password,
                                @Field("fullname") String fullName,
                                @Field("device_id") String deviceId);

    @FormUrlEncoded
    @POST("list_notifications")
    Call<NotifyResponse> getNotification(@Field("user_id") String userId);

    @FormUrlEncoded
    @POST("get_bookings_by_user")
    Call<BookingResponse> getBookings(@Field("user_id") String userId);

    @FormUrlEncoded
    @POST("resend_verfication_code")
    Call<UserResponse> resendCode(@Field("user_id") String userId);

    @FormUrlEncoded
    @POST("book_table")
    Call<UserResponse> bookTable(@Field("user_id") String userId,
                                  @Field("restaurant_id") String restaurantId,
                                  @Field("contact_name") String contactName,
                                  @Field("no_of_persons") String noOfPersons,
                                  @Field("booking_date") String bookingDate,
                                  @Field("booking_time") String bookingTime,
                                  @Field("contact_phone") String contactPhone,
                                  @Field("contact_email") String contactEmail,
                                  @Field("message") String message);

    @FormUrlEncoded
    @POST("pending_orders")
    Call<OrdrResponse> getOrders(@Field("user_id") String userId);

    @FormUrlEncoded
    @POST("order_history")
    Call<OdrHistoryResponse> orderHistory (@Field("user_id") String userId);

    @FormUrlEncoded
    @POST("favorite_restaurants")
    Call<FavResponse> favList (@Field("user_id") String userId);

    @FormUrlEncoded
    @POST("unfavorite_restaurant_by_user")
    Call<UserResponse> unFavorite (@Field("user_id") String userId,
                                  @Field("restaurant_id") String restaurantId);


    @FormUrlEncoded
    @POST("register_by_facebook")
    Call<UserResponse> registerFb(@Field("id") String id,
                                  @Field("email") String email,
                                  @Field("first_name") String firstName,
                                  @Field("last_name") String lastName,
                                  @Field("gender") String gender,
                                  @Field("image") String image,
                                  @Field("device_id") String deviceId);

    @FormUrlEncoded
    @POST("register_by_googleplus")
    Call<UserResponse> registerGplus(@Field("id") String id,
                                     @Field("email") String email,
                                     @Field("given_name") String givenName,
                                     @Field("family_name") String familyName,
                                     @Field("picture") String picture,
                                     @Field("device_id") String deviceId);

    @FormUrlEncoded
    @POST("login")
    Call<LoginResponse> login(@Field("email") String email,
                              @Field("password") String password,
                              @Field("device_id") String deviceId);

    @FormUrlEncoded
    @POST("track_order_status")
    Call<TrackResponse> trackStatus(@Field("order_id") String orderId);

    @FormUrlEncoded
    @POST("forgot_password")
    Call<LoginResponse> forget(@Field("email") String email);


}
