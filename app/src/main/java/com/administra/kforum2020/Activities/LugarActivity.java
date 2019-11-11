package com.administra.kforum2020.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.administra.kforum2020.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class LugarActivity  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venue);
        Button btnGoggleMaps = findViewById(R.id.google_maps1);
        Button btnWaze = findViewById(R.id.waze1);
        Button btnUber = findViewById(R.id.uber1);

        btnGoggleMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/place/Hilton+Mexico+City+Reforma/@19.4345141,-99.1462206,15z/data=!4m8!3m7!1s0x0:0xcc366795ad494b89!5m2!4m1!1i2!8m2!3d19.4345141!4d-99.1462206"));
                startActivity(browserIntent);
            }
        });

        btnWaze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/place/Aut%C3%B3dromo+Hermanos+Rodr%C3%ADguez/@19.4040417,-99.0895156,15z/data=!4m5!3m4!1s0x0:0x33ff6581b240a2dd!8m2!3d19.4040417!4d-99.0895156"));
                startActivity(browserIntent);
            }
        });
        btnUber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/place/Aut%C3%B3dromo+Hermanos+Rodr%C3%ADguez/@19.4040417,-99.0895156,15z/data=!4m5!3m4!1s0x0:0x33ff6581b240a2dd!8m2!3d19.4040417!4d-99.0895156"));
                startActivity(browserIntent);
            }
        });


    }

}
