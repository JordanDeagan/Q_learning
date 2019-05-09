import java.util.Random;

class Snek {
    private int X,Y;
    private static final int UP = 0, RIGHT = 1, DOWN = 2, LEFT = 3;
    private Character[][] vision;
    private boolean big;
    private int value, moves;
    private Maze home;
    private Random rand;

    Snek(int x, int y, Character[][] vis, Maze maze){
        X = x;
        Y = y;
        big = false;
        vision = vis;
        value = 0;
        home = maze;
        moves = 0;
        rand = new Random();
    }

    void getsEaten(){
        if(isBig()){
            setBig(false);
        }
        else {
            value = 0;
            home.endGame(1);
        }
    }

    int getNumMoves(){
        return moves;
    }

    String getSight(){
        StringBuilder result = new StringBuilder();
        for (Character[] line:vision){
            for(Character part:line){
                result.append(part);
            }
        }
        return result.toString();
    }

    Character[][] getVision(){
        return vision;
    }

    void eatMouse(){
        setBig(true);
        value += 1;
    }

    void move(int dir){
        moves++;
        switch (dir){
            case (UP) : if(vision[2][3]==null){
                home.endGame(0);
            } else {
                if(vision[2][3] != '-' && vision[2][3] != '+'){
                    setY(Y-1);
                } else {
                    moves--;
                    move(rand.nextInt(4));
                }
            }return;

            case (RIGHT) : if(vision[3][4]==null){
                home.endGame(0);
            }  else {
                if (vision[3][4] != '|' && vision[3][4] != '+') {
                    setX(X + 1);
                } else {
                    moves--;
                    move(rand.nextInt(4));
                }
            }return;

            case (DOWN) : if(vision[4][3]==null){
                home.endGame(0);
            }  else {
                if (vision[4][3] != '-' && vision[4][3] != '+') {
                    setY(Y + 1);
                } else {
                    moves--;
                    move(rand.nextInt(4));
                }
            }return;

            case (LEFT) : if(vision[3][2]==null){
                home.endGame(0);
            }  else {
                if (vision[3][2] != '|' && vision[3][2] != '+') {
                    setX(X - 1);
                } else {
                    moves--;
                    move(rand.nextInt(4));
                }
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

    Character getSize(){
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
