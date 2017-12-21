package FileNamer.Filters;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class FileSongFilter extends FileFilter {

	@Override
	public boolean accept(File f) {
		
		return f.isDirectory() || f.getName().endsWith(".mp3") ;
		
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Music file";
	}

}
