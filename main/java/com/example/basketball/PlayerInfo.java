package com.example.basketball;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PlayerInfo extends AppCompatActivity {

    TextView first_name, last_name, position, team, city, conference, division, height, weight;
    ImageView image;

    int teamId;
    int[] team_logo = {R.drawable.ic_atlanta, R.drawable.ic_boston, R.drawable.ic_brooklyn, R.drawable.ic_charlotte, R.drawable.ic_chicago,
            R.drawable.ic_cleveland, R.drawable.ic_dallas, R.drawable.ic_denver, R.drawable.ic_detroit_pistons, R.drawable.ic_warriors,
            R.drawable.ic_houston_rockets, R.drawable.ic_indiana_pacers, R.drawable.ic_la_clippers, R.drawable.ic_la_lakers, R.drawable.ic_memphis_grizzlies,
            R.drawable.ic_miami_heat, R.drawable.ic_milwaukee_bucks, R.drawable.ic_timberwolves, R.drawable.ic_pelicans, R.drawable.ic_new_york_knicks,
            R.drawable.ic_okc_thunder, R.drawable.ic_orlando, R.drawable.ic_76ers, R.drawable.ic_phoenix_suns, R.drawable.ic_portland_trail,
            R.drawable.ic_sacramento_kings, R.drawable.ic_spurs, R.drawable.ic_toronto_raptors, R.drawable.ic_utah_jazz, R.drawable.ic_washington_wizards};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_info);

        image = findViewById(R.id.imageView);

        first_name = findViewById(R.id.first_name);
        last_name = findViewById(R.id.last_name);
        position = findViewById(R.id.position);
        team = findViewById(R.id.team);
        city = findViewById(R.id.city);
        conference = findViewById(R.id.conference);
        division = findViewById(R.id.division);
        height = findViewById(R.id.height);
        weight = findViewById(R.id.weight);

        Intent parent = getIntent();

        teamId = parent.getIntExtra("team_id", 0);
        image.setImageResource(team_logo[teamId]);

        first_name.setText(parent.getStringExtra("first_name"));
        last_name.setText(parent.getStringExtra("last_name"));
        position.setText(parent.getStringExtra("position"));
        team.setText(parent.getStringExtra("team"));
        city.setText(parent.getStringExtra("city"));
        conference.setText(parent.getStringExtra("conference"));
        division.setText(parent.getStringExtra("division"));
        height.setText(String.valueOf(parent.getIntExtra("height_feet", -1)));
        weight.setText(String.valueOf(parent.getIntExtra("weight_pounds", -1)));

    }

}
