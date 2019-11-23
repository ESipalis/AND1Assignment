package com.example.andassignment.ui.pokemonlist;

import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.example.andassignment.PokemonRepository;
import com.example.andassignment.PokemonViewHolder;
import com.example.andassignment.R;
import com.example.andassignment.model.Pokemon;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.ArrayList;
import java.util.List;

// I'm not sure this is the best structure, but I couldn't come up with anything better when using FirebaseRecyclerAdapter
public class PokemonListViewModel extends ViewModel {

    private final FirebaseRecyclerAdapter<Pokemon, PokemonViewHolder> adapter;
    private final List<LifecycleOwner> listeners;

    private final static int STOP_LISTENING_DELAY_MS = 2000;
    private boolean adapterListeningStopIsPending = false;
    private final Handler handler = new Handler();
    private final Runnable stopAdapterListeningRunnable = new Runnable() {
        @Override
        public void run() {
            adapter.stopListening();
            adapterListeningStopIsPending = false;
        }
    };


    public PokemonListViewModel() {
        listeners = new ArrayList<>();
        FirebaseRecyclerOptions<Pokemon> options = new FirebaseRecyclerOptions.Builder<Pokemon>()
                .setQuery(PokemonRepository.INSTANCE.getPokemonListReference(), Pokemon.class)
                .build();

        adapter = new FirebaseRecyclerAdapter<Pokemon, PokemonViewHolder>(options) {

            @NonNull
            @Override
            public PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View pokemonListItemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.pokemon_list_item, parent, false);
                return new PokemonViewHolder(pokemonListItemView);
            }

            @Override
            protected void onBindViewHolder(@NonNull PokemonViewHolder holder, int position, @NonNull Pokemon model) {
                holder.setPokemon(model);
                // Move this outside ViewModel
                String colorString = position % 2 == 0 ? "#FFFFFF" : "#EEEEEE";
                holder.itemView.setBackgroundColor(Color.parseColor(colorString));
            }
        };
    }


    public RecyclerView.Adapter<PokemonViewHolder> getAdapter() {
        return this.adapter;
    }

    public void startListeningToPokemonList(LifecycleOwner listener) {
        if (this.listeners.contains(listener)) {
            throw new IllegalArgumentException("Listener already registered!");
        }
        this.listeners.add(listener);
        if (this.listeners.size() == 1) {
            if (adapterListeningStopIsPending) {
                this.handler.removeCallbacks(stopAdapterListeningRunnable);
            } else {
                this.adapter.startListening();
            }
        }
    }

    public void stopListeningToPokemonList(LifecycleOwner listener) {
        this.listeners.remove(listener);
        if (this.listeners.size() == 0) {
            this.adapterListeningStopIsPending = true;
            this.handler.postDelayed(stopAdapterListeningRunnable, STOP_LISTENING_DELAY_MS);
        }
    }
}