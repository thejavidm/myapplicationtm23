package mx.uabcs.idecasso.myapplicationtm.views.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.vansuita.materialabout.builder.AboutBuilder;

import mx.uabcs.idecasso.myapplicationtm.R;

public class AboutActivity extends AppCompatActivity {
    //region Create & Listeners
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_about);
        initAboutView();
        initHomeButton();
    }

    private void initHomeButton() {
        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
    //endregion

    //region Fill About
    private void initAboutView(){
        View materialAboutView = AboutBuilder.with(this)
                .setAppName(R.string.app_name)
                .setAppIcon(R.mipmap.ic_launcher_round)
                .setVersionNameAsAppSubTitle()
                .setPhoto(R.drawable.profile)
                .setCover(R.drawable.cover)
                .setLinksAnimated(true)
                .setDividerDashGap(13)
                .setName(R.string.developer)
                .setSubTitle(R.string.developer_subtitle)
                .setLinksColumnsCount(4)
                .setBrief("I'm warmed of mobile technologies. Ideas maker, curious and nature lover.")
                .addGooglePlayStoreLink(R.string.google_play_link)
                .addGitHubLink(R.string.developer_username)
                .addFiveStarsAction()
                .addFacebookLink(R.string.developer_username)
                .addTwitterLink(R.string.developer_username)
                .addEmailLink(R.string.developer_email)
                .addGooglePlusLink(R.string.developer_gplus_username)
                .addWebsiteLink(R.string.web_uabcs)
                .addShareAction(R.string.app_name)
                .setWrapScrollView(true)
                .setLinksAnimated(true)
                .setShowAsCard(true)
                .build();
        setContentView(materialAboutView);
    }
    //endregion
}
