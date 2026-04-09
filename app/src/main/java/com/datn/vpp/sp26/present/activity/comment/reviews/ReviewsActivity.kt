package com.datn.vpp.sp26.present.activity.comment.reviews

import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.datn.vpp.sp26.R
import com.datn.vpp.sp26.common.AppConst
import com.datn.vpp.sp26.common.base.BaseActivity
import com.datn.vpp.sp26.common.base.ext.click
import com.datn.vpp.sp26.common.base.ext.goneView
import com.datn.vpp.sp26.common.base.ext.visibleView
import com.datn.vpp.sp26.common.toListResCommentDTO
import com.datn.vpp.sp26.databinding.ActivityReviewsBinding
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
                val list = it.listComment.toListResCommentDTO()

                binding.rcvComment.visibleView()
                binding.loadingView.goneView()

                val data = list.filter { comment -> comment.productId?._id == idProductCurrent }
                reviewAdapter?.submitData(data)
                binding.tvStars.text =
                    (data.sumOf { comment -> comment.rating ?: 0 }
                        .toFloat() / if (data.count() == 0) 1 else data.count()).toString().take(3)
            }
        }
    }
}