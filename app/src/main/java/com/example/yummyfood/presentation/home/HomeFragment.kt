
package com.example.yummyfood.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.yummyfood.databinding.FragmentHomeBinding
import com.example.yummyfood.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
// Kế thừa từ BaseFragment với các kiểu Generic cụ thể
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    // Khởi tạo ViewModel bằng Hilt
    override val viewModel: HomeViewModel by viewModels()

    // Khai báo adapter
    private val foodAdapter = HomeAdapter()

    // Cung cấp cách để tạo (inflate) ViewBinding cho Fragment
    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Yêu cầu ViewModel tải dữ liệu
        viewModel.loadFoodItems()
    }

    // Nơi để khởi tạo các view
    override fun initViews() {
        super.initViews()
        // Setup RecyclerView
        binding.rvFoodList.adapter = foodAdapter
    }

    // Nơi để lắng nghe các thay đổi từ ViewModel
    override fun initObservers() {
        super.initObservers() // Quan trọng: Gọi hàm của lớp cha để xử lý loading/error chung

        // Lắng nghe thay đổi của danh sách món ăn
        lifecycleScope.launch {
            viewModel.foodList.collect { list ->
                // Cập nhật danh sách cho adapter
                foodAdapter.submitList(list)
            }
        }
    }
}