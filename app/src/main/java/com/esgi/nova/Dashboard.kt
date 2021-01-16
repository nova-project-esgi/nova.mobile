package com.esgi.nova

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.esgi.nova.difficulties.application.GetAllDetailedDifficulties
import com.esgi.nova.difficulties.ports.IDetailedDifficulty
import com.esgi.nova.dtos.difficulty.DetailedDifficultyDto
import com.esgi.nova.files.infrastructure.ports.IFileWrapper
import com.esgi.nova.resources.application.GetImageResourceWrappersByIds
import com.esgi.nova.resources.ports.IResource
import com.esgi.nova.utils.reflectMapCollection
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.activity_leader_board.*
import org.jetbrains.anko.doAsync
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class Dashboard : AppCompatActivity(), View.OnClickListener, AdapterView.OnItemClickListener {

    @Inject
    lateinit var getAllDetailedDifficulties: GetAllDetailedDifficulties

    @Inject
    lateinit var getImageResourceWrappersByIds: GetImageResourceWrappersByIds

    private lateinit var difficulties: List<DetailedDifficultyDto>

    private lateinit var currentDifficulty: DetailedDifficultyDto

    private var wrapperResources = mutableListOf<IFileWrapper<IResource>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        doAsync {
            try {
                val result = getAllDetailedDifficulties.execute()

                difficulties = result.reflectMapCollection<IDetailedDifficulty, DetailedDifficultyDto>().toList()
                runOnUiThread {
                    val arrayAdapter = ArrayAdapter(
                        this@Dashboard,
                        R.layout.list_item,
                        difficulties
                    )
                    tv_difficulty.setAdapter(arrayAdapter)
                    tv_difficulty.inputType = 0
                    if (difficulties.isNotEmpty()){
                        currentDifficulty = difficulties[0]
                        tv_difficulty.setText(difficulties[0].name, false)
                        generateResourceViews()
                    } else {
                        tv_difficulty.isEnabled = false
                    }
                    tv_difficulty.onItemClickListener = this@Dashboard
                }
            } catch (e: Error){

            }
        }


        btn_to_leaderboard.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        if (view == btn_to_leaderboard) {
            val intent = Intent(this, LeaderBoard::class.java)
            startActivity(intent);
        }
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        currentDifficulty = parent?.getItemAtPosition(position) as DetailedDifficultyDto
        generateResourceViews()
    }

    private fun generateResourceViews() {
        doAsync {
            try {
                val result = getImageResourceWrappersByIds.execute(currentDifficulty.resources)
                wrapperResources.clear()
                wrapperResources.addAll(result)
                runOnUiThread {
                    wrapperResources.clear()
                    wrapperResources.addAll(result)
                    val inflater = LayoutInflater.from(this@Dashboard)
                    list_resource.removeAllViews()
                    wrapperResources.forEach {
                        val resourceView = createResourceView(inflater, it)
                        list_resource.addView(resourceView)
                    }
                }
            } catch (e: Error) {

            }
        }
    }

    private fun createResourceView(inflater: LayoutInflater, resource: IFileWrapper<IResource>): View? {
        val view =
            inflater.inflate(R.layout.horizontal_list_resource, list_resource, false)
        val resourceName: TextView =  view.findViewById(R.id.tv_resource_name)
        resourceName.text = resource.data.name

        val imgView: ImageView = view.findViewById(R.id.iv_resource_img)
        val lp = LinearLayout.LayoutParams(100, 100)
        imgView.setImageBitmap(resource.img)
        imgView.layoutParams = lp

        val resourceStartValue: TextView = view.findViewById(R.id.tv_resource_value)
        val startValueResource = currentDifficulty.resources.filter { r -> r.id == resource.data.id }[0]
        resourceStartValue.text = "${startValueResource.startValue}"

        return view
    }
}