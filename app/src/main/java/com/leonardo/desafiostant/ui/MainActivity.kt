package com.leonardo.desafiostant.ui


import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.leonardo.desafiostant.R
import com.leonardo.desafiostant.interfacesRetro.RetrofitService
import com.leonardo.desafiostant.model.Genre
import com.leonardo.desafiostant.model.Movie
import com.leonardo.desafiostant.repositories.MainRepository
import com.leonardo.desafiostant.ui.adapter.MovieAdapter
import com.leonardo.desafiostant.viewmodel.MainViewModelFactory
import com.leonardo.desafiostant.viewmodel.MianViewModel
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    var page : Int = 1
    var list : MutableList<Movie> = mutableListOf()
    var listGenre : MutableList<Genre> = mutableListOf()
    var adapter = MovieAdapter{}
    lateinit var  recyclerView : RecyclerView
    lateinit var viewModel : MianViewModel
    lateinit var  layoutManager : GridLayoutManager
    private val retrofitService = RetrofitService.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this@MainActivity, MainViewModelFactory(MainRepository(retrofitService)))[MianViewModel::class.java] //ViewModel

        recyclerView = findViewById(R.id.recycler_vie_movie_list)
        layoutManager = GridLayoutManager(applicationContext, 3, GridLayoutManager.VERTICAL, false)

        val toolbarMain = findViewById<Toolbar>(R.id.toolbarMain)
        val searchView : EditText = findViewById(R.id.searchView)
        val btnSearch : ImageButton = findViewById(R.id.btnSearch)

        setSupportActionBar(toolbarMain) //Toolbae
        supportActionBar?.title = null //Remove o Titulo da Toolbar

        btnSearch.setOnClickListener { //Botão de busca da Toolbar
            searchView.isVisible = !searchView.isVisible  //Verifica se esta visivel o EditText de busca
            closeKeyboard() //Fecha o teclado
            searchView.text.clear() //Limpa o EditText
            adapter.setDataSet(list) //Atualiza a lista
        }


        searchView.addTextChangedListener(object  : TextWatcher {  //Listener do EditText
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(p0: Editable?) {
                filter(p0.toString()) //Função que filtra a busca e atualiza a RecycleView

            }
        })




        try {
            adapter = MovieAdapter{ //Envia os dados para a activity de Details
                it.let {
                    intent = Intent(applicationContext, DetailsActivity::class.java)
                    intent.putExtra("posterPatchMovie", it.poster_path)
                    intent.putExtra("nameMovie", it.title)
                    intent.putExtra("dateMovie", it.release_date)
                    intent.putExtra("idMovie", it.id.toString())
                    intent.putExtra("overviewMovie", it.overview)
                    intent.putExtra("languageMovie", it.original_language)

                    for (g in listGenre){
                        if (it.genre_ids!!.contains(g.id)){
                            intent.putExtra("genresMovie", g.name)
                        }
                    }

                    startActivity(intent)
                }
           }
        } catch (e: Exception) {
            Log.d("MyDebug", e.toString())
        }

    }


    override fun onResume() {
        super.onResume()
        val scope = MainScope()
        adapter.setDataSet(list)
        scope.launch {
            //Faz a chamada na API
            viewModel.getAllMovies(page)
            viewModel.getAllGenre()
        }

        val searchView : EditText = findViewById(R.id.searchView)
        searchView.isVisible = false
        searchView.text.clear()
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){  //Evento de Scroll
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy >0){
                    val visibleItemCount: Int = layoutManager.childCount
                    val pastVisibleItem : Int = layoutManager.findFirstCompletelyVisibleItemPosition()
                    val total = adapter.itemCount

                    if (visibleItemCount + pastVisibleItem >= total){
                        if (page < 66){
                            page ++ //Muda a page da requisição de acordo com o Scroll
                        }

                        viewModel.getAllMovies(page)
                        adapter.setDataSet(list)
                        recyclerView.post {
                            adapter.notifyDataSetChanged() // Notifica o adapter
                        }

                    }

                }

                super.onScrolled(recyclerView, dx, dy)
            }
        })

            println(viewModel.movieList)

        }



    override fun onStart() {
        super.onStart()
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        viewModel.movieList.observe(this, Observer {
            Log.d("MyDebug", "onCreate: $it")
            for (i in it.results){
                list.add(i)
                    }
            if (page < 66){
                page ++
            }
                adapter.setDataSet(list)
                progressBar.isVisible = false
                adapter.notifyDataSetChanged()

        })


        viewModel.errorMessage.observe(this, Observer {
            Log.d("Observer", "Error: $it")
        })

        viewModel.genreList.observe(this, Observer {
            Log.d("MyDebug", "genr: $it")
            adapter.setDataGenre(it.genres)
            for (i in it.genres){
                listGenre.add(i)
            }
        })


    }

    private fun filter(text: String) { // FAZ O FILTRO DO SERARCH VIEW
        val listaFiltrada: MutableList<Movie> =
            mutableListOf()   //nova lista que conterá os dados filtrados
        for (s in list) {
            if (s.title.uppercase().contains(text.uppercase())) {
                listaFiltrada.add(s)
            }
        }
        adapter.filterList(listaFiltrada) //Metodo do adapter que atualiza a lista
    }

    private fun closeKeyboard(){ //Fecha o teclado
        val view:View? = currentFocus
        view?.let {
            val imm: InputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }





}