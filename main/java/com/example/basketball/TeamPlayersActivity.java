package com.example.basketball;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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

    private int team_id;
    ListView list_players;
    ArrayAdapter <String> adapter;
    HttpUrl.Builder urlBuilder;
    JsonParser jsonParser;

    ArrayList<String> players_names = new ArrayList<>();
    ArrayList <Player> players = new ArrayList<>();
    EditText inputSearch;

    Player currPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_players);

        init();

        try {
            run();
        }catch (IOException e){
            e.printStackTrace();
        }

        listOnClick();

        createSearch();
    }

    private void init() {
        inputSearch = (EditText)findViewById(R.id.editText);
        list_players = (ListView)findViewById(R.id.list_players);
        team_id = getIntent().getIntExtra("teamId", 1);

        jsonParser = new JsonParser();

        adapter = new ArrayAdapter<>(this, R.layout.list_item, players_names);
    }

    void run() throws IOException {
        OkHttpClient client = new OkHttpClient();
        String url;
        Request request;

        for (int p = 25; p < 34; p++ ){
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
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final String myResponse = response.body().string();

                    TeamPlayersActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject json = new JSONObject(myResponse);
                                JSONArray data = json.getJSONArray("data");
                                JSONObject person;
                                for (int k = 0; k < data.length(); k++) {
                                    person = data.getJSONObject(k);
                                    if (person.getJSONObject("team").getInt("id") == team_id) {
                                        players_names.add(person.getString("first_name") + " " + person.getString("last_name"));
                                        //adapter.notifyDataSetChanged();
                                        list_players.setAdapter(adapter);

                                        players.add(jsonParser.getPlayer(person));
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
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

                TextView selectedItem = (TextView)view;

                //currPlayer = players.get(position);
                for (Player man :players) {
                    if ((selectedItem.getText()).equals(man.getFirst_name() + " " + man.getLast_name())) {
                        currPlayer = man;
                        break;
                    }
                }

                Intent intent = new Intent(TeamPlayersActivity.this, PlayerInfoActivity.class);
                intent.putExtra("player_id", currPlayer.getId());
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

    void createSearch() {
        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
