package com.chinachino.mvvm.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.chinachino.mvvm.R
import com.chinachino.mvvm.uiHelpers.DrawGlide
import com.chinachino.mvvm.utils.Constants
import com.chinachino.mvvm.viewModels.MovieDetailsViewModel
import com.chinachino.mvvm.models.Details

class MovieDetailsFragment : Fragment() {
    //UI
    var imageView: AppCompatImageView? = null
    var title: TextView? = null
    var overview: TextView? = null
    var rank: TextView? = null
    var genres: TextView? = null
    var scrollView: ScrollView? = null
    var viewModel: MovieDetailsViewModel? = null

    //image
    private var drawGlide: DrawGlide? = null
    private val TAG = "MovieDetailsActivity"
    var movieID = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MovieDetailsViewModel::class.java)
        movieID = requireArguments().getInt("movieID")
        instance()
        SearchMovieDetails()
        SubscribeOnObservers()
    }

    private fun instance() {
        drawGlide = DrawGlide()
    }

    fun SearchMovieDetails() {
        viewModel!!.searchMovieDetails(movieID)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }

    private fun SubscribeOnObservers() {
        Log.d(TAG, "SubscribeOnObservers: ")
        viewModel!!.movieDetails.observe(this, { details: Details? ->
            if (details != null) {
                InitViewItems(details)
                viewModel!!.isMovieRetrieved = true
            } else {
                Log.d(TAG, "onChanged: detail is null")
            }
        })
        viewModel!!.isRequestTimedOut.observe(this, { aBoolean ->
            if (aBoolean && !viewModel!!.isMovieRetrieved) {
                Log.d(TAG, "onChanged: Connection Timed Out... ")
                ShowErrorMessage("ConnectionTimedOut!")
            }
        })
    }

    private fun ShowErrorMessage(error: String) {
        title!!.text = error
        title!!.textSize = 20f
        overview!!.text = ""
        rank!!.text = ""
        genres!!.text = ""
        drawGlide!!.draw(context, Constants.DEFAULT_IMAGE_REQUEST, Constants.DEFAULT_IMAGE, imageView)
    }

    private fun InitViewItems(details: Details) {
        var genre = "genres : "
        scrollView!!.visibility = View.VISIBLE
        title!!.text = details.title
        overview!!.text = details.overview
        rank!!.text = details.voteAverage.toString()
        for (s in details.genres) genre += """
 - ${s.name}"""
        genres!!.text = genre
        drawGlide!!.draw(
                context,
                Constants.DEFAULT_IMAGE_REQUEST,
                Constants.IMAGE_BASE_URL + details.backdropPath,
                imageView)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageView = view.findViewById(R.id.movie_image_detail)
        title = view.findViewById(R.id.movie_title_detail)
        overview = view.findViewById(R.id.overview_title)
        rank = view.findViewById(R.id.movie_vote)
        scrollView = view.findViewById(R.id.parent)
        genres = view.findViewById(R.id.genres)
    }
}