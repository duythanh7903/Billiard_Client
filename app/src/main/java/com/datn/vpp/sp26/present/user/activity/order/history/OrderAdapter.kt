package com.datn.vpp.sp26.present.user.activity.order.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.datn.vpp.sp26.common.AppConst
import com.datn.vpp.sp26.present.user.activity.order.history.tab.TabOrderFragment

class OrderAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {
    override fun createFragment(position: Int): Fragment =
        TabOrderFragment().apply {
            arguments = Bundle().apply {
                putInt(AppConst.KEY_ORDER_TYPE, position)
            }
        }

    override fun getItemCount(): Int = 4
}