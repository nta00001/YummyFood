package com.example.yummyfood.presentation.home

import androidx.lifecycle.viewModelScope
import com.example.yummyfood.data.model.FoodItem
import com.example.yummyfood.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : BaseViewModel() {

    private val _foodList = MutableStateFlow<List<FoodItem>>(emptyList())
    val foodList = _foodList.asStateFlow()

    // Hàm này sẽ được gọi để tải danh sách món ăn
    fun loadFoodItems() {
        // Hiện tại, chúng ta sẽ dùng dữ liệu giả (dummy data)
        // Trong tương lai, bạn sẽ gọi UseCase hoặc Repository ở đây
        viewModelScope.launch {
            _isLoading.value = true // Bắt đầu loading
            val dummyList = listOf(
                FoodItem(
                    "1",
                    "Chicken Burger",
                    "100 gr chicken + tomato + cheese",
                    "$20.00",
                    "https://www.allrecipes.com/thmb/5JVfA7MxfTUPfRerQMdF-nGKsLY=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/25473-the-perfect-basic-burger-DDMFS-4x3-56eaba3833fd4a26a82755bcd0be0c54.jpg"
                ),
                FoodItem(
                    "2",
                    "Special Pizza",
                    "Beef + mozzarella + special sauce",
                    "$25.00",
                    "https://www.foodandwine.com/thmb/Wd4lBRZz3X_8qBr69UOu2m7I2iw=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/classic-cheese-pizza-FT-RECIPE0422-31a2c938fc2546c9a07b7011658cfd05.jpg"
                ),
                FoodItem(
                    "3",
                    "Chocolate Cake",
                    "Sweet chocolate with cherry",
                    "$15.00",
                    "https://www.mybakingaddiction.com/wp-content/uploads/2011/10/lr-chocolate-molten-lava-cakes-735x1103.jpg"
                )
            )
            _foodList.value = dummyList
            _isLoading.value = false // Kết thúc loading
        }
    }
}