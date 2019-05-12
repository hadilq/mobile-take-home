package com.github.hadilq.mobiletakehome.data.utils.impl

import android.content.Context
import android.util.Log
import com.github.hadilq.mobiletakehome.data.db.table.AirlineRow
import com.github.hadilq.mobiletakehome.data.db.table.AirportRow
import com.github.hadilq.mobiletakehome.data.db.table.RouteRow
import com.github.hadilq.mobiletakehome.data.utils.CsvReader
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.regex.Pattern

class CsvReaderImpl(
    private val context: Context
) : CsvReader {

    override fun loadAirlines(): Map<String, AirlineRow> {
        val map = HashMap<String, AirlineRow>()
        load("airlines.csv") {
            val list = csvLineReader(it)
            val twoDigitCode = list[1]
            map[twoDigitCode] = AirlineRow(
                name = list[0],
                twoDigitCode = twoDigitCode,
                threeDigitCode = list[2],
                country = list[3]
            )
        }
        return map
    }

    override fun loadAirports(): Map<String, AirportRow> {
        val map = HashMap<String, AirportRow>()
        load("airports.csv") {
            val list = csvLineReader(it)
            val iata = list[3]
            map[iata] = AirportRow(
                name = list[0],
                city = list[1],
                country = list[2],
                iata = iata,
                latitude = list[4].toDouble(),
                longitude = list[5].toDouble()
            )
        }
        return map
    }

    override fun loadRouts(): List<RouteRow> {
        val l = ArrayList<RouteRow>()
        load("routes.csv") {
            val list = csvLineReader(it)
            l += RouteRow(
                airlineId = list[0],
                originId = list[1],
                destinationId = list[2]
            )
        }
        return l
    }

    private fun csvLineReader(line: String): List<String> {
        val list = ArrayList<String>()
        val matcher = CSV_PATTERN.matcher(line)
        while (matcher.find()) {
            val s = matcher.group()
            list += if (s.startsWith(",")) s.substring(1, s.length) else s
        }
        return list
    }

    private inline fun load(fileName: String, lines: (String) -> Unit) {
        try {
            val inputStream = context.assets.open(fileName)
            val reader = BufferedReader(InputStreamReader(inputStream))
            var firstLine = true
            while (reader.ready()) {
                if (firstLine) {
                    @Suppress("UNUSED_VARIABLE") val header = reader.readLine()
                    firstLine = false
                } else {
                    lines(reader.readLine())
                }
            }
            inputStream.close()
        } catch (e: IOException) {
            Log.d("CsvReaderImpl", "Failed to open file $fileName", e)
            e.printStackTrace()
        }
    }

    companion object {
        private val CSV_PATTERN = Pattern.compile("(?:^|,)\\s*(?:(?:(?=\")\"([^\"].*?)\")|(?:(?!\")(.*?)))(?=,|\$)")
    }
}