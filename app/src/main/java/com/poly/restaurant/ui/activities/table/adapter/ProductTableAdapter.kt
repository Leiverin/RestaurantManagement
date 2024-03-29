package com.poly.restaurant.ui.activities.table.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.poly.restaurant.data.models.Product
import com.poly.restaurant.databinding.ItemProductTableBinding

@SuppressLint("SetTextI18n")
class ProductTableAdapter(var mListProduct: List<Product>, private val onItemProductTableListener: IOnItemProductTableListener): RecyclerView.Adapter<ProductTableAdapter.ProductTableViewHolder>() {

    class ProductTableViewHolder(binding: ItemProductTableBinding): RecyclerView.ViewHolder(binding.root){
        private val imgProduct = binding.imgProduct
        private val tvName = binding.tvNameProduct
        private val tvPrice = binding.tvPriceProduct
        val tvQuantity = binding.tvQuantityProduct
        val imbDecrease = binding.imbDecrease
        val imbIncrease = binding.imbIncrease
        val imbDelete = binding.imbDelete
        val viewItem = binding.viewItem
        private val viewCurtain = binding.viewCurtain

        fun bind(product: Product){
            Glide.with(imgProduct.context).load(product.urlImage).into(imgProduct)
            tvName.text = product.name
            tvPrice.text = "${(product.price * 23000).toInt()} vnđ"
            tvQuantity.text = "${product.amount}"
            if (product.status == 0){
                viewCurtain.visibility = View.VISIBLE
                viewItem.isEnabled = false
            }else{
                viewCurtain.visibility = View.GONE
                viewItem.isEnabled = true
            }
        }
    }

    fun setList(mListProduct: List<Product>){
        this.mListProduct = mListProduct
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductTableViewHolder {
        return ProductTableViewHolder(ItemProductTableBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ProductTableViewHolder, position: Int) {
        val product = mListProduct[position]
        holder.bind(product)
        holder.imbDelete.setOnClickListener {
            onItemProductTableListener.onClickDelete(product)
        }
        holder.imbDecrease.setOnClickListener {
            onItemProductTableListener.onClickDecrease(product, holder.tvQuantity, position)
        }
        holder.imbIncrease.setOnClickListener {
            onItemProductTableListener.onClickIncrease(product, holder.tvQuantity, position)
        }
        holder.viewItem.setOnClickListener {
            onItemProductTableListener.onClickViewItem(product)
        }
    }

    override fun getItemCount(): Int = mListProduct.size

}

interface IOnItemProductTableListener {
    fun onClickDelete(product: Product)
    fun onClickDecrease(product: Product, textView: TextView, position: Int)
    fun onClickIncrease(product: Product, textView: TextView, position: Int)
    fun onClickViewItem(product: Product)
}