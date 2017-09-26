package Presenter

import ViewInterfaces.BaseView

/**
 * Created by gzano on 26/09/2017.
 */
class LoginPresenter(private var loginView: BaseView.LoginView) : Presenter {


    override fun onCreate() {
      loginView.onLoginPressed();
    }
}