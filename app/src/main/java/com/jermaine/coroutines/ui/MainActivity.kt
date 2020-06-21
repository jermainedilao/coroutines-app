package com.jermaine.coroutines.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jermaine.coroutines.R
import com.jermaine.coroutines.data.Item
import com.jermaine.coroutines.databinding.ActivityMainBinding
import com.jermaine.coroutines.databinding.ItemTrackBinding
import com.jermaine.coroutines.ui.adapters.ItemDiffUtilCallback
import com.jermaine.coroutines.ui.adapters.SimpleListAdapter
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.serialization.UnstableDefault

@UnstableDefault
class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private val adapter by lazy {
        SimpleListAdapter<Item, ItemTrackBinding>(
            ItemDiffUtilCallback(),
            R.layout.item_track
        ) { item ->
            Log.d(TAG, "setupViews: $item")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )

        viewModel =
            ViewModelProvider(
                this,
                MainViewModelFactory()
            ).get(MainViewModel::class.java)

        setupViews()
        setupVmObservers()

        viewModel.search()
    }

    private fun setupVmObservers() {
        viewModel
            .items
            .observe(this, Observer { items ->
                adapter.submitList(items)
            })

        lifecycleScope.launch {
            viewModel
                .state
                .collect {
                    Log.d(TAG, "onStart: $it")
                    handleState(it)
                }
        }
    }

    private fun handleState(state: MainState) {
        when (state) {
            MainState.ShowLoading -> {
                binding
                    .loading
                    .visibility = View.VISIBLE
            }
            MainState.HideLoading -> {
                binding
                    .loading
                    .visibility = View.GONE
            }
            MainState.Error -> {
                Toast
                    .makeText(
                        this,
                        getString(R.string.generic_error),
                        Toast.LENGTH_SHORT
                    )
                    .show()
            }
        }
    }

    private fun setupViews() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            addItemDecoration(
                DividerItemDecoration(
                    this@MainActivity,
                    DividerItemDecoration.VERTICAL
                )
            )

            adapter = this@MainActivity.adapter
        }
    }
}
