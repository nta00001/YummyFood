
package com.example.yummyfood.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.launch



abstract class BaseFragment<VB : ViewBinding, VM : BaseViewModel> : Fragment() {

    // Biến binding được bảo vệ, chỉ có thể thay đổi trong class này
    private var _binding: VB? = null
    // Biến binding public, non-null để các lớp con sử dụng an toàn
    protected val binding get() = _binding!!

    // ViewModel của Fragment, được cung cấp bởi lớp con
    protected abstract val viewModel: VM

    // Phương thức trừu tượng buộc các lớp con phải cung cấp cách để inflate layout
    abstract fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate layout bằng phương thức do lớp con cung cấp
        _binding = getViewBinding(inflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Gọi các hàm khởi tạo theo thứ tự
        initViews()
        initListeners()
        initObservers()
    }

    // Nơi để khởi tạo các view (ví dụ: setup RecyclerView)
    protected open fun initViews() {}

    // Nơi để thiết lập các listener (ví dụ: setOnClickListener)
    protected open fun initListeners() {}

    // Nơi để lắng nghe các thay đổi từ ViewModel
    protected open fun initObservers() {
        // Tự động lắng nghe trạng thái lỗi chung
        lifecycleScope.launch {
            viewModel.error.collect { errorMessage ->
                if (errorMessage != null) {
                    showToast(errorMessage)
                }
            }
        }

        // Bạn cũng có thể thêm observer cho isLoading ở đây để hiển thị/ẩn ProgressBar
        lifecycleScope.launch {
            viewModel.isLoading.collect { isLoading ->
                // Ví dụ: (cần có ProgressBar trong layout)
                // binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Gán binding về null để tránh memory leak
        _binding = null
    }

    // Hàm tiện ích để hiển thị Toast
    protected fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}