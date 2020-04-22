package mx.lifehealthsolutions.myhealthjournal.controllers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mx.lifehealthsolutions.myhealthjournal.R
import mx.lifehealthsolutions.myhealthjournal.interfaces.ListenerRecycler
import mx.lifehealthsolutions.myhealthjournal.models.AdapterViewCondition
import mx.lifehealthsolutions.myhealthjournal.models.AdapterViewEntry
import mx.lifehealthsolutions.myhealthjournal.models.Condition
import mx.lifehealthsolutions.myhealthjournal.models.Entry

/**
 * A simple [Fragment] subclass.
 */
class DiaryFragment : Fragment(), ListenerRecycler {
    lateinit var recyclerView: RecyclerView
    protected lateinit var rootView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_diary, container, false);
        recyclerView = rootView.findViewById(R.id.recyclerEntradas)
        configurarRecycler()

        return inflater.inflate(R.layout.fragment_diary, container, false)
    }


    private fun configurarRecycler() {

        var adaptadorEntrada = AdapterViewEntry(Entry.arrEntradas)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.setHasFixedSize(true)

        adaptadorEntrada?.listener =  this
        recyclerView.adapter =  adaptadorEntrada


    }

    override fun itemClicked(position: Int) {

    }
}
