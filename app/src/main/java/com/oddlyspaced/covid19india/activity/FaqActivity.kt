package com.oddlyspaced.covid19india.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.oddlyspaced.covid19india.R
import com.oddlyspaced.covid19india.adapter.FaqAdapter
import com.oddlyspaced.covid19india.modal.FaqItem
import kotlinx.android.synthetic.main.activity_faq.*

class FaqActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faq)
        setupBottomNavigation()
        loadFaq()
    }

    private fun setupBottomNavigation() {
        viewTouchHome.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }
    }

    private fun loadFaq() {
        val list = ArrayList<FaqItem>()
        list.add(FaqItem("Are you official?","No"))
        list.add(FaqItem("What are your sources? How is the data gathered for this project?","We are using state bulletins and official handles to update our numbers. The data is validated by a group of volunteers and published into a Google sheet and an API. API is available for all at api.covid19india.org. We would love it if you can use this data in the fight against this virus. The source list is here."))
        list.add(FaqItem("Why does covid19india.org have more positive count than MoH?","MoHFW updates the data at a scheduled time. However, we update them based on state press bulletins, official (CM, Health M) handles, PBI, Press Trust of India, ANI reports. These are generally more recent."))
        list.add(FaqItem("Where can I find the data for this?","You can preview all the data collected in this page : patientdb.covid19india.org . All the data is available through an API for further analysis and development here : api.covid19india.org"))
        list.add(FaqItem("Who are you?","We are a group of dedicated volunteers who curate and verify the data coming from several sources. We extract the details, like a patient's relationship with other patients to identify local and community transmissions, travel history and status. We never collect or expose any personally identifiable data regarding the patients."))
        list.add(FaqItem("Why are you guys putting in time and resources to do this while not gaining a single penny from it?","Because it affects all of us. Today it's someone else who is getting infected; tomorrow it could be us. We need to prevent the spread of this virus. We need to document the data so that people with knowledge can use this data to make informed decisions."))
        val layoutManager = LinearLayoutManager(this)
        recyclerFaq.setHasFixedSize(true)
        recyclerFaq.layoutManager = layoutManager
        val adapter = FaqAdapter(list, this)
        recyclerFaq.adapter = adapter
    }
}
