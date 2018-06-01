package br.fiap.apogianeli.azurebd.business

import android.content.Context
import br.fiap.apogianeli.azurebd.entities.PriorityEntity
import br.fiap.apogianeli.azurebd.repository.PriorityRepository

class PriorityBusiness (context: Context) {

    private val mPriorityRepository: PriorityRepository = PriorityRepository.getInstance(context = context)

    // func para disponibilizar o acesso a dados repo para a camada view
    fun  getList () : MutableList<PriorityEntity> = mPriorityRepository.getList()


}