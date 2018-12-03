package com.maia.appofthrones

import android.app.DownloadManager
import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject
import java.security.AccessControlContext
import java.util.*

const val URL_CHARACTERS = "http://5bf17ea5a60fe600134cde97.mockapi.io/characters"

object CharactersRepo {

     var characters: MutableList<Character> = mutableListOf()



    private fun parseCharacters(jsonArray: JSONArray): MutableList<Character> {

        val characters = mutableListOf<Character>()

        for(index in 0..(jsonArray.length()-1)){

            val character = parseCharacter(jsonArray.getJSONObject(index))
            characters.add(character)
        }

        return characters


    }

    fun requestCharacters(context: Context, success: ((MutableList<Character>) -> Unit),
                          error: () -> Unit){

        if(characters.isEmpty()){



       val request =  JsonArrayRequest(Request.Method.GET, URL_CHARACTERS, null, {response ->



            characters = parseCharacters(response)
           success.invoke(characters)


        },
               {volleyError ->

                error.invoke()

        })

            Volley.newRequestQueue(context)
                    .add(request)

        }else {

            success.invoke(characters)
        }
    }


    private fun parseCharacter(jsonCharacter: JSONObject) : Character {

       return Character(jsonCharacter.getString("id"),
               jsonCharacter.getString("name"),
               jsonCharacter.getString("born"),
               jsonCharacter.getString("title"),
               jsonCharacter.getString("actor"),
               jsonCharacter.getString("quote"),
               jsonCharacter.getString("father"),
               jsonCharacter.getString("mother"),
               jsonCharacter.getString("spouse"),
               jsonCharacter.getString("img"),
               parseHouse(jsonCharacter.getJSONObject("house")))
    }

    private fun parseHouse(jsonHouse: JSONObject): House{

        return House(jsonHouse.getString("name"),
                jsonHouse.getString("region"),
                jsonHouse.getString("words"),
                jsonHouse.getString("img"))
    }

    /*
    private fun dummyCharacters(): MutableList<Character>{

        return (1..10).map {

             intToCharacter(it)

        }.toMutableList()



    }

    */

     fun findCharacterById(id: String): Character? {

       return characters.find {character ->

            character.id == id
        }


    }
/*
    private fun intToCharacter(index: Int): Character{

      return  Character(
                name = "Personaje ${index}",
                title = "Titulo ${index}",
                born =  "Nacio en ${index}",
                actor = "Actor ${index}",
                quote = "quoute ${index}",
                father = "Padre ${index}",
                mother = "Madre ${index}",
                spouse = "Espos@ ${index}",
                house = dummyHouse()

        )

    }

    */
/*
    private fun dummyHouse(): House {

        val ids = arrayOf("stark", "lannister", "tyrell", "arryn", "baratheon", "tully")

        val randomIdPosition = Random().nextInt(ids.size)

        return House(name = ids[randomIdPosition],
                region = "Region",
                words = "Lema"
        )

    }

    */
}