package ViewInterfaces

import Presenter.Presenter
import Presenter.WelcomePresenter
import Presenter.LoginPresenter

/**
 * Created by gzano on 26/09/2017.
 */
interface BaseView<in T : Presenter> {
    fun setPresenter(presenter: T)

    interface LoginView : BaseView<LoginPresenter>{

        fun onLoginPressed()

        fun onSignUpPressed()

    }

    interface WelcomeView:BaseView<WelcomePresenter>{
        fun onGoToAppPressed()

        fun onFailedLogin()

        fun onSuccessfulLogin()
    }
}
