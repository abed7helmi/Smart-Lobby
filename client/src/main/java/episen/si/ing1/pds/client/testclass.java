package episen.si.ing1.pds.client;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class testclass {
    public static void main(String[] args) {
        //Pattern pattern = Pattern.compile("\\d+");
        //Matcher matcher = pattern.matcher("permission54");
        //int idpermission =Integer.parseInt(matcher.group(1));
        String ch= "dmkfl=45, dfisjk=12";
        String per[]=ch.split(",");
        int x=Integer.parseInt(getNbr(per[0]));
        System.out.println(x);
    }

    static String getNbr(String str)
    {
        // Remplacer chaque nombre non numérique par un espace
        str = str.replaceAll("[^\\d]", " ");
        // Supprimer les espaces de début et de fin
        str = str.trim();
        // Remplacez les espaces consécutifs par un seul espace
        str = str.replaceAll(" +", " ");

        return str;
    }

}