package com.etc.emoji;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;

import com.etc.emoji.app.emojiApp;
import com.etc.emoji.task.ShowMyemojiTask;
import com.etc.emoji.task.ShowMymaterialTask;

public class SelfFragment extends Fragment {
    private GridView gridSelf;
    private View view;
    private emojiApp app;
    private Button btnSelfMaterial;
    private Button btnSelfEmoji;
    Context context;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_self,null);
        app = (emojiApp)this.getActivity().getApplication();
        gridSelf  = (GridView) view.findViewById(R.id.gridSelf);
        btnSelfMaterial = (Button) view.findViewById(R.id.btnSelfMaterial);
        btnSelfEmoji = (Button) view.findViewById(R.id.btnSelfEmoji);
        btnSelfMaterial.setOnClickListener(new OnClickListenerImpl());
        btnSelfEmoji.setOnClickListener(new OnClickListenerImpl());
        
        context = this.getActivity();
       
            
        //new ShowMymaterialTask(this.getActivity(),gridSelf).execute(app.getUser().getUserid()+"");
     
      
       // new ShowMyemojiTask(this.getActivity(),gridSelf).execute(app.getUser().getUserid()+"");            }
      
      
        return view;
    }
    private class OnClickListenerImpl implements View.OnClickListener{

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btnSelfMaterial:
				new ShowMymaterialTask(context,gridSelf).execute(app.getUser().getUserid()+"");
				break;
			case R.id.btnSelfEmoji:
				 new ShowMyemojiTask(context,gridSelf).execute(app.getUser().getUserid()+"");  
				break;
			}
		}
		
	}
    
  
  


}
