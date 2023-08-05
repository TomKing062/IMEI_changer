package com.example.imeichanger

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private lateinit var editText1: EditText
    private lateinit var editText2: EditText
    private lateinit var button: Button
    private lateinit var outputTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editText1 = findViewById(R.id.editText1)
        editText2 = findViewById(R.id.editText2)
        button = findViewById(R.id.button)
        outputTextView = findViewById(R.id.outputTextView)

        button.setOnClickListener {
            executeCommand()
        }
    }

    private fun executeCommand() {
        val input1 = editText1.text.toString().trim()
        val input2 = editText2.text.toString().trim()

        if (input1.isEmpty() && input2.isEmpty()) {
            outputTextView.text = "至少输入一个IMEI"
            return
        }

        if (input1.isNotEmpty()) {
            val command1 = "su -c echo -e 'AT+SPIMEI=0,\"$input1\"\\r' >/dev/stty_nr0"
            Runtime.getRuntime().exec(command1)
            outputTextView.text = command1
        }

        if (input2.isNotEmpty()) {
            if (input1.isNotEmpty()) {
                Thread.sleep(2000)
            }
            val command2 = "su -c echo -e 'AT+SPIMEI=1,\"$input2\"\\r' >/dev/stty_nr0"
            Runtime.getRuntime().exec(command2)
            outputTextView.text = command2
        }

        try {
            Toast.makeText(this, "reboot in 10 seconds", Toast.LENGTH_SHORT).show();
            Thread.sleep(10000)
            val command3 = "su -c reboot"
            Runtime.getRuntime().exec(command3)
        } catch (e: IOException) {
            e.printStackTrace();
        }
    }
}
