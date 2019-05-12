/***
 * Copyright 2019 Hadi Lashkari Ghouchani
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * */
package com.github.hadilq.mobiletakehome.presentationpathselector

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.Filterable
import com.github.hadilq.mobiletakehome.domain.entity.Airport
import com.github.hadilq.mobiletakehome.presentationcommon.inflate
import kotlinx.android.synthetic.main.airport_row.view.*
import javax.inject.Inject

class AirportAutoCompleteAdapter @Inject constructor(context: Context) :
    ArrayAdapter<Airport>(context, R.layout.airport_row), Filterable {

    private val list = mutableListOf<Airport>()

    fun addAirports(airports: List<Airport>) {
        list.clear()
        list += airports
        notifyDataSetChanged()
    }

    override fun clear() {
        list.clear()
        super.clear()
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Airport? {
        return if (position < list.size) list[position] else null
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView?.let { it } ?: let { parent.inflate(R.layout.airport_row) }
        view.iataView.text = getItem(position)?.iata
        view.nameView.text = getItem(position)?.name
        return view
    }

    override fun getFilter(): Filter {
        return object : Filter() {

            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                constraint?.let {
                    filterResults.values = list
                    filterResults.count = list.size
                }
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged()
                } else {
                    notifyDataSetInvalidated()
                }
            }

            override fun convertResultToString(resultValue: Any?): CharSequence {
                return (resultValue as? Airport)?.iata ?: ""
            }
        }
    }

}