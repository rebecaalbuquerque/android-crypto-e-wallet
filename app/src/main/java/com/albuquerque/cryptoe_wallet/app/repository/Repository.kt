package com.albuquerque.cryptoe_wallet.app.repository


/**
 * @see RemoteRepository
 * @see LocalRepository
 * */

class Repository(
    val remote: IRemoteRepository,
    val local: ILocalRepository
) : IRepository {


}