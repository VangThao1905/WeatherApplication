package vangthao.app.weatherapplication.repository;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import vangthao.app.weatherapplication.model.places.CitySave;
import vangthao.app.weatherapplication.remote.CityLoadListener;

public class CitySavedRepository {

    private static CitySavedRepository instance;
    private final ArrayList<CitySave> citySaves = new ArrayList<>();
    private static Application application;
    private static CityLoadListener cityLoadListener;
    DatabaseReference databaseReference;

    public static CitySavedRepository getInstance(Context context) {
        if (instance == null) {
            instance = new CitySavedRepository(application);
        }
        cityLoadListener = (CityLoadListener) context;
        return instance;
    }

    public CitySavedRepository(Application application) {
        CitySavedRepository.application = application;
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public MutableLiveData<ArrayList<CitySave>> getCitiesSaved(String email) {
        loadCityListSaved(email);
        MutableLiveData<ArrayList<CitySave>> citySave = new MutableLiveData<>();
        citySave.setValue(citySaves);
        return citySave;
    }

    public void saveCity(CitySave citySave) {
        databaseReference.child("CITY_SAVED").push().setValue(citySave, (error, ref) -> {
            if (error == null) {
                Toast.makeText(application.getApplicationContext(), "Save place success!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(application.getApplicationContext(), "Save place failure!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void deleteCity(CitySave cityDelete) {
        Query query = databaseReference.child("CITY_SAVED").orderByChild("email").equalTo(cityDelete.getEmail());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot phongSnapshot : dataSnapshot.getChildren()) {
                    CitySave citySave = phongSnapshot.getValue(CitySave.class);
                    assert citySave != null;
                    if (citySave.getCity().getId() == cityDelete.getCity().getId()) {
                        phongSnapshot.getRef().removeValue();
                        Toast.makeText(application.getApplicationContext(), "Delete place success!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(application.getApplicationContext(), "Delete place failure!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void loadCityListSaved(String email) {
        citySaves.clear();
        Query query = databaseReference.child("CITY_SAVED");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    CitySave citySave = dataSnapshot.getValue(CitySave.class);
                    assert citySave != null;
                    if (citySave.getEmail().equals(email)) {
                        citySaves.add(dataSnapshot.getValue(CitySave.class));
                    }
                }
                cityLoadListener.onCityLoaded();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
