package com.example.basketball;

public class Stats {
    private int season, games;
    String min;
    private double from_game, three, free_throw, of_reb, def_reb, assists, steals, blocks, turnovers, fouls, points;

    Stats(int season, int games, String min, double from_game, double three, double free_throw,
          double of_reb, double def_reb, double assists, double steals, double blocks, double turnovers, double fouls, double points){
        this.season = season;
        this.games = games;
        this.min = min;
        this.from_game = from_game;
        this.three = three;
        this.free_throw = free_throw;
        this.of_reb = of_reb;
        this.def_reb = def_reb;

        this.points = points;
        this.assists = assists;
        this.steals = steals;
        this.blocks = blocks;
        this.turnovers = turnovers;
        this.fouls = fouls;
    }

    public int getGames() {
        return games;
    }

    public double getPoints() {
        return points;
    }

    public double getAssists() {
        return assists;
    }

    public double getSteals() {
        return steals;
    }

    public double getBlocks() {
        return blocks;
    }

    public int getSeason() {
        return season;
    }

    public String getMin() {
        return min;
    }

    public double getFrom_game() {
        return from_game;
    }

    public double getThree() {
        return three;
    }

    public double getFree_throw() {
        return free_throw;
    }

    public double getOf_reb() {
        return of_reb;
    }

    public double getDef_reb() {
        return def_reb;
    }

    public double getTurnovers() {
        return turnovers;
    }

    public double getFouls() {
        return fouls;
    }
}
