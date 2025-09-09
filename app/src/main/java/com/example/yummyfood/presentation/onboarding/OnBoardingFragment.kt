package com.example.yummyfood.presentation.onboarding

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.yummyfood.R
import com.example.yummyfood.databinding.FragmentOnboardingBinding

class OnBoardingFragment : Fragment() {

    private var _binding: FragmentOnboardingBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: OnBoardingAdapter
    private val indicators = mutableListOf<ImageView>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnboardingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Khởi tạo Adapter và gán cho ViewPager2
        // Khởi tạo Adapter và gán cho ViewPager2
        // Lưu ý: Bạn cần cập nhật constructor của OnBoardingAdapter để nhận Fragment
        adapter = OnBoardingAdapter(this)
        binding.viewPager.adapter = adapter

        // Thiết lập các chỉ báo (indicators)
        setupIndicators()
        updateIndicators(0) // Cập nhật chỉ báo cho trang đầu tiên

        // Lắng nghe sự kiện chuyển trang để cập nhật chỉ báo
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updateIndicators(position) // Cập nhật chỉ báo khi trang thay đổi
            }
        })

        // Xử lý sự kiện click cho nút Next
        binding.fabNext.setOnClickListener {
            val currentItem = binding.viewPager.currentItem
            if (currentItem < adapter.itemCount - 1) {
                binding.viewPager.setCurrentItem(currentItem + 1, true)
            } else {
                // Người dùng đã hoàn thành onboarding
                saveOnboardingFinished()
                findNavController().navigate(R.id.action_onBoardingFragment_to_signInFragment)
            }
        }
    }

    /**
     * Hàm này tạo ra các ImageView chỉ báo và thêm chúng vào LinearLayout.
     */
    private fun setupIndicators() {
        // Xóa các view cũ để tránh trùng lặp khi onViewCreated được gọi lại
        binding.indicatorsContainer.removeAllViews()
        indicators.clear()

        val indicatorParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply {
            setMargins(8, 0, 8, 0) // Thêm khoảng cách giữa các chỉ báo
        }

        repeat(adapter.itemCount) {
            val indicator = ImageView(requireContext()).apply {
                layoutParams = indicatorParams
            }
            indicators.add(indicator)
            binding.indicatorsContainer.addView(indicator)
        }
    }

    /**
     * Hàm này cập nhật trạng thái (chọn/không chọn) của các chỉ báo.
     * @param selectedPosition Vị trí của trang hiện tại.
     */
    private fun updateIndicators(selectedPosition: Int) {
        for (i in indicators.indices) {
            val indicator = indicators[i]
            if (i == selectedPosition) {
                // Trang được chọn: dùng drawable 'selected'
                indicator.setImageDrawable(
                    ContextCompat.getDrawable(requireContext(), R.drawable.tab_indicator_selected)
                )
            } else {
                // Trang không được chọn: dùng drawable 'default'
                indicator.setImageDrawable(
                    ContextCompat.getDrawable(requireContext(), R.drawable.tab_indicator_default)
                )
            }
        }
    }

    private fun saveOnboardingFinished() {
        val sharedPref = requireActivity().getSharedPreferences("YummyAppPrefs", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putBoolean("has_seen_onboarding", true)
            apply() // Dùng apply() để lưu bất đồng bộ
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
