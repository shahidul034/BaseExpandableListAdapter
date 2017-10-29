package com.shakibcsekuet.baseexpandablelistadapter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class MainActivity extends AppCompatActivity {

    private LinkedHashMap<String, GroupInfo> team = new LinkedHashMap<String, GroupInfo>();
    private ArrayList<GroupInfo> deptList = new ArrayList<GroupInfo>();

    private CustomAdapter listAdapter;
    private ExpandableListView simpleExpandableListView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // add data for displaying in expandable list view
        loadData();

        //get reference of the ExpandableListView
        simpleExpandableListView = (ExpandableListView) findViewById(R.id.simpleExpandableListView);
        // create the adapter by passing your ArrayList data
        listAdapter = new CustomAdapter(MainActivity.this, deptList);
        // attach the adapter to the expandable list view
        simpleExpandableListView.setAdapter(listAdapter);

        // setOnChildClickListener listener for child row click
        simpleExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                //get the group header
                GroupInfo headerInfo = deptList.get(groupPosition);
                //get the child info
                ChildInfo detailInfo = headerInfo.getPlayerName().get(childPosition);
                //display it or do something with it
                Toast.makeText(getBaseContext(), " Team And Player :: " + headerInfo.getName()
                        + "/" + detailInfo.getName(), Toast.LENGTH_LONG).show();
                return false;
            }
        });
        // setOnGroupClickListener listener for group heading click
        simpleExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                //get the group header
                GroupInfo headerInfo = deptList.get(groupPosition);
                //display it or do something with it
                Toast.makeText(getBaseContext(), " Team Name :: " + headerInfo.getName(),
                        Toast.LENGTH_LONG).show();

                return false;
            }
        });


    }

    // load some initial data into out list
    private void loadData() {

        addProduct("India", "Virat Kohli");
        addProduct("India", "Mahendar Dhoni");
        addProduct("India", "Yuvraj Singh");

        addProduct("Australia", "Brat Lee");
        addProduct("Australia", "Hayden");

    }


    // here we maintain team and player names
    private int addProduct(String teamName, String playerName) {

        int groupPosition = 0;

        //check the hash map if the group already exists
        GroupInfo headerInfo = team.get(teamName);
        //add the group if doesn't exists
        if (headerInfo == null) {
            headerInfo = new GroupInfo();
            headerInfo.setName(teamName);
            team.put(teamName, headerInfo);
            deptList.add(headerInfo);
        }

        // get the children for the group
        ArrayList<ChildInfo> productList = headerInfo.getPlayerName();
        // size of the children list
        int listSize = productList.size();
        // add to the counter
        listSize++;

        // create a new child and add that to the group
        ChildInfo detailInfo = new ChildInfo();
        detailInfo.setName(playerName);
        productList.add(detailInfo);
        headerInfo.setPlayerName(productList);

        // find the group position inside the list
        groupPosition = deptList.indexOf(headerInfo);
        return groupPosition;
    }

}