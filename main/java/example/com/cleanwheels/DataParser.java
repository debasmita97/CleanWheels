package example.com.cleanwheels;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataParser
{
    private HashMap<String ,String> getSingleNearbyPlace(JSONObject googlePlaceJSon)
    {
        HashMap<String, String> googlePlaceMap = new HashMap<>();
        String NameOfPlace = "-NA-";
        String vincinity= "-NA-";
        String latitude = "";
        String longitude = "";
        String reference = "";
        try {
            if(!googlePlaceJSon.isNull("name"))
            {
                NameOfPlace = googlePlaceJSon.getString("name");
            }
            if(!googlePlaceJSon.isNull("vicinity"))
            {
                vincinity = googlePlaceJSon.getString("vicinity");
            }
            latitude = googlePlaceJSon.getJSONObject("geometry").getJSONObject("location").getString("lat");
            longitude = googlePlaceJSon.getJSONObject("geometry").getJSONObject("location").getString("lng");
            reference = googlePlaceJSon.getString("reference");
            googlePlaceMap.put("Place_Name", NameOfPlace);
            googlePlaceMap.put("vicinity", vincinity);
            googlePlaceMap.put("lat", latitude);
            googlePlaceMap.put("lng", longitude);
            googlePlaceMap.put("reference", reference);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return googlePlaceMap;
    }
    private List<HashMap<String, String>> getAllNearbyPlaces(JSONArray jsonArray)
    {
        int counter= jsonArray.length();
        List<HashMap<String, String>> NearbyPlacesList= new ArrayList<>();
        HashMap<String, String> NearbyPlaceMap= null;
        for(int i=0; i<counter; i++)
        {
            try {
                NearbyPlaceMap = getSingleNearbyPlace((JSONObject) jsonArray.get(i));
                NearbyPlacesList.add(NearbyPlaceMap);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return NearbyPlacesList;
    }
    public List<HashMap<String, String>> parse(String jSONdata)
    {
        JSONArray jsonArray=null;
        JSONObject jsonObject;

        try {
            jsonObject= new JSONObject(jSONdata);
            jsonArray= jsonObject.getJSONArray("results");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getAllNearbyPlaces(jsonArray);
    }
}
