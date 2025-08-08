package com.simpati.bontoharu.simpatika;
import android.content.Intent; import android.os.Bundle; import android.widget.Button; import android.widget.EditText; import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth; import com.google.firebase.auth.FirebaseUser;
public class LoginActivity extends AppCompatActivity {
  private EditText etEmail, etPassword; private Button btnLogin, btnRegister; private FirebaseAuth mAuth;
  @Override protected void onCreate(Bundle s){ super.onCreate(s); setContentView(R.layout.activity_login);
    mAuth = FirebaseAuth.getInstance();
    etEmail = findViewById(R.id.etEmail); etPassword = findViewById(R.id.etPassword);
    btnLogin = findViewById(R.id.btnLogin); btnRegister = findViewById(R.id.btnRegister);
    btnLogin.setOnClickListener(v->{
      String e=etEmail.getText().toString().trim(), p=etPassword.getText().toString().trim();
      if(e.isEmpty()||p.isEmpty()){ Toast.makeText(this,"Isi email & password",Toast.LENGTH_SHORT).show(); return; }
      mAuth.signInWithEmailAndPassword(e,p).addOnCompleteListener(task->{ if(task.isSuccessful()){ startActivity(new Intent(this,DashboardActivity.class)); finish(); } else Toast.makeText(this,"Login gagal: "+task.getException().getMessage(),Toast.LENGTH_LONG).show(); });
    });
    btnRegister.setOnClickListener(v->{
      String e=etEmail.getText().toString().trim(), p=etPassword.getText().toString().trim();
      if(e.isEmpty()||p.isEmpty()){ Toast.makeText(this,"Isi email & password",Toast.LENGTH_SHORT).show(); return; }
      mAuth.createUserWithEmailAndPassword(e,p).addOnCompleteListener(task->{ if(task.isSuccessful()) Toast.makeText(this,"Registrasi berhasil. Silakan login.",Toast.LENGTH_LONG).show(); else Toast.makeText(this,"Registrasi gagal: "+task.getException().getMessage(),Toast.LENGTH_LONG).show(); });
    });
  }
}
