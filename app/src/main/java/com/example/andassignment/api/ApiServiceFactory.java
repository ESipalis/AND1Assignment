package com.example.andassignment.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiServiceFactory {

    private static Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
            .baseUrl("https://pokeapi.co")
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();

    private static PokemonApi pokemonApi = retrofit.create(PokemonApi.class);

    public static PokemonApi getPokemonApi() {
        return pokemonApi;
    }

}
