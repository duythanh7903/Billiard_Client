package com.datn.bia.a.model.domain

import com.datn.bia.a.R

data class Onboarding(
    var id: Long = 0L,
    var imageRes: Int = 0,
    var titleRes: Int = 0,
    var desRes: Int = 0,
) {
    companion object {
        fun getAllOnboardings(): MutableList<Onboarding> {
            val listOnboarding = mutableListOf<Onboarding>()

            listOnboarding.add(Onboarding(0, R.drawable.ic_language_vietnamese, R.string.title_onb_1, R.string.des_onb_1))
            listOnboarding.add(Onboarding(1, R.drawable.ic_language_china, R.string.title_onb_2, R.string.des_onb_2))
            listOnboarding.add(Onboarding(2, R.drawable.ic_language_dutch, R.string.title_onb_3, R.string.des_onb_3))

            return listOnboarding
        }
    }
}