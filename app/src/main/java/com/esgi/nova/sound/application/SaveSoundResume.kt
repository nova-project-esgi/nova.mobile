package com.esgi.nova.sound.application

import com.esgi.nova.sound.infrastructure.storage.SoundStorageRepository
import com.esgi.nova.sound.ports.ISoundResume
import javax.inject.Inject

class SaveSoundResume @Inject constructor(private val soundStorageRepository: SoundStorageRepository){
    fun execute(soundResume: ISoundResume) = soundStorageRepository.save(soundResume)
}