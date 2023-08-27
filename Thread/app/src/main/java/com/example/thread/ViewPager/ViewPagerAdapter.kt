package com.example.thread.ViewPager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.thread.Screens.FavoriteListFragment.FavoriteListFragment
import com.example.thread.Screens.MainFragment.MainFragment
import com.example.thread.Root.RootFragment

class ViewPagerAdapter(rootFragment: RootFragment) : FragmentStateAdapter(rootFragment) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> MainFragment()
            else -> FavoriteListFragment()
        }
    }
}