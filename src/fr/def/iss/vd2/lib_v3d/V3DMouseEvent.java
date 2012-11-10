package fr.def.iss.vd2.lib_v3d;

public class V3DMouseEvent extends V3DInputEvent {

    public enum Action {
        MOUSE_DRAGGED, MOUSE_MOVED, MOUSE_CLICKED, MOUSE_PRESSED, MOUSE_RELEASED, MOUSE_WHEEL_DOWN, MOUSE_WHEEL_UP,
    }

    private Action action;

    private final int button;
    private final int y;
    private final int x;

    public V3DMouseEvent(Action action, int x, int y, int button) {
        this.action = action;
        this.x = x;
        this.y = y;
        this.button = button;
    }

    public Action getAction() {
        return action;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getButton() {
        return button;
    }
    
    public V3DMouseEvent relativeTo(int xOffset, int yOffset) {
        return new V3DMouseEvent(action, x - xOffset, y - yOffset, button);
    }

    // public MouseEvent toAwtEvent(Component component) {
    // return new MouseEvent(component, MouseEvent.MOUSE_PRESSED ,
    // System.currentTimeMillis(), 0, x, y, x, y, 0, false, button);
    // }

}
