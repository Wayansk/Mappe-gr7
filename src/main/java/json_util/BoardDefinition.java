package json_util;
import java.util.List;

public class BoardDefinition {
  private int size;
  private List<Jump> ladders;
  private List<Jump> snakes;

  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }

  public List<Jump> getLadders() {
    return ladders;
  }

  public void setLadders(List<Jump> ladders) {
    this.ladders = ladders;
  }

  public List<Jump> getSnakes() {
    return snakes;
  }

  public void setSnakes(List<Jump> snakes) {
    this.snakes = snakes;
  }
}
