package io.github.eng1group9.systems;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Rectangle;

import java.util.LinkedList;
import java.util.List;

public class CollisionSystem {
    private List<Rectangle> worldCollision;
    private List<Rectangle> exitDoorCollision;
    private TiledMap map;

    public void init(TiledMap map) {
        this.map = map;
        MapLayer collisionLayer = map.getLayers().get("Collision");
        MapObjects collisionObjects = collisionLayer.getObjects();
        worldCollision = new LinkedList<>();
        for (MapObject mapObject : collisionObjects) {
            Rectangle nextRectangle = ((RectangleMapObject) mapObject).getRectangle();
            nextRectangle.set(nextRectangle.x * 2,nextRectangle.y * 2, nextRectangle.width * 2, nextRectangle.height * 2);
            worldCollision.add(nextRectangle);
        }


        MapLayer collisionLayer2 = map.getLayers().get("exitdoor collision");
        MapObjects collisionObjects2 = collisionLayer2.getObjects();
        exitDoorCollision = new LinkedList<>();
        for (MapObject mapObject : collisionObjects2) {
            Rectangle nextRectangle = ((RectangleMapObject) mapObject).getRectangle();
            nextRectangle.set(nextRectangle.x * 2,nextRectangle.y * 2, nextRectangle.width * 2, nextRectangle.height * 2);
            exitDoorCollision.add(nextRectangle);
        }
    }

    public List<Rectangle> getWorldCollision() { return worldCollision; }
    public List<Rectangle> getExitDoorCollision() { return exitDoorCollision; }

    // Serches collision layer for name
    // Then deletes from world collision by the rectangle
    public void removeCollisionByName(String name) {
        MapLayer collisionLayer = map.getLayers().get("Collision");
        MapObjects collisionObjects = collisionLayer.getObjects();

        for (MapObject m : collisionObjects) {

            if (m.getName() == null) {
                continue;
            }

            if (!m.getName().equals(name)) {
                continue;
            }

            Rectangle r = ((RectangleMapObject) m).getRectangle();
            worldCollision.remove(r);
        }
    }

    public void deleteKeyTile() {
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get("key");

        // i = 1; j = 7
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 30; j++) {
                Cell cell = layer.getCell(i, j);

                if (cell != null) {
                    cell.setTile(null);
                }
            }
        }
    }

    public boolean safeToMove(float x, float y, Rectangle currentHitbox) {
        Rectangle testHitbox = new Rectangle();
        testHitbox = testHitbox.set(currentHitbox);
        testHitbox.setPosition(x + 16, y + 16);

        for (Rectangle rectangle : worldCollision) {
            if (rectangle.overlaps(testHitbox)) {
                return false;
            }
        }
        if (!io.github.eng1group9.Main.instance.player.hasExitKey()) {
            for (Rectangle rectangle : exitDoorCollision) {
                if (rectangle.overlaps(testHitbox)) {
                    return false;
                }
            }
        }
        return true;
    }
}
