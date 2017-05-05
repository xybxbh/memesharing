package com.etc.emoji;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import com.etc.emoji.app.emojiApp;
import com.etc.emoji.task.ShowMaterialphotoTask;


public class MaterialFragment extends Fragment implements OnRefreshListener {
    private GridView gridMaterial;
    private View view;
    private emojiApp app;
    private SwipeRefreshLayout swipe;
 
   


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
   

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_material,null);
        app = (emojiApp)this.getActivity().getApplication();
        gridMaterial  = (GridView) view.findViewById(R.id.gridMaterial);
        
       
        
        
        swipe = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container_material);
		swipe.setOnRefreshListener(this);
		swipe.setColorScheme(R.color.white, R.color.red, R.color.white, R.color.red);
        new ShowMaterialphotoTask(this.getActivity(),gridMaterial).execute(app.getUser().getUserid()+"");

        return view;
    }
  
    

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		swipe.setRefreshing(false);

		new ShowMaterialphotoTask(this.getActivity(),gridMaterial).execute(app.getUser().getUserid()+"");
	}

}
