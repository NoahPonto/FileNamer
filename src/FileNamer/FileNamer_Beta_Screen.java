package FileNamer;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.wb.swt.SWTResourceManager;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.NotSupportedException;
import com.mpatric.mp3agic.UnsupportedTagException;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.graphics.Point;

public class FileNamer_Beta_Screen {

	private FileNamer filenamer;
	private boolean songFilesSelected;
	private boolean textFileSelected;
	protected Shell window;

	public FileNamer_Beta_Screen(){
		filenamer = new FileNamer();
		songFilesSelected = false;
		textFileSelected = false;
	}

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			FileNamer_Beta_Screen window = new FileNamer_Beta_Screen();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {

		Display display = Display.getDefault();
		createContents();
		window.open();
		window.layout();
		while (!window.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		window = new Shell(SWT.CLOSE | SWT.TITLE | SWT.BORDER | 
				SWT.APPLICATION_MODAL | SWT.MIN );
		window.setImage(SWTResourceManager.getImage(FileNamer_Beta_Screen.class, "/FileNamer/HeadphoneIcon.ico"));
		window.setMinimumSize(new Point(450, 300));
		window.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		window.setSize(450, 300);
		window.setText("FileNamer_Beta");


		Label songFileLabel = new Label(window, SWT.NONE);
		songFileLabel.setBounds(50, 175, 90, 20);
		songFileLabel.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		songFileLabel.setBackground(window.getBackground());
		songFileLabel.setAlignment(SWT.CENTER);
		songFileLabel.setText("Unselected");

		Label textFileLabel = new Label(window, SWT.NONE);
		textFileLabel.setBounds(292, 175, 90, 20);
		textFileLabel.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		textFileLabel.setBackground(window.getBackground());
		textFileLabel.setText("Unselected");
		textFileLabel.setAlignment(SWT.CENTER);

		Label programTitle = new Label(window, SWT.NONE);
		programTitle.setBounds(30, 10, 221, 32);
		programTitle.setBackground(window.getBackground());
		programTitle.setFont(SWTResourceManager.getFont("Arial", 20, SWT.NORMAL));
		programTitle.setText("FileNamer Beta");

		Label signature = new Label(window, SWT.NONE);
		signature.setBounds(30, 43, 85, 20);
		signature.setBackground(window.getBackground());
		signature.setFont(SWTResourceManager.getFont("Arial", 9, SWT.ITALIC));
		signature.setText("Noah Ponto");

		Button btnRenameFiles = new Button(window, SWT.NONE);
		int rfwidth = 130;
		int rfheight = 30;
		int rfx = (window.getClientArea().width - rfwidth)/2;
		int rfy = (window.getClientArea().height - rfheight)/2;
		btnRenameFiles.setBounds(rfx, rfy, rfwidth, rfheight);
		btnRenameFiles.setEnabled(false);
		btnRenameFiles.setFont(SWTResourceManager.getFont("Arial", 9, SWT.NORMAL));
		btnRenameFiles.setText("Rename Files");


		Button textFileButton = new Button(window, SWT.NONE);
		textFileButton.setBounds(272, 200, 130, 30);
		textFileButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				File textfile = filenamer.uploadTextFile();

				if(textfile != null){
					textFileSelected = true;
					textFileLabel.setText("Selected");
					textFileLabel.setForeground(SWTResourceManager.getColor(SWT.COLOR_GREEN));

					if (songFilesSelected){
						btnRenameFiles.setEnabled(true);
					}
				}
			}
		});
		textFileButton.setText("Select Text File");

		Button songFilesButton = new Button(window, SWT.NONE);
		songFilesButton.setBounds(30, 200, 130, 30);
		songFilesButton.setForeground(SWTResourceManager.getColor(SWT.COLOR_CYAN));
		songFilesButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				File[] songfiles = filenamer.uploadSongFiles();
				if (songfiles.length != 0){	//if song files are selected
					songFilesSelected = true;
					songFileLabel.setText("Selected");
					songFileLabel.setForeground(SWTResourceManager.getColor(SWT.COLOR_GREEN));

					if (textFileSelected){
						btnRenameFiles.setEnabled(true);
					}
				}

			}
		});
		songFilesButton.setText("Select Song Files");







		btnRenameFiles.addSelectionListener(new SelectionAdapter() {	//btnRenameFiles selection
			@Override
			public void widgetSelected(SelectionEvent e) {

				songFilesButton.dispose();
				textFileButton.dispose();
				songFileLabel.dispose();
				textFileLabel.dispose();
				btnRenameFiles.dispose();


				Label lblNamingsongs = new Label(window, SWT.NONE);		//create naming songs label
				lblNamingsongs.setBackground(window.getBackground());
				lblNamingsongs.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
				lblNamingsongs.setBounds(30, 84, 104, 20);
				lblNamingsongs.setText("Naming songs...");

				Button btnClose = new Button(window, SWT.NONE);		//create close button
				btnClose.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						window.close();
					}
				});
				btnClose.setEnabled(false);
				btnClose.setBounds(272, 200, 130, 30);
				btnClose.setText("Close");


				Button btnOpenInFolder = new Button(window, SWT.NONE);
				btnOpenInFolder.setTouchEnabled(true);
				btnOpenInFolder.setFont(SWTResourceManager.getFont("Arial", 9, SWT.NORMAL));
				btnOpenInFolder.setBounds(30, 200, 130, 30);
				btnOpenInFolder.setText("Open in Folder");
				window.setDefaultButton(btnOpenInFolder);
				btnOpenInFolder.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						File f = new File(filenamer.getfolder());
						
						Desktop d = Desktop.getDesktop();

						try {
							d.open(f);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

					}
				});


				try {
					filenamer.renameSongs();
				} catch (UnsupportedTagException | InvalidDataException 
						| NotSupportedException | IOException e1) {

					e1.printStackTrace();
				}


				Label lblDone = new Label(window, SWT.NONE);	//create done label
				lblDone.setText("Done.");
				lblDone.setBackground(window.getBackground());
				lblDone.setForeground(SWTResourceManager.getColor(SWT.COLOR_GREEN));
				lblDone.setBounds(30, 110, 104, 20);
				
				lblNamingsongs.setForeground(SWTResourceManager.getColor(SWT.COLOR_GREEN));



				btnClose.setEnabled(true);
				window.setDefaultButton(btnClose);

			}
		});




	}
}
