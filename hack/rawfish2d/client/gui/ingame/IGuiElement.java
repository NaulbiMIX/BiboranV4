package hack.rawfish2d.client.gui.ingame;

public interface IGuiElement {
	public int getX();
	public int getY();
	public int getW();
	public int getH();
	public String text();
	public int getTextWidth();
	
	public boolean isPinned();
	
	public void draw(int x, int y);
	public void keyTyped(char ch, int ivar);
	public void mouseOver(int mx, int my);
	public void mouseClicked(int mx, int my, int button);
	public void mouseMovedOrUp(int x, int y, int button);
}
