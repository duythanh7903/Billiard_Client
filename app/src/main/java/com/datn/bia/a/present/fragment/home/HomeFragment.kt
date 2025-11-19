package com.datn.bia.a.present.fragment.home

import com.datn.bia.a.common.base.BaseFragment
import com.datn.bia.a.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override fun inflateBinding(): FragmentHomeBinding =
        FragmentHomeBinding.inflate(layoutInflater)

}