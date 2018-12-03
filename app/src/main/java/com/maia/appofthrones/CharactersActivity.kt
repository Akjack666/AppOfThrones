package com.maia.appofthrones

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.activity_characters.*



class CharactersActivity: AppCompatActivity(), CharactersFragment.onItemClickListener{




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_characters)



        if(savedInstanceState == null){



        val fragment = CharactersFragment()
        this.supportFragmentManager
                .beginTransaction()
                .add(R.id.listContainer,fragment)
                .commit()

        }
    }

    override fun onItemClicked(character: Character) {

        if(isDetalViewAvailable())
            showFragmentDeatails(character.id)
            else
            launchDetailActivity(character.id)


    }

    private fun showFragmentDeatails(characterId: String) {

       val detalFragment = DetalFragment.newInstance(characterId)
        /*      val args = Bundle()
             args.putString("key_id", characterId)

             detalFragment.arguments = args
         */



        //Con esto mostramos en pantalla

        supportFragmentManager.beginTransaction()
                .replace(R.id.detalContainer, detalFragment)
                .commit()
    }

   private fun isDetalViewAvailable() = detalContainer != null

    fun launchDetailActivity(characterId: String) {

        val intent: Intent = Intent(this,DetailActivity::class.java)
        intent.putExtra("key_id", characterId)
        startActivity(intent)
    }






}