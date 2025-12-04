package com.datn.bia.a.present.dialog

import android.content.Context
import com.datn.bia.a.common.base.BaseDialog
import com.datn.bia.a.common.base.ext.click
import com.datn.bia.a.databinding.DialogCommentBinding

class CommentDialog(
    contextParams: Context,
    private val onLater: () -> Unit,
    private val onRate: (star: Int, comment: String, orderId: String) -> Unit
) : BaseDialog<DialogCommentBinding>(
    context = contextParams,
    isSetShowBottom = true
) {
    private var stars = 5
    private var orderId: String = ""

    override fun inflateBinding(): DialogCommentBinding =
        DialogCommentBinding.inflate(layoutInflater)

    override fun initViews() {
        super.initViews()

        setCancelable(false)
    }

    override fun onClickViews() {
        super.onClickViews()

        binding.imgStar1.click {
            stars = 1

            binding.imgStar1.isActivated = stars >= 1
            binding.imgStar2.isActivated = stars >= 2
            binding.imgStar3.isActivated = stars >= 3
            binding.imgStar4.isActivated = stars >= 4
            binding.imgStar5.isActivated = stars >= 5
        }

        binding.imgStar2.click {
            stars = 2

            binding.imgStar1.isActivated = stars >= 1
            binding.imgStar2.isActivated = stars >= 2
            binding.imgStar3.isActivated = stars >= 3
            binding.imgStar4.isActivated = stars >= 4
            binding.imgStar5.isActivated = stars >= 5
        }

        binding.imgStar3.click {
            stars = 3

            binding.imgStar1.isActivated = stars >= 1
            binding.imgStar2.isActivated = stars >= 2
            binding.imgStar3.isActivated = stars >= 3
            binding.imgStar4.isActivated = stars >= 4
            binding.imgStar5.isActivated = stars >= 5
        }

        binding.imgStar4.click {
            stars = 4

            binding.imgStar1.isActivated = stars >= 1
            binding.imgStar2.isActivated = stars >= 2
            binding.imgStar3.isActivated = stars >= 3
            binding.imgStar4.isActivated = stars >= 4
            binding.imgStar5.isActivated = stars >= 5
        }

        binding.imgStar5.click {
            stars = 5

            binding.imgStar1.isActivated = stars >= 1
            binding.imgStar2.isActivated = stars >= 2
            binding.imgStar3.isActivated = stars >= 3
            binding.imgStar4.isActivated = stars >= 4
            binding.imgStar5.isActivated = stars >= 5
        }

        binding.btnLater.click {
            onLater.invoke()
            dismiss()
        }

        binding.btnRate.click {
            onRate.invoke(
                stars,
                binding.edtReview.text.toString().trim(),
                orderId
            )
        }
    }

    fun showDialog(orderId: String) {
        this@CommentDialog.orderId = orderId
        stars = 5

        binding.imgStar1.isActivated = stars >= 1
        binding.imgStar2.isActivated = stars >= 2
        binding.imgStar3.isActivated = stars >= 3
        binding.imgStar4.isActivated = stars >= 4
        binding.imgStar5.isActivated = stars >= 5

        binding.edtReview.text.clear()

        show()
    }
}