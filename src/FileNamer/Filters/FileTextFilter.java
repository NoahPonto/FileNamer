package FileNamer.Filters;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class FileTextFilter extends FileFilter {

	@Override
	public boolean accept(File file) {
		// TODO Auto-generated method stub
		return file.isDirectory() || file.getName().endsWith(".txt");
	}

	@Override
	public String getDescription() {
		return "Text file";
	}

}
