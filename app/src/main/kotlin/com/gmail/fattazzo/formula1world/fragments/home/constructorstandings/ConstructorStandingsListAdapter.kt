package com.gmail.fattazzo.formula1world.fragments.home.constructorstandings

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.gmail.fattazzo.formula1world.domain.F1ConstructorStandings
import org.androidannotations.annotations.AfterInject
import org.androidannotations.annotations.EBean
import org.androidannotations.annotations.RootContext
import java.util.*

@EBean
internal open class ConstructorStandingsListAdapter : BaseAdapter() {

    private var constructors: MutableList<F1ConstructorStandings>? = null

    @RootContext
    lateinit var context: Context

    fun setConstructors(constructors: MutableList<F1ConstructorStandings>) {
        this.constructors = constructors
    }

    @AfterInject
    fun initAdapter() {
        constructors = ArrayList()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val constructorStandingsItemView: ConstructorStandingsItemView
        if (convertView == null) {
            constructorStandingsItemView = ConstructorStandingsItemView_.build(context)
        } else {
            constructorStandingsItemView = convertView as ConstructorStandingsItemView
        }

        constructorStandingsItemView.bind(getItem(position))

        return constructorStandingsItemView
    }

    override fun getCount(): Int {
        return constructors!!.size
    }

    override fun getItem(position: Int): F1ConstructorStandings {
        return constructors!![position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun clearItems() {
        constructors!!.clear()
    }
}