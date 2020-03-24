package com.bipin.healthcare.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bipin.healthcare.MapActivity;
import com.bipin.healthcare.R;

public class AboutFragment extends Fragment {
    Button rateUs,who;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_about,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rateUs=view.findViewById(R.id.rateUs);
        who=view.findViewById(R.id.who);

        rateUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rateIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+getContext().getPackageName()));
                startActivity(rateIntent);
            }
        });
        who.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), MapActivity.class);
                intent.putExtra("Who",true);
                startActivity(intent);
            }
        });
    }
}
