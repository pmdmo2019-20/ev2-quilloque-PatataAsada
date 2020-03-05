package es.iessaladillo.pedrojoya.quilloque

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import es.iessaladillo.pedrojoya.quilloque.data.Consultas
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity() {
    private val navCtrl: NavController by lazy {
        findNavController(R.id.navHostFragment)
    }

    val viewmodel: MainViewModel by viewModels {
        MainViewModelFactory(application, Consultas.getInstance(this))
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.main_activity)
        setSupportActionBar(toolbar)
        setupBottomNavDrawer()
    }

    private fun setupBottomNavDrawer() {
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.mnuDial -> {
                    navigateToDial(); true
                }
                R.id.mnuRecent -> {
                    navigateToRecent(); true
                }
                else -> {
                    navigateToContacts(); true
                }
            }
        }
    }

    private fun navigateToRecent() {
        navCtrl.navigate(R.id.recentFragment)
    }

    private fun navigateToContacts() {
        navCtrl.navigate(R.id.contactsFragment)
    }

    private fun navigateToDial() {
        navCtrl.navigate(R.id.dialFragment)
    }

}
