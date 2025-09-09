package com.example.yummyfood.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding


abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    // Biến binding để các lớp con có thể truy cập vào các view.
    protected lateinit var binding: VB
        private set // Chỉ cho phép gán giá trị bên trong BaseActivity

    // Phương thức trừu tượng buộc các lớp con phải cung cấp cách để inflate layout.
    abstract fun getViewBinding(inflater: LayoutInflater): VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Gán giá trị cho biến binding bằng cách gọi phương thức do lớp con cung cấp.
        binding = getViewBinding(layoutInflater)
        // Thiết lập content view cho activity.
        setContentView(binding.root)
    }
}