package com.wsa.shows.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wsa.shows.R
import com.wsa.shows.ui.fragments.HomeFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().add(R.id.fragment_container_view, HomeFragment()).commit()

    }


}