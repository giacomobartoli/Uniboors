package ViewInterfaces

import Presenter.Presenter
import Presenter.WelcomePresenter
import Presenter.LoginPresenter
import android.text.Layout
import android.view.View

/**
 * Created by gzano on 26/09/2017.
 */
interface BaseView<in T : Presenter> {
    fun setPresenter(presenter: T)

    interface LoginView : BaseView<LoginPresenter>{
        fun setButtonListener()

        fun replaceFragment()



    }

    interface WelcomeView:BaseView<WelcomePresenter>{


        fun onGoToAppPressed()


    }
}
