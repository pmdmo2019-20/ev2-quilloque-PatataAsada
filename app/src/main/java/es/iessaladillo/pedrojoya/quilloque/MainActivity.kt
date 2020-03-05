package es.iessaladillo.pedrojoya.quilloque

import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import androidx.navigation.NavController
import androidx.navigation.findNavController
import es.iessaladillo.pedrojoya.quilloque.data.Consultas
import es.iessaladillo.pedrojoya.quilloque.ui.dial.DialFragment
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
        setFragment()
        setSupportActionBar(toolbar)
        setupBottomNavDrawer()
        setupObservers()
    }

    private fun setFragment() {
        navCtrl.navigate(R.id.dialFragment)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun setupObservers() {
        viewmodel.myPositionInNavigation.observe(this) {
            updateToolbar()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.mnuAddContact -> {
                navigateToCreateContact(); true
            }
            else -> true
        }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    private fun updateToolbar() {
        toolbar.title = viewmodel.myPositionInNavigation.value
        when (viewmodel.myPositionInNavigation.value) {
            getString(R.string.contact_creation_title) -> {
                toolbar.menu.clear()
                supportActionBar!!.setDisplayShowHomeEnabled(true)
                supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            }
            getString(R.string.contacts_title) -> {
                onCreateOptionsMenu(toolbar.menu)
            }
            else -> {
                supportActionBar!!.setHomeButtonEnabled(false)
                toolbar.menu.clear()
            }
        }
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
        viewmodel.changeToolbarTitle(getString(R.string.recent_title))
    }

    private fun navigateToContacts() {
        navCtrl.navigate(R.id.contactsFragment)
        viewmodel.changeToolbarTitle(getString(R.string.contacts_title))
    }

    private fun navigateToDial() {
        navCtrl.navigate(R.id.dialFragment)
        viewmodel.changeToolbarTitle(getString(R.string.dial_title))
    }

    private fun navigateToCreateContact() {
        navCtrl.navigate(R.id.action_contactsFragment_to_contactCreationFragment)
        viewmodel.changeToolbarTitle(getString(R.string.contact_creation_title))
    }

}
