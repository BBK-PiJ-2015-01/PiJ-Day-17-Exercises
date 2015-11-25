import org.junit.*;
import static org.junit.Assert.*;

//
//	Don't forget org.junit.runner.JUnitCore !
//
public class TestLinkedList {

	private List instance;
	private final int SLEEP = 6000;
	
	@Before
	public void init() {
		
		instance = new LinkedList();
	}
	
//	@Test
	public void addTwoIntegers() {
		
		int expectedSize = 2;
		Integer highValue = 1;
		int indexOfHighValue = 0;
		//
		instance.add(highValue);
		instance.add(0);
		int resultSize = instance.size();
		assertEquals(expectedSize, resultSize);
		ReturnObject result = instance.get(indexOfHighValue);
		assertFalse(result.hasError());
		//
		try {
			Thread.sleep(SLEEP);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Integer resultValue = (Integer) result.getReturnValue();
		assertEquals(highValue, resultValue);
	}
	
	@Test
	public void addThreeIntegers() {
		
		int expectedSize = 3;
		Integer highValue = 2;
		int indexOfHighValue = 0;
		//
		instance.add(highValue);		
		instance.add(1);
		instance.add(0);
		int resultSize = instance.size();
		assertEquals(expectedSize, resultSize);
		ReturnObject result = instance.get(indexOfHighValue);
		assertFalse(result.hasError());
		//
		try {
			Thread.sleep(SLEEP);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Integer resultValue = (Integer) result.getReturnValue();
		assertEquals(highValue, resultValue);
	}
}