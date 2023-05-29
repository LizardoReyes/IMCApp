package com.lizardoreyes.imcapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.lizardoreyes.imcapp.databinding.ActivityMainBinding
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var isMaleSelected: Boolean = true
    private var isFemaleSelected: Boolean = false
    private var currentWeight: Int = 62
    private var currentHeight: Float = 120f
    private var currentAge : Int = 26

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListeners()
    }

    private fun initListeners() = with(binding) {
        cardFemale.setOnClickListener { setGenderColor(Gender.FEMALE) }
        cardMale.setOnClickListener { setGenderColor(Gender.MALE) }
        rsHeight.addOnChangeListener { _, value, _ -> listenerOnChangeHeight(value) }
        btnAddWeight.setOnClickListener { updateCurrentWeight(1) }
        btnSubtractWeight.setOnClickListener { updateCurrentWeight(-1) }
        btnAddAge.setOnClickListener { updateCurrentAge(1) }
        btnSubtractAge.setOnClickListener { updateCurrentAge(-1) }
        btnCalculate.setOnClickListener { calculateIMC() }
    }

    private fun calculateIMC() {
        val imc = currentWeight / (currentHeight * currentHeight) * 10000
        val imcResult = when {
            imc < 18.5 -> getString(R.string.imc_underweight)
            imc < 24.9 -> getString(R.string.imc_normal)
            imc < 29.9 -> getString(R.string.imc_overweight)
            else -> getString(R.string.imc_obesity)
        }

        Toast.makeText(this, getString(R.string.result_imc, imc, imcResult), Toast.LENGTH_SHORT).show()
    }

    private fun updateCurrentAge(change: Int) {
        if (change > 0)
            currentAge += change
        else if (currentAge in 1..120)
            currentAge += change
        binding.tvAge.text = currentAge.toString()
    }

    private fun updateCurrentWeight(change: Int) {
        if (change > 0)
            currentWeight += change
        else if (currentWeight in 1..300)
            currentWeight += change
        binding.tvWeight.text = currentWeight.toString()
    }

    private fun listenerOnChangeHeight(value: Float) = with(binding) {
        currentHeight = value
        val df = DecimalFormat("#.##")
        val result = df.format(currentHeight)
        tvHeight.text = getString(R.string.result_height, result)
    }

    private fun setGenderColor(gender: Gender) = with(binding) {
        val cardFemaleBackground: Int
        val cardMaleBackground: Int

        when(gender) {
            Gender.MALE -> {
                cardMaleBackground = R.color.colorPrimaryDark
                cardFemaleBackground = R.color.colorPrimary
                isMaleSelected = true
                isFemaleSelected = false
            }
            Gender.FEMALE -> {
                cardFemaleBackground = R.color.colorPrimaryDark
                cardMaleBackground = R.color.colorPrimary
                isMaleSelected = false
                isFemaleSelected = true
            }
        }

        changeGenderColor(cardFemale, cardFemaleBackground)
        changeGenderColor(cardMale, cardMaleBackground)
    }

    private fun changeGenderColor(card: CardView, cardBackground: Int) {
        card.setCardBackgroundColor(ContextCompat.getColor(this@MainActivity, cardBackground))
    }
}