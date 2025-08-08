package com.simpati.bontoharu.simpatika;
import android.os.Bundle; import android.widget.ArrayAdapter; import android.widget.Button; import android.widget.EditText; import android.widget.Spinner; import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;
import java.text.SimpleDateFormat; import java.util.ArrayList; import java.util.Date; import java.util.HashMap; import java.util.Locale; import java.util.Map;
public class PengajuanSuratActivity extends AppCompatActivity {
  private EditText etNama, etNIK; private Spinner spinnerJenisSurat; private Button btnKirim; private FirebaseFirestore db; private ArrayList<String> listJenis = new ArrayList<>(); private ArrayAdapter<String> adapter;
  @Override protected void onCreate(Bundle s){ super.onCreate(s); setContentView(R.layout.activity_pengajuan_surat); db=FirebaseFirestore.getInstance();
    etNama=findViewById(R.id.etNama); etNIK=findViewById(R.id.etNIK); spinnerJenisSurat=findViewById(R.id.spinnerJenisSurat); btnKirim=findViewById(R.id.btnKirim);
    adapter=new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,listJenis); adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); spinnerJenisSurat.setAdapter(adapter); loadJenisSurat();
    btnKirim.setOnClickListener(v->simpanPengajuan());
  }
  private void loadJenisSurat(){ db.collection("jenis_surat").get().addOnSuccessListener(q->{ listJenis.clear(); for(com.google.firebase.firestore.QueryDocumentSnapshot doc: q){ String nama=doc.getString("nama"); if(nama!=null) listJenis.add(nama);} if(listJenis.isEmpty()) listJenis.add("Tidak ada jenis surat"); adapter.notifyDataSetChanged(); }).addOnFailureListener(e->{ listJenis.clear(); listJenis.add("Gagal memuat"); adapter.notifyDataSetChanged(); Toast.makeText(this,"Gagal: "+e.getMessage(),Toast.LENGTH_LONG).show(); }); }
  private void simpanPengajuan(){ String nama=etNama.getText().toString().trim(), nik=etNIK.getText().toString().trim(), jenis=(String)spinnerJenisSurat.getSelectedItem(); if(nama.isEmpty()||nik.isEmpty()||jenis==null||jenis.startsWith("Tidak")){ Toast.makeText(this,"Harap isi semua data",Toast.LENGTH_SHORT).show(); return; } Map<String,Object> data=new HashMap<>(); data.put("nama_pemohon",nama); data.put("nik",nik); data.put("jenis_surat",jenis); data.put("tanggal", new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date())); data.put("status","Menunggu Verifikasi"); db.collection("pengajuan_surat").add(data).addOnSuccessListener(r->{ Toast.makeText(this,"Pengajuan terkirim",Toast.LENGTH_LONG).show(); etNama.setText(""); etNIK.setText(""); spinnerJenisSurat.setSelection(0); }).addOnFailureListener(e->{ Toast.makeText(this,"Gagal: "+e.getMessage(),Toast.LENGTH_LONG).show(); }); }
}
