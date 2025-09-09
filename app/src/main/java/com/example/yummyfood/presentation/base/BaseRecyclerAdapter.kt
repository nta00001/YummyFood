package com.example.yummyfood.presentation.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 * Lớp Adapter cơ sở cho RecyclerView để giảm thiểu code lặp lại.
 * T: Kiểu dữ liệu của item trong danh sách.
 * VB: Lớp ViewBinding cho layout của item.
 */
abstract class BaseRecyclerAdapter<T : Any, VB : ViewBinding> :
    RecyclerView.Adapter<BaseRecyclerAdapter.BaseViewHolder<VB>>() {

    // Cung cấp cách để inflate ViewBinding cho item.
    abstract fun M(parent: ViewGroup): VB

    // Sử dụng AsyncListDiffer để xử lý danh sách và tính toán sự khác biệt trên background thread.
    private val differCallback = object : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
            return areItemsSame(oldItem, newItem)
        }

        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
            return areContentsSame(oldItem, newItem)
        }
    }

    // Các lớp con phải implement 2 phương thức này để so sánh item.
    // Thường là so sánh id của item.
    abstract fun areItemsSame(oldItem: T, newItem: T): Boolean
    // Thường là so sánh toàn bộ object.
    abstract fun areContentsSame(oldItem: T, newItem: T): Boolean

    // Khởi tạo AsyncListDiffer.
    protected val differ = AsyncListDiffer(this, differCallback)

    // Hàm để cập nhật danh sách từ bên ngoài.
    fun submitList(list: List<T>) {
        differ.submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<VB> {
        val binding = M(parent)
        return BaseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<VB>, position: Int) {
        val item = differ.currentList[position]
        bind(holder.binding, item, position)
    }

    // Lớp con sẽ implement logic bind dữ liệu vào view ở đây.
    protected abstract fun bind(binding: VB, item: T, position: Int)

    override fun getItemCount(): Int = differ.currentList.size

    // ViewHolder cơ sở, chỉ chứa binding.
    class BaseViewHolder<VB : ViewBinding>(val binding: VB) : RecyclerView.ViewHolder(binding.root)
}