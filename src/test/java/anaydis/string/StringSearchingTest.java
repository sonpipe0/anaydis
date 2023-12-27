package anaydis.string;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Iterator;


public class StringSearchingTest {
    String text;
    StringSearcherProviderImpl searcherProvider;

    @Before
    public void setUp() {
        text = "En un lugar de la Mancha, de cuyo nombre no quiero acordarme, no ha mucho tiempo que vivía un hidalgo de los de lanza en astillero, adarga antigua, rocín flaco y galgo corredor. Una olla de algo más vaca que carnero, salpicón las más noches, duelos y quebrantos los sábados, lantejas los viernes, algún palomino de añadidura los domingos, consumían las tres partes de su hacienda. El resto della concluían sayo de velarte, calzas de velludo para las fiestas, con sus pantuflos de lo mesmo, y los días de entresemana se honraba con su vellorí de lo más fino. Tenía en su casa una ama que pasaba de los cuarenta, y una sobrina que no llegaba a los veinte, y un mozo de campo y plaza, que así ensillaba el rocín como tomaba la podadera. Frisaba la edad de nuestro hidalgo con los cincuenta años; era de complexión recia, seco de carnes, enjuto de rostro, gran madrugador y amigo de la caza. Quieren decir que tenía el sobrenombre de Quijada, o Quesada, que en esto hay alguna diferencia en los autores que deste caso escriben; aunque por conjeturas verosímiles se deja entender que se llamaba Quijana. Pero esto importa poco a nuestro cuento: basta que en la narración dél no se salga un punto de la verdad.\n" +
                "\n" +
                "Es, pues, de saber que este sobredicho hidalgo, los ratos que estaba ocioso, que eran los más del año, se daba a leer libros de caballerías, con tanta afición y gusto, que olvidó casi de todo punto el ejercicio de la caza, y aun la administración de su hacienda; y llegó a tanto su curiosidad y desatino en esto, que vendió muchas hanegas de tierra de sembradura para comprar libros de caballerías en que leer, y así, llevó a su casa todos cuantos pudo haber dellos; y de todos, ningunos le parecían tan bien como los que compuso el famoso Feliciano de Silva; porque la claridad de su prosa y aquellas entricadas razones suyas le parecían de perlas, y más cuando llegaba a leer aquellos requiebros y cartas de desafíos, donde en muchas partes hallaba escrito: «La razón de la sinrazón que a mi razón se hace, de tal manera mi razón enflaquece, que con razón me quejo de la vuestra fermosura». Y también cuando leía: «... los altos cielos que de vuestra divinidad divinamente con las estrellas os fortifican, y os hacen merecedora del merecimiento que merece la vuestra grandeza».";
        searcherProvider = new StringSearcherProviderImpl();

    }


    public void testFirst(StringSearcher stringSearcher) {
        int result = stringSearcher.first("hidalgo");
        Assert.assertEquals(94, result);
        Assert.assertEquals(text.substring(result,result+7),"hidalgo");
    }


    public void testIterator(StringSearcher stringSearcher) {
        ArrayList<Integer> expected = new ArrayList<>();
        expected.add(94);
        expected.add(761);
        expected.add(1245);

        ArrayList<Integer> actual = new ArrayList<>();
        Iterator<Integer> iterator = stringSearcher.all("hidalgo");
        while (iterator.hasNext()) {
            actual.add(iterator.next());
        }

        Assert.assertEquals(expected, actual);
        for(int x: actual){
            Assert.assertEquals(text.substring(x,x+7),"hidalgo");
        }
    }


    @Test
    public void testFirstAll(){
        for(StringSearcher searcher: searcherProvider.getAllStringSearchers(text)){
            testFirst(searcher);
        }
    }

    @Test
    public void testIteratorAll(){
        for(StringSearcher searcher: searcherProvider.getAllStringSearchers(text)){
            testIterator(searcher);
        }
    }
}
