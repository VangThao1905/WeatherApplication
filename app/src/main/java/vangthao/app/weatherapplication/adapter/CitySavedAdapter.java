package vangthao.app.weatherapplication.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import vangthao.app.weatherapplication.R;
import vangthao.app.weatherapplication.model.places.City;
import vangthao.app.weatherapplication.model.places.CitySave;
import vangthao.app.weatherapplication.viewmodel.CitySaveViewModel;

public class CitySavedAdapter extends RecyclerView.Adapter<CitySavedAdapter.CityViewHolder>{

    private final ArrayList<CitySave> citySaveList;
    private Context context;

    public CitySavedAdapter(ArrayList<CitySave> citySaves){
        this.citySaveList = citySaves;
    }

    @NonNull
    @Override
    public CityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_saved_item_row   , parent, false);
        return new CityViewHolder(itemView, parent.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull CityViewHolder holder, int position) {
        CitySave currCity = citySaveList.get(position);
        holder.cityLatSaved.setText(currCity.getCity().getLat());
        holder.cityLonSaved.setText(currCity.getCity().getLon());
        holder.cityNameSaved.setText(currCity.getCity().getName());

        holder.cityNameSaved.setOnClickListener(v -> {
            City city = new City(currCity.getCity().getId(), currCity.getCity().getLat(), currCity.getCity().getLon(), currCity.getCity().getName());
            Intent resultIntent = new Intent();
            resultIntent.putExtra("city", city);
            if (holder.context instanceof Activity) {
                ((Activity) holder.context).setResult(Activity.RESULT_OK, resultIntent);
                ((Activity) holder.context).finish();
            }
        });
        holder.imgBtnDeletePlace.setOnClickListener(v -> {
            Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.dialog_delete_place);
            dialog.setTitle("Xác nhận xóa");

            Button btnYes_DeletePlace = dialog.findViewById(R.id.btnYes_DeletePlace);
            Button btnNo_DeletePlace = dialog.findViewById(R.id.btnNo_DeletePlace);
            btnYes_DeletePlace.setOnClickListener(v12 -> {
                CitySaveViewModel citySaveViewModel = new ViewModelProvider((FragmentActivity) context).get(CitySaveViewModel.class);
                citySaveViewModel.deleteCity(currCity);
                dialog.dismiss();
            });

            btnNo_DeletePlace.setOnClickListener(v1 -> dialog.dismiss());
            dialog.show();
        });
    }

    @Override
    public int getItemCount() {
        return citySaveList.size();
    }


    public static class CityViewHolder extends RecyclerView.ViewHolder {
        private final Context context;
        private final TextView cityLatSaved;
        private final TextView cityLonSaved;
        private final TextView cityNameSaved;
        private final ImageButton imgBtnDeletePlace;

        public CityViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.cityLatSaved = itemView.findViewById(R.id.txtCityLatSaved);
            this.cityLonSaved = itemView.findViewById(R.id.txtCityLonSaved);
            this.cityNameSaved = itemView.findViewById(R.id.txtCityNameSaved);
            this.imgBtnDeletePlace = itemView.findViewById(R.id.imgViewDeletePlace);
            this.context = context;
        }
    }
}
