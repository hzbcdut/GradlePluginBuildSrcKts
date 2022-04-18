package com.example.learnasm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
//        Log.i("MainActivity", "onCreate:  insert bytecode ")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}