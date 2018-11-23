package net.micode.notes.news;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.peak.recycler.base.BaseAdapter;
import com.peak.recycler.base.BaseHolder;
import com.peak.recycler.strategy.StrategyAdapter;

import net.micode.notes.notes.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Description:
 * Created by peak on 2018/11/21.
 */
public class PicListActivity extends Activity {
    private static final String TAG = "PicListActivity";
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        mRecyclerView = findViewById(R.id.recyclerview);

        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        final MyAdapter adapter = new MyAdapter();
        mRecyclerView.setAdapter(adapter);
        loadPic(adapter);

        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup viewGroup, View view, int i) {
                Log.i(TAG, "onItemClick: " + adapter.getData(i).toString());
                Intent intent = new Intent();
                String url = ((Info) adapter.getData(i)).url;
                intent.putExtra("result", url);
                setResult(10086, intent);
                finish();
            }
        });

    }


    class MyStrategy extends StrategyAdapter.AbsStrategy<Info> {

        @Override
        public BaseHolder createViewHolder(LayoutInflater layoutInflater, ViewGroup viewGroup) {
            View view = layoutInflater.inflate(R.layout.activity_new_item, viewGroup, false);
            return new BaseHolder(view);
        }

        @Override
        public void bindData(BaseHolder baseHolder, Info info, int i) {
            ImageView imageView = (ImageView) baseHolder.getViewById(R.id.pic);
            Glide.with(imageView)
                    .load(info.url)
                    .into(imageView);
        }

        @Override
        public void onClicked(View view, int i, Info info) {

        }

        @Override
        public void onLongClicked(View view, int i, Info info) {

        }

        @Override
        public boolean canHandle(Object o) {
            return o instanceof Info;
        }
    }

    class MyAdapter extends StrategyAdapter {
        List<AbsStrategy> mStrategies;

        private MyStrategy mMyStrategy;

        public MyAdapter() {
            mStrategies = new ArrayList<>();
            mMyStrategy = new MyStrategy();
            mStrategies.add(mMyStrategy);
            setStrategies(mStrategies);
        }
    }

    class Info {
        String name;
        String url;

        public Info(String name, String url) {
            this.name = name;
            this.url = url;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("{");
            sb.append("\"name\":\"")
                    .append(name).append('\"');
            sb.append(",\"url\":\"")
                    .append(url).append('\"');
            sb.append('}');
            return sb.toString();
        }
    }

    private void loadPic(MyAdapter adapter) {
        List<Info> list = new ArrayList<>();
        Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()) {
            list.add(new Info(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)),
                    cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))));
        }

        adapter.add(list);

    }


}
