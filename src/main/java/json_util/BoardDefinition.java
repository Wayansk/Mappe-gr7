package json_util;
import java.util.List;

public class BoardDefinition {
  private int size;
  private int rows;
  private int cols;
  private List<Jump> ladders;
  private List<Jump> snakes;

  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }

  public int getRows() {
    return rows;
  }

  public void setRows(int rows) {
    this.rows = rows;
  }

  public int getCols() {
    return cols;
  }

  public void setCols(int cols) {
    this.cols = cols;
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
