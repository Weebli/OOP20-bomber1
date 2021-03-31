package bomberOne.model.gameObjects;

import java.awt.image.BufferedImage;

import bomberOne.model.common.P2d;
import bomberOne.model.physics.BoundingBox;
import bomberOne.model.physics.BoundingBoxImpl;

public abstract class GameObjectImpl implements GameObject {
	

	private static final int RECTDIMENTIONS = 32;
	
	protected double speed;
	protected BufferedImage img;
	protected P2d position;
	protected BoundingBox collider;
	protected int lifes;
	protected boolean isAlive;
	protected boolean isBreakable;
	
	public GameObjectImpl(P2d pos, BufferedImage img, int lifes, boolean isBreakable) {
		this.position = pos;
		this.img = img;
		this.lifes = lifes;
		this.isBreakable = isBreakable;
		this.isAlive = true;
		this.collider = new BoundingBoxImpl(this.position, new P2d(this.position.x + RECTDIMENTIONS, this.position.y + RECTDIMENTIONS));
	}

	@Override
	public BufferedImage getImage() {
		return img;
	}

	@Override
	public abstract void update();

	@Override
	public P2d getPosition() {
		return this.position;
	}

	@Override
	public BoundingBox getBoundingBox() {
		return this.collider;
	}

	@Override
	public double getSpeed() {
		return this.speed;
	}

	@Override
	public int getLifes() {
		return this.lifes;
	}

	@Override
	public boolean isAlive() {
		return this.isAlive;
	}

	@Override
	public boolean isHitted() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void hitted() {
		this.lifes--;
	}

	@Override
	public boolean isBreakable() {
		return this.isBreakable;
	}

	@Override
	public void setImage(BufferedImage img) {
		this.img = img;
	}

}