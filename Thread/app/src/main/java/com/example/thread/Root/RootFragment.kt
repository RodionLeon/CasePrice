package com.example.thread.Root

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.thread.R
import com.example.thread.ViewPager.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RootFragment : Fragment() {
    lateinit var adapter: ViewPagerAdapter
    private var ctx : Context? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ctx = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_root, container,false)
        val tabLayout = view.findViewById<TabLayout>(R.id.tab_layout)
        val viewPager = view.findViewById<ViewPager2>(R.id.view_pager)
        adapter = ViewPagerAdapter(this)
        tabLayout.tabIconTint = null
        viewPager.adapter = adapter
        TabLayoutMediator(tabLayout,viewPager){ tab, pos ->
            when(pos){
                0 -> {
                    tab.setIcon(R.drawable.baseline_list_24)
                }
                1 ->{
                    tab.setIcon(R.drawable.baseline_favorite_border_24)
                }
            }
        }.attach()
        return view
    }


}