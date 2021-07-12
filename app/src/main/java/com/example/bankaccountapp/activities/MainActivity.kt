package com.example.bankaccountapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import com.example.bankaccountapp.R

/*
- criar classe MenuItem com os atributos: (texto : String, imagem: Int ou Imageview)
- criar classe MenuItemAdapter
- o fragment menu irá adicionar os menus num array<MenuItem> e utilizará esse array no adapter
*/



class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}



