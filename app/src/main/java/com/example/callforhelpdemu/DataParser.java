package com.example.callforhelpdemu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataParser {

    private HashMap<String , String> singleNearbyPlaces(JSONObject googlePLaceJSON)
    {
        HashMap<String,String> googlePlaces = new HashMap<>();
        String NameOfPlace = "-NA-";
        String vicinity = "-NA-";
        String latitude = "";
        String longitude = "";
        String reference = "";

        try
        {
            if (!googlePLaceJSON.isNull("name"))
            {
                NameOfPlace = googlePLaceJSON.getString("name");
            }
            if (!googlePLaceJSON.isNull("vicinity"))
            {
                vicinity = googlePLaceJSON.getString("vicinity");
            }
            latitude = googlePLaceJSON.getJSONObject("geometry").getJSONObject("location").getString("lat");
            longitude = googlePLaceJSON.getJSONObject("geometry").getJSONObject("location").getString("lng");
            reference = googlePLaceJSON.getString("reference");

            googlePlaces.put("Place_name",NameOfPlace);
            googlePlaces.put("vicinity",vicinity);
            googlePlaces.put("lat",latitude);
            googlePlaces.put("lng",longitude);
            googlePlaces.put("reference",reference);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        return googlePlaces;

    }

    private List<HashMap<String,String>> allNearbyPlaces(JSONArray jsonArray)
    {
        int count = jsonArray.length();

        List<HashMap<String,String>> NearbyPlacesList = new ArrayList<>();

        HashMap<String,String> Places = null;

        for (int i=0; i<count; i++)
        {
            try
            {
                Places = singleNearbyPlaces((JSONObject) jsonArray.get(i));
                NearbyPlacesList.add(Places);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }

        return NearbyPlacesList;
    }

    public List<HashMap<String,String>> parse(String JSONdata)
    {
        JSONArray jsonArray = null;
        JSONObject jsonObject;

        try
        {
            jsonObject = new JSONObject(JSONdata);
            jsonArray = jsonObject.getJSONArray("results");
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        return allNearbyPlaces(jsonArray);
    }
}
