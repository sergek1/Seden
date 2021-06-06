package com.tin

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.tin.databinding.ActivityMainBinding
import com.tin.ui.main.SectionsPagerAdapter
import com.tin.ui.main.adapter.ViewPagerAdapter
import com.tin.ui.main.fragments.Hot
import com.tin.ui.main.fragments.Last
import com.tin.ui.main.fragments.Top


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewPager: ViewPager
    private lateinit var tabs: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewPager = binding.viewPager
        tabs = binding.tabs
//        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
//        val viewPager: ViewPager = findViewById(R.id.view_pager)
//        viewPager.adapter = sectionsPagerAdapter
//        val tabs: TabLayout = findViewById(R.id.tabs)
//        tabs.setupWithViewPager(viewPager)
//        val fab: FloatingActionButton = findViewById(R.id.fab)

        setUpTabs()

//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
//        }
    }



    private fun setUpTabs() {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(Last(), "Последние")
        adapter.addFragment(Top(), "Лучшие")
        adapter.addFragment(Hot(), "Горячие")
        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)
    }


}


