import javax.swing.plaf.synth.SynthEditorPaneUI;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Maxim on 13.02.2016.
 */
public class Test {
    public static void main(String[] args) {

        String str1= "sdasda sdqweas dadsdqwe adsqw";
        String str2= "asd";

        str1.indexOf(str2);
        System.out.println( str1.lastIndexOf(str2)-str1.indexOf(str2)  );


        String [] strarr;

            strarr =  str1.split("[^0-9a-zA-Zа-яА-Я]+");;
            System.out.println(Arrays.toString(strarr) );


        ArrayList<String> al = new ArrayList<String>();

        for (int i =10 ; i<40 ; i++ ){
            al.add(Integer.toString(i));
        }

        al.remove("19");
        al.remove("16");
        al.remove("10");
        al.remove("39");
        for (String str: al) {
            if(str.compareTo("21")==0){
                System.out.println("found");
            }

        }
        String regex = "[w+]";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher("asdasd123123");
        System.out.println( m.find() );

        System.out.println(al);

        int a=1,b=7;

        a =  (a>b) ? a : b ;

        System.out.println("Hello World!"); // Display the string.
    }
}
