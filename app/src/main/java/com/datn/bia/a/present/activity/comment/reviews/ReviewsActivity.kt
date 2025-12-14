package com.datn.bia.a.present.activity.comment.reviews

import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.datn.bia.a.R
import com.datn.bia.a.common.AppConst
import com.datn.bia.a.common.UiState
import com.datn.bia.a.common.base.BaseActivity
import com.datn.bia.a.common.base.ext.click
import com.datn.bia.a.common.base.ext.goneView
import com.datn.bia.a.common.base.ext.showToastOnce
import com.datn.bia.a.common.base.ext.visibleView
import com.datn.bia.a.databinding.ActivityReviewsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ReviewsActivity : BaseActivity<ActivityReviewsBinding>() {

    private val viewModel: ReviewViewModel by viewModels()

    private var reviewAdapter: ReviewAdapter? = null
    private var idProductCurrent: String? = null

    override fun getLayoutActivity(): Int = R.layout.activity_reviews

    override fun initViews() {
        super.initViews()

        idProductCurrent = intent.getStringExtra(AppConst.KEY_ID_PRODUCT)

        binding.rcvComment.apply {
            reviewAdapter = ReviewAdapter()

            adapter = reviewAdapter
        }
    }

    override fun onClickViews() {
        super.onClickViews()

        binding.icBack.click {
            finish()
        }
    }

    override fun observerData() {
        super.observerData()

        lifecycleScope.launch {
            viewModel.state.collect {
                when (val uiState = it.uiState) {
                    is UiState.Error -> {
                        showToastOnce(uiState.message)
                        finish()
                    }
                    UiState.Idle -> {

                    }
                    UiState.Loading -> {
                        binding.rcvComment.goneView()
                        binding.loadingView.visibleView()
                    }
                    is UiState.Success -> {
                        binding.rcvComment.visibleView()
                        binding.loadingView.goneView()

                        val data = uiState.data.filter { comment -> comment.productId?._id == idProductCurrent }
                        reviewAdapter?.submitData(data)
                        binding.tvStars.text =
                            (data.sumOf { comment -> comment.rating ?: 0 }.toFloat() / if (data.count() == 0) 1 else data.count()).toString().take(3)
                    }
                }
            }
        }
    }
}