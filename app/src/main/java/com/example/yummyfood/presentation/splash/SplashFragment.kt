package com.example.yummyfood.presentation.splash

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.yummyfood.R
import com.example.yummyfood.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Chỉ cần inflate một view đơn giản, không cần binding phức tạp
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Kiểm tra xem onboarding đã được hiển thị hay chưa
        val sharedPref = requireActivity().getSharedPreferences("YummyAppPrefs", Context.MODE_PRIVATE)
        val hasSeenOnboarding = sharedPref.getBoolean("has_seen_onboarding", false)

        if (hasSeenOnboarding) {
            // Nếu đã xem -> đi đến màn hình đăng nhập
            findNavController().navigate(R.id.action_splashFragment_to_signInFragment)
        } else {
            // Nếu chưa xem -> đi đến màn hình onboarding
            findNavController().navigate(R.id.action_splashFragment_to_onBoardingFragment)
        }
    }
}
    