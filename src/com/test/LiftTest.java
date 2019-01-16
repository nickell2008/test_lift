import com.lift.Building;
import com.lift.Lift;
import org.junit.Before;
import org.junit.Test;


public class LiftTest {

    Building building;
    Lift lift1;


    /**
     * Начало теста
     * Этажей в доме - 10
     * @throws Exception
     */
    @Before
    public void setUp()  {
        building = new Building(10);
        lift1 = new Lift(building, 1);
    }

    /**
     * Простой запуск лифта
     */
    @Test
    public void run() {
        building.Stage(1).callLift(4);
        building.Stage(3).callLift(2);
        building.Stage(4).callLift(1);
        lift1.run();
    }

    /**
     * Запуск лифта с вип ключем
     */
    @Test
    public void vipPerson() {
        building.Stage(5).callLift(4).turnVipKey();
        building.Stage(1).callLift(8);
        lift1.run();
    }

    /**
     * Запуск лифта с большим кол-вом людей
     */
    @Test
    public void morePerson() {
        for (int i = 0; i <= 12; i++)
            building.Stage(3).callLift(7);
        building.Stage(5).callLift(10);
        building.Stage(2).callLift(6);
        lift1.run();
    }

}