package com.bipin.healthcare.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bipin.healthcare.InfoActivity;
import com.bipin.healthcare.MapActivity;
import com.bipin.healthcare.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Context.MODE_PRIVATE;

public class InfoFragment extends Fragment {
    private TextView confirm,death,recovered;
    private SharedPreferences sharedPreferences;
    private Button viewMap,moreDetail;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_info,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        GetData getData=new GetData();
        getData.execute();
        confirm=view.findViewById(R.id.confirm);
        death=view.findViewById(R.id.death);
        recovered=view.findViewById(R.id.recovered);
        viewMap=view.findViewById(R.id.viewMap);
        moreDetail=view.findViewById(R.id.viewDetails);
        sharedPreferences=getContext().getSharedPreferences("DATA",MODE_PRIVATE);
        String confirmString=sharedPreferences.getString("Confirm","229,761");
        String recoveredString=sharedPreferences.getString("Recovered","85,275");
        String deathString=sharedPreferences.getString("Death","9,375");
        String countriesString=sharedPreferences.getString("Countries","165/195");

        confirm.setText(confirmString);
        death.setText(deathString);
        recovered.setText(recoveredString);

        viewMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), MapActivity.class);
                startActivity(intent);
            }
        });

        moreDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), InfoActivity.class);
                startActivity(intent);
            }
        });




    }


    private class GetData extends AsyncTask<Void,Void,Void>{



        @Override
        protected Void doInBackground(Void... voids) {

            String result="";
            try {
                Document doc = Jsoup.connect("https://ncov2019.live/").get();

                Elements ptags=doc.select("p");
                result=ptags.text();
                Log.i("RESULT",result);
                String[] strArray = result.split("Quick Facts updated: a few seconds ago");
                result=strArray[1];
                Log.i("RES",result);
                setToArrayList(result.trim());

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            String confirmString=sharedPreferences.getString("Confirm","229,761");
            String recoveredString=sharedPreferences.getString("Recovered","85,275");
            String deathString=sharedPreferences.getString("Death","9,375");
            String countriesString=sharedPreferences.getString("Countries","165/195");
            confirm.setText(confirmString);
            death.setText(deathString);
            recovered.setText(recoveredString);


        }

        private void setToArrayList(String result){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            //Total Case
            Pattern totalconfirmed=Pattern.compile("(.*?) Total Confirmed Cases");
            Matcher m=totalconfirmed.matcher(result);
            while (m.find()){
                Log.i("Confirm",m.group(1));
                editor.putString("Confirm",m.group(1));
            }
            //Total Death
            Pattern totaldeath=Pattern.compile("Total Confirmed Cases (.*?) Total Deceased");
            m=totaldeath.matcher(result);
            while (m.find()){
                Log.i("Death",m.group(1));
                editor.putString("Death",m.group(1));
            }

            //Total Recovered
            Pattern totalrecovered=Pattern.compile("Total Serious (.*?) Total Recovered");
            m=totalrecovered.matcher(result);
            while (m.find()){
                Log.i("Recovered",m.group(1));
                editor.putString("Recovered",m.group(1));
                editor.apply();
            }
        }
    }


}
