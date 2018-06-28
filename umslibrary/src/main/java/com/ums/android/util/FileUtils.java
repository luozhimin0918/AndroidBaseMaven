package com.ums.android.util;

import android.content.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileUtils {

	private static final String Tag = "FileUtils";

	public static void copyAssets(Context context, String assetDir, String sdcardDir) {
		String[] files;
		try {
			Log.d(Tag, "assetDir>>" + assetDir);
			files = context.getResources().getAssets().list(assetDir);
			Log.d(Tag, "files.length>>" + files.length);
		} catch (IOException e1) {
			Log.d(Tag, "IOException>>" + e1.toString());
			return;
		}
		for (int i = 0; i < files.length; i++) {
			Log.d(Tag, "i>>" + i);
			try {
				String fileName = files[i];
				Log.d(Tag, "fileName>>" + fileName);
				if (isFolder(fileName)) {
					Log.d(Tag, fileName + "is folder");
					continue;
				}
				String fullName = sdcardDir + fileName;
				Log.d(Tag, "fullName>>" + fullName);
				if (isExist(fullName)) {
					Log.d(Tag, fullName + "Exist");
					continue;
				} else {
					Log.d(Tag, fullName + "not Exist");
				}
				File fileTemp = new File(fullName + "_temp");
				File fileResult = new File(fullName);
				FileOutputStream fos;
				InputStream in = null;
				if (0 != assetDir.length()) {
					in = context.getAssets().open(
							assetDir + File.separator + fileName);
				} else {
					in = context.getAssets().open(fileName);
				}
				if (!fileTemp.exists()) {
					fileTemp.createNewFile();
					fos = new FileOutputStream(fileTemp);
					int len = 0;
					byte[] buffer = new byte[1024];
					while ((len = (in.read(buffer))) != -1) {
						fos.write(buffer, 0, len);
						fos.flush();
					}
					fos.close();
					in.close();
				}
				fileTemp.renameTo(fileResult);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static String[] suffixs = { ".png", ".jpg", ".css", ".js", ".html",
			".htm", ".jsp", ".apx", ".txt", ".LICENSE", ".swf" };

	private static boolean isFolder(String fileName) {
		for (int i = 0; i < suffixs.length; i++) {
			if (fileName.contains(suffixs[i])) {
				return false;
			}
		}
		return true;
	}

	private static boolean isExist(String fileName) {
		File f = new File(fileName);
		if (!f.exists()) {
			return false;
		}
		return true;
	}

	public static boolean bAssetsFile(Context context, String pt) {
		InputStream in = context.getClass().getResourceAsStream(pt);
		if (in != null) {
			return true;
		}
		return false;
	}
}
