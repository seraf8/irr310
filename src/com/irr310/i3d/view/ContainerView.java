package com.irr310.i3d.view;

import java.util.ArrayList;
import java.util.List;

import com.irr310.i3d.Graphics;

public abstract class ContainerView extends View implements ViewParent {

    List<View> children = new ArrayList<View>();
    
    public ContainerView(Graphics g) {
        super(g);
    }

    @Override
    public void onDraw() {
        for (View widget : children) {
            widget.draw();
        }
    }
    
//    @Override
//    public View duplicate() {
//        ContainerView view = new ContainerView(g);
//        for (View widget : children) {
//            view.addChild(widget.duplicate());
//        }
//        view.setLayout(getLayout().duplicate());
//        return view;
//    }
    
    public void addChild(View widget) {
        children.add(widget);
        widget.assignParent(this);
    }
    
//    @Override
//    public boolean doLayout(Layout parentLayout) {
//        for (View widget : children) {
//            widget.layout(layout);
//        }
//        return true;
//    }
    
    
    

    @Override
    public void requestLayout() {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public View findViewById(String id) {
    	View outputView = null;
    	outputView = super.findViewById(id); 
    	if(outputView == null) {
    		for (View view : children) {
    			outputView = view.findViewById(id);
    			if(outputView != null) {
    				break;
    			}
            }
    	}
    	return outputView;
    }

}