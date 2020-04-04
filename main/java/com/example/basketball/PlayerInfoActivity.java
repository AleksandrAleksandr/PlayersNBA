package com.example.basketball;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerFullScreenListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PlayerInfoActivity extends AppCompatActivity {

    TextView first_name, last_name, position, team, city, conference, division, height, weight;
    ImageView image;

    HttpUrl.Builder urlBuilder;
    TextView season, games, minutes, from_game, three, free_throw, of_reb, def_reb, assists, steals, blocks, turnovers, fouls, points;
    Stats averages;
    JsonParser jsonParser;

    String[] seasons = {"2019", "2018", "2017", "2016", "2015", "2014", "2013", "2012"};
    Spinner spinner;

    int teamId;
    int playerId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_info);

        findViews();
        setInfo();
        jsonParser = new JsonParser();

        createSpinner();
    }

    private void findViews(){
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

        season = findViewById(R.id.season);

        games = findViewById(R.id.games);
        minutes = findViewById(R.id.minutes);
        from_game = findViewById(R.id.from_game_pct);
        three = findViewById(R.id.three_point_pct);
        free_throw = findViewById(R.id.free_throw_pct);
        of_reb = findViewById(R.id.oreb);
        def_reb = findViewById(R.id.dreb);
        assists = findViewById(R.id.assists);
        steals = findViewById(R.id.steals);
        blocks = findViewById(R.id.blocks);
        turnovers = findViewById(R.id.turnovers);
        fouls = findViewById(R.id.fouls);
        points = findViewById(R.id.points);
    }

    private void setInfo() {
        Intent parent = getIntent();

        teamId = parent.getIntExtra("team_id", 0);
        playerId = parent.getIntExtra("player_id", 0);
        image.setImageResource(SomeData.team_logo[teamId]);

        if (SomeData.videos.containsKey(playerId)){
            String video_id = SomeData.videos.get(playerId);
            loadVideo(video_id);
        }

        first_name.setText(parent.getStringExtra("first_name"));
        last_name.setText(parent.getStringExtra("last_name"));
        position.setText(parent.getStringExtra("position"));
        //position.setText("PlayeId " + String.valueOf(playerId));
        team.setText(parent.getStringExtra("team"));
        city.setText(parent.getStringExtra("city"));
        conference.setText(parent.getStringExtra("conference"));
        division.setText(parent.getStringExtra("division"));
        height.setText(String.valueOf(parent.getIntExtra("height_feet", -1)));
        weight.setText(String.valueOf(parent.getIntExtra("weight_pounds", -1)));
    }

    private void createSpinner() {
        spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, seasons);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setPrompt("Season");

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                season.setText(getSelectedSeason());
                try {
                    run();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    void run() throws IOException {
        OkHttpClient client = new OkHttpClient();
        String url;
        Request request;
        String season = spinner.getSelectedItem().toString();


            urlBuilder = HttpUrl.parse("https://www.balldontlie.io/api/v1/season_averages").newBuilder();
            urlBuilder.addQueryParameter("season", season);
            urlBuilder.addQueryParameter("player_ids[]", String.valueOf(playerId));
            url = urlBuilder.build().toString();

            request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    call.cancel();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final String myResponse = response.body().string();

                    PlayerInfoActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject json = new JSONObject(myResponse);
                                JSONArray data = json.getJSONArray("data");
                                if (data.length() != 0) {
                                    JSONObject stats = data.getJSONObject(0);
                                    //averages.add(jsonParser.getStats(stats));
                                    averages = jsonParser.getStats(stats);
                                    setStats();
                                }else {
                                    setZeroStats();
                                    Toast.makeText(PlayerInfoActivity.this, "Man don't play this year", Toast.LENGTH_LONG).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            });


    }

    private String getSelectedSeason() {
        Integer year = Integer.valueOf(spinner.getSelectedItem().toString());
        String season = "Season " + String.valueOf(year) + " - " + String.valueOf(year+1);
        return season;
    }

    private void setStats() {
        games.setText(String.valueOf(averages.getGames()));
        minutes.setText(averages.getMin());
        from_game.setText(String.valueOf(averages.getFrom_game()));
        three.setText(String.valueOf(averages.getThree()));
        free_throw.setText(String.valueOf(averages.getFree_throw()));
        of_reb.setText(String.valueOf(averages.getOf_reb()));
        def_reb.setText(String.valueOf(averages.getDef_reb()));
        assists.setText(String.valueOf(averages.getAssists()));
        steals.setText(String.valueOf(averages.getSteals()));
        blocks.setText(String.valueOf(averages.getBlocks()));
        turnovers.setText(String.valueOf(averages.getTurnovers()));
        fouls.setText(String.valueOf(averages.getFouls()));
        points.setText(String.valueOf(averages.getPoints()));
    }

    private void setZeroStats() {
        games.setText("");
        minutes.setText("");
        from_game.setText("");
        three.setText("");
        free_throw.setText("");
        of_reb.setText("");
        def_reb.setText("");
        assists.setText("");
        steals.setText("");
        blocks.setText("");
        turnovers.setText("");
        fouls.setText("");
        points.setText("");
    }

    private void loadVideo(final String video_id){

        LinearLayout mainLayout = (LinearLayout)findViewById(R.id.main_layout);
        YouTubePlayerView youTubePlayerView = new YouTubePlayerView(this);
        mainLayout.addView(youTubePlayerView);

        getLifecycle().addObserver(youTubePlayerView);

        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                //String videoId = video_id;
                youTubePlayer.cueVideo(video_id, 0f);
            }
        });
    }

}