package com.gmail.fattazzo.formula1world.fragments.current.drivers

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.gmail.fattazzo.formula1world.domain.F1Driver
import com.gmail.fattazzo.formula1world.fragments.current.drivers.comparator.DriverDateOfBirthComparator
import com.gmail.fattazzo.formula1world.fragments.current.drivers.comparator.DriverNameComparator
import com.gmail.fattazzo.formula1world.fragments.current.drivers.comparator.DriverNationalityComparator
import com.gmail.fattazzo.formula1world.fragments.current.drivers.comparator.DriverNumberComparator
import org.androidannotations.annotations.AfterInject
import org.androidannotations.annotations.EBean
import org.androidannotations.annotations.RootContext
import org.apache.commons.collections4.CollectionUtils
import java.util.*

@EBean
open class DriversListAdapter : BaseAdapter() {

    private val driverNameComparator = DriverNameComparator()
    private val driverNumberComparator = DriverNumberComparator()
    private val driverNationalityComparator = DriverNationalityComparator()
    private val driverDateOfBirthComparator = DriverDateOfBirthComparator()
    @RootContext
    lateinit internal var context: Context
    private var drivers: MutableList<F1Driver>? = null
    private var sortType = SortType.NAME

    internal fun setSortType(sortType: SortType) {
        this.sortType = sortType
    }

    fun setDrivers(drivers: List<F1Driver>) {
        this.drivers = ArrayList()
        CollectionUtils.addAll(this.drivers, CollectionUtils.emptyIfNull(drivers))
        sortDrivers()
    }

    @AfterInject
    internal fun initAdapter() {
        drivers = ArrayList()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val driverItemView: DriverItemView
        if (convertView == null) {
            driverItemView = DriverItemView_.build(context)
        } else {
            driverItemView = convertView as DriverItemView
        }

        driverItemView.bind(getItem(position))

        return driverItemView
    }

    override fun getCount(): Int {
        return drivers!!.size
    }

    override fun getItem(position: Int): F1Driver {
        return drivers!![position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    internal fun clearItems() {
        drivers!!.clear()
    }

    internal fun sortDrivers() {
        val comparator: Comparator<F1Driver>

        when (sortType) {
            DriversListAdapter.SortType.NAME -> comparator = driverNameComparator
            DriversListAdapter.SortType.NUMBER -> comparator = driverNumberComparator
            DriversListAdapter.SortType.NATIONALITY -> comparator = driverNationalityComparator
            DriversListAdapter.SortType.DATE_OF_BIRTH -> comparator = driverDateOfBirthComparator
            else -> comparator = driverNameComparator
        }
        Collections.sort(drivers!!, comparator)
    }

    internal enum class SortType {
        NUMBER, NAME, NATIONALITY, DATE_OF_BIRTH
    }
}