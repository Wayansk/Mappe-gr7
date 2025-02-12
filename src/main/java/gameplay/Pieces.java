package gameplay;

public class Pieces {

    private int gamePieces;

    public Pieces(int gamePieces){
        setNumberOfPieces(gamePieces);
    }








    public void setNumberOfPieces(int gamePieces){
        this.gamePieces = gamePieces;
    }

    public int getPieces(){
        return gamePieces;
    }

}
