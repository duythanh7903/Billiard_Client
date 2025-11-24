package com.datn.bia.a.domain.model.domain

import android.content.res.Resources
import com.datn.bia.a.R

data class Language(
    var languageName: String = "",
    var isoLanguage: String = "",
    var isTypeAds: Boolean = false,
    var image: Int = 0
) {
    companion object {
        fun getListLanguageApp(): ArrayList<Language> {
            val lists: ArrayList<Language> = arrayListOf()
            lists.add(Language("Hindi", "hi", false, R.drawable.ic_language_hindi))
            lists.add(Language("Spanish", "es", false, R.drawable.ic_language_spanish))
            lists.add(Language("Croatian", "hr", false, R.drawable.ic_language_croatia))
            lists.add(Language("Czech", "cs", false, R.drawable.ic_language_czech_republic))
            lists.add(Language("Dutch", "nl", false, R.drawable.ic_language_dutch))
            lists.add(Language("English", "en", false, R.drawable.ic_language_english))
            lists.add(Language("Filipino", "fil", false, R.drawable.ic_language_filipino))
            lists.add(Language("French", "fr", false, R.drawable.ic_language_france))
            lists.add(Language("German", "de", false, R.drawable.ic_language_german))
            lists.add(Language("Indonesian", "in", false, R.drawable.ic_language_indonesian))
            lists.add(Language("Italian", "it", false, R.drawable.ic_language_italian))
            lists.add(Language("Japanese", "ja", false, R.drawable.ic_language_japanese))
            lists.add(Language("Korean", "ko", false, R.drawable.ic_language_korean))
            lists.add(Language("Malay", "ms", false, R.drawable.ic_language_malay))
            lists.add(Language("Polish", "pl", false, R.drawable.ic_language_polish))
            lists.add(Language("Portuguese", "pt", false, R.drawable.ic_language_portugal))
            lists.add(Language("Russian", "ru", false, R.drawable.ic_language_russian))
            lists.add(Language("Serbian", "sr", false, R.drawable.ic_language_serbian))
            lists.add(Language("Swedish", "sv", false, R.drawable.ic_language_swedish))
            lists.add(Language("Turkish", "tr", false, R.drawable.ic_language_turkish))
            lists.add(Language("Vietnamese", "vi", false, R.drawable.ic_language_vietnamese))
            lists.add(Language("China", "zh", false, R.drawable.ic_language_china))
            return lists
        }

        fun getLanguage(): Language? {
            var languageModel: Language? = null
            val lang =
                Resources.getSystem().configuration.locales[0].language
            val key = if (!languageApp.contains(lang)) {
                ""
            } else {
                lang
            }
            for (model in getListLanguageApp()) {
                if (key == model.isoLanguage) {
                    languageModel = model
                    break
                }
            }
            return languageModel
        }

        val languageApp: List<String>
            get() {
                val languages: MutableList<String> = ArrayList()
                /*languages.add("ar"); // Arabic*/
                languages.add("cs") // Czech
                languages.add("de") // Germany
                languages.add("en") // English
                languages.add("es") // Spanish
                languages.add("fil") // Filipino
                languages.add("fr") // French
                languages.add("hi") // Hindi
                languages.add("hr") // Croatian
                languages.add("in") // indonesian
                languages.add("it") // italian
                languages.add("ko") // korean
                languages.add("ja") //japanese
                languages.add("ms") // Malay
                languages.add("nl") // Dutch
                languages.add("pl") // Polish
                languages.add("pt") // Portugal
                languages.add("ru") // Russian
                languages.add("sr") // Serbian
                languages.add("sv") // Swedish
                languages.add("tr") // Turkish
                languages.add("vi") // Vietnamese
                languages.add("zh") // Chinese
                return languages
            }
    }
}