package com.example.basketball;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonParser {

    public Player getPlayer(JSONObject obj) throws JSONException{
        int id = obj.getInt("id");
        String f_name = obj.getString("first_name");
        String s_name = obj.getString("last_name");
        String pos = obj.getString("position");
        int height_feet;
        if (obj.isNull("height_feet"))
            height_feet = 0;
        else height_feet = obj.getInt("height_feet");

        int height_inches;
        if (obj.isNull("height_inches"))
            height_inches = 0;
        else height_inches = obj.getInt("height_inches");

        int weight_pounds;
        if (obj.isNull("weight_pounds"))
            weight_pounds = 0;
        else weight_pounds = obj.getInt("weight_pounds");

        String abbreviation = obj.getJSONObject("team").getString("abbreviation");
        String city = obj.getJSONObject("team").getString("city");
        String conference = obj.getJSONObject("team").getString("conference");
        String division = obj.getJSONObject("team").getString("division");
        String team_name = obj.getJSONObject("team").getString("full_name");

        return new Player(id, f_name, s_name, pos, height_feet, height_inches, weight_pounds, abbreviation, city, conference, division, team_name);
    }
}