package hack.rawfish2d.client.utils;

import java.awt.Font;
import java.io.InputStream;

import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.util.ResourceLoader;

public class TextRenderer {
	private TrueTypeFont font;
    private TrueTypeFont font2;
    public String fontName;
    public String fontPath;

    public TextRenderer(String fp, String fn) {
    	this.fontPath = fp;
    	this.fontName = fn;
        Font awtFont = new Font(fontName, Font.TRUETYPE_FONT, 16);
        font = new TrueTypeFont(awtFont, true);
        
        try {
    		InputStream inputStream	= ResourceLoader.getResourceAsStream(fp);
     
    		Font awtFont2 = Font.createFont(Font.TRUETYPE_FONT, inputStream);
    		awtFont2 = awtFont2.deriveFont(24f); // set font size
    		font2 = new TrueTypeFont(awtFont2, true);
     
    	} catch (Exception e) {
    		e.printStackTrace();
    	}	
    }
    
    public void drawText(String text, int x, int y, Color color) {
    	if(font == null)
    		return;
        font.drawString((int)x, (int)y, text, Color.yellow);
    }
    
    public void drawText(String text, int x, int y, int color) {
    	if(font == null)
    		return;
        font.drawString((int)x, (int)y, text, MiscUtils.int2Color2(color));
    }
    
    public void drawText2(String text, int x, int y, Color color) {
    	if(font2 == null)
    		return;
        font2.drawString((int)x, (int)y, text, Color.yellow);
    }
    
    public void drawText2(String text, int x, int y, int color) {
    	if(font2 == null)
    		return;
        font2.drawString((int)x, (int)y, text, MiscUtils.int2Color2(color));
    }
}
