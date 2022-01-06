package com.example.barchar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
class MainActivity : AppCompatActivity() {
    private var TAG = "MainActivity"

        private lateinit var barChart: BarChart

        private var tasksList = ArrayList<Tasks>()

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

            barChart = findViewById(R.id.barChar)

            tasksList = getTasksList()

            initBarChart()


            //now draw bar chart with dynamic data
            val entries: ArrayList<BarEntry> = ArrayList()

            //you can replace this data object with  your custom object
            for (i in tasksList.indices) {
                val task = tasksList[i]
                entries.add(BarEntry(i.toFloat(), task.time.toFloat()))
            }

            val barDataSet = BarDataSet(entries, "Tasks")
            barDataSet.setColors(*ColorTemplate.VORDIPLOM_COLORS)

            val data = BarData(barDataSet)
            barChart.data = data

            barChart.invalidate()

        }

        private fun initBarChart() {


//        hide grid lines
            barChart.axisLeft.setDrawGridLines(true)
            val xAxis: XAxis = barChart.xAxis
            xAxis.setDrawGridLines(true)
            xAxis.setDrawAxisLine(true)

            //remove right y-axis
            barChart.axisRight.isEnabled = false

            //remove legend
            barChart.legend.isEnabled = false


            //remove description label
            barChart.description.isEnabled = false


            //add animation
            barChart.animateY(3000)

            // to draw label on xAxis
            xAxis.position = XAxis.XAxisPosition.BOTTOM_INSIDE
            xAxis.valueFormatter = MyAxisFormatter()
            xAxis.setDrawLabels(true)
            xAxis.granularity = 1f
            xAxis.labelRotationAngle = +90f

        }


        inner class MyAxisFormatter : IndexAxisValueFormatter() {

            override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                val index = value.toInt()
                Log.d(TAG, "getAxisLabel: index $index")
                return if (index < tasksList.size) {
                    tasksList[index].name
                } else {
                    ""
                }
            }
        }
        // simulate api call
        // we are initialising it directly
        private fun getTasksList(): ArrayList<Tasks> {
            tasksList.add(Tasks("Reading", 100))
            tasksList.add(Tasks("sports", 110))
            tasksList.add(Tasks("Alia", 99))
            tasksList.add(Tasks("Shopping", 200))
            tasksList.add(Tasks("sleep", 140))
            tasksList.add(Tasks("watch", 130))

            return tasksList
        }
    }