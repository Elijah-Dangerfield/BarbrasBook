package com.dangerfield.barbrasbook.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import com.dangerfield.barbrasbook.R
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btn_search.setOnClickListener {
            search(tv_search.text.toString())
        }
    }

    fun search(term: String) {
        if(term.isEmpty()) {
            Toast.makeText(context,"Enter some shit bro", Toast.LENGTH_LONG).show()
            return
        }

        val data = Bundle()
        data.putString("SEARCH_TERM",term)
        NavHostFragment.findNavController(this).navigate(R.id.action_searchFragment_to_newsFragment,data)
    }


}
