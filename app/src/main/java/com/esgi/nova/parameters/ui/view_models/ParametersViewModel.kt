package com.esgi.nova.parameters.ui.view_models

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.esgi.nova.dtos.languages.AppLanguageDto
import com.esgi.nova.languages.application.GetAllLanguages
import com.esgi.nova.languages.ports.IAppLanguage
import com.esgi.nova.parameters.application.GetParameters
import com.esgi.nova.parameters.application.SaveParameters
import com.esgi.nova.parameters.application.toLanguageParameters
import com.esgi.nova.parameters.ui.models.Parameters
import com.esgi.nova.ui.AppViewModel
import com.esgi.nova.utils.reflectMap
import com.esgi.nova.utils.reflectMapCollection

class ParametersViewModel @ViewModelInject constructor(
    private val getAllLanguages: GetAllLanguages,
    private val getParameters: GetParameters,
    private val saveParameters: SaveParameters
) : AppViewModel() {


    val languages: LiveData<List<AppLanguageDto>> get() = _languages
    private var _languages = MutableLiveData<List<AppLanguageDto>>()

    val selectedLanguage: LiveData<AppLanguageDto> get() = _selectedLanguage
    private var _selectedLanguage = MutableLiveData<AppLanguageDto>()

    val parameters: LiveData<Parameters> get() = _parameters
    private var _parameters = MutableLiveData<Parameters>()

    val parametersSaved: LiveData<Boolean> get() = _parametersSaved
    private var _parametersSaved = MutableLiveData<Boolean>()


    val startDashboard: LiveData<Boolean> get() = _startDashboard
    private var _startDashboard = MutableLiveData<Boolean>()

    val startResynchronize: LiveData<Boolean> get() = _startResynchronize
    private var _startResynchronize = MutableLiveData<Boolean>()


    fun initialize() {
        if (initialized) return

        loadingLaunch {
            _languages.value =
                getAllLanguages.execute()
                    .reflectMapCollection<IAppLanguage, AppLanguageDto>()
                    .toList()
            _parameters.value = getParameters.execute().reflectMap()
            _selectedLanguage.value = _languages.value?.firstOrNull { l -> l.isSelected }
        }
        initialized = true
    }

    fun persistParameters() {
        parameters.value?.toLanguageParameters(selectedLanguage.value)?.let { languageParams ->
            loadingLaunch {
                val previousParams = getParameters.execute()
                saveParameters.execute(languageParams)
                _parametersSaved.value = true

                if (previousParams.selectedLanguage?.id != selectedLanguage.value?.id) {
                    _startResynchronize.value = true
                } else {
                    _startDashboard.value = true
                }
            }
        }
    }


    fun setSelectedLanguage(languageDto: AppLanguageDto){
        _selectedLanguage.value = languageDto
    }

}
