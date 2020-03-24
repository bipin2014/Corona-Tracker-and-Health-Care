package com.bipin.healthcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.bipin.healthcare.Database.DatabaseController;
import com.bipin.healthcare.Database.Model;
import com.bipin.healthcare.Recycle.RecycleAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InfoActivity extends AppCompatActivity {
    DatabaseController db;
    SharedPreferences sharedPreferences;
    RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private RecycleAdapter recycleAdapter;
    ArrayList<Model> dataArrayList;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        db=new DatabaseController(this);
        sharedPreferences=this.getSharedPreferences("DATA",MODE_PRIVATE);
        new GetData().execute();

        recyclerView=findViewById(R.id.recyclerView);

        setData();

        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        setRecycleAdapter();

        handleIntent(getIntent());


    }

    public void setRecycleAdapter(){
        recycleAdapter = new RecycleAdapter(this, dataArrayList);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recycleAdapter);
    }
    private void setData() {
        dataArrayList = db.getAllData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView =
                (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            Log.i("Search", query);

            dataArrayList = db.searchData(query);
            if (dataArrayList.size() == 0) {
                Toast.makeText(this, "No Result Found", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Found These Result", Toast.LENGTH_SHORT).show();
                setRecycleAdapter();
            }
        }
    }


    private class GetData extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... voids) {

            try {
                Document doc = Jsoup.connect("https://ncov2019.live/").get();

                Elements ptags=doc.select("#sortable_table_Global tbody tr");

                Boolean inserted=sharedPreferences.getBoolean("Inserted",false);
                if(!inserted){
                    for (Element e:ptags){
                        Elements es=e.getElementsByTag("td");
                        insert(es);
                    }
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putBoolean("Inserted",true);
                    editor.apply();

                }else {
                    for (Element e:ptags) {
                        Elements es = e.getElementsByTag("td");
                        update(es);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        private void insert(Elements es) {
            Model model = new Model();
            model.setName(es.get(0).text());
            model.setConfirmed(es.get(1).text());
            model.setCtodayschange(es.get(2).text() );
            model.setDeath(es.get(4).text());
            model.setDtodayschange(es.get(5).text() );
            model.setRecovered(es.get(7).text());
            model.setSerious(es.get(8).text());
            db.insertData(model);
        }

        private void update(Elements es) {
            Model model = new Model();
            model.setName(es.get(0).text());
            model.setConfirmed(es.get(1).text());
            model.setCtodayschange(es.get(2).text() );
            model.setDeath(es.get(4).text());
            model.setDtodayschange(es.get(5).text() );
            model.setRecovered(es.get(7).text());
            model.setSerious(es.get(8).text());

            db.updateData(model);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            dataArrayList = db.getAllData();
            setRecycleAdapter();


        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_reload:
                dataArrayList = db.getAllData();
                setRecycleAdapter();

        }
        return super.onOptionsItemSelected(item);
    }
}
