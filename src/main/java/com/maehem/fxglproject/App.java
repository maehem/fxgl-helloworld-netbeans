package com.maehem.fxglproject;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.Camera3D;
import com.almasb.fxgl.dsl.EntityBuilder;
import com.almasb.fxgl.dsl.FXGL;
import static com.almasb.fxgl.dsl.FXGL.getGameScene;
import com.almasb.fxgl.entity.components.TransformComponent;
import javafx.geometry.Point3D;
import javafx.scene.Cursor;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

/**
 * JavaFX App
 */
public class App extends GameApplication {

    private Camera3D camera;
    private boolean titleSet = false;

    @Override
    protected void initSettings(GameSettings gs) {
        gs.setWidth(800);
        gs.setHeight(600);
        gs.set3D(true);
    }

    @Override
    protected void initGame() {
        // disable big mouse cursor
        getGameScene().getRoot().setCursor(Cursor.DEFAULT);

        // Set your custom cursor: (searched for in assets/ui/cursors/ )
        // getGameScene().setCursor(String imageName, Point2D hotspot);
        camera = getGameScene().getCamera3D();
        TransformComponent t = camera.getTransform();
        t.translateX(-10);
        t.translateY(-10);
        //t.translateZ(10);
        t.lookAt(new Point3D(0, 0, 0));

        var box = new Box(10.0, 10.0, 10.0);
        var material = new PhongMaterial(Color.DARKGREY);
        box.setMaterial(material);
        new EntityBuilder()
                .at(0, 0, 0)
                .view(box)
                .buildAndAttach();
    }

    @Override
    protected void onUpdate(double tpf) {
        // JavaFX and FXGL versions not available until after game inits.
        if (!titleSet) {
            var javaVersion = System.getProperty("java.version");
            var javafxVersion = System.getProperty("javafx.version");
            var fxglVersion = FXGL.getVersion();

            String label = "Basic FXGL App :: Java: " + javaVersion + " with JavaFX: " + javafxVersion + " and FXGL: " + fxglVersion;
            FXGL.getPrimaryStage().setTitle(label);
            titleSet = true;
        }
    }

    @Override
    protected void initInput() {
        //super.initInput();
        getGameScene().getRoot().setOnScroll((t) -> {
            TransformComponent tr = camera.getTransform();
            tr.translateZ(t.getDeltaY());
            tr.lookAt(new Point3D(0, 0, 0));
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
