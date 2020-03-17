package com.example.basketball;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends YouTubeBaseActivity {

    TextView text;
    //public String url = "https://api-nba-v1.p.rapidapi.com/teams/city/Atlanta";
    //public String url = "https://reqres.in/api/users/2";
    //public String url = "https://free-nba.p.rapidapi.com/games/1";
    HttpUrl.Builder urlBuilder;

    Button button;
    private YouTubePlayerView youTubePlayerView;
    private YouTubePlayer.OnInitializedListener onInitializedListener;

    private ListView list;
    private TeamAdapter myAdapter;
    private String[] teams = new String[] {"Atlanta Hawks", "Boston Celtics",
            "Brooklyn Nets", "Charlotte Hornets", "Chicago Bulls", "Cleveland Cavaliers",
            "Dallas Maverics", "Denver Nuggets", "Detroit Pistons", "Golden State Warriors",
            "Houston Rockets", "Indiana Pacers", "LA Clippers", "LA Lakers", "Memphis Grizzlies",
            "Miami Heat", "Milwaukee Bucks", "Minnesota Timberwolves", "New Orleans Pelicans",
            "New York Knicks", "Oklahoma City Thunder", "Orlando Magic", "Philadelphia 76ers",
            "Phoenix Suns", "Portland Trail Blazers", "Sacramento Kings", "San Antonio Spurs",
            "Toronto Raptors", "Utah Jazz", "Washington Wizards"};
    //private String[] teams = getResources().getStringArray(R.array.team_names);

    int[] team_logo = {R.drawable.ic_atlanta, R.drawable.ic_boston, R.drawable.ic_brooklyn, R.drawable.ic_charlotte, R.drawable.ic_chicago,
            R.drawable.ic_cleveland, R.drawable.ic_dallas, R.drawable.ic_denver, R.drawable.ic_detroit_pistons, R.drawable.ic_warriors,
            R.drawable.ic_houston_rockets, R.drawable.ic_indiana_pacers, R.drawable.ic_la_clippers, R.drawable.ic_la_lakers, R.drawable.ic_memphis_grizzlies,
            R.drawable.ic_miami_heat, R.drawable.ic_milwaukee_bucks, R.drawable.ic_timberwolves, R.drawable.ic_pelicans, R.drawable.ic_new_york_knicks,
            R.drawable.ic_okc_thunder, R.drawable.ic_orlando, R.drawable.ic_76ers, R.drawable.ic_phoenix_suns, R.drawable.ic_portland_trail,
            R.drawable.ic_sacramento_kings, R.drawable.ic_spurs, R.drawable.ic_toronto_raptors, R.drawable.ic_utah_jazz, R.drawable.ic_washington_wizards};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = (TextView)findViewById(R.id.text);

//        try {
//            run();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        button = (Button)findViewById(R.id.button);
        youTubePlayerView = findViewById(R.id.youtube_view);

//        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
//            @Override
//            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
//                youTubePlayer.loadVideo("JsUwmz84e48");
//            }
//
//            @Override
//            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
//
//            }
//        };
//
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                youTubePlayerView.initialize("AIzaSyA4uDPk1F61JwX1HutRe2t0V-NZa5rbFmo", onInitializedListener);
//            }
//        });
        list();
    }

    void list() {
        list = (ListView)findViewById(R.id.list);
//        ArrayAdapter <String> adapter = new ArrayAdapter<>(this, R.layout.list_item, teams);
//        list.setAdapter(adapter);
        myAdapter = new TeamAdapter(this);
        list.setAdapter(myAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(MainActivity.this, "id " + position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent("com.example.basketball.TeamPlayersActivity");
                //Intent intent2 = new Intent(MainActivity.this, TeamPlayersActivity.class);
                intent.putExtra("teamId", position+1);
                startActivity(intent);
            }
        });
    }

    void run() throws IOException {
        OkHttpClient client = new OkHttpClient();

        urlBuilder = HttpUrl.parse("https://www.balldontlie.io/api/v1/season_averages").newBuilder();
        urlBuilder.addQueryParameter("season", "2018");
        urlBuilder.addQueryParameter("player_ids[]", "237");
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .get()
                //.addHeader("x-rapidapi-host", "api-nba-v1.p.rapidapi.com")
                //.addHeader("x-rapidapi-key", "1a79ac2216msh87e00e768ac677fp1c2489jsn4a28f3782df9")
                //.addHeader("x-rapidapi-host", "free-nba.p.rapidapi.com")
                //.addHeader("x-rapidapi-key", "1a79ac2216msh87e00e768ac677fp1c2489jsn4a28f3782df9")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
                text.setText(" call was cancel");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String myResponse = response.body().string();
                //text.setText(myResponse);
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //JSONObject json = new JSONObject(myResponse);
//                            text.setText(json.getJSONObject("home_team").getString("full_name") + " - "
//                                    + json.getJSONObject("visitor_team").getString("full_name"));
                            JSONObject json = new JSONObject(myResponse);
                            text.setText(String.valueOf(json.getJSONArray("data").getJSONObject(0).getLong("games_played")));
                            //text.setText(myResponse);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        });
    }

    private class TeamAdapter extends BaseAdapter {
        private LayoutInflater layoutInflater;

        TeamAdapter(Context context) {
            layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return teams.length;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null)
                convertView = layoutInflater.inflate(R.layout.names, null);

            ImageView image = (ImageView)convertView.findViewById(R.id.image_icon);
            image.setImageResource(team_logo[position]);

            TextView text = convertView.findViewById(R.id.team_name);
            text.setText(teams[position]);

            return convertView;
        }
    }


}


