package com.example.tippy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.SeekBar
import com.example.tippy.databinding.ActivityMainBinding


private const val INITIAL_TIP_PERCENT = 15
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.sbTipPercentageBar.progress = INITIAL_TIP_PERCENT
        binding.tvTipPercentage.text = "$INITIAL_TIP_PERCENT"


        binding.sbTipPercentageBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                Log.d("onProgressChanged", "$progress")
                binding.tvTipPercentage.text = progress.toString()
                computeTipAndTotal()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })

        binding.etBaseAmount.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                Log.d("afterTextChanged", "$s")


                computeTipAndTotal()
            }
        })

    }

    /* This code overrides the menu created, for the menu Items on the tippy menu bar to be visible upon appearance  */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.tippy_bar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.miSettings -> Setings()
            R.id.miAbout -> About()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun About() {
        Intent(this, AboutActivity::class.java).also {
            startActivity(it)
        }
    }

    private fun Setings() {
        Intent(this, SettingsActivity::class.java).also {
            startActivity(it)
        }

    }


    private fun computeTipAndTotal() {
        if(binding.etBaseAmount.text.isEmpty()) {
            binding.tvTip.text = ""
            binding.tvTotal.text = ""
            return
        }

        //1. Get the value of the tip and total
        val baseAmount = binding.etBaseAmount.text.toString().toDouble()
        val tipPercent = binding.sbTipPercentageBar.progress
        //2. Compute the tip and total
        val tipAmount = baseAmount * tipPercent / 100
        val totalAmount = baseAmount + tipAmount

        //3. Update the UI
        binding.tvTip.text = "%.2f".format(tipAmount)
        binding.tvTotal.text = "%.2f".format(totalAmount)
    }
}