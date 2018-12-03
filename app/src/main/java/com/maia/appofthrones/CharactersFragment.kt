package com.maia.appofthrones

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.maia.appofthrones.CharactersRepo.characters
import kotlinx.android.synthetic.main.fragment_characters.*
import java.lang.IllegalArgumentException

class CharactersFragment: Fragment() {



    //Con !! le decimos a kotlin que lo de la posible vista nula , es cosa nuestra

    val adapter = CharactersAdapter {item, position ->

        clickListener.onItemClicked(item)


    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      //  return super.onCreateView(inflater, container, savedInstanceState)


      return inflater.inflate(R.layout.fragment_characters,container,false)




    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val characters: MutableList<Character> = CharactersRepo.characters

        val list: RecyclerView = view!!.findViewById(R.id.list)
        adapter.setCharaters(characters)

        list.layoutManager = LinearLayoutManager(context)
        list.adapter = adapter

        btnRetry.setOnClickListener {

            retry()
        }


    }

    private fun retry (){

        layoutError.visibility = View.INVISIBLE
        progressBar.visibility = View.VISIBLE
    }

    private fun requestCharacters() {

        CharactersRepo.requestCharacters(context!!,
                {characters ->

                    view?.let {

                    progressBar.visibility = View.INVISIBLE
                    list.visibility = View.VISIBLE
                    adapter.setCharaters(characters)
                    }
                },
                {
                    view?.let {


                    progressBar.visibility = View.INVISIBLE
                    layoutError.visibility = View.VISIBLE

                    }

                })
    }

    override fun onResume() {
        super.onResume()
        requestCharacters()
    }

   lateinit var clickListener: onItemClickListener

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if(context is onItemClickListener){
            clickListener = context
        }else{
            throw IllegalArgumentException("Attached activity doesn t implemented CharacterFragment onItemClickListener")

        }

    }




    interface onItemClickListener {

        fun onItemClicked(character: Character){




        }
    }
}