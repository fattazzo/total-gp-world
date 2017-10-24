package com.gmail.fattazzo.formula1world.fragments.current.constructors

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.gmail.fattazzo.formula1world.domain.F1Constructor
import org.androidannotations.annotations.AfterInject
import org.androidannotations.annotations.EBean
import org.androidannotations.annotations.RootContext
import org.apache.commons.collections4.CollectionUtils
import java.util.*

@EBean
internal open class ConstructorsListAdapter : BaseAdapter() {

    @RootContext
    lateinit var context: Context

    private var constructors: MutableList<F1Constructor>? = null

    fun setConstructors(constructors: List<F1Constructor>) {
        this.constructors = ArrayList()
        CollectionUtils.addAll(this.constructors, CollectionUtils.emptyIfNull(constructors))
    }

    @AfterInject
    fun initAdapter() {
        constructors = ArrayList()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val constructorItemView: ConstructorItemView
        if (convertView == null) {
            constructorItemView = ConstructorItemView_.build(context)
        } else {
            constructorItemView = convertView as ConstructorItemView
        }

        constructorItemView.bind(getItem(position))

        return constructorItemView
    }

    override fun getCount(): Int {
        return constructors!!.size
    }

    override fun getItem(position: Int): F1Constructor {
        return constructors!![position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun clearItems() {
        constructors!!.clear()
    }
}