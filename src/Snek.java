public class Snek {
    private int X,Y;
    private Character[][] vision;
    private boolean big;
    private int value, moves;
    private Maze home;
    public Snek(int x, int y, Character[][] vis, Maze maze){
        X = x;
        Y = y;
        big = false;
        vision = vis;
        value = 0;
        home = maze;
        moves = 0;
    }

    public void getsEaten(){
        if(isBig()){
            setBig(false);
        }
        else {
            home.endGame(false);
        }
    }

    public String getVision(){
        StringBuilder result = new StringBuilder();
        for (Character[] line:vision){
            for(Character part:line){
                if(part!=null) {
                    result.append(part);
                } else {
                    result.append(' ');
                }
            }
            result.append('\n');
        }
        return result.toString();
    }

    public void eatMouse(){
        setBig(true);
        value += 1;
    }

    public void move(int dir){
        moves++;
        switch (dir){
            case (0) : if(vision[2][3]==null){
                home.endGame(true);
            } else if(vision[2][3] != '-' && vision[2][3] != '+'){
                setY(Y-1);
            }return;

            case (1) : if(vision[3][4]==null){
                home.endGame(true);
            } else if(vision[3][4] != '|' && vision[3][4] != '+'){
                setX(X+1);
            }return;

            case (2) : if(vision[4][3]==null){
                home.endGame(true);
            } else if(vision[4][3] != '-' && vision[4][3] != '+'){
                setY(Y+1);
            }return;

            case (3) : if(vision[3][2]==null){
                home.endGame(true);
            } else if(vision[3][2] != '|' && vision[3][2] != '+'){
                setX(X-1);
            }
        }
    }

    int getX() {
        return X;
    }

    void setX(int x) {
        X = x;
    }

    int getY() {
        return Y;
    }

    void setY(int y) {
        Y = y;
    }

    boolean isBig() {
        return big;
    }

    void setBig(boolean grow) {
        big = grow;
    }

    void setVision(Character[][] vis){
        vision = vis;
    }

    Character getSnek(){
        if(big){
            return 'S';
        } else {
            return 's';
        }
    }

    int getValue(){
        return value;
    }

    void foundExit(int amount){
        value+=amount-moves;
    }
}
