import java.util.*;

class Learning {
    private Maze maze;
    private Snek snek;
    private int runs;
    private Double[] averageRuns;
    private Map<String, Map<Integer, Double>> states;
    private String vision, lastVis;
    private Random rand;
    private double rate;

    Learning(Maze home, int learnRate){
        maze = home;
        rand = new Random();
        states = new HashMap<String, Map<Integer, Double>>();
        rate = learnRate;
        runs = 0;
        averageRuns = new Double[500];
        Arrays.fill(averageRuns, 0.0);
    }

    void keepGoing(int howLong){
        while(runs<howLong){
            runThrough();
            runStats();
            maze.reset();
            runs++;
        }
        System.out.println("Maze run");
    }

    Double[] multiTest(int numTests){
        int testsRun = 0;
        while (testsRun < numTests){
            keepGoing(500);
            states = new HashMap<String, Map<Integer, Double>>();
            runs = 0;
            testsRun++;
        }
        for (int i = 0; i<500; i++){
            averageRuns[i] = averageRuns[i]/numTests;
        }
        return averageRuns;
    }

    void runThrough(){
        maze.placeSnek();
        snek = maze.getSnek();
        vision = snek.getSight();
        getState();
        int choice;
        while (!maze.gameOver()){
//            if(snek.getNumMoves()>0 && (snek.getNumMoves()%1000)==0){
//                rate = 3*rate/4;
//            }
//            if(rand.nextInt(101)<rate){
//                choice = rand.nextInt(4);
//            } else {
                choice = chooseMove();
//            }
            maze.update(choice);
            lastVis = vision;
            vision = snek.getSight();
            getState();
            updateState(choice,calcValue(choice,maze.lastMove()));
        }
    }

    private int chooseMove(){
        ArrayList<Integer> choices = new ArrayList<>();
        int choice;
        Double move = null;
        for(int i = 0; i<4;i++){
            if(move==null || states.get(vision).get(i)>move){
                move = states.get(vision).get(i);
                choices.clear();
                choices.add(i);
            } else if (states.get(vision).get(i).equals(move)){
                choices.add(i);
            }
        }
        if(choices.size()>1){
            choice = choices.get(rand.nextInt(choices.size()));
        } else {
            choice = choices.get(0);
        }
        return choice;
    }

    private void getState(){
        Map<Integer, Double> curState;
        if (!states.containsKey(vision)){
            curState = new HashMap<>();
            for (int i = 0;i<4;i++) {
                curState.put(i, 0.0);
            }
            states.put(vision, curState);
        }
    }

    private Double calcValue(int move, int reward){
        Double val = states.get(lastVis).get(move);
        Double calcs = 0.0, nextQ = 0.0;
        calcs += reward;
        for(int i = 0;i<4;i++){
            if(i == 0 || states.get(vision).get(i)>nextQ) {
                nextQ = states.get(vision).get(i);
            }
        }
        calcs += (0.9*nextQ);
        calcs -= states.get(lastVis).get(move);
        val = val + ((rate/100)*calcs);
        return val;
    }

    private void updateState(int choice, Double value){
        states.get(lastVis).put(choice, value);
    }

    private void runStats(){
        averageRuns[runs] += snek.getValue();
    }
}
