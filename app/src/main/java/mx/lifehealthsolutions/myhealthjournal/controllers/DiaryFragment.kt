package mx.lifehealthsolutions.myhealthjournal.controllers

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_diary.*
import mx.lifehealthsolutions.myhealthjournal.R
import mx.lifehealthsolutions.myhealthjournal.interfaces.ListenerRecycler
import mx.lifehealthsolutions.myhealthjournal.models.AdapterViewCondition
import mx.lifehealthsolutions.myhealthjournal.models.AdapterViewEntry
import mx.lifehealthsolutions.myhealthjournal.models.Entry

/**
 * A simple [Fragment] subclass.
 */
class DiaryFragment : Fragment(), ListenerRecycler {
    var adaptadorEntrada: AdapterViewEntry ?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        configurarRecycler()
        return inflater.inflate(R.layout.fragment_diary, container, false)
    }
    private fun configurarRecycler() {
        val layout = LinearLayoutManager(activity)
        layout.orientation = LinearLayoutManager.VERTICAL
        recyclerEntradas.layoutManager = layout

        adaptadorEntrada = AdapterViewEntry(Entry.arrEntradas)
        adaptadorEntrada?.listener =  this
        recyclerEntradas.adapter =  adaptadorEntrada

        val divisor = DividerItemDecoration(activity, layout.orientation)
        recyclerEntradas.addItemDecoration(divisor)

    }

    override fun itemClicked(position: Int) {

    }
}
