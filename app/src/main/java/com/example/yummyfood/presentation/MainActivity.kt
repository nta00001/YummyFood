package com.example.yummyfood.presentation

import android.view.LayoutInflater
import com.example.yummyfood.databinding.ActivityMainBinding
import com.example.yummyfood.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    // Implement phương thức để cung cấp ActivityMainBinding
    override fun getViewBinding(inflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(inflater)
    }


}