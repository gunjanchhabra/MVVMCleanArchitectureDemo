package com.example.kotlinmvvmcode.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import com.example.kotlinmvvmcode.MyApplication
import com.example.kotlinmvvmcode.R
import com.example.kotlinmvvmcode.databinding.ActivityMainBinding

class ProductActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        (application as MyApplication).appComponent.inject(this)
        Navigation.findNavController(this, R.id.content_fragment).setGraph(R.navigation.product_graph)
    }
}