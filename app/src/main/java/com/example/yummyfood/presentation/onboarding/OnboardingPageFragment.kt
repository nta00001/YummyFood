package com.example.yummyfood.presentation.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.yummyfood.databinding.FragmentOnboardingPageBinding

class OnboardingPageFragment: Fragment() {
    private var _binding: FragmentOnboardingPageBinding? = null
    private val binding get() = _binding!!


    companion object {
        private const val ARG_IMAGE_RES = "image_res"
        private const val ARG_TITLE = "title"
        private const val ARG_DESC = "description"


        fun newInstance(imageRes: Int, title: String, description: String): OnboardingPageFragment {
            return OnboardingPageFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_IMAGE_RES, imageRes)
                    putString(ARG_TITLE, title)
                    putString(ARG_DESC, description)
                }
            }
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnboardingPageBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            binding.ivOnboardingImage.setImageResource(it.getInt(ARG_IMAGE_RES))
            binding.tvOnboardingTitle.text = it.getString(ARG_TITLE)
            binding.tvOnboardingDescription.text = it.getString(ARG_DESC)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }


}