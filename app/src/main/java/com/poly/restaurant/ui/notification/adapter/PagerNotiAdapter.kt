package com.poly.restaurant.ui.notification.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.poly.restaurant.ui.notification.receiptnotice.ReceiptNoticeFr
import com.poly.restaurant.ui.notification.sendnotification.SendNotificationFr

class PagerNotiAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    private val fragments = arrayOf(
        SendNotificationFr(),
        ReceiptNoticeFr()
    )

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]
}