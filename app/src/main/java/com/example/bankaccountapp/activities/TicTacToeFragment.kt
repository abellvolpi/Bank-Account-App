package com.example.bankaccountapp.activities

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.view.forEach
import com.example.bankaccountapp.R
import com.example.bankaccountapp.databinding.FragmentTicTacToeBinding
import java.text.FieldPosition


class TicTacToeFragment : Fragment(R.layout.fragment_tic_tac_toe) {


    private lateinit var binding: FragmentTicTacToeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentTicTacToeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            var boardlist = ArrayList<Button>()
            boardlist = arrayListOf(btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9)











        }
    }

    fun freeSpace(button: Button): Boolean {
        if (button.text.isEmpty()) {
            return true
        }
        return false
    }

    fun newMove(player: String, button: Button) {
        if (freeSpace(button)) {
            button.text = player
        }
    }


}


//            tr1.forEach {
//                it.setOnClickListener {
//                    when (it.id) {
//                        btn1.id -> Toast.makeText(requireContext(), "btn1", Toast.LENGTH_SHORT).show()
//                        btn2.id -> Toast.makeText(requireContext(), "btn2", Toast.LENGTH_SHORT).show()
//                        btn3.id -> Toast.makeText(requireContext(), "btn3", Toast.LENGTH_SHORT).show()
//                    }
//                }
//            }
//            tr2.forEach {
//                it.setOnClickListener {
//                    when (it.id) {
//                        btn4.id -> Toast.makeText(requireContext(), "btn4", Toast.LENGTH_SHORT).show()
//                        btn5.id -> Toast.makeText(requireContext(), "btn5", Toast.LENGTH_SHORT).show()
//                        btn6.id -> Toast.makeText(requireContext(), "btn6", Toast.LENGTH_SHORT).show()
//                    }
//                }
//            }
//            tr3.forEach {
//                it.setOnClickListener {
//                    when (it.id) {
//                        btn7.id -> Toast.makeText(requireContext(), "btn7", Toast.LENGTH_SHORT).show()
//                        btn8.id -> Toast.makeText(requireContext(), "btn8", Toast.LENGTH_SHORT).show()
//                        btn9.id -> Toast.makeText(requireContext(), "btn9", Toast.LENGTH_SHORT).show()
//                    }
//                }
//            }



//fun checkWin(): Boolean {
//
//    if (btn1.text == btn2.text && btn2.text == btn3.text && btn1.text.isNotEmpty()) { //primeira linha
//        return true
//    } else if (btn4.text == btn5.text && btn5.text == btn6.text && btn4.text.isNotEmpty()) { //segunda linha
//        return true
//    } else if (btn7.text == btn8.text && btn8.text == btn9.text && btn7.text.isNotEmpty()) { //terceira linha
//        return true
//    } else if (btn1.text == btn4.text && btn4.text == btn7.text && btn1.text.isNotEmpty()) { //primeira coluna
//        return true
//    } else if (btn2.text == btn5.text && btn5.text == btn8.text && btn2.text.isNotEmpty()) { //segunda coluna
//        return true
//    } else if (btn3.text == btn6.text && btn6.text == btn9.text && btn3.text.isNotEmpty()) { //terceira coluna
//        return true
//    }else if (btn1.text == btn5.text && btn5.text == btn9.text && btn1.text.isNotEmpty()) { //primeira diagonal
//        return true
//    } else if (btn3.text == btn5.text && btn5.text == btn7.text && btn3.text.isNotEmpty()) { //segunda diagonal
//        return true
//    }
//    return false
//}
//
//if(checkWin()==false){
//    Toast.makeText(requireContext(),"teste",Toast.LENGTH_SHORT)
//
//}
//else{
//}