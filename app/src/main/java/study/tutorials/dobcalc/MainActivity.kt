package study.tutorials.dobcalc

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    private var tvSelectedDate : TextView? = null
    private var tvAgeInMinutes : TextView? = null
    private var tvAgeInDay : TextView? =null
    private var tvAgeInYear : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvSelectedDate = findViewById(R.id.tvSelectedDate)
        tvAgeInMinutes = findViewById(R.id.tvAgeInMin)
        tvAgeInDay = findViewById(R.id.tvAgeInDay)
        tvAgeInYear = findViewById(R.id.tvAgeInYear)

        initAgeInMinutes()

        val btnSelectDate : Button = findViewById<Button>(R.id.btnSelectDate)
        btnSelectDate.setOnClickListener {
            clickDatePicker()
        }

    }

    private fun initAgeInMinutes()
    {
        val currentDateMillis  : Long = System.currentTimeMillis()
        val currentDate: String = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(currentDateMillis)
        calculateAgeInMinutes( currentDate )

        tvSelectedDate?.text = currentDate
        tvAgeInMinutes?.text = calculateAgeInMinutes( currentDate ).toString()
        tvAgeInDay?.text = calculateAgeInDay( currentDate ).toString()
        tvAgeInYear?.text = calculateAgeInDay( currentDate ).toString()

    }

    private fun clickDatePicker(){
        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get( Calendar.YEAR )
        val month = myCalendar.get( Calendar.MONTH)
        val day = myCalendar.get( Calendar.DAY_OF_MONTH)
        val dpd = DatePickerDialog( this,
            DatePickerDialog.OnDateSetListener{ _, selectedYear, selectedMonth, selectedDay ->

                val selectedDate = "$selectedDay/${selectedMonth + 1 }/ $selectedYear"
                tvSelectedDate?.text = selectedDate
                tvAgeInMinutes?.text = calculateAgeInMinutes( selectedDate ).toString()
                tvAgeInDay?.text = calculateAgeInDay( selectedDate ).toString()
                tvAgeInYear?.text = calculateAgeInYear( selectedDate ).toString()

            }
            ,year
            ,month,
            day )

        dpd.datePicker.maxDate = System.currentTimeMillis()
        dpd.show()
    }

    private fun calculateAgeInMinutes(selectedDate : String ) : Long
    {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault() )
        val theDate = sdf.parse(selectedDate)
        val selectedDateInMin = theDate.time.div(60000 )
        val currentDateInMin = sdf.parse(sdf.format(System.currentTimeMillis())).time.div(60000 )
        return currentDateInMin - selectedDateInMin

    }

    private fun calculateAgeInDay ( selectedDate : String) : Long {
        return (calculateAgeInMinutes(selectedDate) / 60) / 24
    }

    private fun calculateAgeInYear ( selectedDate : String) : Int {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault() )
        val theDate = sdf.parse(selectedDate)
        return sdf.parse(sdf.format(System.currentTimeMillis())).year - theDate.year

    }
}