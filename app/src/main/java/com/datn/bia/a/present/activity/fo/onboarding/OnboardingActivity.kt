package com.datn.bia.a.present.activity.fo.onboarding

import android.content.Intent
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.datn.bia.a.R
import com.datn.bia.a.common.base.BaseActivity
import com.datn.bia.a.common.base.ext.click
import com.datn.bia.a.databinding.ActivityOnboardingBinding
import com.datn.bia.a.domain.model.domain.Onboarding
import com.datn.bia.a.domain.model.domain.OnboardingAdapter
import com.datn.bia.a.present.activity.home.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingActivity: BaseActivity<ActivityOnboardingBinding>() {
    private lateinit var onbAdapter: OnboardingAdapter

    private val lastPageSwipeListener = object : RecyclerView.OnItemTouchListener {
        private var initialX = 0f

        override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
            when (e.action) {
                MotionEvent.ACTION_DOWN -> {
                    initialX = e.x
                }

                MotionEvent.ACTION_MOVE -> {
                    val deltaX = e.x - initialX

                    if (deltaX < -50 && !rv.canScrollHorizontally(1)) {
                        onNextEvent()
                        return true // Consume event
                    }
                }
            }
            return false
        }

        override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) = Unit
        override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) = Unit
    }

    private val onboardingCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)

            onPageSelectedEvent(position)
        }
    }

    override fun getLayoutActivity(): Int = R.layout.activity_onboarding

    override fun initViews() {
        super.initViews()

        binding.vgp2.apply {
            onbAdapter = OnboardingAdapter(this@OnboardingActivity).apply {
                submitData(Onboarding.getAllOnboardings())
            }
            adapter = onbAdapter
            clipToPadding = false
            clipChildren = false
            getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_ALWAYS
            (getChildAt(0) as? RecyclerView)?.addOnItemTouchListener(lastPageSwipeListener)
            registerOnPageChangeCallback(onboardingCallback)
        }
    }

    override fun onClickViews() {
        super.onClickViews()

        binding.tvNext.click {
            onNextEvent()
        }
    }

    private fun onPageSelectedEvent(position: Int) =
        when (position) {
            0 -> onPage1Selected()
            1 -> onPage2Selected()
            2 -> onPage3Selected()
            else -> Unit
        }

    private fun onNextEvent() {
        val isLastItem =
            binding.vgp2.currentItem == onbAdapter.list.size - 1
        if (isLastItem) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        else binding.vgp2.currentItem++
    }

    override fun onDestroy() {
        binding.vgp2.unregisterOnPageChangeCallback(onboardingCallback)
        (binding.vgp2.getChildAt(0) as? RecyclerView)?.removeOnItemTouchListener(
            lastPageSwipeListener
        )

        super.onDestroy()
    }

    private fun onPage1Selected() {
        binding.icDot1.isActivated = true
        binding.icDot2.isActivated = false
        binding.icDot3.isActivated = false
    }

    private fun onPage2Selected() {
        binding.icDot1.isActivated = false
        binding.icDot2.isActivated = true
        binding.icDot3.isActivated = false
    }

    private fun onPage3Selected() {
        binding.icDot1.isActivated = false
        binding.icDot2.isActivated = false
        binding.icDot3.isActivated = true
    }
}