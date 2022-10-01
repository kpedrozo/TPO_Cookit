package com.example.cookit

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class Login : AppCompatActivity() {


    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var btnLogin : SignInButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        val gsiOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gsiOptions)

        // iniciar firebase auth
        firebaseAuth = FirebaseAuth.getInstance()


        btnLogin = findViewById(R.id.btnGoogleSignIn)
        // Click para comenzar el Google SignIn
        btnLogin.setOnClickListener {
            val intent = googleSignInClient.signInIntent
            startActivityForResult(intent, 100)
        }
        checkUser()
    }

    private fun checkUser() {
        // check si el usuario esta logeado
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null ) {
            // Si el usuario esta logeado pasamos a Home
            startActivity(Intent(this@Login, Home::class.java))
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 ){
            val accountTask = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // SignIn Google OK -> intenta authenticar firebase
                var account = accountTask.getResult(ApiException::class.java)
                firebaseAuthWithGoogleAccount(account)
            }
            catch (e : Exception) {
                // SignIn Google fallo
                Log.d("Login", "onActivityResult: ${e.message}")
            }
        }
    }

    private fun firebaseAuthWithGoogleAccount (account : GoogleSignInAccount?) {
        Log.d("Login", "firebaseAuthWithGoogleAccount: comienza auth firebase con Google")
        val credential = GoogleAuthProvider.getCredential(account!!.idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnSuccessListener { authResult ->
                // get usuario loggeado
                var firebaseUser = firebaseAuth.currentUser
                // get user info
                var uid = firebaseUser!!.uid
                var email = firebaseUser!!.email

                // check si el usuario es nuevo o existente
                if ( authResult.additionalUserInfo!!.isNewUser ) {
                    // user NEW -> crea cuenta
                    Toast.makeText(this@Login, "Creando cuenta...", Toast.LENGTH_LONG).show()
                } else {
                    // user existente -> Logeado
                    Toast.makeText(this@Login, "Cuenta existente...", Toast.LENGTH_LONG).show()
                }
                // pasamos a pantalla Home
                startActivity(Intent(this@Login, Home::class.java))
                finish()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this@Login, "Fallo en login", Toast.LENGTH_LONG).show()
            }
    }




}