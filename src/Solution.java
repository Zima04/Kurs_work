import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

/**
 * Created by User on 13.03.2018.
 */

import java.util.List;
import java.util.Objects;

public class Solution {
    List<Float> AforCSR = new ArrayList<Float>();
    List<Float> LJforCSR = new ArrayList<Float>();
    List<Float> LIforCSR = new ArrayList<Float>();
    List<Integer> diagElements = new ArrayList<Integer>();

    List<Integer> AforCOO = new ArrayList<Integer>();
    List<Integer> LJforCOO = new ArrayList<Integer>();
    List<Integer> LIforCOO = new ArrayList<Integer>();

    List<Integer> AAforCOOinHYB = new ArrayList<Integer>();
    List<Integer> LJforCOOinHYB = new ArrayList<Integer>();
    List<Integer> LIforCOOinHYB = new ArrayList<Integer>();

    public static void main(String[] args) throws Exception {
        Solution solution = new Solution();
        File input = new File("in2.txt");
        BufferedReader in = new BufferedReader(new FileReader(input));
        Integer[] razm = getRazm(in.readLine());
        Integer[] stringOfKeys;
        int razmX = razm[0];
        int razmY = razm[1];
        Integer[][] inputMatrix = new Integer[razmX][razmY];
//        Integer[][] resaltMatrix = new Integer[razmX][razmY];
        System.out.print("Исходная матрица: \n");
        for (int i = 0; i < razmX; i++) {
            int count = 0;
            stringOfKeys = getStringOfKeys(in.readLine());
            for (int j = 0; j < razmY; j++) {
                inputMatrix[i][j] = stringOfKeys[count];
                count++;
                System.out.print(inputMatrix[i][j] + " ");
            }
            System.out.println();
        }
        solution.getCRS(inputMatrix, razmX, razmY);
//        solution.getCOO(inputMatrix,razmX,razmY);
//        solution.getMforHYB(inputMatrix,razmX,razmY);
//        solution.getHYB(inputMatrix, razmX, razmY);
        solution.methodGaussa();
        solution.getResult();
//        File newFile = new File("out.txt");
//        newFile.createNewFile();
//        FileWriter fileOut = new FileWriter(newFile);
//        fileOut.write("No");
//        fileOut.close();
    }

    public static Integer[] getRazm(String a) {
        String[] mas = a.split(" ");
        Integer[] result = new Integer[2];
        result[0] = Integer.parseInt(mas[0]);
        result[1] = Integer.parseInt(mas[1]);
        return result;
    }

    public static Integer[] getStringOfKeys(String a) {
        String[] mas = a.split(" ");
        Integer[] resalt = new Integer[mas.length];
        for (int i = 0; i < resalt.length; i++)
            resalt[i] = Integer.parseInt(mas[i]);
        return resalt;
    }

    public void getCRS(Integer[][] inputMatrix, int razmI, int razmJ) {
        int markCRS = 0;
        Boolean ifFirstElem = true;
        int itemLI = 0;
        for (int i = 0; i < razmI; i++) {
            for (int j = 0; j < razmJ; j++) {
                if (inputMatrix[i][j] != 0) {
                    this.AforCSR.add(new Float(inputMatrix[i][j]));
                    markCRS++;
                    itemLI++;
                    this.LJforCSR.add(new Float(j + 1));
                    if (ifFirstElem) {
                        this.LIforCSR.add(new Float(itemLI));
                        ifFirstElem = false;
                    }
                }
                if (i == j) {
                    diagElements.add(this.AforCSR.size());
                }
            }
            if (ifFirstElem) {
                this.LIforCSR.add(new Float(itemLI + 1));
            }
            ifFirstElem = true;
            if (razmI == i + 1) {
                this.LIforCSR.add(new Float(itemLI + 1));
            }
        }
        markCRS *= 3;
        System.out.print("Массив А для CSR: \n");
        for (int i = 0; i < this.AforCSR.size(); i++) {
            System.out.print(this.AforCSR.get(i) + " ");
        }
        System.out.println();
        System.out.print("Массив LJ для CSR: \n");
        for (int i = 0; i < this.LJforCSR.size(); i++) {
            System.out.print(this.LJforCSR.get(i) + " ");
        }
        System.out.println();
        System.out.print("Массив LI для CSR: \n");
        for (int i = 0; i < this.LIforCSR.size(); i++) {
            System.out.print(this.LIforCSR.get(i) + " ");
        }
        System.out.println();

    }

    public void getCOO(Integer[][] inputMatrix, int razmI, int razmJ) {
        int markCOO = 0;
        for (int i = 0; i < razmI; i++) {
            for (int j = 0; j < razmJ; j++) {
                if (inputMatrix[i][j] != 0) {
                    this.AforCOO.add(inputMatrix[i][j]);
                    markCOO++;
                    this.LJforCOO.add(j + 1);
                    this.LIforCOO.add(i + 1);
                }
            }
        }
        markCOO = markCOO * 2 + this.LIforCOO.size();
        System.out.print("Массив A для COO: \n");
        for (int i = 0; i < this.AforCOO.size(); i++) {
            System.out.print(this.AforCOO.get(i) + " ");
        }
        System.out.println();
        System.out.print("Массив LJ для COO: \n");
        for (int i = 0; i < this.LJforCOO.size(); i++) {
            System.out.print(this.LJforCOO.get(i) + " ");
        }
        System.out.println();
        System.out.print("Массив LI для COO: \n");
        for (int i = 0; i < this.LIforCOO.size(); i++) {
            System.out.print(this.LIforCOO.get(i) + " ");
        }
        System.out.println();
    }

    public void getMforHYB(Integer[][] inputMatrix, int razmI, int razmJ) {
        int countOfElements;
        int m = 0;
        for (int i = 0; i < razmI; i++) {
            countOfElements = 0;
            for (int j = 0; j < razmJ; j++) {
                if (inputMatrix[i][j] != 0) {
                    countOfElements++;
                }
                if (2 * razmI <= countOfElements * 3) {
                    m += 1;
                } else
                    m = j;
            }
        }
        System.out.println("Число M : " + m);

    }

    public void getHYB(Integer[][] inputMatrix, int razmI, int razmJ) {
        int markHYB = 0;
        int countNNZ = 0;
        int m = 2;
        Integer[][] matrixAA = new Integer[razmI][m];
        Integer[][] matrixLJ = new Integer[razmI][m];
        int countOfElement;

        for (int i = 0; i < razmI; i++) {
            countOfElement = 0;
            for (int j = 0; j < razmJ; j++) {
                if (inputMatrix[i][j] != 0 && m > countOfElement) {
                    matrixAA[i][countOfElement] = inputMatrix[i][j];
                    matrixLJ[i][0] = i + 1;
                    matrixLJ[i][1] = j + 1;
                    countOfElement++;
                    countNNZ++;
                } else {
                    if (inputMatrix[i][j] != 0 && m <= countOfElement) {
                        this.AAforCOOinHYB.add(inputMatrix[i][j]);
                        this.LJforCOOinHYB.add(j + 1);
                        this.LIforCOOinHYB.add(i + 1);
                        countOfElement++;
                        countNNZ++;
                    }
                }
            }
        }
        markHYB = 2 * razmI * razmJ + (countNNZ - razmI * razmJ) * 3;
        System.out.print("Матрица АА для HYB: \n");
        for (int i = 0; i < razmI; i++) {
            for (int j = 0; j < m; j++) {
                System.out.print(matrixAA[i][j] + " ");
            }
            System.out.println();
        }
        System.out.print("Матрица LJ для HYB: \n");
        for (int i = 0; i < razmI; i++) {
            for (int j = 0; j < m; j++) {
                System.out.print(matrixLJ[i][j] + " ");
            }
            System.out.println();
        }
        System.out.print("Массив АА для HYB/COO: \n");
        for (int i = 0; i < this.AAforCOOinHYB.size(); i++) {
            System.out.print(this.AAforCOOinHYB.get(i) + " ");
        }
        System.out.println();
        System.out.print("Массив LJ для HYB/COO: \n");
        for (int i = 0; i < this.LJforCOOinHYB.size(); i++) {
            System.out.print(this.LJforCOOinHYB.get(i) + " ");
        }
        System.out.println();
        System.out.print("Массив LI для HYB/COO: \n");
        for (int i = 0; i < this.LIforCOOinHYB.size(); i++) {
            System.out.print(this.LIforCOOinHYB.get(i) + " ");
        }
        System.out.println();
    }

    public void methodGaussa() {
        int razmI = 5;
        int pointerActiveString = 0;
        int pointerNextString = 0;
        float elem = 0;
        float diagElem = 0;
        int startPointForActive = 0;
        int startPointForNext = 0;
        float diagJ = 0;
        float alpha = 0;
        long startTime = System.nanoTime();

        for (int i = 0; i < razmI-2; i++) {

            for (int t = startPointForActive; t < this.LJforCSR.size(); t++) {
                if (this.LJforCSR.get(t) > this.LJforCSR.get(t + 1)) {
                    pointerActiveString = t + 1;
                    break;
                }
            }

            for (int k = i + 1; k < 5; k++) {

                for (int t = startPointForActive; t < LJforCSR.size(); t++) {
                    if (this.LJforCSR.get(t) == i + 1) {
                        diagElem = this.AforCSR.get(t);
                        diagJ = this.LJforCSR.get(t);
                        break;
                    }
                }

                if (k == i + 1) {
                    startPointForNext = pointerActiveString;
                }

                for (int t = startPointForNext; t < this.LJforCSR.size(); t++) {
                    if (this.LJforCSR.size() == t + 1) {
                        pointerNextString = t + 1;
                        break;
                    }
                    if (this.LJforCSR.get(t) > this.LJforCSR.get(t + 1)) {
                        pointerNextString = t + 1;
                        break;
                    }
                }

                for (int s = startPointForNext; s < pointerNextString; s++) {
                    alpha = 0;
                    if (this.LJforCSR.get(s) == diagJ) {
                        alpha = this.AforCSR.get(s);
                        break;
                    }
                }

                if (alpha != 0) {
                    elem = alpha / diagElem;

                    for (int s = startPointForActive; s < pointerActiveString; s++) {
                        for (int t = startPointForNext; t < pointerNextString; t++) {
                            if (Objects.equals(this.LJforCSR.get(s), this.LJforCSR.get(t))) {
                                float newElem = this.AforCSR.get(t) - elem * this.AforCSR.get(s);
                                this.AforCSR.set(t, newElem);
                                break;
                            }
                        }
                    }
                }
                startPointForNext = pointerNextString;
            }
            startPointForNext = pointerActiveString;
            startPointForActive = pointerActiveString;
        }
        long stopTime = System.nanoTime();
        double elapsedTime = (stopTime - startTime)/1000000000.0;
        System.out.println("Время затраченное на выполнение прямого хода метода Гаусса: " + elapsedTime + "c.");

    }
    public void getResult() {
        for (int i = 0; i < this.AforCSR.size(); i++)
        {
            if(this.AforCSR.get(i) == 0){
                this.AforCSR.remove(i);
                this.LJforCSR.remove(i);
                i = i-1;
            }
        }
        System.out.print("Массив А для CSR: \n");
        for (int i = 0; i < this.AforCSR.size(); i++) {
            System.out.print(this.AforCSR.get(i) + " ");
        }
        System.out.println();
        System.out.print("Массив LJ для CSR: \n");
        for (int i = 0; i < this.LJforCSR.size(); i++) {
            System.out.print(this.LJforCSR.get(i) + " ");
        }
        System.out.println();
        System.out.print("Массив LI для CSR: \n");
        this.LIforCSR.clear();
        for (int t = 0; t < this.LJforCSR.size(); t++) {
            if(t == this.LJforCSR.size()-1){
                this.LIforCSR.add(new Float(t));
                break;
            }
            if (this.LJforCSR.get(t) >= this.LJforCSR.get(t + 1)) {
                this.LIforCSR.add(new Float (t));
            }
        }
        this.LIforCSR.add(new Float(this.AforCSR.size()+1));
        for (int t = 0; t < this.LIforCSR.size(); t++) {
            System.out.print(this.LIforCSR.get(t) + " ");
        }
    }
}
