package com.numberONE.maryfarm.Retrofit;

import com.numberONE.maryfarm.Retrofit.Diary.DiaryInit;
import com.numberONE.maryfarm.Retrofit.Diary.DiaryTopRecommend;
import com.numberONE.maryfarm.Retrofit.Diary.UserItem;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;

public interface RetrofitApiSerivce {

//     diary api 시작

//    @POST("diary/follower")
//    Call<UserApi> postFollowerFeed(@Body String userid);

    @Multipart
    @POST("maryfarm-plant-service/api/diary/init")
    Call<DiaryInit> postInitFeed( @Part MultipartBody.Part file
                                 ,@PartMap Map<String, RequestBody> params);

//    @Multipart
//    @POST("api/diary/init")
//    Call<DiaryInit> postInitFeed( @Part MultipartBody.Part image
//                                 ,@PartMap Map<String, RequestBody> params);

    @Multipart
    @POST("api/diary/add")
    Call<DiaryInit> postAddFeed( @Part MultipartBody.Part file
                                ,@PartMap Map<String,RequestBody> params);

//    @GET("api/diary/follower/{userid}")
//    Call<List<UserItem>> getFollowerFeed(@Path("userid") String userid);

//    @GET("api/diary/top")
//    Call<List<DiaryTopRecommend>> getOthersFeed();




//    diary api 끝

}
