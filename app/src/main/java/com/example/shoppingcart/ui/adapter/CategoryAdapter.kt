package com.example.shoppingcart.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingcart.R
import com.example.shoppingcart.databinding.ItemCategoriesBinding

class CategoryAdapter(private var categoryImages: List<String>, private val navController: NavController) :
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

        holder.binding.categoryCard.setOnClickListener {
            when(categoryText) {
                "men's clothing" -> navController.navigate(R.id.action_homeFragment_to_mensFragment)
                "women's clothing" -> navController.navigate(R.id.action_homeFragment_to_womenFragment)
                "jewelery" -> navController.navigate(R.id.action_homeFragment_to_jewelleryFragment)
                "electronics" -> navController.navigate(R.id.action_homeFragment_to_electronicsFragment)
            }
        }




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
