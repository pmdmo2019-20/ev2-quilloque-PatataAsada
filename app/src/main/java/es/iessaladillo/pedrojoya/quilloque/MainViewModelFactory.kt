package es.iessaladillo.pedrojoya.quilloque

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import es.iessaladillo.pedrojoya.quilloque.data.Consultas

class MainViewModelFactory(val application: Application, val db: Consultas) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(application, db) as T
        }
        throw IllegalArgumentException("Must provide MainViewmodel class")
    }

}
