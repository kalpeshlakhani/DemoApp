package com.demoapp.APIUtils;

import com.demoapp.Model.MovieDetails;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


public class JSONParser {

    public static List<MovieDetails> getMovieList(String jsonString) throws JSONException {
        JSONObject responseJson;
        Gson gson =  new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

        responseJson = new JSONObject(jsonString);

        return gson.fromJson(responseJson.getJSONArray("results").toString(), new TypeToken<List<MovieDetails>>(){}.getType());

    }
}
