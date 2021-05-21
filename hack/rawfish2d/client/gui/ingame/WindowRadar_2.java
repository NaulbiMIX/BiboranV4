package hack.rawfish2d.client.gui.ingame;

import java.awt.image.BufferedImage;

import org.lwjgl.opengl.GL11;

import hack.rawfish2d.client.utils.Coordinates;
import hack.rawfish2d.client.utils.R2DUtils;
import hack.rawfish2d.client.utils.TimeHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.src.Block;
import net.minecraft.src.Chunk;
import net.minecraft.src.MapColor;
import net.minecraft.src.MathHelper;
import net.minecraft.src.Tessellator;

public class WindowRadar_2 implements IGuiElement {
	public byte[] colors = new byte[16384];
	public int bufferedImage;
	public int[] intArray;
	//public TextureButton imageButton;
	public Coordinates lastPos;
	//public Counter msCounter = new Counter(50L, 1);
	private Minecraft mc;
	
	int x;
	int y;
	int w;
	int h;
	
	public WindowRadar_2(Minecraft mc, int x, int y, int w, int h) {
		this.mc = mc;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		
		this.bufferedImage = this.mc.renderEngine.allocateAndSetupTexture(new BufferedImage(128, 128, 2));
		this.intArray = new int[16384];
		for (int var4 = 0; var4 < 16384; ++var4) {
			this.intArray[var4] = 0;
		}
	}

	public void render(int mouseX, int mouseY) {
		//super.render(mouseX, mouseY);
		if (this.shouldRenderBody()) {
			int x2 = this.getX() + 2;
			int y2 = this.getY() + 14;
			int width = this.getW() - 4;
			int height = this.getH() - 16;
			this.renderMap(x2, y2, width, height);
			GL11.glPushMatrix();
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			//GL11.glEnable(GL11.GL_BLEND);
			//GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			
			GL11.glTranslatef((float)(x2 + width / 2), (float)(y2 + height / 2), (float)0.0f);
			GL11.glRotatef((float)180.0f, (float)0.0f, (float)0.0f, (float)1.0f);
			GL11.glRotatef((float)this.mc.thePlayer.rotationYaw, (float)0.0f, (float)0.0f, (float)1.0f);
			GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)0.6f);
			GL11.glLineWidth((float)1.0f);
			
			this.renderTriangle(0, 0, 2, 5);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glPopMatrix();
		}
	}

	private boolean shouldRenderBody() {
		return true;
	}

	public void renderTriangle(int x2, int y2, int width, int height) {
		GL11.glBegin(GL11.GL_TRIANGLES);
		GL11.glVertex2f((float)x2, (float)(-height));
		GL11.glVertex2f((float)(-width), (float)y2);
		GL11.glVertex2f((float)width, (float)y2);
		GL11.glEnd();
		//GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		
		GL11.glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex2f((float)width, (float)y2);
		GL11.glVertex2f((float)(-width), (float)y2);
		GL11.glVertex2f((float)width, (float)y2);
		GL11.glVertex2f((float)x2, (float)(-height));
		GL11.glVertex2f((float)(-width), (float)y2);
		GL11.glVertex2f((float)x2, (float)(-height));
		GL11.glEnd();
		
		GL11.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);
		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex2f((float)width, (float)y2);
		GL11.glVertex2f((float)(-width), (float)y2);
		GL11.glVertex2f((float)width, (float)y2);
		GL11.glVertex2f((float)x2, (float)(-height));
		GL11.glVertex2f((float)(-width), (float)y2);
		GL11.glVertex2f((float)x2, (float)(-height));
		GL11.glEnd();
	}

	public void renderMap(int x2, int y2, int width, int height) {
		for (int var4 = 0; var4 < 16384; ++var4) {
			byte var5 = this.colors[var4];
			if (var5 / 4 == 0) {
				this.intArray[var4] = (var4 + var4 / 128 & 1) * 8 + 16 << 24;
				continue;
			}
			int var6 = MapColor.mapColorArray[var5 / 4].colorValue;
			int var7 = var5 & 3;
			int var8 = 220;
			if (var7 == 2) {
				var8 = 255;
			}
			if (var7 == 0) {
				var8 = 180;
			}
			int var9 = (var6 >> 16 & 255) * var8 / 255;
			int var10 = (var6 >> 8 & 255) * var8 / 255;
			int var11 = (var6 & 255) * var8 / 255;
			if (this.mc.gameSettings.anaglyph) {
				int var12 = (var9 * 30 + var10 * 59 + var11 * 11) / 100;
				int var13 = (var9 * 30 + var10 * 70) / 100;
				int var14 = (var9 * 30 + var11 * 70) / 100;
				var9 = var12;
				var10 = var13;
				var11 = var14;
			}
			this.intArray[var4] = -16777216 | var9 << 16 | var10 << 8 | var11;
		}
		this.mc.renderEngine.createTextureFromBytes(this.intArray, 128, 128, this.bufferedImage);
		Tessellator tesselator = Tessellator.instance;
		float var18 = 0.0f;
		GL11.glBindTexture((int)3553, (int)this.bufferedImage);
		GL11.glDisable((int)2929);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
		GL11.glHint((int)3155, (int)4354);
		GL11.glEnable((int)3553);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		tesselator.startDrawingQuads();
		//tesselator.setColorOpaque_F(1.0f, 1.0f, 1.0f);
		tesselator.addVertexWithUV((float)(x2 + 0) + var18, (float)(y2 + height) - var18, -0.009999999776482582, 0.0, 1.0);
		tesselator.addVertexWithUV((float)(x2 + width) - var18, (float)(y2 + height) - var18, -0.009999999776482582, 1.0, 1.0);
		tesselator.addVertexWithUV((float)(x2 + width) - var18, (float)(y2 + 0) + var18, -0.009999999776482582, 1.0, 0.0);
		tesselator.addVertexWithUV((float)(x2 + 0) + var18, (float)(y2 + 0) + var18, -0.009999999776482582, 0.0, 0.0);
		tesselator.draw();
		GL11.glDisable((int)3042);
		GL11.glEnable((int)2929);
		GL11.glPushMatrix();
		GL11.glTranslatef((float)0.0f, (float)0.0f, (float)-0.04f);
		GL11.glScalef((float)1.0f, (float)1.0f, (float)1.0f);
		GL11.glPopMatrix();
	}

	public void updateMap(int scale) {
		int var4 = 128;
		int var5 = 128;
		int var6 = 1 << scale;
		int var7 = (int)this.mc.thePlayer.posX;
		int var8 = (int)this.mc.thePlayer.posZ;
		int var9 = MathHelper.floor_double(this.mc.thePlayer.posX - (double)var7) / var6 + var4 / 2;
		int var10 = MathHelper.floor_double(this.mc.thePlayer.posZ - (double)var8) / var6 + var5 / 2;
		int var11 = 128 / var6;
		
		if (this.mc.theWorld.provider.hasNoSky) {
			var11 /= 2;
		}
		
		for (int var13 = var9 - var11 + 1; var13 < var9 + var11; ++var13) {
			int var14 = 255;
			int var15 = 0;
			double var16 = 0.0;
			for (int var18 = var10 - var11 - 1; var18 < var10 + var11; ++var18) {
				int var36;
				byte var38;
				int var33;
				byte var41;
				int var32;
				int var31;
				
				if (var13 < 0 || var18 < -1 || var13 >= var4 || var18 >= var5)
					continue;
				
				int var19 = var13 - var9;
				int var20 = var18 - var10;
				boolean var21 = var19 * var19 + var20 * var20 > (var11 - 2) * (var11 - 2);
				int var22 = (var7 / var6 + var13 - var4 / 2) * var6;
				int var23 = (var8 / var6 + var18 - var5 / 2) * var6;
				int[] var24 = new int[256];
				
				Chunk var25 = this.mc.theWorld.getChunkFromBlockCoords(var22, var23);
				
				if (var25.isEmpty())
					continue;
				
				int var26 = var22 & 15;
				int var27 = var23 & 15;
				int var28 = 0;
				double var29 = 0.0;
				if (this.mc.theWorld.provider.hasNoSky) {
					var31 = var22 + var23 * 231871;
					if (((var31 = var31 * var31 * 31287121 + var31 * 11) >> 20 & 1) == 0) {
						int[] arrn = var24;
						int n2 = Block.dirt.blockID;
						arrn[n2] = arrn[n2] + 10;
					} else {
						int[] arrn = var24;
						int n3 = Block.stone.blockID;
						arrn[n3] = arrn[n3] + 10;
					}
					var29 = 100.0;
				} else {
					for (var31 = 0; var31 < var6; ++var31) {
						for (var32 = 0; var32 < var6; ++var32) {
							var33 = var25.getHeightValue(var31 + var26, var32 + var27) + 1;
							int var34 = 0;
							if (var33 > 1) {
								boolean var35;
								do {
									var35 = true;
									var34 = var25.getBlockID(var31 + var26, var33 - 1, var32 + var27);
									
									if (var34 == 0) {
										var35 = false;
									} else if (var33 > 0 && var34 > 0 && Block.blocksList[var34].blockMaterial.materialMapColor == MapColor.airColor) {
										var35 = false;
									}
									
									if (var35)
										continue;
									
									if (--var33 <= 0)
										break;
									
									var34 = var25.getBlockID(var31 + var26, var33 - 1, var32 + var27);
								} while (var33 > 0 && !var35);
								if (var33 > 0 && var34 != 0 && Block.blocksList[var34].blockMaterial.isLiquid()) {
									int var43;
									var36 = var33 - 1;
									boolean var37 = false;
									do {
										var43 = var25.getBlockID(var31 + var26, var36--, var32 + var27);
										++var28;
									} while (var36 > 0 && var43 != 0 && Block.blocksList[var43].blockMaterial.isLiquid());
								}
							}
							var29 += (double)var33 / (double)(var6 * var6);
							int[] arrn = var24;
							int n4 = var34;
							arrn[n4] = arrn[n4] + 1;
						}
					}
				}
				var28 /= var6 * var6;
				var31 = 0;
				var32 = 0;
				for (var33 = 0; var33 < 256; ++var33) {
					if (var24[var33] <= var31) continue;
					var32 = var33;
					var31 = var24[var33];
				}
				double var40 = (var29 - var16) * 4.0 / (double)(var6 + 4) + ((double)(var13 + var18 & 1) - 0.5) * 0.4;
				int var39 = 1;
				if (var40 > 0.6) {
					var39 = 2;
				}
				if (var40 < -0.6) {
					var39 = 0;
				}
				var36 = 0;
				if (var32 > 0) {
					MapColor var42 = Block.blocksList[var32].blockMaterial.materialMapColor;
					if (var42 == MapColor.waterColor) {
						var40 = (double)var28 * 0.1 + (double)(var13 + var18 & 1) * 0.2;
						var39 = 1;
						if (var40 < 0.5) {
							var39 = 2;
						}
						if (var40 > 0.9) {
							var39 = 0;
						}
					}
					var36 = var42.colorIndex;
				}
				var16 = var29;
				if (var18 < 0 || var19 * var19 + var20 * var20 >= var11 * var11 || var21 && (var13 + var18 & 1) == 0 || (var41 = this.colors[var13 + var18 * var4]) == (var38 = (byte)(var36 * 4 + var39))) continue;
				if (var14 > var18) {
					var14 = var18;
				}
				if (var15 < var18) {
					var15 = var18;
				}
				this.colors[var13 + var18 * var4] = var38;
			}
		}
	}
	
	private TimeHelper time = new TimeHelper();
	//Executed 20 times in 1 seconds
	public void onUpdate() {
		if(!time.hasReached(50))
			return;
		
		time.reset();
		
		if (this.lastPos == null) {
			this.lastPos = new Coordinates((int)this.mc.thePlayer.posX, (int)this.mc.thePlayer.posY, (int)this.mc.thePlayer.posZ);
		}
		
		Coordinates crd = new Coordinates((int)this.mc.thePlayer.posX, (int)this.mc.thePlayer.posY, (int)this.mc.thePlayer.posZ);
		
		if (
				this.lastPos.getX() != crd.getX() ||
				this.lastPos.getY() != crd.getY() ||
				this.lastPos.getZ() != crd.getZ()) {
			this.updateMap(0);
			this.lastPos = new Coordinates((int)this.mc.thePlayer.posX, (int)this.mc.thePlayer.posY, (int)this.mc.thePlayer.posZ);
		}
	}

	/*
	@Override
	public void onEvent(Event event) {
		if (event instanceof EventTick && this.shouldRenderBody() && (this.mc.s instanceof PanelScreen || this.isPinned())) {
			this.msCounter.count();
			if (this.lastPos == null) {
				this.lastPos = new Coordinates(this.mc.g.u, this.mc.g.v, this.mc.g.w);
			}
			if (new Coordinates(this.mc.g.u, this.mc.g.v, this.mc.g.w).getDifference(this.lastPos) != 0.0 && this.msCounter.getCount() > 1) {
				this.updateMap(0);
				this.msCounter.reset();
				this.lastPos = new Coordinates(this.mc.g.u, this.mc.g.v, this.mc.g.w);
			}
		}
	}
	*/

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public int getW() {
		return w;
	}

	@Override
	public int getH() {
		return h;
	}

	@Override
	public String text() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTextWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isPinned() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void draw(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(char ch, int ivar) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseOver(int mx, int my) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(int mx, int my, int button) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMovedOrUp(int x, int y, int button) {
		// TODO Auto-generated method stub
		
	}
}

