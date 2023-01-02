package com.example.kotlinmvvmcode.view.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kotlinmvvmcode.databinding.AdapterProductsBinding
import com.example.kotlinmvvmcode.view.model.ProductListUiModel

class ProductViewHolder(
    private val itemBinding: AdapterProductsBinding,
    private val onItemClicked: ((productItem: ProductListUiModel) -> Unit)?
) :
    RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(product: ProductListUiModel) {
        itemBinding.apply {
            name.text = product.name
            price.text = product.price
            Glide.with(this.root).load(product.apiFeaturedImage).into(imageview)
            rvItem.setOnClickListener {
                onItemClicked?.invoke(product)
            }
        }
    }
}
