package com.example.andassignment.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PokemonApi {

    @GET("api/v2/pokemon/{name}")
    Call<PokemonDto> getPokemon(@Path("name") String name);
}
