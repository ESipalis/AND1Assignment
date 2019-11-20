package com.example.andassignment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.andassignment.api.ApiServiceFactory;
import com.example.andassignment.api.PokemonDto;
import com.example.andassignment.model.Pokemon;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PokemonAddActivity extends AppCompatActivity {

    private EditText editTextFindPokemonNameOrId;
    private LinearLayout foundPokemonContainer;

    private ImageView imageViewFoundPokemonImage;
    private TextView textViewFoundPokemonName;
    private EditText editTextCustomPokemonName;

    private Pokemon foundPokemon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.pokemon_add);

        editTextFindPokemonNameOrId = findViewById(R.id.editTextFindPokemonNameOrId);
        foundPokemonContainer = findViewById(R.id.foundPokemonContainer);
        imageViewFoundPokemonImage = findViewById(R.id.imageViewFoundPokemonImage);
        textViewFoundPokemonName = findViewById(R.id.textViewFoundPokemonName);
        editTextCustomPokemonName = findViewById(R.id.editTextCustomPokemonName);

        showFoundPokemon(null);
    }

    public void findPokemon(View view) {
        Call<PokemonDto> pokemonCall = ApiServiceFactory.getPokemonApi().getPokemon(editTextFindPokemonNameOrId.getText().toString());
        pokemonCall.enqueue(new Callback<PokemonDto>() {
            @Override
            public void onResponse(Call<PokemonDto> call, Response<PokemonDto> response) {
                if (response.isSuccessful()) {
                    showFoundPokemon(response.body().asPokemon());
                } else {
                    new AlertDialog.Builder(getApplicationContext())
                            .setTitle("Unsuccessful response")
                            .setMessage(response.body() + "")
                            .show();
                }
            }

            @Override
            public void onFailure(Call<PokemonDto> call, Throwable t) {
                new AlertDialog.Builder(getApplicationContext())
                        .setTitle("Error occurred")
                        .setMessage(t.getMessage())
                        .show();

            }
        });
    }

    private void showFoundPokemon(Pokemon pokemon) {
        this.foundPokemon = pokemon;
        if (pokemon == null) {
            foundPokemonContainer.setVisibility(View.INVISIBLE);
            return;
        }


        View currentView = getCurrentFocus();
        if (currentView != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(currentView.getWindowToken(), 0);
        }
        editTextFindPokemonNameOrId.clearFocus();
        editTextFindPokemonNameOrId.setText(null);
        foundPokemonContainer.setVisibility(View.VISIBLE);
        textViewFoundPokemonName.setText(pokemon.getOriginalName());
        Glide.with(this).load(pokemon.getImageUrl()).into(imageViewFoundPokemonImage);
    }

    public void addPokemon(View view) {
        foundPokemon.setCustomName(editTextCustomPokemonName.getText().toString());
        PokemonRepository.INSTANCE.addPokemon(foundPokemon)
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        showFoundPokemon(null);
                        Toast.makeText(PokemonAddActivity.this, "Pokemon added successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(PokemonAddActivity.this, "An error occurred while trying to add new pokemon", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
