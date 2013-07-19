
@SuppressWarnings("rawtypes")
public class Process implements Comparable
{
	public int A;	
	public int B;
	public int C;
	public int D;
	
	public int remainingTime;
	public stateType state;
	
	public Process(int A, int B, int C, int D) 
	{
		this.A = A;
		this.B = B;
		this.C = C;
		this.D = D;
		remainingTime = B;
	}
	protected enum stateType {
		ready, running, blocked,finished
	}
	
	public String toString() {
		return String.format("%d %d %d %d", A, B, C, D);
	}
	
	public int getA() {
		return A;
	}
	public int getB() {
		return B;
	}
	public int getC() {
		return C;
	}
	public int getD() {
		return D;
	}
	public stateType getState() {
		return state;
	}
	public void setState(stateType state) {
		this.state = state;
	}
	public int compareTo(Object o) {
		if (this.D != ((Process) o).getD()) {
			return this.D - ((Process) o).getD();
		} /*else if (this.B != ((Process) o).getB()) {
			return this.B - ((Process) o).getB();
		} else if (this.C != ((Process) o).getC()) {
			return this.C - ((Process) o).getC();
		} else if (this.D != ((Process) o).getD()) {
			return this.D - ((Process) o).getD();
		}*/
		return 0;
	}
	
}
