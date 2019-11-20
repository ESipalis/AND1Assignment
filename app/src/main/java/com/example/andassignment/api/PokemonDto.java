package com.example.andassignment.api;

import com.example.andassignment.model.Pokemon;

import java.util.Map;

public class PokemonDto {
    private int id;
    private String name;
    private Map<String, String> sprites;

    public Pokemon asPokemon() {
        return new Pokemon(id, name, "", sprites.get("front_default"));
    }
}
