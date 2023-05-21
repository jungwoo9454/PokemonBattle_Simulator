import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Main {

    static int[][] compatibility_Table;

    public static void main(String[] args){

        final String pokemon_path = "info.csv";
        final String compatibility_path = "compatibility.csv";
        String pokemon_data = LoadFile(pokemon_path);
        String compatibility_data = LoadFile(compatibility_path);

        compatibility_Table = ParseToCompatibility(compatibility_data);
        List<Pokemon> pokemonList = ParseToPokemon(pokemon_data);

        for(int i=0;i<pokemonList.size();i++)
        {
            for(int j=0;j<pokemonList.size();j++)
            {
                if(i != j)
                {
                    pokemonList.get(i).Init();
                    pokemonList.get(j).Init();

                    while (true) {
                        Fight(pokemonList.get(i), pokemonList.get(j));

                        if(pokemonList.get(j).hp <= 0)
                        {
                            pokemonList.get(i).FirstAttack++;
                            break;
                        }
                        Fight(pokemonList.get(j), pokemonList.get(i));
                        if(pokemonList.get(i).hp <= 0)
                        {
                            pokemonList.get(j).SecondAttack++;
                            break;
                        }
                    }
                }
            }
        }

        Collections.sort(pokemonList, new PokemonComparator());
        for(int i=0;i<5;i++)
        {
            System.out.println(pokemonList.get(i).toString());
        }

    }

    static String LoadFile(String path)
    {
        String content = "";
        //파일을 열 때 경로에 파일이 없을 수 있기에 예외처리
        try {
            FileInputStream fin = new FileInputStream(path);
            InputStreamReader in = new InputStreamReader(fin, "MS949");

            int c;
            while((c=in.read()) != -1) {
                content += (char)c;
            }

            in.close();
            fin.close();
        }
        catch(IOException e)
        {
            System.out.print(e);
        }
        return content;
    }

    static List<Pokemon> ParseToPokemon(String data)
    {
        List<Pokemon> pokemons = new ArrayList<>();
        String[] datas = data.split("\n");

        for(int i=1;i<datas.length;i++)
        {
            String[] property = datas[i].split(",");
            pokemons.add(new Pokemon(Integer.parseInt(property[0]), property[1], Integer.parseInt(property[2]), Integer.parseInt(property[3]), Integer.parseInt(property[4]), property[5].trim()));
        }
        return pokemons;
    }

    static int[][] ParseToCompatibility(String data)
    {
        String[] lines = data.split("\n");
        int[][] dataArr = new int[lines.length][lines.length];

        for(int i=0;i< lines.length;i++)
        {
            String[] numbers = lines[i].split(",");
            for(int j=0;j<numbers.length;j++)
            {
                dataArr[i][j] = Integer.parseInt(numbers[j].trim());
            }
        }
        return dataArr;
    }

    static int TypeToString(String type)
    {
        switch (type)
        {
            case "노말":
                return 0;
            case "격투":
                return 1;
            case "비행":
                return 2;
            case "독":
                return 3;
            case "땅":
                return 4;
            case "바위":
                return 5;
            case "벌레":
                return 6;
            case "고스트":
                return 7;
            case "강철":
                return 8;
            case "불꽃":
                return 9;
            case "물":
                return 10;
            case "풀":
                return 11;
            case "전기":
                return 12;
            case "에스퍼":
                return 13;
            case "얼음":
                return 14;
            case "드래곤":
                return 15;
            case "악":
                return 16;
            case "페어리":
                return 17;

        }
        return -1;
    }

    //A가 선공 포켓몬
    static int Fight(Pokemon a, Pokemon b)
    {
        float pct = 1.0f;
        int a_type = TypeToString(a.type);
        int b_type = TypeToString(a.type);

        int result = compatibility_Table[a_type][b_type];
        if(result == 1)
            pct = 0.8f;
        else if(result == 2)
            pct = 1.25f;


        int dmg = (int)(10 * ((float)a.attack/b.deffence) * pct) + 1;

        b.hp -= dmg;

        return dmg;
    }

    public static class PokemonComparator implements Comparator<Pokemon> {
        @Override
        public int compare(Pokemon f1, Pokemon f2) {
            if (f1.FirstAttack + f1.SecondAttack < f2.FirstAttack + f2.SecondAttack) {
                return 1;
            } else if (f1.FirstAttack + f1.SecondAttack > f2.FirstAttack + f2.SecondAttack) {
                return -1;
            }
            return 0;
        }
    }

}