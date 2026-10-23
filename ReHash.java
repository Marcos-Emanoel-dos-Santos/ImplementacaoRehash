import java.util.Random;

public class ReHash {
    static int tamanhoDados = 100;
    static int[][] tabela = new int[tamanhoDados][2];

    static int qtdElementos = 0;
    static int totalColisoes = 0;
    static int[][] tresMaisColidiram = new int[3][3]; static int idxMenor = 0;


    public ReHash(){
        for(int i = 0; i < tamanhoDados; i++){
            tabela[i][0] = -1;
            tabela[i][1] = -1;
        }

        for(int i = 0; i < 3; i++){
            tresMaisColidiram[i][2] = -1;
        }
    }


    int funcaoHash(int chave){
        return chave % tamanhoDados;
    }


    int buscaHash(int chave){
        int idx = funcaoHash(chave);

        while(tabela[idx][0] != chave && tabela[idx][0] != -1){
            idx = (idx + 1) % tamanhoDados;
        }

        if(tabela[idx][0] == -1) return -1;
        return tabela[idx][1]; 
    }


    void insereHash(int novaChave, int novoValor){
        if(qtdElementos >= tamanhoDados * 0.9){
            return;
        }


        int idx = funcaoHash(novaChave);

        if(tabela[idx][0] != -1){
            int colisoesLocais = 0;

            while(tabela[idx][0] != -1){
                idx = (idx + 1) % tamanhoDados;
                colisoesLocais++;
                totalColisoes++;
            }

            if(colisoesLocais > tresMaisColidiram[idxMenor][2]){
                tresMaisColidiram[idxMenor][0] = novaChave;
                tresMaisColidiram[idxMenor][1] = novoValor;
                tresMaisColidiram[idxMenor][2] = colisoesLocais;

                for(int i = 0; i < 3; i++){
                    if(tresMaisColidiram[i][2] < tresMaisColidiram[idxMenor][2]){
                        idxMenor = i;
                    }
                }
            }
        }


        tabela[idx][0] = novaChave;
        tabela[idx][1] = novoValor;
        qtdElementos++;
    }



    public static void main(String[] args){
        ReHash hash = new ReHash();
        Random random = new Random(1090);

        int quantidadeParaInserir = 100;

        // Inserção
        for (int i = 0; i < quantidadeParaInserir; i++) {
            int chaveAleatoria = random.nextInt(100001);
            int valorAleatorio = random.nextInt(100001);
            
            hash.insereHash(chaveAleatoria, valorAleatorio);
            System.out.println("Valor hipotético: " + valorAleatorio + " Valor inserido: " + hash.buscaHash(chaveAleatoria));
        }

        System.out.println("-----------------------------------------------------------");
        System.out.println("Total de colisões: " + totalColisoes);

        System.out.println("Posições livres:");
        for(int i = 0; i < tamanhoDados; i++){
            if(hash.tabela[i][0] == -1){
                System.out.print(i);

                if(i != tamanhoDados-1) System.out.print("  ");
            }
        }
        System.out.println();

        System.out.println("Três que mais colidiram:");
        for(int i = 0; i < 3; i++){
            if(tresMaisColidiram[i][2] != -1)
                System.out.println("Chave: " + tresMaisColidiram[i][0] + " | Valor: " + tresMaisColidiram[i][1]);
        }
    }
}
