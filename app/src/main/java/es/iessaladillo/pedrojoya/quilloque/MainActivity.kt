package es.iessaladillo.pedrojoya.quilloque

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import es.iessaladillo.pedrojoya.quilloque.data.Consultas
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity() {

    val viewmodel: MainViewModel by viewModels {
        MainViewModelFactory(application, Consultas.getInstance(this))
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.main_activity)
        setupToolbar()
        setupBottomNavDrawer()
    }

    private fun setupToolbar() {

    }

    private fun setupBottomNavDrawer() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
