package com.example.kirmi.ks1807;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class RestInterface {
    //static final String BASE_URL = "localhost:4567/";
    static final String BASE_URL = "http://pe-ks1807.scem.westernsydney.edu.au/MMH_API/webresources/";
    //static final String BASE_URL = "http://pe-ks1807.scem.uws.edu.au:8080/MMH_API/webresources/";

    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    public static Retrofit retrofit = null;
    public static Retrofit getClient() {
        if (retrofit==null) {
            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create());
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            retrofit = builder.client(httpClient.build()).build();
        }
        return retrofit;
    }

    public static String EncryptPassword(String Password)
    {
        String EncryptedPassword = "";
        try
        {
            //Using MD5 Message-Digest Algorithm to encrypt the password.
            MessageDigest Digest = MessageDigest.getInstance("MD5");

            //Add password bytes to digest.
            Digest.update(Password.getBytes());

            //Get the hash's bytes.
            byte[] Bytes = Digest.digest();

            //Convert these decimal bytes to hexadecimal format.
            StringBuilder StringToBuild = new StringBuilder();

            for(int i=0; i< Bytes.length ;i++)
            {
                StringToBuild.append(Integer.toString((Bytes[i] & 0xff) +
                        0x100, 16).substring(1));
            }

            EncryptedPassword = StringToBuild.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return EncryptedPassword;
    }

    public interface Ks1807Client {
        @GET("mmhpackage.useraccount/GetMusicHistory/{id}/{password}")
        Call<String> GetMusicHistory(@Path("id") String id, @Path("password") String password);

        @GET("mmhpackage.useraccount/GetUserDetailsRegistration/{id}/{password}")
        Call<String> GetUserDetailsRegistration(@Path("id") String id, @Path("password") String password);

        @GET("mmhpackage.useraccount/GetUserDetails/{id}/{password}")
        Call<String> GetUserDetails(@Path("id") String id, @Path("password") String password);

        @GET("mmhpackage.useraccount/GetUserID/{email}")
        Call<String> GetUserID(@Path("email") String email);

        @GET("mmhpackage.useraccount/GetUserSettings/{id}/{password}")
        Call<String> GetUserSettings(@Path("id") String id, @Path("password") String password);

        @GET("mmhpackage.useraccount/InsertNewUser/{firstname}/{lastname}/{email}/{date}/{gender}/{ethicsAgreed}/{password}/")
        Call<String> InsertNewUse(
                @Path("firstname") String firstname, @Path("lastname") String lastname,
                @Path("email") String email, @Path("date") String date,
                @Path("gender") String gender, @Path("ethicsAgreed") String ethicsAgreed,
                @Path("password") String password);

        @GET("mmhpackage.useraccount/UpdateUser/{firstname}/{lastname}/{email}/{date}/{gender}/{id}/{password}/")
        Call<String> UpdateUser(
                @Path("firstname") String firstname,
                @Path("lastname") String lastname, @Path("email") String email,
                @Path("date") String date, @Path("gender") String gender,
                @Path("id") String id, @Path("password") String password);

        @GET("mmhpackage.useraccount/UpdateUserSecondPage/{q1}/{q2}/{q3}/{q4}/{id}/{password}")
        Call<String> UpdateUserSecondPage(@Path("q1") String q1,
                                          @Path("q2") String q2, @Path("q3") String q3,
                                          @Path("q4") String q4, @Path("id") String id,
                                          @Path("password") String password);

        @GET("mmhpackage.useraccount/IsEmailAddressUnique/{email}")
        Call<String> IsEmailAddressUnique(@Path("email") String email);

        @GET("mmhpackage.useraccount/VerifyLogin/{email}/{password}")
        Call<String> VerifyLogin(@Path("email") String email, @Path("password") String password);

        @GET("mmhpackage.useraccount/UpdatePassword/{newpassword}/{id}/{currentpassword}")
        Call<String> UpdatePassword(@Path("newpassword") String newpassword, @Path("id") String id,
                                    @Path("currentpassword") String currpassword);

        @GET("mmhpackage.useraccount/UpdateSettings/{MakeRecommendations}/{frequency}/{id}/{password}")
        Call<String> UpdateSettings(@Path("MakeRecommendations") String MakeRecommendations, @Path("frequency") String frequency,
                                    @Path("id") String id, @Path("password") String password);

        @GET("mmhpackage.useraccount/VerifyPassword/{id}/{password}")
        Call<String> VerifyPassword(@Path("id") String id, @Path("password") String password);

        @GET("mmhpackage.musictrack/TrackStarted/{track}/{genre}/{artist}/{duration}/{moodBefore}/{id}/{password}")
        Call<String> TrackStarted(@Path("track") String track,
                                  @Path("genre") String genre, @Path("artist") String artist,
                                  @Path("duration") String duration, @Path("moodBefore") String moodBefore,
                                  @Path("id") String id, @Path("password") String password);

        @GET("mmhpackage.musictrack/TrackEnded/{moodID}/{moodAfter}/{userLiked}/{entry1}/{entry2}/{entry3}/{id}/{password}")
        Call<String> TrackEnded(@Path("moodID") String moodID, @Path("moodAfter") String moodAfter, @Path("userLiked") String userLiked,
                                @Path("entry1") String entry1, @Path("entry2") String entry2,
                                @Path("entry3") String entry3, @Path("id") String id,
                                @Path("password") String password);

        @GET("mmhpackage.moodscore/GetMoodList")
        Call<String> listRepos();

        @GET("mmhpackage.musictrack/GetRecommendedTracksUser/{id}/{password}")
        Call<String> GetRecommendedTracksUser(@Path("id") String id, @Path("password") String password);

        @GET("mmhpackage.musictrack/GetRecommendedTracksSystem/{id}/{password}")
        Call<String> GetRecommendedTracksSystem(@Path("id") String id, @Path("password") String password);

        @GET("mmhpackage.musictrack/CheckMoodEntry/{id}/{password}")
        Call<String> CheckMoodEntry(@Path("id") String id, @Path("password") String password);
    }

    //Data Structures
    public class Track
    {
        String songName;
        String Genre;
        String Artist;
        String Duration;
    }

    //Use on GetMusicHistory, GetRecommendedTracksUser and GetRecommendedTracksSystem return values
    public static List<Track> getMusicHistory(String body)
    {
        ArrayList<Track> list = new ArrayList<>();
        String result[] = body.split(System.getProperty("line.separator"));
        for(int i = 0; i < result.length; i++)
        {
            Track item = new RestInterface().new Track();
            String temp[] = result[i].split(",");
            item.songName = temp[0];
            item.Genre = temp[1];
            item.Artist = temp[2];
            item.Duration = temp[3];
            list.add(item);
        }

        return list;
    }

}
