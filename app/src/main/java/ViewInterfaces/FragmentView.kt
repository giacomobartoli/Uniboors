package ViewInterfaces

import android.support.v4.app.Fragment

/**
 * Created by gzano on 26/09/2017.
 */
interface FragmentView {


    interface LoginFragmentView : FragmentView {
        fun setButtonListener()

        fun replaceFragment(fragment: Fragment)

        fun informUser(message: String)

        fun showProgressBar(message: String)

        fun hideProgressBar()
    }

    interface WelcomeFragmentView : FragmentView {

        fun updateWelcomeMessage(message:String)

        fun backToSignIn()

        fun onGoToAppPressed()


    }
}
