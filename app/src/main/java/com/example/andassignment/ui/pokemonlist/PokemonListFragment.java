package com.example.andassignment.ui.pokemonlist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.andassignment.R;

public class PokemonListFragment extends Fragment {

    private PokemonListViewModel pokemonListViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        super.onCreateView(inflater, container, savedInstanceState);
        pokemonListViewModel = ViewModelProviders.of(this).get(PokemonListViewModel.class);
        View root = inflater.inflate(R.layout.fragment_pokemon_list, container, false);

        RecyclerView recyclerViewPokemons = root.findViewById(R.id.recyclerViewPokemons);
        recyclerViewPokemons.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerViewPokemons.setAdapter(pokemonListViewModel.getAdapter());

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        pokemonListViewModel.startListeningToPokemonList(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        pokemonListViewModel.stopListeningToPokemonList(this);
    }


}