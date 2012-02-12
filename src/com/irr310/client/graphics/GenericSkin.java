package com.irr310.client.graphics;

import java.io.File;

import com.irr310.common.tools.TransformMatrix;
import com.irr310.common.tools.TransformMatrix.TransformMatrixChangeListener;
import com.irr310.common.world.Part;
import com.irr310.common.world.WorldObject;

import fr.def.iss.vd2.lib_v3d.V3DColor;
import fr.def.iss.vd2.lib_v3d.V3DContext;
import fr.def.iss.vd2.lib_v3d.V3DVect3;
import fr.def.iss.vd2.lib_v3d.element.V3DBox;
import fr.def.iss.vd2.lib_v3d.element.V3DColorElement;
import fr.def.iss.vd2.lib_v3d.element.V3DElement;
import fr.def.iss.vd2.lib_v3d.element.V3DGroupElement;
import fr.def.iss.vd2.lib_v3d.element.V3DrawElement;
import fr.def.iss.vd2.lib_v3d.element.V3DBox.RenderMode;

public class GenericSkin extends Skin {

    private V3DGroupElement elements;

    public GenericSkin(V3DContext context, WorldObject object) {
        elements = new V3DGroupElement(context);
        
        for (final Part part : object.getParts()) {

                final V3DBox box = new V3DBox(context);
                box.setRenderMode(RenderMode.SOLID);

                TransformMatrix transform = part.getTransform();
                //box.setTransformMatrix(transform.toFloatBuffer());

                box.setSize(part.getShape().toV3DVect3());


                final V3DGroupElement element = new V3DGroupElement(context);
                //if (inShip) {
                element.add(new V3DColorElement(box, V3DColor.blue));
                /*} else {
                    element = new V3DColorElement(box, V3DColor.red);
                }*/
                    
                final V3DBox max = new V3DBox(context);
                max.setRenderMode(RenderMode.PLAIN);
                max.setPosition(part.getShape().toV3DVect3().divideBy(2));
                max.setSize(new V3DVect3(0.1f, 0.1f, 0.1f));
                
                element.add(new V3DColorElement(max, V3DColor.red));
                

                transform.addListener(new TransformMatrixChangeListener() {

                    @Override
                    public void valueChanged() {
                        element.setTransformMatrix(part.getTransform().toFloatBuffer());
                    }
                });
                elements.add(element);
        }
    }

    @Override
    public boolean isAnimated() {
        return false;
    }

    @Override
    public V3DElement getElement() {
        return elements;
    }

}