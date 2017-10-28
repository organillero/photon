package rangerhealth.com.rangerandroidtest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;

    private List<HashMap<String, String>> userList;
    private RecyclerAdapter mAdapter;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;
                case R.id.navigation_dashboard:
                    return true;
                case R.id.navigation_notifications:
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        try {
            getPeople(new Callback() {
                @Override
                public void onNetworkResponse(String string) {
                    List<HashMap<String, String>> list = new ArrayList<>();

                    String[] eyes = {"eyes1", "eyes2", "eyes3", "eyes4", "eyes5", "eyes6", "eyes7", "eyes8", "eyes9"};
                    String[] noses = {"nose1", "nose2", "nose3", "nose4", "nose5", "nose6", "nose7", "nose8", "nose9"};
                    String[] mouths = {"mouth1", "mouth2", "mouth3", "mouth4", "mouth5", "mouth6", "mouth7", "mouth8", "mouth9"};
                    try {
                        JSONArray array = new JSONArray(string);
                        for(int i = 0; i < array.length(); i++) {
                            String name = array.getString(i);

                            HashMap<String, String> map = new HashMap<>();

                            Random generator = new Random();
                            int randomEye = generator.nextInt(eyes.length);
                            int randomNose = generator.nextInt(noses.length);
                            int randomMouth = generator.nextInt(mouths.length);

                            map.put("name", name);
                            map.put("avatar", "https://api.adorable.io/avatars/face/" + eyes[randomEye] + "/" + noses[randomNose] + "/" + mouths[randomMouth] + "/ffff66");

                            list.add(map);
                        }

                        userList = list;

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mAdapter = new RecyclerAdapter();
                                mRecyclerView.setAdapter(mAdapter);
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public interface Callback {
        void onNetworkResponse(String response);
    }

    void getPeople(final Callback callback) throws IOException {
        final URL url = new URL("http://namey.muffinlabs.com/name.json?count=10");

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                    java.util.Scanner s = new java.util.Scanner(in).useDelimiter("\\A");
                    String string = s.hasNext() ? s.next() : "";

                    callback.onNetworkResponse(string);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(runnable).start();
    }

    class RecyclerAdapter extends RecyclerView.Adapter<UserHolder> {
        @Override
        public UserHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View inflatedView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.ranger_item_row, parent, false);
            return new UserHolder(inflatedView);
        }

        @Override
        public void onBindViewHolder(UserHolder holder, int position) {
            HashMap<String, String> user = userList.get(position);
            holder.bindPhoto(user);
        }

        @Override
        public int getItemCount() {
            return userList.size();
        }
    }

    public class UserHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mItemImage;
        private TextView mItemName;

        private static final String PHOTO_KEY = "PHOTO";

        public UserHolder(View v) {
            super(v);

            mItemImage = (ImageView) v.findViewById(R.id.item_image);
            mItemName = (TextView) v.findViewById(R.id.item_name);
            v.setOnClickListener(this);
        }

        public void bindPhoto(final HashMap<String, String> user) {
            mItemName.setText(user.get("name"));

            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        URL url = new URL(user.get("avatar"));
                        HttpURLConnection connection = (HttpURLConnection) url
                                .openConnection();
                        connection.setDoInput(true);
                        connection.connect();
                        InputStream input = connection.getInputStream();
                        final Bitmap avatar = BitmapFactory.decodeStream(input);

                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mItemImage.setImageBitmap(avatar);
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };
            new Thread(runnable).start();
        }

        @Override
        public void onClick(View v) {
            Log.d("RecyclerView", "CLICK!");
        }
    }
}
