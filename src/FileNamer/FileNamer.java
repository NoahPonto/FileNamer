package FileNamer;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JFileChooser;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.NotSupportedException;
import com.mpatric.mp3agic.UnsupportedTagException;

import FileNamer.Filters.FileSongFilter;
import FileNamer.Filters.FileTextFilter;

public class FileNamer {
	private File textfile;
	private File[] songfiles;
	private String folder;
	
	public String getfolder(){
		return folder;
	}

	public File uploadTextFile(){
		
		JFileChooser textfilechooser = new JFileChooser(new File("C:\\Users\\s-ponton\\Desktop\\"));
		textfilechooser.setFileFilter(new FileTextFilter());
		textfilechooser.setDialogTitle("Text file containing song information");

		textfilechooser.showOpenDialog(null);
		File txtfl = textfilechooser.getSelectedFile();
		
		if (txtfl != null){
			textfile = txtfl;
		}

		return textfile;
	}

	public File[] uploadSongFiles(){
		JFileChooser jfc = new JFileChooser(new File("C:\\Users\\s-ponton\\Desktop\\"));
		jfc.setMultiSelectionEnabled(true);
		jfc.setFileFilter(new FileSongFilter());
		jfc.setDialogTitle("Choose files to rename");
		jfc.showOpenDialog(null);
		
		File[] sngs = jfc.getSelectedFiles();
		
		if (sngs.length != 0){
			songfiles = jfc.getSelectedFiles();
		}

		return songfiles;
	}

	public void renameSongs() throws UnsupportedTagException, InvalidDataException, IOException, NotSupportedException{

		
		Scanner console = new Scanner(textfile);

		for (File song : songfiles){

			String str = console.nextLine();

			String title = str.substring(0, str.indexOf(" -"));
			String artist = str.substring(str.indexOf("- ")+1, str.length());
			String filename = replaceCharacters(title);

			int endpoint = song.getPath().length() - song.getName().length();
			folder = song.getPath().substring(0, endpoint);

			Mp3File m = new Mp3File(song.getPath());
			ID3v2 tag = m.getId3v2Tag();

			//tag.setTrack("");
			tag.setArtist(artist);
			tag.setTitle(title);

			File tempsongfile = new File(folder + filename + ".mp3");
			
			
			//Check if the file already exists
			if (tempsongfile.exists()){
				m.save(folder + filename + " - copy.mp3");	//if it already exists add "copy" to the name
			}
			else {
				m.save(folder + filename + ".mp3");	//else save the file
			}
		
			song.delete(); //delete original file

		}
		


	}

	private String replaceCharacters(String title){
		//WORKING 12-20-17 8:23AM
		String filename = "";

		for (int i = 0; i < title.length(); i++){

			char cursor = title.charAt(i);

			if (cursor == '~' || cursor == '#' || cursor == '%' || cursor == '&' || cursor == '*' || 
					cursor == ':' || cursor == '?' || cursor == '/' || cursor == '+' || cursor == '|'){
				filename+= "_";
			}
			else filename += cursor;
		}

		return filename;
	}
}
