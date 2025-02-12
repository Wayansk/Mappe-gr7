package main;


import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class GamePanel {
  private static final int WIDTH = 800;
  private static final int HEIGHT = 600;
  private Canvas canvas;
  private GraphicsContext graphicsContext;

  public GamePanel() {
    canvas = new Canvas(WIDTH, HEIGHT);
    graphicsContext = canvas.getGraphicsContext2D();
    render();
  }

  public Canvas getCanvas() {
    return canvas;
  }

  private void render() {
    // background color
    graphicsContext.setFill(Color.LIGHTGOLDENRODYELLOW);
    graphicsContext.fillRect(0, 0, WIDTH, HEIGHT);

    // placeholder rectangle as a "game element"
    graphicsContext.setFill(Color.BISQUE);
    graphicsContext.fillRect(100, 100, 50, 50);
  }
}
