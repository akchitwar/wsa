package com.wsa.shows.ui.fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.wsa.shows.R

object  FragmentTransaction {

    fun add(activity: FragmentActivity?, fragment: Fragment){
        activity?.supportFragmentManager?.beginTransaction()?.add(R.id.fragment_container_view, fragment)?.commit()

    }

    fun addChildFragment(activity: FragmentActivity?, fragment: Fragment){
        activity?.supportFragmentManager?.beginTransaction()?.add(R.id.fragment_container_view, fragment)?.addToBackStack(null)?.commit()

    }
    fun popBackStack(activity: FragmentActivity?, fragment: Fragment){
        activity?.supportFragmentManager?.popBackStack()
    }


}