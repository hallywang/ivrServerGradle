package com.emag.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class FileTools {
	public static void appendTxt(String str, String fileNane) throws Exception {
		write(str, fileNane, true);
	}

	public static final void copy(String from, String to) throws Exception {
		makeParentDir(to);
		FileInputStream fis = new FileInputStream(from);
		try {
			FileOutputStream fos = new FileOutputStream(to, false);
			try {
				byte[] buf = new byte[16384];
				int size = 0;
				while ((size = fis.read(buf)) != -1)
					fos.write(buf, 0, size);
			} catch (Exception ex) {
				throw ex;
			} finally {
				fos.flush();
				fos.close();
			}
		} catch (Exception ex) {
			throw ex;
		} finally {
			fis.close();
		}
	}

	public static final void copyDir(String from, String to, boolean recursive)
			throws Exception {
		if (from.endsWith("/"))
			from = from.substring(0, from.length() - 1);
		if (to.endsWith("/"))
			to = to.substring(0, to.length() - 1);
		File file = new File(from);
		if (file.isDirectory()) {
			String[] fileList = file.list();
			for (int i = 0; i < fileList.length; i++) {
				String tmp = from + "/" + fileList[i];
				if ((new File(tmp).isFile()) || (recursive))
					copyDir(tmp, to + "/" + fileList[i], recursive);
			}
		} else {
			copy(from, to);
		}
	}

	public static String getExtFilename(File file) {
		return getExtFilename(file.getName());
	}

	public static String getExtFilename(String filename) {
		int i = filename.lastIndexOf(".");
		if (i >= 0) {
			return filename.substring(i + 1);
		}
		return filename;
	}

	public static List<File> listFile(File dir) {
		return listFile(dir, true);
	}

	public static List<File> listFile(File dir, boolean recursive) {
		List<File> result = new ArrayList<File>();
		File[] files = dir.listFiles();
		for (File file : files) {
			if (file.isFile())
				result.add(file);
			else if ((file.isDirectory()) && (recursive)) {
				result.addAll(listFile(file, true));
			}
		}
		return result;
	}

	public static final void makeParentDir(String filename) throws Exception {
		File file = new File(filename);
		if (!file.exists()) {
			String parent = file.getParent();
			if (parent != null)
				new File(parent).mkdirs();
		}
	}

	public static boolean mkDir(String dirName) {
		File file = new File(dirName);
		if (file.exists()) {
			return file.canWrite();
		}

		String path = null;

		int firstSlash = dirName.indexOf("/");
		int finalSlash = dirName.lastIndexOf("/");
		if ((firstSlash < 0) && (finalSlash < 0)) {
			firstSlash = dirName.indexOf("\\");
			finalSlash = dirName.lastIndexOf("\\");
		}

		if (finalSlash == 0) {
			return false;
		}
		if (finalSlash == 1) {
			path = File.separator;
		} else if (firstSlash == finalSlash) {
			path = dirName.substring(0, finalSlash + 1);
		} else
			path = dirName.substring(0, finalSlash);

		File dir = new File(path);
		return dir.mkdirs();
	}

	public static byte[] read(String fileName) throws Exception {
		if (fileName == null) {
			return null;
		}
		FileInputStream in = null;
		try {
			in = new FileInputStream(fileName);
			byte[] bytes = new byte[in.available()];
			in.read(bytes);
			byte[] arrayOfByte1 = bytes;
			return arrayOfByte1;
		} catch (Exception e) {
			throw e;
		} finally {
			in.close();
		}
	}

	public static String readLine(String fileName) throws Exception {
		if (fileName == null) {
			return null;
		}
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(fileName));
			String str = in.readLine();
			if (str != null) {
				str = str.trim();
			}
			String str1 = str;
			return str1;
		} catch (Exception e) {
			throw e;
		} finally {
			in.close();
		}
	}

	public static List<String> readLines(File file) throws Exception {
		List<String> result = new ArrayList<String>();
		FileReader fileReader = new FileReader(file);
		try {
			BufferedReader bufferedReader = new BufferedReader(fileReader,
					65536);
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				result.add(line);
			}
			List<String> localList1 = result;
			return localList1;
		} catch (Exception ex) {
			throw ex;
		} finally {
			fileReader.close();
		}
	}

	public static List<String> readLines(String filename) throws Exception {
		return readLines(new File(filename));
	}

	public static String readTxt(File file) throws Exception {
		FileReader fileReader = new FileReader(file);
		try {
			char[] cs = new char[16384];

			StringBuilder buf = new StringBuilder(500);
			int n;
			while ((n = fileReader.read(cs)) >= 0) {
				buf.append(cs, 0, n);
			}
			String str = buf.toString();
			return str;
		} catch (Exception ex) {
			throw ex;
		} finally {
			fileReader.close();
		}
	}

	public static String readTxt(String fileName) throws Exception {
		return readTxt(new File(fileName));
	}

	public static void rmDir(File dir) {
		if (!dir.exists())
			return;
		if (dir.isDirectory()) {
			File[] files = dir.listFiles();
			for (File file : files) {
				if (file.isDirectory()) {
					rmDir(file);
				}
				file.delete();
			}
		}
		dir.delete();
	}

	public static void write(byte[] bytes, String fileName) throws Exception {
		if ((bytes == null) || (fileName == null)) {
			return;
		}
		mkDir(fileName);
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(fileName);
			out.write(bytes);
		} catch (Exception e) {
			throw e;
		} finally {
			out.close();
		}
	}

	public static void write(String str, String fileName, boolean isAppend)
			throws Exception {
		if ((fileName == null) || (str == null)) {
			return;
		}
		mkDir(fileName);
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new FileWriter(fileName, isAppend));
			out.write(str);
		} catch (Exception e) {
			throw e;
		} finally {
			out.close();
		}
	}

	public static void writeTxt(String str, String fileName) throws Exception {
		write(str, fileName, false);
	}
}