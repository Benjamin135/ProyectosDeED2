package com.uagrm.grafos.Grafos.Pesados;

import com.uagrm.grafos.Grafos.Excepciones.ExcepcionAristaYaExiste;
import com.uagrm.grafos.Grafos.Excepciones.NroVerticesInvalidoExcepcion;

import java.util.List;

public class Floyd {

    private GrafoPesado grafo;
    private double[][] matriz;    // Matriz de adyacencia de pesos
    private int[][] predecesores;

    public Floyd(GrafoPesado unGrafo) {
        this.grafo = unGrafo;
        int n = grafo.cantidadDeVertices();
        this.matriz = new double[n][n];
        this.predecesores = new int[n][n];
    }

    private void matrizDeCaminos() {
        int n = matriz.length;
        double max = Double.POSITIVE_INFINITY;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    matriz[i][j] = 0;
                    predecesores[i][j] = -1;
                } else {
                    matriz[i][j] = max;
                    predecesores[i][j] = -1;
                }
            }
        }

        for (int i = 0; i < n; i++) {
            List<AdyacenteConPeso> adyacentes = grafo.listaDeAdyacencias.get(i);
            for (int j = 0; j < adyacentes.size(); j++) {
                int elemento = adyacentes.get(j).getIndiceDeVertice();
                double peso = adyacentes.get(j).getPeso();
                matriz[i][elemento] = peso;
            }
        }
    }

    public String caminosMasCortos() {
        this.matrizDeCaminos();
        int n = matriz.length;
        double minimo;
        
        String caminos[][] = new String[n][n];
        String caminosAuxiliares[][] = new String[n][n];
        String caminoRecorrido = "", caminitos = "";
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                caminos[i][j] = "";
                caminosAuxiliares[i][j] = "";
            }
        }
        
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    minimo = Math.min(matriz[i][j], matriz[i][k] + matriz[k][j]);
                    if (matriz[i][j] != matriz[i][k] + matriz[k][j]) {
                        if (minimo == matriz[i][k] + matriz[k][j]) {
                            caminoRecorrido = "";
                            caminosAuxiliares[i][j] = k + "";
                            caminos[i][j] = caminosR(i, k, caminosAuxiliares, caminoRecorrido) + (k);
                            predecesores[i][j] = k;
                        }
                    }
                    matriz[i][j] = minimo;
                }
            }
        }
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (matriz[i][j] != Double.POSITIVE_INFINITY) {
                    if (i != j) {
                        if (caminos[i][j].equals("")) {
                            caminitos += "  Origen: " + i + "     Destino: " + j + "     Recorrido: [" +
                                    i + ", " + j + "]\n";
                        } else {
                            caminitos += "  Origen: " + i + "     Destino: " + j + "     Recorrido: [" +
                                    i + ", " + caminos[i][j] + ", " + j + "]\n";
                        }
                    }
                }
            }
        }
        return "Los diferentes caminos más cortos entre vértices son:\n\n"
                + caminitos;
    }

    private String caminosR(int i, int k, String[][] caminosAuxiliares, String caminoRecorrido) {
        if (caminosAuxiliares[i][k].equals("")) {
            return "";
        } else {
            caminoRecorrido += caminosR(i, Integer.parseInt(caminosAuxiliares[i][k].toString()), 
                    caminosAuxiliares, caminoRecorrido) + (Integer.parseInt(caminosAuxiliares[i][k].toString())) + ", ";
        }
        return caminoRecorrido;
    }
    
    
    public String mostrarMatriz() {
        String m = "[ ";
        int n = matriz.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                double max = Double.POSITIVE_INFINITY;
                if (matriz[i][j] == max) {
                    m = m + "***" + " ";
                } else {
                    m = m + matriz[i][j] + " ";
                }
            }
            m = m + "]\n[ ";
        }
        m = m.substring(0, m.length() - 2);
        return m;
    }

    public String mostrarPredecesores() {
        String m = "[ ";
        int n = predecesores.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                m = m + predecesores[i][j] + " ";
            }
            m = m + "]\n[ ";
        }
        m = m.substring(0, m.length() - 2);
        return m;
    }

    public static void main(String[] args) throws ExcepcionAristaYaExiste, NroVerticesInvalidoExcepcion {

        GrafoPesado grafo = new GrafoPesado(10);
        DigrafoPesado digrafo = new DigrafoPesado(5);

//        grafo.insertarArista(4, 7, 3);
//        grafo.insertarArista(4, 6, 4);
//        grafo.insertarArista(6, 8, 4);
//        grafo.insertarArista(4, 3, 5);
//        grafo.insertarArista(5, 1, 5);
//        grafo.insertarArista(0, 1, 5);
//        grafo.insertarArista(1, 3, 6);
//        grafo.insertarArista(6, 9, 6);
//        grafo.insertarArista(2, 3, 7);
//        grafo.insertarArista(8, 9, 7);
//        grafo.insertarArista(5, 8, 7);
//        grafo.insertarArista(2, 4, 8);
//        grafo.insertarArista(0, 3, 8);
//        grafo.insertarArista(5, 6, 9);
//        grafo.insertarArista(0, 2, 10);
//        grafo.insertarArista(3, 5, 11);
//        grafo.insertarArista(6, 7, 12);
//        grafo.insertarArista(7, 9, 12);
//        grafo.insertarArista(2, 7, 15);

        digrafo.insertarArista(0, 1, 1);
        digrafo.insertarArista(1, 3, 4);
        digrafo.insertarArista(1, 4, 7);
        digrafo.insertarArista(2, 0, 3);
        digrafo.insertarArista(2, 1, 2);
        digrafo.insertarArista(2, 4, 4);
        digrafo.insertarArista(3, 0, 6);
        digrafo.insertarArista(3, 4, 2);
        digrafo.insertarArista(4, 3, 3);

        Floyd f = new Floyd(digrafo);

        System.out.println(f.caminosMasCortos());

        System.out.println(f.mostrarMatriz());
        System.out.println(f.mostrarPredecesores());

    }

}
