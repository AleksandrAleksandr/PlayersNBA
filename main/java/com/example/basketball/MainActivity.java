package com.example.basketball;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ListView list;
    private TeamAdapter myAdapter;

    private String[] teams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        teams = getResources().getStringArray(R.array.team_names);
        SomeData.fillData();

        createListOfTeams();
    }

    void createListOfTeams() {
        list = (ListView)findViewById(R.id.list);

        myAdapter = new TeamAdapter(this);
        list.setAdapter(myAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent("com.example.basketball.TeamPlayersActivity");
                intent.putExtra("teamId", position+1);
                startActivity(intent);
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
            image.setImageResource(SomeData.team_logo[position]);

            TextView text = convertView.findViewById(R.id.team_name);
            text.setText(teams[position]);

            return convertView;
        }
    }
}


