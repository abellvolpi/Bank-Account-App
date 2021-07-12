package com.example.bankaccountapp.models

import android.widget.ImageView

class MenuItem {

    var menuIcons : Int? =0
    var menuText: String? = null

    constructor(menuIcons: Int?, menuText: String?) {
        this.menuIcons = menuIcons
        this.menuText = menuText
    }
}