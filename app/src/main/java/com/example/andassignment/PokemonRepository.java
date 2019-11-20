package com.example.andassignment;

import com.example.andassignment.model.Pokemon;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public enum PokemonRepository {
    INSTANCE; // Singleton pattern



    public DatabaseReference getPokemonListReference() {
        return getPokemonListRef();
    }

    public Task<Void> addPokemon(Pokemon pokemon) {
        return getPokemonListRef()
                .push()
                .setValue(pokemon);
    }



    private static DatabaseReference getPokemonListRef() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        return FirebaseDatabase.getInstance().getReference("users/" + user.getUid() + "/pokemons");
    }
}
