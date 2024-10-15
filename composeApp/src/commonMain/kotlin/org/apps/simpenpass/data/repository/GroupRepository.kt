package org.apps.simpenpass.data.repository

import org.apps.simpenpass.data.source.localData.LocalStoreData
import org.apps.simpenpass.data.source.remoteData.RemotePassDataSources

class GroupRepository(
    private val remotePassSources: RemotePassDataSources,
    private val localData : LocalStoreData
) {

    fun createGroup(){

    }
}