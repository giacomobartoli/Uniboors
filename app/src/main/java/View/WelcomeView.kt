package View

import com.example.gzano.uniboors.WelcomeActivity
import com.google.firebase.auth.FirebaseUser

/**
 * Created by gzano on 26/09/2017.
 */
interface WelcomeView {

    fun updateView(user: FirebaseUser)

    fun onLoginPressed()

    fun getActivityContext():WelcomeActivity


}