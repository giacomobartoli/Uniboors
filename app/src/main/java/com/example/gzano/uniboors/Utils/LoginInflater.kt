package com.example.gzano.uniboors.Utils

import Presenter.LoginPresenter
import com.example.gzano.uniboors.Fragments.LoginFragment
import com.example.gzano.uniboors.R
import com.example.gzano.uniboors.WelcomeActivity

/**
 * Created by gzano on 26/09/2017.
 */
class LoginInflater(private val activity: WelcomeActivity):FragmentInflater<LoginFragment>{
    override fun inflateFragment(fragment: LoginFragment) {

        activity.fragmentContainer.setOnClickListener({
            activity.supportFragmentManager.beginTransaction().add(R.id.fragment_container, fragment).commit()
            fragment.setPresenter(LoginPresenter(fragment))
            activity.fragmentContainer.isEnabled = false
        })

    }


}