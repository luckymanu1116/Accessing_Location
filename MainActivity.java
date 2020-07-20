package com.example.ca2_227;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
   private static final int REQUEST_LOCATION_PERMISSION =1 ;
    Button LastLocation;
    double lat,lng;
    TextView TextView,address;
    Location location;
    FusedLocationProviderClient fusedLocationProviderClient;
    boolean flag=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView=findViewById(R.id.txt1);
        address=findViewById(R.id.txt2);
        LastLocation=findViewById(R.id.Click);
        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(getApplicationContext());

        LastLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLocation();

            }
        });
    }

    private void getLocation()
    {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        }
        else
        {
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location locationa)
                {
                    if(location!=null)
                    {
                        location=locationa;
                        lat=location.getLatitude();
                        lng=location.getLongitude();
                        TextView.setText(""+lat+"\n"+lng);
                        Geocoder geocoder=new Geocoder(MainActivity.this, Locale.getDefault());
                        try {
                            List<Address> loclist=geocoder.getFromLocation(lat,lng,1);
                            if (loclist.size()>0)
                            {
                                Address add=loclist.get(0);
                                address.setText(""+add);
                            }
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                }
            });
        }
    }
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                    flag=true;

                } else {
                    Toast.makeText(this, "Permission denied",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
