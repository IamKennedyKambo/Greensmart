package org.triniti.greensmart.ui.main

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import kotlinx.android.synthetic.main.layout_f_points.*
import org.triniti.greensmart.R

class Points : Fragment() {
    private val yData = floatArrayOf(25.3f, 74.7f)
    private val xData = arrayOf("Mitch", "Jessica")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.layout_f_points,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ivMarket.setOnClickListener {
            findNavController().navigate(R.id.destination_mall)
        }

        val desc = Description()
        desc.text = ""
        pieChart.setDrawEntryLabels(false)
        pieChart.description = desc
        pieChart.isRotationEnabled = false
        pieChart.setUsePercentValues(true)
        pieChart.setHoleColor(Color.TRANSPARENT)
        pieChart.setCenterTextColor(Color.WHITE)
        pieChart.holeRadius = 70f
        pieChart.setTransparentCircleAlpha(0)
        pieChart.centerText = "Level 1"
        pieChart.setCenterTextSize(10f)
        pieChart.setDrawEntryLabels(true)
        pieChart.setEntryLabelTextSize(20f)
        pieChart.animateX(500)

        addDataSet()

        pieChart.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
            override fun onValueSelected(e: Entry?, h: Highlight) {

//                var pos1 = e.toString().indexOf("(sum): ")
//                val sales = e.toString().substring(pos1 + 7)
//
//                for (i in yData.indices) {
//                    if (yData[i] == java.lang.Float.parseFloat(sales)) {
//                        pos1 = i
//                        break
//                    }
//                }
//                val employee = xData[pos1 + 1]
//                Toast.makeText(
//                    context,
//                    "Employee " + employee + "\n" + "Sales: $" + sales + "K",
//                    Toast.LENGTH_LONG
//                ).show()
            }

            override fun onNothingSelected() {

            }
        })
    }

    private fun addDataSet() {
        val yEntrys = arrayListOf<PieEntry>()
        val xEntrys = arrayListOf<String>()

        for (i in yData.indices) {
            yEntrys.add(PieEntry(yData[i], i))
        }

        for (i in 1 until xData.size) {
            xEntrys.add(xData[i])
        }

        //create the data set
        val pieDataSet = PieDataSet(yEntrys, "")
        pieDataSet.sliceSpace = 2f
        pieDataSet.valueTextSize = 12f

        //add colors to dataset
        val colors = arrayListOf<Int>()
        colors.add(Color.GRAY)
        colors.add(Color.WHITE)

        pieDataSet.colors = colors

//        add legend to chart
        val legend = pieChart.legend
        legend.form = Legend.LegendForm.NONE

        //create pie data object
        val pieData = PieData(pieDataSet)
        pieChart.data = pieData
        pieChart.invalidate()
    }
}