package com.gmail.fattazzo.formula1world.fragments.stats.adapters

import android.app.Activity
import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.SparseIntArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ImageView
import android.widget.TextView
import com.activeandroid.Model
import com.activeandroid.query.Select
import com.gmail.fattazzo.formula1world.R
import com.gmail.fattazzo.formula1world.domain.F1Constructor
import com.gmail.fattazzo.formula1world.ergast.imagedb.objects.Circuit
import com.gmail.fattazzo.formula1world.ergast.imagedb.objects.Constructor
import com.gmail.fattazzo.formula1world.ergast.imagedb.objects.Driver
import com.gmail.fattazzo.formula1world.ergast.imagedb.service.stats.StatsCircuitsData
import com.gmail.fattazzo.formula1world.service.DataService
import com.gmail.fattazzo.formula1world.utils.ImageUtils
import com.gmail.fattazzo.formula1world.utils.LocaleUtils
import com.gmail.fattazzo.formula1world.utils.Utils

class StatsCircuitsDataListAdapter(private val context: Context, private val data: List<StatsCircuitsData>, private val dataService: DataService, private val localeUtils: LocaleUtils, private val imageUtils: ImageUtils, private val utils: Utils) : BaseExpandableListAdapter() {
    private val mInflater: LayoutInflater

    private val constructorsColorCache: SparseIntArray

    init {

        constructorsColorCache = SparseIntArray()

        mInflater = (context as Activity).layoutInflater
    }

    override fun getGroupCount(): Int {
        return data.size
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return 1
    }

    override fun getGroup(groupPosition: Int): Any {
        return data[groupPosition]
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return data[groupPosition]
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val data = getGroup(groupPosition) as StatsCircuitsData

        val holder: ViewHolderItem

        if (convertView == null) {
            holder = ViewHolderItem()
            when (data.type) {
                StatsCircuitsData.Type.CONSTRUCTORS_WINNER, StatsCircuitsData.Type.DRIVERS_WINNER -> {
                    convertView = mInflater.inflate(R.layout.stats_circuits_winner_row, null)
                    holder.winnerTV = convertView!!.findViewById<TextView>(R.id.winnerTV)
                }
                StatsCircuitsData.Type.COUNT -> {
                    convertView = mInflater.inflate(R.layout.stats_circuits_count_row, null)
                    holder.countTV = convertView!!.findViewById<TextView>(R.id.countTV)
                }
            }
            holder.nameTV = convertView!!.findViewById<TextView>(R.id.nameTV)
            holder.teamIconImage = convertView.findViewById<ImageView>(R.id.teamIconImage)
            convertView.tag = holder
        } else {
            holder = convertView.tag as ViewHolderItem
        }

        holder.nameTV!!.text = data.name
        if (holder.winnerTV != null) {
            holder.winnerTV!!.text = data.winnerName
        } else {
            holder.countTV!!.text = data.count.toString()
        }

        val color = getConstructorColor(data.constructorId, data.constructorRef)
        holder.teamIconImage!!.setColorFilter(color)

        return convertView
    }

    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val data = getGroup(groupPosition) as StatsCircuitsData

        val holder: ViewHolderChildItem

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.stats_circuits_detail, null)

            holder = ViewHolderChildItem()
            holder.circuitNameTV = convertView!!.findViewById<TextView>(R.id.circuitNameTV)
            holder.circuitCountryTv = convertView.findViewById<TextView>(R.id.circuitCountryTv)
            holder.circuitLocationTV = convertView.findViewById<TextView>(R.id.circuitLocationTV)
            holder.circuitFlagImage = convertView.findViewById<ImageView>(R.id.circuitFlagImage)

            holder.driverLayout = convertView.findViewById<ConstraintLayout>(R.id.driverLayout)
            holder.driverSurnameTV = convertView.findViewById<TextView>(R.id.driverSurnameTV)
            holder.driverFornameTV = convertView.findViewById<TextView>(R.id.driverFornameTV)
            holder.driverDobTV = convertView.findViewById<TextView>(R.id.driverDobTV)
            holder.driverFlagImage = convertView.findViewById<ImageView>(R.id.driverFlagImage)

            holder.constructorLayout = convertView.findViewById<ConstraintLayout>(R.id.constructorLayout)
            holder.constructorNameTV = convertView.findViewById<TextView>(R.id.constructorNameTV)
            holder.constructorFlagImage = convertView.findViewById<ImageView>(R.id.constructorFlagImage)
            convertView.tag = holder
        } else {
            holder = convertView.tag as ViewHolderChildItem
        }

        val circuit = Select().from(Circuit::class.java).where("Id = ?", data.id).executeSingle<Circuit>()
        holder.circuitNameTV!!.text = circuit.name
        holder.circuitCountryTv!!.text = circuit.country
        holder.circuitLocationTV!!.text = circuit.location
        holder.circuitFlagImage!!.setImageBitmap(imageUtils.getFlagForCountryCode(localeUtils.getCountryCode(circuit.country!!)))

        holder.driverLayout!!.visibility = if (data.driverId > 0) View.VISIBLE else View.GONE
        if (data.driverId > 0) {
            val driver = Model.load<Driver>(Driver::class.java, data.driverId.toLong())
            holder.driverSurnameTV!!.setText(driver.surname)
            holder.driverFornameTV!!.setText(driver.forename)
            holder.driverDobTV!!.setText(driver.dob)

            val countryNationality = utils.getCountryNationality(driver.nationality)
            if (countryNationality != null) {
                holder.driverFlagImage!!.setImageBitmap(imageUtils.getFlagForCountryCode(countryNationality.alpha2Code!!))
            }
        }

        holder.constructorLayout!!.visibility = if (data.constructorId > 0) View.VISIBLE else View.GONE
        if (data.constructorId > 0) {
            val constructor = Model.load<Constructor>(Constructor::class.java, data.constructorId.toLong())
            holder.constructorNameTV!!.setText(constructor.name)

            val countryNationality = utils.getCountryNationality(constructor.nationality)
            if (countryNationality != null) {
                holder.constructorFlagImage!!.setImageBitmap(imageUtils.getFlagForCountryCode(countryNationality.alpha2Code!!))
            }
        }

        return convertView
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return false
    }

    private fun getConstructorColor(constructorId: Int, constructorRef: String?): Int {
        if (constructorRef == null) {
            return android.R.color.transparent
        }

        var color = constructorsColorCache.get(constructorId, -1)
        if (color == -1) {
            val constructor = F1Constructor()
            constructor.constructorRef = constructorRef
            color = dataService.loadContructorColor(constructor)
            constructorsColorCache.put(constructorId, color)
        }
        return color
    }

    private class ViewHolderItem {
        internal var nameTV: TextView? = null
        internal var countTV: TextView? = null
        internal var winnerTV: TextView? = null
        internal var teamIconImage: ImageView? = null
    }

    private class ViewHolderChildItem {
        internal var circuitNameTV: TextView? = null
        internal var circuitCountryTv: TextView? = null
        internal var circuitLocationTV: TextView? = null
        internal var circuitFlagImage: ImageView? = null

        internal var driverLayout: ConstraintLayout? = null
        internal var constructorLayout: ConstraintLayout? = null

        internal var driverSurnameTV: TextView? = null
        internal var driverFornameTV: TextView? = null
        internal var driverDobTV: TextView? = null
        internal var constructorNameTV: TextView? = null
        internal var driverFlagImage: ImageView? = null
        internal var constructorFlagImage: ImageView? = null
    }
}