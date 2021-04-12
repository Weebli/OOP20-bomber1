package bomberOne.model.gameObject;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import bomberOne.model.common.P2d;
import bomberOne.model.gameObjects.FireImpl;
import bomberOne.tools.img.SpriteMapsObj;

public class TestFire {
	FireImpl fire;
	
	@BeforeEach
	public void init() {
		this.fire = new FireImpl(new P2d(0, 0), SpriteMapsObj.EXPLOSION_SPRITEMAP.getImage(), 1, false);
	}
	
	@Test
	public void testUpdate() {
		for(int c = 0 ; c<210 ;c++) {
			this.fire.update(0);
			assertTrue(this.fire.isAlive());
		}
		this.fire.update(0);
		assertFalse(this.fire.isAlive());
	}
	
	
}