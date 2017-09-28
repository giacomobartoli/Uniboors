package Model

import org.jetbrains.annotations.NotNull

/**
 * Created by gzano on 26/09/2017.
 */
class User(@NotNull private var email: String) {
    override fun toString(): String = email.substringBefore('@')
}