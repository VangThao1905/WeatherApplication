package vangthao.app.weatherapplication.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vangthao.app.weatherapplication.R;
import vangthao.app.weatherapplication.model.places.City;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityViewHolder> {

    private List<City> cityList = new ArrayList<>();

    @NonNull
    @Override
    public CityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_item_row, parent, false);
        return new CityViewHolder(itemView, parent.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull CityViewHolder holder, int position) {
        City currCity = cityList.get(position);
        holder.cityLat.setText(currCity.getLat());
        holder.cityLon.setText(currCity.getLon());
        holder.cityName.setText(currCity.getName());

        holder.cityName.setOnClickListener(v -> {
            City city = new City(currCity.getId(), currCity.getLat(), currCity.getLon(), currCity.getName());
            Intent resultIntent = new Intent();
            resultIntent.putExtra("city", city);
            if (holder.context instanceof Activity) {
                ((Activity) holder.context).setResult(Activity.RESULT_OK, resultIntent);
                ((Activity) holder.context).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return cityList.size();
    }

    public void setCities(List<City> cities) {
        this.cityList = cities;
        notifyDataSetChanged();
    }

    public static class CityViewHolder extends RecyclerView.ViewHolder {
        private final Context context;
        private final TextView cityLat;
        private final TextView cityLon;
        private final TextView cityName;

        public CityViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.cityLat = itemView.findViewById(R.id.txtCityLat);
            this.cityLon = itemView.findViewById(R.id.txtCityLon);
            this.cityName = itemView.findViewById(R.id.txtCityName);
            this.context = context;
        }
    }
}
