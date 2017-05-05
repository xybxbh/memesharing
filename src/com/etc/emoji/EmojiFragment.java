package com.etc.emoji;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;

import com.etc.emoji.app.emojiApp;
import com.etc.emoji.task.ShowEmojiphotoTask;

public class EmojiFragment extends Fragment implements OnRefreshListener {
    private GridView gridEmoji;
    private View view;
    private emojiApp app;
    private SwipeRefreshLayout swipe;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_emoji,null);
        app = (emojiApp)this.getActivity().getApplication();
        gridEmoji  = (GridView) view.findViewById(R.id.gridEmoji);
        swipe = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container_emoji);
		swipe.setOnRefreshListener(this);
		swipe.setColorScheme(R.color.white, R.color.red, R.color.white, R.color.red);
		
        new ShowEmojiphotoTask(this.getActivity(),gridEmoji).execute(app.getUser().getUserid()+"");
        return view;
    }

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		swipe.setRefreshing(false);
		new ShowEmojiphotoTask(this.getActivity(),gridEmoji).execute(app.getUser().getUserid()+"");
	}


}
