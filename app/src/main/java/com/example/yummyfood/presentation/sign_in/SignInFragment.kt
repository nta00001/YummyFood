package com.example.yummyfood.presentation.sign_in

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.yummyfood.R
import com.example.yummyfood.databinding.FragmentSignInBinding
import com.example.yummyfood.utils.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SignInViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSignIn.setOnClickListener {
            handleSignIn()
        }
        binding.tvSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
        }
        binding.root.setOnTouchListener { v, event ->
            hideKeyboard()
            false
        }

        observeViewModel()
    }

    private fun handleSignIn() {
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()

        if (email.isEmpty()) {
            binding.emailInputLayout.error = "Please enter your email"
            return
        }
        if (password.isEmpty()) {
            binding.passwordInputLayout.error = "Please enter your password"
            return
        }

        binding.emailInputLayout.error = null
        binding.passwordInputLayout.error = null
        viewModel.signInWithEmail(email, password)
    }

    private fun observeViewModel() {
        viewModel.signInState.observe(viewLifecycleOwner) { state ->
            // Ẩn/hiện ProgressBar và nút đăng nhập
            binding.progressBar.isVisible = state is SignInState.Loading
            binding.btnSignIn.text = if (state is SignInState.Loading) "" else "Sign in"
            binding.btnSignIn.isEnabled = state !is SignInState.Loading

            when (state) {
                is SignInState.Success -> {
                    Toast.makeText(requireContext(), "Sign in successful!", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_signInFragment_to_homeFragment)
                }
                is SignInState.Error -> {
                    // Hiển thị lỗi giống trong thiết kế
                    binding.emailInputLayout.error = "You entered it wrong"
                    binding.passwordInputLayout.error = " " // Để giữ khoảng trống cho lỗi
                }
                else -> { /* Do nothing */ }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
