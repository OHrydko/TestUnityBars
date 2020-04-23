package com.example.test;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.adapter.Adapter;
import com.example.test.databinding.ActivityMainBinding;
import com.example.test.model.JsonModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Adapter.onItemClick {
    private Adapter adapter;
    private MainViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this,
                R.layout.activity_main);
        List<JsonModel> jsonModels = new ArrayList<>();

        RecyclerView recyclerView = binding.recyclerView;
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setNavigationIcon(null);
        binding.toolbar.setNavigationOnClickListener(v -> viewModel.back());

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.init(this);
        binding.setLifecycleOwner(this);
        binding.setViewModel(viewModel);
        binding.getRoot();

        viewModel.getState().observe(this,
                state -> getSupportActionBar().setDisplayHomeAsUpEnabled(state != 0));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        adapter = new Adapter(jsonModels, this);
        recyclerView.setAdapter(adapter);

        getFiles();

    }

    private void getFiles() {
        viewModel.getModel().observe(this, jsonModels -> adapter.setJsonModels(jsonModels));
    }


    @Override
    public void onClick(int position, String type, String content) {
        if (type.equals("FOLDER")) {
            viewModel.onClick(position);
        } else {
            startActivity(new Intent(this, ContentActivity.class)
                    .putExtra("content", content));
        }
    }

    @Override
    public void onBackPressed() {
        viewModel.back();
    }
}
