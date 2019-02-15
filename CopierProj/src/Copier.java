import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

import javax.swing.text.DefaultEditorKit.CopyAction;

public class Copier implements AutoCloseable {
	
	private final static String KEYWORD = "Jam";
	private final static int commandCount = 5;
	private boolean isValidPath = false;
	private boolean isValidCommand = false;
	private String fileCopyCommand;
	private Path sourcePath;
	private Path destPath;
	private int totalKeyWordFiles = 0;
	private ArrayList<File> files = new ArrayList<File>();
	
	public Copier(String fileCopyCommand) throws InvalidFileCopyCommand, IOException {
		this.fileCopyCommand = fileCopyCommand;
		checkCommandPath();
		checkFilePath();
		
		if(isValidPath && isValidCommand) {
			getTotalKeywordFiles();
			copyFiles();
		}
		
	}
	
	private void checkCommandPath() throws InvalidFileCopyCommand {
		if(fileCopyCommand == null || "".contentEquals(fileCopyCommand)) {
			throw new InvalidFileCopyCommand("null command path");
			} //null string
		
		String[] strArr = fileCopyCommand.split(" ");
		
		if(strArr.length != commandCount) {
			throw new InvalidFileCopyCommand("invalid command path length. Enter command in format 'java Copier Jam c:\\folder1 c:\\folder2'");
			}//invalid no of parameters
		
		if(!"JAVA".equals(strArr[0].toUpperCase())) {
			throw new InvalidFileCopyCommand("invalid prefix keyword. java is the prefix");
		}//invalid command
		
		if("Copier".equals(strArr[1].toUpperCase())) {
			throw new InvalidFileCopyCommand("invalid prefix keyword. Copier is the prefix");
		}//invalid command
		
		if(KEYWORD.equals(strArr[2].toUpperCase())) {
			throw new InvalidFileCopyCommand("invalid copy keyword");
		}//invalid command
		
		
		sourcePath = Paths.get(strArr[3]);
		destPath = Paths.get(strArr[4]);
		
		isValidCommand = true;
		
	}
	
	private void checkFilePath() {
		if(!Files.isReadable(sourcePath)) {System.out.println("you do not have read permission on " + sourcePath); return;}
		if(!Files.isWritable(destPath)) {System.out.println("you do not have write permission on " + destPath); return;}
		
		if(!Files.exists(sourcePath)) {
			System.out.println("Invalid source path: " + sourcePath);
			return;
		}
		else if(!Files.exists(destPath)) {
			System.out.println("invalid destination path: " + destPath);
			return;
		}
		else {
			isValidPath = true;
		}
	}
	
	private void getTotalKeywordFiles() {
		
		
		File sourceFiles = new File(sourcePath.toString());
		
		File[] folder = sourceFiles.listFiles();
		
		
		for(File f: folder) {
			if(f.getName().startsWith(KEYWORD)) {
				totalKeyWordFiles++;
				files.add(f);
			}
		}
		
		if(totalKeyWordFiles == 0) {System.out.println("No file to copy begins with " + KEYWORD);return;}
	}

	private void copyFiles() throws IOException {
		
		File[] outputFiles = new File[totalKeyWordFiles];
		
		if(sourcePath != null && destPath != null) {
			
		    FileInputStream instream = null;
			FileOutputStream outstream = null;
			
			for(int i = 0; i < totalKeyWordFiles; i++) {
				outputFiles[i] = new File(destPath + "\\" + files.get(i).getName()); //create output file path
				System.out.println("source file: " + files.get(i).getName());
				System.out.println("destination file" + outputFiles[i]);
				
				instream = new FileInputStream(files.get(i).getName());
	    	    outstream = new FileOutputStream(outputFiles[i]);
	 
	    	    byte[] buffer = new byte[1024];
	 
	    	    int length;
	    	    /*copying the contents from input stream to
	    	     * output stream using read and write methods
	    	     */
	    	    
	    	    while ((length = instream.read(buffer)) > 0){
	    	    	outstream.write(buffer, 0, length);
	    	    }

	    	    //Closing the input/output file streams
	    	    instream.close();
	    	    outstream.close();

	    	    System.out.println("File copied successfully!!");
			}
			
			//System.out.println("successfully copies " + totalKeyWordFiles + " from " + sourcePath + " to " + destPath);
		}
	}

	@Override
	public void close() throws Exception {
		// TODO Auto-generated method stub
		
	}
}
