package com.irr310.client.graphics.ether.activities.worldmap;



import com.irr310.common.world.system.WorldSystem;
import com.irr310.i3d.I3dRessourceManager;
import com.irr310.i3d.Measure;
import com.irr310.i3d.Measure.Axis;
import com.irr310.i3d.view.LayoutParams.LayoutMeasure;
import com.irr310.i3d.view.RelativeLayout;
import com.irr310.i3d.view.TextView;
import com.irr310.i3d.view.TextView.Gravity;

public class SystemView extends RelativeLayout {

    private final WorldSystem system;
    private float size;
    private float zoom;
    private float zoomedSize;
    private SystemCircleView systemCircleView;
    private TextView textView;

    public SystemView(WorldSystem system) {
        this.system = system;
        
        size = 30;
        zoom = 1;

        
        systemCircleView = new SystemCircleView(system);
        
        textView = new TextView();
        textView.setText(system.getName());
        textView.setFont(I3dRessourceManager.getInstance().loadFont("systemNameWorldMap@fonts"));
        textView.setTextColor(I3dRessourceManager.getInstance().loadColor("systemNameWorldMap@color")); 
        addChild(systemCircleView);
        addChild(textView);
        
        reshape();
    }
    
    public WorldSystem getSystem() {
        return system;
    }
    
    private void reshape() {
        
        systemCircleView.reshape();
        
        zoomedSize = size * zoom;
        
        layoutParams.setLayoutWidthMeasure(LayoutMeasure.FIXED);
        layoutParams.setWidthMeasure(new Measure(zoomedSize, false, Axis.HORIZONTAL));
        layoutParams.setLayoutHeightMeasure(LayoutMeasure.FIXED);
        layoutParams.setHeightMeasure(new Measure(zoomedSize, false, Axis.VERTICAL));
        
        layoutParams.setMarginLeftMeasure(new Measure((float) (system.getLocation().x * zoom) - zoomedSize/2, false, Axis.HORIZONTAL));
        layoutParams.setMarginTopMeasure(new Measure((float) (system.getLocation().y * zoom) - zoomedSize/2, false, Axis.VERTICAL));
        
        textView.getLayoutParams().setMarginTopMeasure(new Measure(-15 - zoomedSize/20 , false, Axis.VERTICAL));
        textView.getLayoutParams().setLayoutWidthMeasure(LayoutMeasure.FIXED);
        textView.getLayoutParams().setWidthMeasure(new Measure(zoomedSize, false, Axis.HORIZONTAL));
        textView.setGravity(Gravity.CENTER);
        
        
        
    }
    
    
    public void setZoom(float zoom) {
        systemCircleView.setZoom(zoom);
        this.zoom = zoom;
        
        
        reshape();
        if(getParent() != null) {
            getParent().requestLayout();
        }
    }

    public void setSelected(boolean selected) {
        systemCircleView.setSelected(selected);
    }

}
