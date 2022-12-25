package com.poly.restaurant.ui.activities.product.appetizer.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.poly.restaurant.data.models.Product
import com.poly.restaurant.databinding.ItemProductBinding

class ProductAdapter(var mListProduct: List<Product>, private val onEventProductListener: IOnEventProductListener): RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    class ProductViewHolder(binding: ItemProductBinding): RecyclerView.ViewHolder(binding.root){
        private val tvName = binding.tvNameProduct
        private val imgProduct = binding.imgProduct
        private val tvPrice = binding.tvPrice
        private val tvDes = binding.tvDescription
        val imgIncrease = binding.imgIncrease
        val imgDecrease = binding.imgDecrease
        val tvQuantity = binding.tvQuantity
        val viewProduct = binding.viewProduct
        val tvDescription = binding.tvDescription

        @SuppressLint("SetTextI18n")
        fun bind(product: Product){
            tvName.text = product.name
            Glide.with(imgProduct.context).load(product.urlImage).into(imgProduct)
            tvPrice.text = "${product.price}$"
            tvQuantity.text = "x"+product.amount
            tvDescription.text = product.description
        }
    }

    fun setList(mListProduct: List<Product>){
        this.mListProduct = mListProduct
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = mListProduct[position]
        holder.bind(product)
        holder.imgIncrease.setOnClickListener {
            onEventProductListener.onClickIncrease(product, holder.tvQuantity, position)
        }
        holder.imgDecrease.setOnClickListener {
            onEventProductListener.onClickDecrease(product, holder.tvQuantity, position)
        }
        holder.viewProduct.setOnClickListener {
            onEventProductListener.onClickViewItem(product)
        }

    }

    override fun getItemCount(): Int = mListProduct.size
}

interface IOnEventProductListener{
    fun onClickIncrease(product: Product, textView: TextView, position: Int)
    fun onClickDecrease(product: Product, textView: TextView, position: Int)
    fun onClickViewItem(product: Product)
}