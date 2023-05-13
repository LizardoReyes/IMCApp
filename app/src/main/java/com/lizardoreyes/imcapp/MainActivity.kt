package com.lizardoreyes.imcapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.lizardoreyes.imcapp.databinding.ActivityMainBinding
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListeners()
    }

    private fun initListeners() {
        binding.cardFemale.setOnClickListener { setGenderColor(Gender.FEMALE) }
        binding.cardMale.setOnClickListener { setGenderColor(Gender.MALE) }
        binding.rsHeight.addOnChangeListener { _, value, _ ->
            val df = DecimalFormat("#.##")
            val result = df.format(value)
            binding.tvHeight.text = getString(R.string.result_height, result)
        }
    }

    private fun setGenderColor(gender: Gender) = with(binding) {
        val cardFemaleBackground: Int
        val cardMaleBackground: Int

        when(gender) {
            Gender.MALE -> {
                cardMaleBackground = R.color.colorPrimaryDark
                cardFemaleBackground = R.color.colorPrimary
            }
            Gender.FEMALE -> {
                cardFemaleBackground = R.color.colorPrimaryDark
                cardMaleBackground = R.color.colorPrimary
            }
        }

        changeGenderColor(cardFemale, cardFemaleBackground)
        changeGenderColor(cardMale, cardMaleBackground)
    }

    private fun changeGenderColor(card: CardView, cardBackground: Int) {
        card.setCardBackgroundColor(ContextCompat.getColor(this@MainActivity, cardBackground))
    }
}