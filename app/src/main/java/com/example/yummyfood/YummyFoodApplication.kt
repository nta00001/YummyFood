package com.example.yummyfood


import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp // <-- ANNOTATION QUAN TRỌNG NHẤT
class YummyFoodApplication : Application() {
    // Lớp này có thể trống, annotation là đủ
}