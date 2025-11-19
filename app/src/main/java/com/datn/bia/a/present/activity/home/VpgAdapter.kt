package com.datn.bia.a.present.activity.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.datn.bia.a.present.fragment.cart.CartFragment
import com.datn.bia.a.present.fragment.home.HomeFragment
import com.datn.bia.a.present.fragment.profile.ProfileFragment

class VpgAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {
    override fun createFragment(position: Int): Fragment =
        when (position) {
            0 -> HomeFragment()
            1 -> CartFragment()
            else -> ProfileFragment()
        }

    override fun getItemCount(): Int = 3
}