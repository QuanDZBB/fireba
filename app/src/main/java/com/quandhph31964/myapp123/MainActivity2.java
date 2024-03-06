package com.quandhph31964.myapp123;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.UUID;

public class MainActivity2 extends AppCompatActivity {
    TextView tvKQ;
    FirebaseFirestore database;
    Context context = this;
    String strKQ = "";
    ToDo toDo = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        tvKQ = findViewById(R.id.txKQ);
        database = FirebaseFirestore.getInstance();//khởi tạo
        insert();

    }

    void insert() {
        String id = UUID.randomUUID().toString();
        toDo = new ToDo(id, "title 11", "content 11");
        database.collection("TODO")//truy cập đến bảng dữ liệu
                .document(id)//truy cập đến dòng dữ liệu
                .set((toDo.convertToHashMap()))//đưa dữ liệu vào dòng
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context,"insert thành công",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    Toast.makeText(context,"insert thất bại",Toast.LENGTH_SHORT).show();
                    }
                });
    }
    void update(){
        String id="";
        toDo=new ToDo(id,"title 11 update","content 11 update");
        database.collection("TODO").document(id).update((toDo.convertToHashMap()))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context,"update thành công",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context,"Update thất bại",Toast.LENGTH_SHORT).show();
                    }
                });
    }
    void delete(){
        String id="";
        database.collection("TODO").document(id).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context,"xóa thành công",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context,"xóa thất bại",Toast.LENGTH_SHORT).show();
                    }
                });
    }
    ArrayList<ToDo> select(){
        ArrayList<ToDo> list=new ArrayList<>();
        database.collection("TODO").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    strKQ="";
                    for (QueryDocumentSnapshot doc :task.getResult()){
                        ToDo t=doc.toObject(ToDo.class);
                        list.add(t);
                        strKQ+="id"+t.getId()+"\n";
                        strKQ+="title"+t.getTitle()+"\n";
                        strKQ+="content"+t.getContent()+"\n";
                    }
                    tvKQ.setText(strKQ);
                }
                else {
                    Toast.makeText(context,"thất bại",Toast.LENGTH_SHORT).show();
                }
            }
        });
        return list;
    }
}