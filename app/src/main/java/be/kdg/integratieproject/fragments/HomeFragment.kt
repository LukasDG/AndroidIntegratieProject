package be.kdg.integratieproject.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import be.kdg.integratieproject.R
import be.kdg.integratieproject.adapters.ProjectsAdapter
import be.kdg.integratieproject.model.project.Project
import be.kdg.integratieproject.model.project.ProjectBasic
import be.kdg.integratieproject.rest.getRetrofit
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_home.view.*
import java.util.ArrayList

class HomeFragment : Fragment(), ProjectsAdapter.Listener{
    private lateinit var rvProjects: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        initViews(view)
        loadData()

        return view
    }

    companion object {
        fun newInstance(): HomeFragment =
            HomeFragment()
    }

    private fun initViews(view: View){
        rvProjects = view.rvProjects
    }

    private fun loadData(){
        getRetrofit().getProjects()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(this::handleResponse)
    }

    private fun handleResponse(myData: List<ProjectBasic>){
        val myDataArrayList = ArrayList(myData)
        rvProjects.adapter = ProjectsAdapter(myDataArrayList, this)
        rvProjects.layoutManager = LinearLayoutManager(this.context)
    }

    override fun onProjectSelected(projectId: Int) {
        val projectFragment = ProjectFragment.newInstance(projectId)
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.fragment_container, projectFragment)
        transaction?.addToBackStack(null)
        transaction?.commit()
    }
}
