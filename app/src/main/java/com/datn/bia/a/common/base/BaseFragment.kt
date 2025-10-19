package com.datn.bia.a.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VB: ViewBinding>(): Fragment() {

    lateinit var binding: VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = inflateBinding()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        LocalizationHelper.setLocale(context = requireContext(), isoCode = isoCode)
        initViews()
        resizeViews()
        clickViews()
        observeData()
    }

    abstract fun inflateBinding(): VB

    open fun initViews() {}
    open fun resizeViews() {}
    open fun clickViews() {}
    open fun observeData() {}
}