package com.datn.bia.a.present.activity.comment.reviews

import com.datn.bia.a.R
import com.datn.bia.a.common.base.BaseActivity
import com.datn.bia.a.databinding.ActivityReviewsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewsActivity : BaseActivity<ActivityReviewsBinding>() {
    override fun getLayoutActivity(): Int = R.layout.activity_reviews
}