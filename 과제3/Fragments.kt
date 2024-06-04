package com.example.week9

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment

class MyDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        //return super.onCreateDialog(savedInstanceState)
        isCancelable = false
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle("2071321")
        builder.setMessage("최승재")
        builder.setPositiveButton("OK") {dialog, id-> println("OK")}
        return builder.create()

        /*return AlertDialog.Builder(requireActivity()).apply {
            setMessage("Dialog Message")
            setPositiveButton("OK") {dialog, id-> println("OK")}
        }.create()*/
    }


}
class Home : Fragment(R.layout.fragment_layout) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.textView).text = "Home"
    }
}

class Page1 : Fragment(R.layout.fragment_layout) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.textView).text = "Page1Fragment"
        view.findViewById<TextView>(R.id.textView2).text = "2071321"

    }
}

class Page2 : Fragment(R.layout.fragment_layout) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.textView).text = "Page2Fragment"
        view.findViewById<TextView>(R.id.textView2).text = "최승재"
    }
}