package onc.backend;


/**
 * This interface is only used so that the gameEngine knows when the gameFaceController has a popup.
 * While a popup is present in the gameFaceScene, all actions by the robots in the gameFace should be paused.
 */
public interface InterfacePopupListener {
    public void popupDisplayed();
    public void popupClosed();
}
