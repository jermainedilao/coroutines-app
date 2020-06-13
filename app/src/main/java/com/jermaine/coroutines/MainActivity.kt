package com.jermaine.coroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jermaine.coroutines.databinding.ActivityMainBinding
import kotlinx.serialization.UnstableDefault

@UnstableDefault
class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        viewModel =
            ViewModelProvider(
                this,
                MainViewModelFactory()
            ).get(MainViewModel::class.java)

        binding.vm = viewModel

        viewModel
            .items
            .observe(this, Observer { items ->
                Log.d(TAG, "onCreate: $items")
            })

        viewModel
            .loadingState
            .observe(this, Observer { isLoading ->
                binding
                    .loading
                    .visibility = if (isLoading) View.VISIBLE else View.GONE
            })

        binding
            .btnSearch
            .setOnClickListener {
                viewModel.search()
            }
    }
}
