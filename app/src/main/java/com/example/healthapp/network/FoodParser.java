package com.example.healthapp.network;

import com.example.healthapp.util.Nutrients;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class FoodParser {
    public static Nutrients fromJson(String json) throws JSONException {
        if(json == null){
            return null;
        }

        JSONObject mainObj = new JSONObject(json);
        if(mainObj == null) return null;
        JSONArray parsed = mainObj.getJSONArray("parsed");
        if(parsed.length()==0||parsed==null) return null;
        JSONObject firstFromArray = parsed.getJSONObject(0);
        if(firstFromArray==null) return null;
        JSONObject food = firstFromArray.getJSONObject("food");
        if(food == null) return null;
        JSONObject nutrients = food.getJSONObject("nutrients");

        Double carbs = nutrients.has("CHOCDF")? nutrients.getDouble("CHOCDF"): 0.0;
        Double calories = nutrients.has("ENERC_KCAL")? nutrients.getDouble("ENERC_KCAL"): 0.0;

        return new Nutrients(carbs, calories);

    }
}
