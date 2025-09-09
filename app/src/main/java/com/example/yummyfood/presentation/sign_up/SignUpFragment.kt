package com.example.yummyfood.presentation.sign_up

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.yummyfood.databinding.FragmentSignUpBinding
import com.example.yummyfood.utils.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment() {
    private var _binding: FragmentSignUpBinding? = null

    private val binding get() = _binding!!

    private val viewModel: SignUpViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Bắt sự kiện click cho nút đăng ký
        binding.btnSignUp.setOnClickListener {
            handleSignUp()
        }

        // Bắt sự kiện click cho text "Sign in" để quay lại màn hình đăng nhập
        binding.tvSignIn.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.nestedScrollView.setOnTouchListener { v, event ->
            hideKeyboard()
            // Trả về false để không can thiệp vào hành vi cuộn
            false
        }

        // Bắt đầu quan sát ViewModel
        observeViewModel()
    }

    private fun handleSignUp() {
        val fullName = binding.etFullName.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        val numberPhone = binding.etNumber.text.toString().trim()

        // Xóa lỗi cũ trước khi kiểm tra
        binding.fullNameInputLayout.error = null
        binding.emailInputLayout.error = null
        binding.passwordInputLayout.error = null
        binding.numberInputLayout.error = null

        var hasError = false
        if (fullName.isEmpty()) {
            binding.fullNameInputLayout.error = "Please enter your full name"
            hasError = true
        }
        if (email.isEmpty()) {
            binding.emailInputLayout.error = "Please enter your email"
            hasError = true
        }
        if (numberPhone.isEmpty()) {
            binding.numberInputLayout.error = "Please enter your phone number"
            hasError = true
        }
        if (password.isEmpty()) {
            binding.passwordInputLayout.error = "Please enter your password"
            hasError = true
        }

        // Nếu không có lỗi, gọi ViewModel
        if (!hasError) {
            // Giả định SignUpViewModel đã được cập nhật để nhận các tham số này
            viewModel.signUpUser(email, password, fullName, numberPhone)
        }
    }

    private fun observeViewModel() {
        viewModel.signUpState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is SignUpState.Loading -> {
                    // Hiển thị ProgressBar và vô hiệu hóa nút đăng ký
                    binding.progressBar.visibility = View.VISIBLE
                    binding.btnSignUp.isEnabled = false
                }

                is SignUpState.Success -> {
                    // Ẩn ProgressBar và kích hoạt lại nút
                    binding.progressBar.visibility = View.GONE
                    binding.btnSignUp.isEnabled = true
                    Toast.makeText(context, "Account created successfully!", Toast.LENGTH_SHORT)
                        .show()
                    // Điều hướng về màn hình đăng nhập sau khi đăng ký thành công
                    findNavController().navigateUp()
                }

                is SignUpState.Error -> {
                    // Ẩn ProgressBar và kích hoạt lại nút
                    binding.progressBar.visibility = View.GONE
                    binding.btnSignUp.isEnabled = true
                    Toast.makeText(context, "Error: ${state.message}", Toast.LENGTH_LONG).show()
                }

                is SignUpState.Idle -> {
                    // Trạng thái ban đầu, đảm bảo ProgressBar ẩn và nút được kích hoạt
                    binding.progressBar.visibility = View.GONE
                    binding.btnSignUp.isEnabled = true
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
