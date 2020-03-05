package es.iessaladillo.pedrojoya.quilloque

import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.lifecycle.observe
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        setSupportActionBar(toolbar)
        setupBottomNavDrawer()
        setupObservers()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun setupObservers() {
        viewmodel.myPositionInNavigation.observe(this) {
            updateToolbar(false)
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
        updateToolbar(true)
        super.onBackPressed()
    }

    override fun onSupportNavigateUp(): Boolean {
        toolbar.menu.getItem(0).isVisible = true
        onBackPressed()
        return true
    }


    private fun updateToolbar(backPressed: Boolean) {
        if (!backPressed) {
            toolbar.title = viewmodel.myPositionInNavigation.value
            when (viewmodel.myPositionInNavigation.value) {
                getString(R.string.contact_creation_title) -> {
                    toolbar.menu.getItem(0).isVisible = false
                    supportActionBar!!.setDisplayHomeAsUpEnabled(true)
                    supportActionBar!!.setDisplayShowHomeEnabled(true)
                }
                getString(R.string.contacts_title) -> {
                    onCreateOptionsMenu(toolbar.menu)
                    toolbar.menu.getItem(0).isVisible = true
                }
                else -> {
                    supportActionBar!!.setDisplayHomeAsUpEnabled(false)
                    supportActionBar!!.setDisplayShowHomeEnabled(false)
                    toolbar.menu.clear()
                }
            }
        } else {
            toolbar.title = viewmodel.arrayBackstackTitles[viewmodel.arrayBackstackTitles.size - 1]
            viewmodel.arrayBackstackTitles.removeAt(viewmodel.arrayBackstackTitles.size - 1)
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
                R.id.mnuContacts -> {
                    navigateToContacts(); true
                }
                else -> {
                    true
                }
            }
        }
    }

    private fun navigateToRecent() {
        navCtrl.navigate(R.id.recentFragment)
        viewmodel.arrayBackstackTitles.add(toolbar.title.toString())
        viewmodel.changeToolbarTitle(getString(R.string.recent_title))
    }

    private fun navigateToContacts() {
        navCtrl.navigate(R.id.contactsFragment)
        viewmodel.arrayBackstackTitles.add(toolbar.title.toString())
        viewmodel.changeToolbarTitle(getString(R.string.contacts_title))
    }

    private fun navigateToDial() {
        navCtrl.navigate(R.id.dialFragment)
        viewmodel.arrayBackstackTitles.add(toolbar.title.toString())
        viewmodel.changeToolbarTitle(getString(R.string.dial_title))
    }

    private fun navigateToCreateContact() {
        navCtrl.navigate(R.id.action_contactsFragment_to_contactCreationFragment)
        viewmodel.arrayBackstackTitles.add(toolbar.title.toString())
        viewmodel.changeToolbarTitle(getString(R.string.contact_creation_title))
    }
}
