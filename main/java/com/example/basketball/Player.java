package com.example.basketball;

public class Player {

    private int id;
    private String first_name;
    private String last_name;
    private String position;
    private int height_feet;
    private int height_inches;
    private int weight_pounds;

    private int team_id;
    private String abbreviation;
    private String city;
    private String conference;
    private String division;
    private String team_name;

    Player(int id_, String first_name_, String last_name_, String position_, int height_feet_, int height_inches_, int weight_pounds_,
           String abbreviation_, String city_, String conference_, String division_, String team_name_){
        id = id_;
        first_name = first_name_;
        last_name = last_name_;
        position = position_;
        height_feet = height_feet_;
        height_inches = height_inches_;
        weight_pounds = weight_pounds_;
        abbreviation = abbreviation_;
        city = city_;
        conference = conference_;
        division = division_;
        team_name = team_name_;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getPosition() {
        return position;
    }

    public int getHeight_feet() {
        return height_feet;
    }

    public int getHeight_inches() {
        return height_inches;
    }

    public int getWeight_pounds() {
        return weight_pounds;
    }

    public int getTeam_id() {
        return team_id;
    }

    public int getId() {
        return id;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public String getCity() {
        return city;
    }

    public String getConference() {
        return conference;
    }

    public String getDivision() {
        return division;
    }

    public String getTeam_name() {
        return team_name;
    }
}
