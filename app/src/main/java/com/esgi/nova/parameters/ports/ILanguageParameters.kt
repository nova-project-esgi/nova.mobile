package com.esgi.nova.parameters.ports

import com.esgi.nova.languages.ports.IAppLanguage

interface ILanguageParameters: IParameters {
    val selectedLanguage: IAppLanguage?
}