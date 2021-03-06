package com.irr310.client.graphics;

import com.irr310.common.event.EngineEventVisitor;
import com.irr310.common.event.system.SystemEventVisitor;

import fr.def.iss.vd2.lib_v3d.camera.V3DCameraBinding;
import fr.def.iss.vd2.lib_v3d.gui.V3DGuiLayer;

public interface GraphicRenderer {

    V3DCameraBinding getCameraBinding();

    void init();

    void frame();

    SystemEventVisitor getEventVisitor();

    void resetGui();

    V3DGuiLayer getPopupLayer();

}
