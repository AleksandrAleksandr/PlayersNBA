package com.example.basketball;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TeamPlayersActivity extends AppCompatActivity {

    ListView list_players;
    HttpUrl.Builder urlBuilder;
    private int team_id;

    ArrayList<String> players_names = new ArrayList<>();
    //ArrayList<JSONObject> players_json = new ArrayList<>();
    TextView text, text2, text3;

    ArrayList <Player> players = new ArrayList<>();
    JsonParser jsonParser;
    ArrayAdapter <String> adapter;

    Player currPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_players);

        list_players = (ListView)findViewById(R.id.list_players);
        team_id = getIntent().getIntExtra("teamId", 1);
        text = findViewById(R.id.textView);
        text2 = findViewById(R.id.textView2);
        text3 = findViewById(R.id.textView3);

        jsonParser = new JsonParser();

        try {
            run();
        }catch (IOException e){
            e.printStackTrace();
        }

        adapter = new ArrayAdapter<>(this, R.layout.list_item, players_names);
        //list_players.setAdapter(adapter);
        listOnClick();
    }


    void run() throws IOException {
        OkHttpClient client = new OkHttpClient();
        String url;
        Request request;

        for (int p = 25; p < 33; p++ ){
            urlBuilder = HttpUrl.parse("https://www.balldontlie.io/api/v1/players").newBuilder();
            urlBuilder.addQueryParameter("page", String.valueOf(p));
            urlBuilder.addQueryParameter("per_page", "100");
            url = urlBuilder.build().toString();

            request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    call.cancel();
                    //text.setText(" call was cancel");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final String myResponse = response.body().string();
                    //text.setText(myResponse);
                    TeamPlayersActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject json = new JSONObject(myResponse);
                                JSONArray data = json.getJSONArray("data");
                                JSONObject person;
                                for (int k = 0; k < 100; k++) {
                                    person = data.getJSONObject(k);
                                    if (person.getJSONObject("team").getInt("id") == team_id) {
                                        //TeamPlayersActivity.this.players_json.add(person);
                                        TeamPlayersActivity.this.players_names.add(person.getString("first_name") + " " + person.getString("last_name"));

                                        list_players.setAdapter(adapter);
                                        //Player player = jsonParser.getPlayer(person);
                                        players.add(jsonParser.getPlayer(person));
                                        text3.setText(String.valueOf(players_names.size()));
                                    }
                                }
                                //json = null;
                                //data = null;
                                //person = null;

                            } catch (JSONException e) {
                                e.printStackTrace();
                                text2.setText(e.getMessage().toString());
                            }
                        }
                    });
                }
            });
        }
    }

    void listOnClick() {
        list_players.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Toast.makeText(TeamPlayersActivity.this, "id " + position, Toast.LENGTH_SHORT).show();
                text3.setText(String.valueOf(players.size()));

                //Intent intent = new Intent("com.example.basketball.PlayerInfo");
                Intent intent = new Intent(TeamPlayersActivity.this, PlayerInfo.class);
                currPlayer = players.get(position);
//                for (Player man :players) {
//                    if (man.getFirst_name() + " " + man.getLast_name() == view.get)
//                }
                intent.putExtra("playerId", position);
                intent.putExtra("first_name", currPlayer.getFirst_name());
                intent.putExtra("last_name", currPlayer.getLast_name());
                intent.putExtra("position", currPlayer.getPosition());
                intent.putExtra("team", currPlayer.getTeam_name());
                intent.putExtra("height_feet", currPlayer.getHeight_feet());
                intent.putExtra("height_inches", currPlayer.getHeight_inches());
                intent.putExtra("weight_pounds", currPlayer.getWeight_pounds());
                intent.putExtra("conference", currPlayer.getConference());
                intent.putExtra("division", currPlayer.getDivision());
                intent.putExtra("city", currPlayer.getCity());
                intent.putExtra("team_id", team_id-1);
                startActivity(intent);
            }
        });
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

}
