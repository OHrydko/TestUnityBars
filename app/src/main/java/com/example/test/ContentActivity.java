package com.example.test;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.test.databinding.ActivityContentBinding;

public class ContentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityContentBinding binding = DataBindingUtil.setContentView(this,
                R.layout.activity_content);
        //get content from main
        String content = getIntent().getStringExtra("content");
        binding.content.setText((content == null || content.isEmpty()) ? "Hello" : content);
        binding.setLifecycleOwner(this);

        setSupportActionBar(binding.toolbar);
        binding.toolbar.setNavigationOnClickListener(v -> onBackPressed());

        binding.getRoot();
    }
}
