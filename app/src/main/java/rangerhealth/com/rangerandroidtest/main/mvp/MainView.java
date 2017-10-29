package rangerhealth.com.rangerandroidtest.main.mvp;

import android.annotation.SuppressLint;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rangerhealth.com.rangerandroidtest.R;
import rangerhealth.com.rangerandroidtest.network.User;
import rx.Observable;

/**
 * Created by carlos on 10/28/17.
 */

@SuppressLint("ViewConstructor")
public class MainView extends ConstraintLayout{

    private final MainAdapter adapter;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    public MainView(AppCompatActivity activity, MainAdapter adapter) {
        super(activity);
        inflate(getContext(), R.layout.activity_main, this);
        ButterKnife.bind(this);
        this.adapter = adapter;

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }


    public void setUsers (List<User> users){
        adapter.setUserList(users);
    }

    public Observable<User> getEventViewClickedObservable (){
        return this.adapter.getEventViewClickedObservable();
    }
}
