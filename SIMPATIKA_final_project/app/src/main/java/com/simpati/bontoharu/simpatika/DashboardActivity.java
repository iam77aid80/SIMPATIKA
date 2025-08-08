package com.simpati.bontoharu.simpatika;
import android.content.Intent; import android.os.Bundle; import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
public class DashboardActivity extends AppCompatActivity {
  private Button btnPengajuan, btnPagade, btnLogout;
  @Override protected void onCreate(Bundle s){ super.onCreate(s); setContentView(R.layout.activity_dashboard);
    btnPengajuan=findViewById(R.id.btnPengajuan); btnPagade=findViewById(R.id.btnPagade); btnLogout=findViewById(R.id.btnLogout);
    btnPengajuan.setOnClickListener(v->startActivity(new Intent(this,PengajuanSuratActivity.class)));
    btnPagade.setOnClickListener(v->startActivity(new Intent(this,PagadeActivity.class)));
    btnLogout.setOnClickListener(v->{ com.google.firebase.auth.FirebaseAuth.getInstance().signOut(); startActivity(new Intent(this,LoginActivity.class)); finish(); });
  }
}
