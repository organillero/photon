package rangerhealth.com.rangerandroidtest.main.mvp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rangerhealth.com.rangerandroidtest.R;
import rangerhealth.com.rangerandroidtest.network.User;
import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by carlos on 10/28/17.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.UserHolder> {

    private final Picasso picasso;
    private List<User> userList = new ArrayList<>();

    public MainAdapter(Picasso picasso) {
        this.picasso = picasso;
    }


    public PublishSubject<User> mViewClickSubject = PublishSubject.create();

    public Observable<User> getEventViewClickedObservable() {
        return mViewClickSubject.asObservable();
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
        notifyDataSetChanged();

    }

    @Override
    public MainAdapter.UserHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.ranger_item_row, parent, false);

        UserHolder uh = new UserHolder(v);

        RxView.clicks(v)
                .takeUntil(RxView.detaches(parent))
                .map(__ -> (User) uh.itemView.getTag())
                .subscribe(mViewClickSubject);

        return uh;
    }

    @Override
    public void onBindViewHolder(UserHolder holder, int position) {

        User user = userList.get(position);

        holder.mItemName.setText(user.name);
        this.picasso.load(user.avatar).into(holder.mItemImage);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }


    public class UserHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_image)
        public ImageView mItemImage;
        @BindView(R.id.item_name)
        public TextView mItemName;

        public UserHolder(View v) {
            super(v);
            ButterKnife.bind(this, itemView);
        }

    }
}
