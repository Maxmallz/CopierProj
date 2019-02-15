
public class CopierTester {
	public static void main(String[] args) {
		
		StringBuilder sb = new StringBuilder();
		sb.append("JAVA ");
		sb.append("Copier ");
		sb.append("Jam ");
		sb.append("C:\\JavaRepo\\CopierProj\\folder1 ");
		sb.append("C:\\JavaRepo\\CopierProj\\folder2");
		
		
		try(Copier cp = new Copier(sb.toString())){
			
		}
		catch(Exception e){
			System.out.println("error");
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
		}
	}
}
