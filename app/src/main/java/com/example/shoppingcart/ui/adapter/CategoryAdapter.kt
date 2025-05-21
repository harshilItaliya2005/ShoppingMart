package com.example.shoppingcart.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingcart.databinding.ItemCategoriesBinding

class CategoryAdapter(private var categoryImages: List<String>) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    inner class CategoryViewHolder(val binding: ItemCategoriesBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding =
            ItemCategoriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {

        val categoryText = categoryImages[position].trim()
        Log.d("========================", "onBindViewHolder: $categoryText")

        holder.binding.categoryTxt.text = categoryText


        holder.itemView.translationX = 300f
        holder.itemView.alpha = 0f
        holder.itemView.animate()
            .translationX(0f)
            .alpha(1f)
            .setDuration(500)
            .start()
    }

    override fun getItemCount(): Int {
        return categoryImages.size
    }

    fun updateData(newImages: List<String>) {
        categoryImages = newImages
        notifyDataSetChanged()
    }


}
