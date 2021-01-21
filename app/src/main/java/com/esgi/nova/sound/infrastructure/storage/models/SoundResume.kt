package com.esgi.nova.sound.infrastructure.storage.models

import com.esgi.nova.sound.ports.ISoundResume

class SoundResume(override val backgroundSoundPosition: Int) : ISoundResume {

}