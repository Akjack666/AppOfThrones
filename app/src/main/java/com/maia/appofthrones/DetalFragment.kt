package com.maia.appofthrones

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.squareup.picasso.Picasso

import kotlinx.android.synthetic.main.data_character.*
import kotlinx.android.synthetic.main.header_character.*


class DetalFragment: Fragment() {


    companion object {
        fun newInstance(id: String): DetalFragment {

            val instance = DetalFragment()
            val args = Bundle()
            args.putString("key_id", id)

            instance.arguments = args

            return instance
        }


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
       return inflater.inflate(R.layout.fragment_detail, container,false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val id = arguments!!.getString("key_id")
        val character = CharactersRepo.findCharacterById(id)

        character?.let {

            with(character) {


                labelNamed.text = name
                labelTitled.text = title
                labelActor.text = actor
                labelBorn.text = born
                labelFather.text = father
                labelQuote.text = quote
                labelSpouse.text = spouse

                val overlayColor = House.getOverlayColor(character.house.name)

                imageOverlay.background = ContextCompat.getDrawable(context!!, overlayColor)

                val baseColor = House.getBaseColor(character.house.name)
                btHouse.backgroundTintList = ContextCompat.getColorStateList(context!!, baseColor)

                val idDrawable = House.getIcon(character.house.name)
                val drawable = ContextCompat.getDrawable(context!!,idDrawable)
                btHouse.setImageDrawable(drawable)

                Picasso.get()
                        .load(character.image)
                        .placeholder(R.drawable.test)
                        .into(imgCharacter)


            }
        }







        btHouse.setOnClickListener {
            if(character!=null)
            showDialog(character.house)
        }
    }

    private fun showDialog(house:House){

        val dialog = HouseDialog.newInstance(house)
        dialog.show(childFragmentManager,"house_dialog")
    }


    }



