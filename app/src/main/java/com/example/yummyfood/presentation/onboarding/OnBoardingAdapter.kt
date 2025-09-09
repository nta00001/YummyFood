package com.example.yummyfood.presentation.onboarding

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.yummyfood.R

class OnBoardingAdapter(fragment: OnBoardingFragment): FragmentStateAdapter(fragment){
    private val pages = listOf(
        Triple(
            R.drawable.iv_onboarding_1, // Thay bằng ID hình ảnh của bạn
            "Delivery everywhere",
            "We are always ready to deliver your items quickly and professionally"
        ),
        Triple(
            R.drawable.iv_onboarding_2, // Thay bằng ID hình ảnh của bạn
            "Fast food delivery",
            "We are always ready to deliver your items quickly and professionally"
        )
    )
    override fun getItemCount(): Int = pages.size

    override fun createFragment(position: Int): Fragment {
        val (imageRes, title, desc) = pages[position]
        return OnboardingPageFragment.newInstance(imageRes, title, desc)
    }
}



