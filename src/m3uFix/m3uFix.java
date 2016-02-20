package m3uFix;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;



public class m3uFix {
	private static String filePath;

	public static void main(String[] args) {
		String m3uFileName="G:\\20140620.m3u";
		try{
			File file = new File(m3uFileName);
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file),"MS932"));
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file.getAbsolutePath()+"_fix.m3u"),"MS932"));
			String str;
			str=br.readLine();
			while(str != null){
				if(!str.startsWith("#") && str.length()>8){
					str=str.replace("\\", "\\\\");
					File mp3File = new File(str);
					if(!mp3File.exists()){
						System.out.print(str+"\n");
						int index=str.lastIndexOf("\\");
						String filename=str.substring(index+1);
						filePath=null;
						String fixedPath=listFiles("E:\\MP3\\",filename);
						//System.out.println(fixedPath);
						if(fixedPath!=null){
							bw.write(fixedPath+"\n");
							System.out.println(fixedPath);
						}else{
							//探索して発見できなかった場合そのまま
							str=str.replace("\\\\", "\\");
							bw.write(str+"\n");
						}

					}else{
						//mp3ファイルが存在するのでそのまま書込む
						str=str.replace("\\\\", "\\");
						bw.write(str+"\n");
					}
				}else{
					bw.write(str+"\n");
				}
				str = br.readLine();
			}
			br.close();
			bw.close();
		}catch(FileNotFoundException e){
		  System.out.println(e);
		}catch(IOException e){
		  System.out.println(e);
		}
	}

	public static String listFiles(String directoryPath,String filename){
		File dir=new File(directoryPath);
		File[] files=dir.listFiles();
		for(int i=0;i<files.length;i++){
			File file=files[i];
			if(file.isDirectory()){
				listFiles(file.getAbsolutePath(), filename);
			}
			if(filePath!=null){
				break;
			}else{
				filePath=check(filename, file);
			}
		}
		return filePath;
	}

	private static String check(String filename,File file){

		if(!filename.equals(file.getName())){
			return null;
		}
		System.out.println("発見");
		return file.getAbsolutePath();
	}

}
