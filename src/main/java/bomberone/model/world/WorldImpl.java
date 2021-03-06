package bomberone.model.world;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import bomberone.controllers.match.event.ExplosionEvent;
import bomberone.controllers.match.event.HitBorderEvent;
import bomberone.controllers.match.event.HitEntityEvent;
import bomberone.controllers.match.event.PickPowerUpEvent;
import bomberone.controllers.match.event.WorldEventListener;
import bomberone.model.bomber.BomberImpl;
import bomberone.model.bombergameboard.BomberOneBoard;
import bomberone.model.common.Maps;
import bomberone.model.common.P2d;
import bomberone.model.enemy.Enemy;
import bomberone.model.enemy.EnemyImpl;
import bomberone.model.factory.GameObjectFactory;
import bomberone.model.factory.GameObjectFactoryImpl;
import bomberone.model.gameObjects.GameObject;
import bomberone.model.gameObjects.bomb.Bomb;
import bomberone.model.gameObjects.box.Box;
import bomberone.model.gameObjects.fire.Fire;
import bomberone.model.gameObjects.powerUp.PowerUp;
import bomberone.model.match.Difficulty;
import bomberone.model.pathfinding.gameboard.BoardPoint;
import bomberone.model.pathfinding.gameboard.Markers;
import bomberone.model.user.Skins;
import bomberone.model.world.collection.GameObjectCollection;
import bomberone.model.world.collection.GameObjectCollectionImpl;

public class WorldImpl implements World {

    /**
     * Constant to set the number of enemies.
     */
    private static final int ENEMYNUMBER = 3;

    /**
     * Size of the playground.
     */
    private static final int DIMENSION = 18;
    private static final int FRAME = 32;
    /**
     * Number of PowerUp's types.
     */
    private static final int NUMTYPEPOWERUP = 5;

    private GameObjectCollection collection = new GameObjectCollectionImpl();
    private GameObjectFactory objectFactory = new GameObjectFactoryImpl();
    private WorldEventListener listener;
    private BomberImpl bomberMan;
    private boolean respawn;
    private Difficulty difficulty;
    private List<List<String>> mapLayout;

    public WorldImpl(final Difficulty difficulty, final Skins skin) {
        this.difficulty = difficulty;
        if (difficulty.equals(Difficulty.EASY)) {
            this.respawn = false;
        } else {
            this.respawn = true;
        }
        this.bomberMan = (BomberImpl) this.objectFactory.createBomber(new P2d(32, 32), skin);
        this.mapLayout = Maps.MAP1.getList();
        this.setHardWall();
        this.setEnemy(WorldImpl.ENEMYNUMBER);
        this.setBox(this.difficulty);
    }

    /**
     * This method create the enemies at the start of the game.
     */
    private void setEnemy(final int n) {
        for (int i = 0; i < n; i++) {
            this.collection
                    .spawn(this.objectFactory.createEnemy(
                            new P2d((WorldImpl.DIMENSION / 2) * WorldImpl.FRAME - WorldImpl.FRAME / 2,
                                    (WorldImpl.DIMENSION / 2) * WorldImpl.FRAME - WorldImpl.FRAME / 2),
                            this.difficulty));
        }
    }

    /**
     * This method creates all HardWall in the World.
     */
    private void setHardWall() {
        for (int y = 0; y < WorldImpl.DIMENSION; y++) {
            for (int x = 0; x < WorldImpl.DIMENSION; x++) {
                if (this.mapLayout.get(y).get(x).equals("H")) {
                    this.collection.spawn(
                            this.objectFactory.createHardWall(new P2d(x * WorldImpl.FRAME, y * WorldImpl.FRAME)));
                }
            }
        }
    }

    /**
     * This method creates all HardWall in the World.
     */
    private void setBox(final Difficulty difficulty) {
        int powerUpCount = 0;
        int boxCount = 0;
        List<P2d> objectList = this.collection.getGameObjectCollection().stream().map(e -> e.getPosition())
                .collect(Collectors.toList());
        objectList.add(this.bomberMan.getPosition());
        int center = (WorldImpl.DIMENSION / 2) - 2;
        for (int i = center; i <= (WorldImpl.DIMENSION / 2) + 2; i++) {
            for (int j = center; j <= (WorldImpl.DIMENSION / 2) + 2; j++) {
                objectList.add(new P2d(i * WorldImpl.FRAME, j * WorldImpl.FRAME));
            }
        }
        objectList.add(
                new P2d(this.bomberMan.getPosition().getX() + WorldImpl.FRAME, this.bomberMan.getPosition().getY()));
        objectList.add(
                new P2d(this.bomberMan.getPosition().getX(), this.bomberMan.getPosition().getY() + WorldImpl.FRAME));
        Random rand = new Random();
        while (boxCount < this.difficulty.getNumBox()) {
            int col = rand.nextInt(WorldImpl.DIMENSION);
            int line = rand.nextInt(WorldImpl.DIMENSION);
            P2d pos = new P2d(line * WorldImpl.FRAME, col * WorldImpl.FRAME);
            if (!objectList.contains(pos)) {
                objectList.add(pos);
                Box box = (Box) this.objectFactory.createBox(pos);
                if (boxCount % 4 == 0) {
                    PowerUp powerUp;
                    switch (powerUpCount % WorldImpl.NUMTYPEPOWERUP) {
                    case 0:
                        powerUp = (PowerUp) this.objectFactory.createPowerUp(pos, PowerUp.Type.FirePower);
                        break;
                    case 1:
                        powerUp = (PowerUp) this.objectFactory.createPowerUp(pos, PowerUp.Type.Speed);
                        break;
                    case 2:
                        powerUp = (PowerUp) this.objectFactory.createPowerUp(pos, PowerUp.Type.Pierce);
                        break;
                    case 3:
                        powerUp = (PowerUp) this.objectFactory.createPowerUp(pos, PowerUp.Type.Time);
                        break;
                    default:
                        powerUp = (PowerUp) this.objectFactory.createPowerUp(pos, PowerUp.Type.Ammo);
                        break;
                    }
                    powerUpCount++;
                    box.addPowerUp(powerUp);
                    this.collection.spawn(powerUp);
                }
                this.collection.spawn(box);
                boxCount++;
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean getRespawn() {
        return this.respawn;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final GameObjectCollection getGameObjectCollection() {
        return this.collection;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final GameObjectFactory getGameObjectFactory() {
        return this.objectFactory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setEventListener(final WorldEventListener event) {
        this.listener = event;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final BomberImpl getBomber() {
        return this.bomberMan;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void updateState(final int time) {
        if (!this.bomberMan.isAlive()) {
            List<Enemy> enemyList = this.collection.getEnemyList();
            for (Enemy enemy : enemyList) {
                this.collection.despawn(enemy);
            }
            this.setEnemy(enemyList.size());
        }

        BomberOneBoard.getInstance().resetItem(Markers.SPOT);
        this.bomberMan.update(time);
        BoardPoint bomberPosition = BomberOneBoard.getInstance().convertPosition(this.bomberMan.getPosition(),
                Markers.SPOT);
        BomberOneBoard.getInstance().setItem(bomberPosition);
        for (GameObject obj : this.collection.getGameObjectCollection()) {
            obj.update(time);
        }

        BomberOneBoard.getInstance().resetAllItems(Markers.REMOVABLE);
        for (Box currentBox : collection.getBoxList()) {
            BoardPoint currentBoxPosition = BomberOneBoard.getInstance().convertPosition(currentBox.getPosition(),
                    Markers.REMOVABLE);
            BomberOneBoard.getInstance().setItem(currentBoxPosition);
        }

        this.checkExplosion();
        List<GameObject> deathObject = this.collection.getDespawnedObject();
        for (GameObject obj : deathObject) {
            this.collection.despawn(obj);
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void checkCollision() {
        List<Fire> fireList = this.collection.getFireList();
        List<GameObject> list = new LinkedList<>();
        list.add(bomberMan);
        list.addAll(this.collection.getBoxList());
        list.addAll(this.collection.getEnemyList());
        for (GameObject obj : list) {
            for (Fire fire : fireList) {
                if (fire.getCollider().intersects(obj.getCollider())) {
                    /* When Enemy is just spawned, it isn't hittable by fire */
                    if (obj.getClass().equals(EnemyImpl.class) && !((Enemy) obj).isHittable()) {
                        break;
                    } else {
                        this.listener.notifyEvent(new HitEntityEvent(obj));
                    }
                }
            }
        }
        List<PowerUp> powerUpList = collection.getPowerUpList().stream().filter(p -> p.isReleased())
                .collect(Collectors.toList());
        for (PowerUp power : powerUpList) {
            if (power.getCollider().intersects(bomberMan.getCollider())) {
                this.listener.notifyEvent(new PickPowerUpEvent(power));
            }
        }
        this.collection.getEnemyList().stream().forEach(enemy -> {
            if (enemy.getCollider().intersects(this.bomberMan.getCollider())) {
                this.listener.notifyEvent(new HitEntityEvent(this.bomberMan));
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void checkRespawn() {
        if (this.collection.getBoxList().size() == 0) {
            this.respawn = false;
        }
        if (this.respawn) {
            if (this.collection.getEnemyList().size() != WorldImpl.ENEMYNUMBER) {
                this.collection
                        .spawn(objectFactory.createEnemy(
                                new P2d((WorldImpl.DIMENSION / 2) * WorldImpl.FRAME - WorldImpl.FRAME / 2,
                                        (WorldImpl.DIMENSION / 2) * WorldImpl.FRAME - WorldImpl.FRAME / 2),
                                this.difficulty));
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void checkBoundary() {
        List<Enemy> enemyList = this.collection.getEnemyList();
        List<GameObject> wallBoxList = new LinkedList<>();
        wallBoxList.addAll(this.collection.getHardWallList());
        wallBoxList.addAll(this.collection.getBoxList());
        for (GameObject wall : wallBoxList) {
            if (wall.getCollider().intersects(this.bomberMan.getCollider())) {
                this.listener.notifyEvent(new HitBorderEvent(this.bomberMan, wall));
            }
        }
        for (Enemy enemy : enemyList) {
            for (GameObject wall : wallBoxList) {
                if (wall.getCollider().intersects(enemy.getCollider())) {
                    this.listener.notifyEvent(new HitBorderEvent(enemy, wall));
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void checkExplosion() {
        List<Bomb> bombList = this.collection.getBombList();
        for (Bomb bomb : bombList) {
            if (!bomb.getExplosion().equals(Optional.empty())) {
                this.listener.notifyEvent(new ExplosionEvent(bomb.getExplosion().get()));
            }
        }
    }

}
