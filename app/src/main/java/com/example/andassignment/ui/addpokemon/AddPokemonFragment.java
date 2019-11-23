package com.example.andassignment.ui.addpokemon;

import androidx.appcompat.app.AlertDialog;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.andassignment.PokemonRepository;
import com.example.andassignment.R;
import com.example.andassignment.api.ApiServiceFactory;
import com.example.andassignment.api.PokemonDto;
import com.example.andassignment.model.Pokemon;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPokemonFragment extends Fragment {

    private EditText editTextFindPokemonNameOrId;
    private Button buttonFindPokemon;
    private LinearLayout foundPokemonContainer;

    private ImageView imageViewFoundPokemonImage;
    private TextView textViewFoundPokemonName;
    private EditText editTextCustomPokemonName;
    private Button buttonAddPokemon;

    private Pokemon foundPokemon;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_add_pokemon, container, false);

        editTextFindPokemonNameOrId = view.findViewById(R.id.editTextFindPokemonNameOrId);
        buttonFindPokemon = view.findViewById(R.id.button_find_pokemon);
        foundPokemonContainer = view.findViewById(R.id.foundPokemonContainer);
        imageViewFoundPokemonImage = view.findViewById(R.id.imageViewFoundPokemonImage);
        textViewFoundPokemonName = view.findViewById(R.id.textViewFoundPokemonName);
        editTextCustomPokemonName = view.findViewById(R.id.editTextCustomPokemonName);
        buttonAddPokemon = view.findViewById(R.id.button_add_pokemon);

        buttonFindPokemon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findPokemon(view);
            }
        });

        buttonAddPokemon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPokemon(view);
            }
        });

        showFoundPokemon(null);
        return view;
    }


    private void findPokemon(final View view) {
        Call<PokemonDto> pokemonCall = ApiServiceFactory.getPokemonApi().getPokemon(editTextFindPokemonNameOrId.getText().toString());
        pokemonCall.enqueue(new Callback<PokemonDto>() {
            @Override
            public void onResponse(Call<PokemonDto> call, Response<PokemonDto> response) {
                if (response.isSuccessful()) {
                    showFoundPokemon(response.body().asPokemon());
                } else {
                    new AlertDialog.Builder(getContext().getApplicationContext())
                            .setTitle("Unsuccessful response")
                            .setMessage(response.body() + "")
                            .show();
                }
            }

            @Override
            public void onFailure(Call<PokemonDto> call, Throwable t) {
                new AlertDialog.Builder(getContext().getApplicationContext())
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


        hideKeyboard();
        editTextFindPokemonNameOrId.clearFocus();
        editTextFindPokemonNameOrId.setText(null);
        foundPokemonContainer.setVisibility(View.VISIBLE);
        textViewFoundPokemonName.setText(pokemon.getOriginalName());
        Glide.with(this).load(pokemon.getImageUrl()).into(imageViewFoundPokemonImage);
    }

    private void hideKeyboard() {
        View currentView = getView();
        if (currentView != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(currentView.getWindowToken(), 0);
        }
    }

    private void addPokemon(View view) {
        foundPokemon.setCustomName(editTextCustomPokemonName.getText().toString());
        PokemonRepository.INSTANCE.addPokemon(foundPokemon)
                .addOnSuccessListener(getActivity(), new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        showFoundPokemon(null);
                        hideKeyboard();
                        Toast.makeText(getContext(), "Pokemon added successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(getActivity(), new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "An error occurred while trying to add new pokemon", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
