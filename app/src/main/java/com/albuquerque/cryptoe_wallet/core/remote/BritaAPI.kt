package com.albuquerque.cryptoe_wallet.core.remote

import com.albuquerque.cryptoe_wallet.app.model.dto.BritaDTO
import com.albuquerque.cryptoe_wallet.core.extensions.format
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BritaAPI {

    /*
    * BASE_URL = https://olinda.bcb.gov.br/olinda/servico/PTAX/versao/v1/odata/
    * CotacaoMoedaDia(moeda=@moeda,dataCotacao=@dataCotacao)?@moeda='USD'&@dataCotacao='08-10-2020'&$format=json
    * */

    @GET("{recurso}")
    suspend fun fetchBritaData(
        @Path("recurso") recurso: String = "CotacaoMoedaDia(moeda=@moeda,dataCotacao=@dataCotacao)",
        @Query("@moeda") moeda: String = "'USD'",
        @Query("@dataCotacao") dataCotacao: String = "'${BritaApiHelper.lastDateRequested.format("MM-dd-yyyy")}'",
        @Query("\$format") formato: String = "json"
    ): BritaDTO

}