package com.ireneengels.myapplication.presentation

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.ireneengels.myapplication.R
import com.ireneengels.myapplication.presentation.list.ListFragment
import com.ireneengels.myapplication.presentation.list.viewmodel.PostListViewModel
import com.ireneengels.myapplication.presentation.text.TextFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout

    private lateinit var toolbarTitle: TextView
    private lateinit var searchEditText: EditText
    private lateinit var searchIcon: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            val firstFragment = TextFragment()
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, firstFragment)
                .commit()
        }

        drawerLayout = findViewById(R.id.drawer_layout)

        toolbarTitle = findViewById(R.id.toolbarTitle)
        searchEditText = findViewById(R.id.searchEditText)
        searchIcon = findViewById(R.id.searchIcon)

        setupSearch()

        val burgerMenuIcon: ImageView = findViewById(R.id.burgerMenuIcon)
        burgerMenuIcon.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        val navView: NavigationView = findViewById(R.id.nav_view)
        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_text_fragment -> {
                    navigateToFragment(TextFragment())
                }
                R.id.nav_list_fragment -> {
                    navigateToFragment(ListFragment())
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }

    private fun setupSearch() {
        searchIcon.setOnClickListener {
            toolbarTitle.visibility = View.GONE
            searchEditText.visibility = View.VISIBLE
            searchEditText.requestFocus()
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(searchEditText, InputMethodManager.SHOW_IMPLICIT)
        }

        searchEditText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch(v.text.toString())
                true
            } else {
                false
            }
        }
    }

    private fun performSearch(query: String) {
        Toast.makeText(this, "Search query: $query", Toast.LENGTH_SHORT).show()

        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(searchEditText.windowToken, 0)

        searchEditText.text.clear()
        searchEditText.visibility = View.GONE
        toolbarTitle.visibility = View.VISIBLE
    }

    private fun navigateToFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}