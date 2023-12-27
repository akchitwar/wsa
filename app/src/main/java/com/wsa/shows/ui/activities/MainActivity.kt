package com.wsa.shows.ui.activities

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.wsa.shows.R
import com.wsa.shows.ui.fragments.FragmentTransaction
import com.wsa.shows.ui.fragments.HomeFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        onBackPressedDispatcher.addCallback(this,onBackPressedCallback)
        FragmentTransaction.add(this, HomeFragment())

    }

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            val fragment = supportFragmentManager.findFragmentById(R.id.fragment_container_view)
            if (fragment != null && fragment is HomeFragment){
                finish()
            }else {
                FragmentTransaction.popBackStack(this@MainActivity, fragment!!)
            }

        }
    }


}