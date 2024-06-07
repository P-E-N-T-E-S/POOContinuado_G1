package br.edu.cesarschool.cc.poo.ac.utils.ordenacao;

public class Ordenadora {
    public static void ordenar(Object[] lista, Comparador comp){
        int tamanho = lista.length;
        for(int i = 0; i  < tamanho; i++){
            for(int j = 0; j < tamanho-1; j++){
                if(comp.comparar(lista[j], lista[j+1]) > 0){
                    Object temp = lista[j];
                    lista[j] = lista[j+1];
                    lista[j+1] = temp;
                }
            }
        }
    }
    public static void ordenar(Comparavel[] lista){
        Comparador comp = new Comparador() {
            @Override
            public int comparar(Object o1, Object o2) {
                return ((Comparavel) o1).comparar(o2);
            }
        };
        ordenar((Object[]) lista, comp);
    }
}
