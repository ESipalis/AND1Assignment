package com.example.andassignment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewPokemons;
    private MainActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseUser user = checkIfSignedIn();
        if (user == null) {
            return;
        }

        setContentView(R.layout.activity_main);

        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        recyclerViewPokemons = findViewById(R.id.recyclerViewPokemons);
        recyclerViewPokemons.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewPokemons.setAdapter(viewModel.getAdapter());
    }

    @Override
    protected void onStart() {
        super.onStart();
        viewModel.startListeningToPokemonList(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        viewModel.stopListeningToPokemonList(this);
    }

    private FirebaseUser checkIfSignedIn() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            startLoginActivity();
            return null;
        }

        Toast.makeText(this, "Welcome " + user.getDisplayName(), Toast.LENGTH_SHORT).show();
        return user;
    }

    private void startLoginActivity() {
        startActivity(new Intent(this, SignInActivity.class));
        finish();
    }


    public void signOutButtonClicked(View view) {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        startLoginActivity();
                    }
                });
    }

    public void addButtonClicked(View view) {
        startActivity(new Intent(this, PokemonAddActivity.class));
    }
}
