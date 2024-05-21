package com.example.week10

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ItemDialog(private val itemPos: Int = -1) : BottomSheetDialogFragment(){
    private val viewModel by activityViewModels<MyViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.item_dialog_layout,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val editTextFirstName = view.findViewById<EditText>(R.id.editTextName)
        val  editTextLastName = view.findViewById<EditText>(R.id.editTextAddress)
        val buttonOk = view.findViewById<Button>(R.id.buttonOK)

        if (itemPos >= 0) {
            with (viewModel.items[itemPos]) {
                editTextFirstName.setText(firstName)
                editTextLastName.setText(lastName)
            }
        }
        buttonOk.setOnClickListener {
            val item = Item(
                editTextFirstName.text.toString(),editTextLastName.text.toString()
            )
            if (itemPos < 0) // 뷰모델에 데이터 추가
                viewModel.addItem(item)
            else // 뷰모델 데이터 변경
                viewModel.updateItem(itemPos, item)
            dismiss()
        }
    }
}