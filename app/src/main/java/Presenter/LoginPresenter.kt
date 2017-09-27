package Presenter

import Model.User
import ViewInterfaces.BaseView
import android.util.Log
import com.google.firebase.auth.FirebaseAuth

/**
 * Created by gzano on 26/09/2017.
 */
class LoginPresenter(private var loginView: BaseView.LoginView) : Presenter {


    val user:User?=null
    val mAuth:FirebaseAuth= FirebaseAuth.getInstance()

    override fun onCreate() {
        loginView.setButtonListener()
    }

    fun createUser(email:String,password:String){
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener { authenticationTask ->
            if(authenticationTask.isSuccessful){
                Log.d("TEST"," user created")
                loginView.replaceFragment()
            }
        }
    }


}