package yapp.co.kr.toycalendar.util.login.facebook

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import com.facebook.GraphRequest
import com.facebook.login.LoginResult
import org.json.JSONObject
import java.net.MalformedURLException
import java.net.URL


fun parseJSONObject(loginResult: LoginResult) {
    val request =
            GraphRequest.newMeRequest(loginResult.accessToken) { jsonObject, response ->
                // Getting FB User Data and checking for null
                val facebookData = parseJSONObject(jsonObject)!!
                var email = ""
                var firstName = ""
                var lateName = ""

                email =
                        if (facebookData.getString("email") != null && !TextUtils.isEmpty(
                                        facebookData.getString("email")))
                            facebookData.getString("email")
                        else
                            ""

                firstName =
                        if (facebookData.getString("first_name") != null && !TextUtils.isEmpty(
                                        facebookData.getString("first_name")))
                            facebookData.getString("first_name") + " "
                        else
                            ""

                lateName =
                        if (facebookData.getString("last_name") != null && !TextUtils.isEmpty(
                                        facebookData.getString("last_name")))
                            facebookData.getString("last_name")
                        else
                            ""

                Log.d("Facebook data : ", "$email $firstName $lateName")
            }

    val parameters = Bundle()
    parameters.putString("fields", "id,first_name,last_name,email,gender")
    request.parameters = parameters

    request.executeAsync()
}

fun parseJSONObject(`object`: JSONObject): Bundle? {
    val bundle = Bundle()

    try {
        val id = `object`.getString("id")
        val profilePic: URL
        try {
            profilePic = URL("https://graph.facebook.com/$id/picture?type=large")
            Log.i("profile_pic", profilePic.toString())
            bundle.putString("profile_pic", profilePic.toString())
        } catch (e: MalformedURLException) {
            e.printStackTrace()
            return null
        }

        bundle.putString("idFacebook", id)
        if (`object`.has("first_name"))
            bundle.putString("first_name", `object`.getString("first_name"))
        if (`object`.has("last_name"))
            bundle.putString("last_name", `object`.getString("last_name"))
        if (`object`.has("email"))
            bundle.putString("email", `object`.getString("email"))
        if (`object`.has("gender"))
            bundle.putString("gender", `object`.getString("gender"))


    } catch (e: Exception) {
        Log.d("TAG", "BUNDLE Exception : " + e.toString())
    }

    return bundle
}