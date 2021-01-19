package com.esgi.nova

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.esgi.nova.difficulties.application.GetAllDetailedDifficulties
import com.esgi.nova.difficulties.ports.IDetailedDifficulty
import com.esgi.nova.dtos.difficulty.DetailedDifficultyDto
import com.esgi.nova.files.infrastructure.ports.IFileWrapper
import com.esgi.nova.games.application.CreateGame
import com.esgi.nova.games.infrastructure.data.game.models.CanLaunchGame
import com.esgi.nova.games.infrastructure.data.game.models.CanResumeGame
import com.esgi.nova.games.ui.GameActivity
import com.esgi.nova.resources.application.GetImageResourceWrappersByIds
import com.esgi.nova.resources.ports.IResource
import com.esgi.nova.utils.reflectMapCollection
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_dashboard.*
import org.jetbrains.anko.doAsync
import javax.inject.Inject


@AndroidEntryPoint
class DashboardActivity : AppCompatActivity(), View.OnClickListener, AdapterView.OnItemClickListener {

    @Inject
    lateinit var createGame: CreateGame

    @Inject
    lateinit var getAllDetailedDifficulties: GetAllDetailedDifficulties

    @Inject
    lateinit var getImageResourceWrappersByIds: GetImageResourceWrappersByIds

    @Inject
    lateinit var canLaunchGame: CanLaunchGame

    @Inject
    lateinit var canResumeGame: CanResumeGame

    private lateinit var difficulties: List<DetailedDifficultyDto>

    private var selectedDifficulty: DetailedDifficultyDto? = null


    private var wrapperResources = mutableListOf<IFileWrapper<IResource>>()

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, DashboardActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        generateDifficulties()

        init_new_game_btn.setOnClickListener(this)
        resume_game_btn.setOnClickListener(this)
        doAsync {
            init_new_game_btn.isEnabled = canLaunchGame.execute()
            resume_game_btn.isEnabled = canResumeGame.execute()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.dashboard_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.leaderboard_btn -> LeaderBoardActivity.start(this)
            R.id.settings_btn -> ParametersActivity.start(this)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun generateDifficulties() {

        doAsync {
            difficulties = getAllDetailedDifficulties
                .execute()
                .reflectMapCollection<IDetailedDifficulty, DetailedDifficultyDto>()
                .toList()

            runOnUiThread {
                val arrayAdapter = ArrayAdapter(
                    this@DashboardActivity,
                    R.layout.list_item,
                    difficulties
                )
                difficulty_tv.setAdapter(arrayAdapter)
                difficulty_tv.inputType = 0
                if (difficulties.isNotEmpty()) {
                    selectedDifficulty = difficulties[0]
                    difficulty_tv.setText(difficulties[0].name, false)
                    generateResourceViews()
                } else {
                    difficulty_tv.isEnabled = false
                }
                difficulty_tv.onItemClickListener = this@DashboardActivity
            }
        }
    }


    override fun onClick(view: View?) {
        when(view){
            init_new_game_btn -> selectedDifficulty?.id?.let {
                GameActivity.newGame(this@DashboardActivity, it)
            }
            resume_game_btn -> GameActivity.start(this@DashboardActivity)
        }

    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        selectedDifficulty = parent?.getItemAtPosition(position) as DetailedDifficultyDto
        generateResourceViews()
    }

    private fun generateResourceViews() {
        selectedDifficulty?.let { difficulty ->
            doAsync {
                val result = getImageResourceWrappersByIds.execute(difficulty.resources)
                wrapperResources.clear()
                wrapperResources.addAll(result)
                runOnUiThread {
                    wrapperResources.clear()
                    wrapperResources.addAll(result)
                    val inflater = LayoutInflater.from(this@DashboardActivity)
                    resources_list_ll.removeAllViews()
                    wrapperResources.forEach {
                        val resourceView = createResourceView(inflater, it)
                        resources_list_ll.addView(resourceView)
                    }
                }
            }
        }
    }

    private fun createResourceView(inflater: LayoutInflater, resource: IFileWrapper<IResource>): View? {
        val view =
            inflater.inflate(R.layout.horizontal_list_resource, resources_list_ll, false)
        val resourceName: TextView =  view.findViewById(R.id.resource_name_tv)
        resourceName.text = resource.data.name

        val imgView: ImageView = view.findViewById(R.id.resource_icon_iv)
        val lp = LinearLayout.LayoutParams(100, 100)
        imgView.setImageBitmap(resource.img)
        imgView.layoutParams = lp

        val resourceStartValue: TextView = view.findViewById(R.id.resource_value_tv)
        selectedDifficulty?.let { difficulty ->
            val startValueResource = difficulty.resources.filter { r -> r.id == resource.data.id }[0]
            resourceStartValue.text = "${startValueResource.startValue}"
        }

        return view
    }
}