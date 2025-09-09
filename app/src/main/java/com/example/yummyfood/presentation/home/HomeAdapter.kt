
package com.example.yummyfood.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import coil.load
import com.example.yummyfood.data.model.FoodItem
import com.example.yummyfood.databinding.ItemFoodBinding
import com.example.yummyfood.presentation.base.BaseRecyclerAdapter

// Kế thừa từ BaseRecyclerAdapter<FoodItem, ItemFoodBinding>
class HomeAdapter : BaseRecyclerAdapter<FoodItem, ItemFoodBinding>() {

    // Cung cấp cách để tạo (inflate) ViewBinding cho item
    override fun M(parent: ViewGroup): ItemFoodBinding =
        ItemFoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)

    // Cung cấp logic để so sánh các item, thường là so sánh ID
    override fun areItemsSame(oldItem: FoodItem, newItem: FoodItem): Boolean {
        return oldItem.id == newItem.id
    }

    // Cung cấp logic để so sánh nội dung của các item
    override fun areContentsSame(oldItem: FoodItem, newItem: FoodItem): Boolean {
        return oldItem == newItem
    }

    // Cung cấp logic để bind dữ liệu từ 'item' vào 'binding'
    override fun bind(binding: ItemFoodBinding, item: FoodItem, position: Int) {
        binding.ivFood.load(item.imageUrl) {
            crossfade(true)
            // Bạn có thể thêm placeholder/error images ở đây
        }
        binding.tvFoodName.text = item.name
        binding.tvFoodDescription.text = item.description
        binding.tvFoodPrice.text = item.price
        // Bạn có thể thêm OnClickListener cho nút "Add" ở đây nếu cần
    }
}