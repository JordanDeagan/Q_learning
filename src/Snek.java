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

    public void printVision(){
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
        System.out.println(result.toString());
    }

    public void eatMouse(){
        setBig(true);
        value += 1;
    }

    public void move(int dir){
        moves++;
        switch (dir){
            case (0) : if(vision[2][3]==null){home.endGame(true); return;}else if(vision[2][3] != '-'){ setY(Y-1);}return;
            case (1) : if(vision[3][4]==null){home.endGame(true); return;}else if(vision[3][4] != '|'){ setX(X+1);}return;
            case (2) : if(vision[4][3]==null){home.endGame(true); return;}else if(vision[4][3] != '-'){ setY(Y+1);}return;
            case (3) : if(vision[3][2]==null){home.endGame(true); return;}else if(vision[3][2] != '|'){ setX(X-1);}
        }
    }

    public int getX() {
        return X;
    }

    public void setX(int x) {
        X = x;
    }

    public int getY() {
        return Y;
    }

    public void setY(int y) {
        Y = y;
    }

    public boolean isBig() {
        return big;
    }

    public void setBig(boolean grow) {
        big = grow;
    }

    public void setVision(Character[][] vis){
        vision = vis;
    }

    public Character getSnek(){
        if(big){
            return 'S';
        } else {
            return 's';
        }
    }

    public int getValue(){
        return value;
    }

    public void foundExit(int amount){
        value+=amount-moves;
    }
}
