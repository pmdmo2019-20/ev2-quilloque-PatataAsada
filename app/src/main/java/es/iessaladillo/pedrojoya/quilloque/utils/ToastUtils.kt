package es.iessaladillo.pedrojoya.quilloque.utils

import android.content.Context
import android.widget.Toast

fun makeToast(message:String,context: Context){
    Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
}