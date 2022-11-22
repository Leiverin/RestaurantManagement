package com.poly.myapplication.ui.activities.product.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.poly.myapplication.ui.activities.product.appetizer.AppetizerFragment
import com.poly.myapplication.ui.activities.product.desserts.DessertFragment
import com.poly.myapplication.ui.activities.product.drinks.DrinksFragment
import com.poly.myapplication.ui.activities.product.main.MainDishesFragment

class PagerProductAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    private val fragments = arrayOf(
        AppetizerFragment(),
        MainDishesFragment(),
        DessertFragment(),
        DrinksFragment()
    )

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]
}