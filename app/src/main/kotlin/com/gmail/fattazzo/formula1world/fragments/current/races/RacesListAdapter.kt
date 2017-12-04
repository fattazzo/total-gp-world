package com.gmail.fattazzo.formula1world.fragments.current.races

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.gmail.fattazzo.formula1world.domain.F1Race
import org.androidannotations.annotations.AfterInject
import org.androidannotations.annotations.EBean
import org.androidannotations.annotations.RootContext
import org.apache.commons.collections4.CollectionUtils
import java.util.*

@EBean
internal open class RacesListAdapter : BaseAdapter() {

    @RootContext
    lateinit var context: Context

    private var races: MutableList<F1Race>? = null

    fun setRaces(races: List<F1Race>) {
        CollectionUtils.addAll(this.races, CollectionUtils.emptyIfNull(races))
    }

    @AfterInject
    fun initAdapter() {
        races = ArrayList()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val raceItemView: RaceItemView = if (convertView == null) {
            RaceItemView_.build(context)
        } else {
            convertView as RaceItemView
        }

        raceItemView.bind(getItem(position))

        return raceItemView
    }

    override fun getCount(): Int {
        return races!!.size
    }

    override fun getItem(position: Int): F1Race {
        return races!![position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun clearItems() {
        races!!.clear()
    }
}