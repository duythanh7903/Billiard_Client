package com.datn.bia.a.present.fragment.cart

import com.datn.bia.a.common.base.BaseFragment
import com.datn.bia.a.databinding.FragmentCartBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment: BaseFragment<FragmentCartBinding>() {
    override fun inflateBinding(): FragmentCartBinding =
        FragmentCartBinding.inflate(layoutInflater)
}