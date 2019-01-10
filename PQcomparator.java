
import java.util.Comparator;

public class PQcomparator implements Comparator<Distance>{

	@Override
	public int compare(Distance arg0, Distance arg1) {
		return (int) (arg0.getMinDistance() - arg1.getMinDistance());
	}
	
}