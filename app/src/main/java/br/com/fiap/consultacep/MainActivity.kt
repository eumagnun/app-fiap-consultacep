package br.com.fiap.consultacep

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import br.com.fiap.consultacep.databinding.ActivityMainBinding
import br.com.fiap.consultacep.model.Logradouro
import br.com.fiap.consultacep.network.CepApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var logradouroConsultado: Logradouro? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btPesquisar.setOnClickListener {
            consultar(binding.etFiltroPokemon.text.toString())
            fecharTeclado(this,binding.etFiltroPokemon)
        }

    }

    fun consultar(cep:String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                 logradouroConsultado = CepApi.retrofitService.getLogradouro(cep)

            } catch (e: Exception) {
                e.printStackTrace()
            }

            withContext(Dispatchers.Main) {
                updateUI()
            }
        }
    }

    private fun updateUI(){
        if (logradouroConsultado==null){
            binding.tvStatus.text = "Não encontrado"
        }else {
            binding.tvStatus.text = ""
            binding.tvCep.text = logradouroConsultado?.cep.toString()
            binding.tvLogradouro.text = logradouroConsultado?.logradouro.toString()
            binding.tvBairro.text = logradouroConsultado?.bairro.toString()
        }
    }

    private fun fecharTeclado(context: Context, v: View) {
        val iMm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        iMm.hideSoftInputFromWindow(v.windowToken, 0)
        v.clearFocus()
    }
}