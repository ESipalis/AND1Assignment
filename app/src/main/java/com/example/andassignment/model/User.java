package com.example.andassignment.model;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String uid;
    private List<Pokemon> pokemons;

    public User() {
        pokemons = new ArrayList<>();
    }

    public User(String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public List<Pokemon> getPokemons() {
        return pokemons;
    }
}
