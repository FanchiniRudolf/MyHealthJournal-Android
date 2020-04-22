package mx.lifehealthsolutions.myhealthjournal.controllers

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_diary.view.*
import mx.lifehealthsolutions.myhealthjournal.R
import mx.lifehealthsolutions.myhealthjournal.interfaces.ListenerRecycler
import mx.lifehealthsolutions.myhealthjournal.models.AdapterViewEntry
import mx.lifehealthsolutions.myhealthjournal.models.Entry


/**
 * A simple [Fragment] subclass.
 */
class DiaryFragment : Fragment(), ListenerRecycler {
    var adaptadorEntrada: AdapterViewEntry = AdapterViewEntry(Entry.arrEntradas)
    lateinit var recyclerView: RecyclerView
    protected lateinit var rootView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val view: View = inflater!!.inflate(R.layout.fragment_diary, container, false)

        view.btnNewEvent.setOnClickListener { view ->
            val intent = Intent(activity, CreateEntryActiv::class.java)
            startActivity(intent)
        }

        // Return the fragment view/layout
        return view

    }
    private fun configurarRecycler() {
        recyclerView = rootView.findViewById(R.id.recyclerEntradas)

        adaptadorEntrada = AdapterViewEntry(Entry.arrEntradas)
        val layout = LinearLayoutManager(activity)
        layout.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = layout

        adaptadorEntrada?.listener =  this
        recyclerView.adapter =  adaptadorEntrada

    }

    override fun itemClicked(position: Int) {

    }
}
