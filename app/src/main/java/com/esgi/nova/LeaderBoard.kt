package com.esgi.nova

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.esgi.nova.adapters.GamesAdapter
import com.esgi.nova.games.infrastructure.dto.LeaderBoardGameView
import com.esgi.nova.games.infrastructure.dto.UserResume
import com.esgi.nova.infrastructure.api.pagination.PageMetadata
import com.esgi.nova.infrastructure.preferences.PreferenceConstants
import com.esgi.nova.models.*
import com.esgi.nova.users.application.GetDefaultGameList
import com.esgi.nova.utils.NetworkUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_leader_board.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class LeaderBoard : AppCompatActivity(), AdapterView.OnItemClickListener{

    @Inject
    lateinit var getDefaultGameList: GetDefaultGameList

    private var difficulties = mutableListOf<Difficulty>(
        Difficulty(UUID(15,20),"Facile"),
        Difficulty(UUID(2,8),"Moyen"),
        Difficulty(UUID(3,10),"Difficile")
    )
    private lateinit var currentDifficulty: Difficulty

    private var games = mutableListOf<LeaderBoardGameView>(
        LeaderBoardGameView(UUID.randomUUID(), UserResume(UUID.randomUUID(),"james.bertho94@gmail.com",Role.USER,"jamso"),1600, difficulties[0].id, emptyList(),45),
        LeaderBoardGameView(UUID.randomUUID(), UserResume(UUID.randomUUID(),"james.bertho94@gmail.com",Role.USER,"jamso"),1600, difficulties[0].id, emptyList(),15),
        LeaderBoardGameView(UUID.randomUUID(), UserResume(UUID.randomUUID(),"james.bertho94@gmail.com",Role.USER,"jamso"),1600, difficulties[0].id, emptyList(),30),
        LeaderBoardGameView(UUID.randomUUID(), UserResume(UUID.randomUUID(),"james.bertho94@gmail.com",Role.USER,"jamso"),1600, difficulties[0].id, emptyList(),30)
    )



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leader_board)
        tv_leaderBoard_filter.setAdapter(ArrayAdapter(this, R.layout.list_item, difficulties))
        tv_leaderBoard_filter.inputType = 0
        tv_leaderBoard_filter.setText(difficulties[0].name, false)
        currentDifficulty = difficulties[0]

        val itemDivider = DividerItemDecoration(applicationContext, 1)
        itemDivider.setDrawable(
            ContextCompat.getDrawable(
                applicationContext,
                R.drawable.golden_divider
            )!!
        )
        rv_scores.addItemDecoration(itemDivider);

        games.sortByDescending { it.eventCount }
        rv_scores?.apply {
            layoutManager = LinearLayoutManager(this@LeaderBoard)
            adapter = GamesAdapter(games)
        }

        swipeContainer.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener { refreshRecyclerView() })
        swipeContainer.setColorSchemeResources(
            android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light
        )

        tv_leaderBoard_filter.onItemClickListener = this

        swipeContainer.isRefreshing = true
        refreshRecyclerView()

    }


    fun refreshRecyclerView() {
        rv_scores.visibility = View.GONE
        if (NetworkUtils.isNetworkAvailable(this)) {
            var token = ""
                this.getSharedPreferences(PreferenceConstants.UserKey, MODE_PRIVATE)
                .getString(PreferenceConstants.TokenKey, null).let {
                    if (it != null) {
                        token = "Bearer $it"
                    }
                }
            getDefaultGameList.execute(currentDifficulty.id, token, object : Callback<PageMetadata<LeaderBoardGameView>> {
                override fun onResponse(
                    call: Call<PageMetadata<LeaderBoardGameView>>,
                    response: Response<PageMetadata<LeaderBoardGameView>>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            games.clear()
                            games.addAll(it.values)
                        }
                        rv_scores.visibility = View.VISIBLE
                        swipeContainer.setRefreshing(false)

                    }
                }
                override fun onFailure(call: Call<PageMetadata<LeaderBoardGameView>>, t: Throwable) {
                    val toast = Toast.makeText(
                        this@LeaderBoard,
                        "Une erreur est survenue lors de la récupération des scores",
                        Toast.LENGTH_LONG
                    )
                    toast.show()
                    rv_scores.visibility = View.VISIBLE
                    swipeContainer.isRefreshing = false
                }
            })

        } else {
            val toast = Toast.makeText(this, "Le réseau n'est pas disponible", Toast.LENGTH_LONG)
            toast.show()
            rv_scores.visibility = View.VISIBLE
            swipeContainer.isRefreshing = false
        }
    }


    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        currentDifficulty = parent?.getItemAtPosition(position) as Difficulty
        swipeContainer.isRefreshing = true
        refreshRecyclerView()
    }

}