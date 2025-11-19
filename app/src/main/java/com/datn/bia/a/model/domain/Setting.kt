package com.datn.bia.a.model.domain

import com.datn.bia.a.R

data class SettingCat(
    var titleRes: Int = 0,
    val items: List<SettingItem> = emptyList(),
    val type: TypeSettingItem = TypeSettingItem.MY_ACCOUNT
) {
    companion object {
        fun getAllSettingsCat() = listOf(
            SettingCat(R.string.my_account, SettingItem.getSettingItemTypeAccount()),
            SettingCat(R.string.setting, SettingItem.getSettingItemTypeSetting(), TypeSettingItem.SETTING),
            SettingCat(R.string.support, SettingItem.getSettingItemTypeHelp(), TypeSettingItem.HELP),
        )
    }
}

data class SettingItem(
    val id: Int = 0,
    val titleRes: Int = 0,
    val cat: TypeSettingItem = TypeSettingItem.MY_ACCOUNT
) {
    companion object {
        fun getSettingItemTypeAccount() = listOf(
            SettingItem(0, R.string.account_and_security),
            SettingItem(1, R.string.address),
            SettingItem(2, R.string.account_and_bank_card),
        )

        fun getSettingItemTypeSetting() = listOf(
            SettingItem(3, R.string.chat_setting, TypeSettingItem.SETTING),
            SettingItem(4, R.string.notification_setting, TypeSettingItem.SETTING),
            SettingItem(5, R.string.privacy_policy, TypeSettingItem.SETTING),
            SettingItem(6, R.string.language, TypeSettingItem.SETTING),
        )

        fun getSettingItemTypeHelp() = listOf(
            SettingItem(7, R.string.support_center, TypeSettingItem.HELP),
            SettingItem(8, R.string.community_standards, TypeSettingItem.HELP),
            SettingItem(9, R.string.about_us, TypeSettingItem.HELP),
        )
    }
}

enum class TypeSettingItem() {
    MY_ACCOUNT,
    SETTING,
    HELP
}