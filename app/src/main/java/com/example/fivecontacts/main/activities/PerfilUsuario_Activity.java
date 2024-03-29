package com.example.fivecontacts.main.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.fivecontacts.R;
import com.example.fivecontacts.main.model.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class PerfilUsuario_Activity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    EditText edUser;
    EditText edPass;
    EditText edNome;
    EditText edEmail;
    Switch swLogado;

    Button btModificar;
    BottomNavigationView bnv;

    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_usuario);

        btModificar = findViewById(R.id.btCriar);
        bnv = findViewById(R.id.bnv);
        bnv.setOnNavigationItemSelectedListener(this);
        bnv.setSelectedItemId(R.id.anvPerfil);

        edUser = findViewById(R.id.edT_Login2);
        edPass = findViewById(R.id.edt_Pass2);
        edNome = findViewById(R.id.edtNome);
        edEmail = findViewById(R.id.edEmail);
        swLogado = findViewById(R.id.swLogado);

        // Recuperando dados da Intent anterior
        Intent quemChamou = this.getIntent();
        if (quemChamou != null) {
            Bundle params = quemChamou.getExtras();
            if (params != null) {
                // Recuperando o Usuario
                user = (User) params.getSerializable("usuario");
                setTitle("Alterar dados de "+user.getNome());

            }
        }

        if (user != null) {
           edUser.setText(user.getLogin());
           edPass.setText(user.getSenha());
           edNome.setText(user.getNome());
           edEmail.setText(user.getEmail());
           swLogado.setChecked(user.isManterLogado());
        }

        btModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            user.setNome(edNome.getText().toString());
            user.setLogin(edUser.getText().toString());
            user.setSenha(edPass.getText().toString());
            user.setEmail(edEmail.getText().toString());
            user.setManterLogado(swLogado.isChecked());
            salvarModificacoes(user);
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Checagem de o Item selecionado é a de mudanças de contatos
        if (item.getItemId() == R.id.anvAdicionar) {
            //Abertura da Tela de Perfil
            Intent intent = new Intent(this, AlterarContatos_Activity.class);
            intent.putExtra("usuario", user);
            startActivity(intent);
        }

        // Checagem de o Item selecionado é menu de Ligar
        if (item.getItemId() == R.id.anvLigar) {
            //Abertura da Tela Contatos Salvos
            Intent intent = new Intent(this, ListaDeContatos_Activity.class);
            intent.putExtra("usuario", user);
            startActivity(intent);
        }
        return true;
    }

    public void salvarModificacoes(User user){
        SharedPreferences salvaUser = getSharedPreferences("usuarioPadrao", Activity.MODE_PRIVATE);
        SharedPreferences.Editor escritor = salvaUser.edit();

        // Escrever novas informações no SharedPreferences
        escritor.putString("nome",user.getNome());
        escritor.putString("senha",user.getSenha());
        escritor.putString("login",user.getLogin());
        escritor.putString("email",user.getEmail());
        escritor.putBoolean("manterLogado",user.isManterLogado());

        escritor.commit(); // Salva em Disco

        Toast.makeText(PerfilUsuario_Activity.this,"Modificações feitas com sucesso!",Toast.LENGTH_LONG).show() ;

        finish();
    }
}