package com.example.andassignment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.andassignment.model.Pokemon;

public class PokemonViewHolder extends RecyclerView.ViewHolder {

    private TextView textViewPokemonName;
    private TextView textViewOriginalPokemonName;
    private ImageView imageViewPokemonImage;


    public PokemonViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewPokemonName = itemView.findViewById(R.id.textViewName);
        textViewOriginalPokemonName = itemView.findViewById(R.id.textViewOriginalName);
        imageViewPokemonImage = itemView.findViewById(R.id.imageViewPokemonImage);
    }

    public void setPokemon(Pokemon pokemon) {
        textViewPokemonName.setText(pokemon.getCustomName());
        textViewOriginalPokemonName.setText(pokemon.getOriginalName());
        Glide.with(itemView).load(pokemon.getImageUrl()).into(imageViewPokemonImage);
    }

}
