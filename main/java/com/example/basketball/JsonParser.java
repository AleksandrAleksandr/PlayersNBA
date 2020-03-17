package com.example.basketball;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

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

    public Stats getStats(JSONObject obj) throws JSONException{
        int games = obj.getInt("games_played");
        int season = obj.getInt("season");

        String min = obj.getString("min");
        double from_game = roundResult(obj.getDouble("fg_pct")*100);
        double three = roundResult(obj.getDouble("fg3_pct")*100);
        double free_throw = roundResult(obj.getDouble("ft_pct")*100);
        double of_reb = obj.getDouble("oreb");
        double def_reb = obj.getDouble("dreb");
        double assists = obj.getDouble("ast");
        double steals = obj.getDouble("stl");
        double blocks = obj.getDouble("blk");
        double turnovers = obj.getDouble("turnover");
        double fouls = obj.getDouble("pf");
        double points = obj.getDouble("pts");

        return new Stats(season, games, min, from_game, three, free_throw, of_reb, def_reb, assists, steals, blocks, turnovers, fouls, points);
    }

    private double roundResult (double d) {
        d = d*10;
        int i = (int) Math.round(d);
        return (double) i/10;
    }
}