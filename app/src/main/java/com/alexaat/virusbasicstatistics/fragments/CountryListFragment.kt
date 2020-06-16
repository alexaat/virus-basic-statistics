package com.alexaat.virusbasicstatistics.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.alexaat.virusbasicstatistics.R
import com.alexaat.virusbasicstatistics.adapters.CountryListAdapter
import com.alexaat.virusbasicstatistics.database.CountriesDatabase
import com.alexaat.virusbasicstatistics.databinding.FragmentCountryListBinding
import com.alexaat.virusbasicstatistics.repository.LoadStatus
import com.alexaat.virusbasicstatistics.repository.SortMode
import com.alexaat.virusbasicstatistics.viewmodels.CountryListFragmentViewModel
import com.alexaat.virusbasicstatistics.viewmodels.CountryListFragmentViewModelFactory
import com.google.android.material.snackbar.Snackbar

const val MyPREFERENCES = "com.alexaat.virusbasicstatistics.MyPREFERENCES"
const val sortModeKey = "com.alexaat.virusbasicstatistics.SORTMODEKEY"

class CountryListFragment : Fragment() {

    private var sortMode = SortMode.MAXMIN
    lateinit var sharedpreferences: SharedPreferences
    lateinit var viewModel: CountryListFragmentViewModel
    lateinit var adapter: CountryListAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val activity = this.requireActivity()
        sharedpreferences = activity.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE)
        sortMode = when(sharedpreferences.getString(sortModeKey, "MAXMIN")){
            "AZ" -> SortMode.AZ
            "ZA" -> SortMode.ZA
            "MINMAX" -> SortMode.MINMAX
            else -> SortMode.MAXMIN
        }

        val binding: FragmentCountryListBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_country_list, container, false)
        binding.lifecycleOwner = this
        setHasOptionsMenu(true)

        //Database
        val database = CountriesDatabase.getInstance(activity).countriesDatabaseDao

        //ViewModel
        val viewModelFactory = CountryListFragmentViewModelFactory(database, sortMode)
        viewModel = ViewModelProvider(this, viewModelFactory).get(CountryListFragmentViewModel::class.java)

        //Adapter
        adapter = CountryListAdapter()
        binding.countriesListRecyclerview.adapter = adapter

        //Swipe Refresh
        val swipeContainer = binding.swipeContainer
        swipeContainer.setOnRefreshListener {
            viewModel.setSortMode(sortMode)
        }
        swipeContainer.setColorSchemeResources(
            android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light)

        setObservers(swipeContainer)

        return binding.root
    }


    private fun setObservers(swipeContainer:SwipeRefreshLayout){
        viewModel.countries.observe(viewLifecycleOwner, Observer {
            it?.let{
                adapter.submitList(it)
            }
        })


         viewModel.repository.loadStatus.observe(viewLifecycleOwner, Observer {
            it?.let { status ->
                if(status == LoadStatus.FAIL){
                    view?.let { v ->
                         val bar = Snackbar.make(v,getString(R.string.cannot_get_data), Snackbar.LENGTH_SHORT)
                            bar.view.setBackgroundResource(R.color.colorPink)
                            bar.show()
                            viewModel.loadStatusDisplayComplete()
                            swipeContainer.isRefreshing = false
                        }
                }

                if(status == LoadStatus.SUCCESS){
                    view?.let { v ->
                        val bar = Snackbar.make(v,"Up to date", Snackbar.LENGTH_SHORT)
                        bar.view.setBackgroundResource(R.color.colorGreen)
                        bar.show()
                        viewModel.loadStatusDisplayComplete()
                        swipeContainer.isRefreshing = false
                    }
                }
                if(status == LoadStatus.LOADING){
                    Toast.makeText(context,"Loading ...", Toast.LENGTH_SHORT).show()
                }

            }
        })

    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.sort_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
       when(item.itemId){
           R.id.sortaz->{
               sortMode = SortMode.AZ
               saveSortMode(sortMode)
               viewModel.setSortMode(sortMode)
               return true
           }
           R.id.sortza->{
               sortMode = SortMode.ZA
               saveSortMode(sortMode)
               viewModel.setSortMode(sortMode)
               return true
           }
           R.id.sortminmax->{
               sortMode = SortMode.MINMAX
               saveSortMode(sortMode)
               viewModel.setSortMode(sortMode)
               return true
           }
           R.id.sortmaxmin->{
               sortMode = SortMode.MAXMIN
               saveSortMode(sortMode)
               viewModel.setSortMode(sortMode)
               return true
           }
       }
        return super.onOptionsItemSelected(item)
    }

    private fun  saveSortMode(sortMode:SortMode){
        val editor = sharedpreferences.edit()
        when(sortMode){
            SortMode.AZ -> editor.putString(sortModeKey, getString(R.string.AZ))
            SortMode.ZA -> editor.putString(sortModeKey, getString(R.string.ZA))
            SortMode.MINMAX ->{
                editor.putString(sortModeKey, getString(R.string.MINMAX))

            }
            else -> {
                editor.putString(sortModeKey, getString(R.string.MAXMIN))

            }
        }
        editor.commit()
    }

}
