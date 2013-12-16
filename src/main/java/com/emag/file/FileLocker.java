package com.emag.file;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.HashMap;
import java.util.Map;

/**
 * ���ļ�������.
 * <p>
 * Copyright: Copyright (c) Jul 20, 2011 11:18:00 AM
 * <p>
 * Company: �����÷���������޹�˾
 * <p>
 * Author: lihui
 * <p>
 * Version: 1.0
 * <p>
 */
public class FileLocker {

	/** �����ļ� */
	private static Map<File, FileLock> lockMap = new HashMap<File, FileLock>();

	/**
	 * �ж��ļ��Ƿ���
	 * 
	 * @param file
	 * @return true:����;false:û�б���
	 */
	public static synchronized boolean isLocked(File file) {
		File dir = file.getParentFile();
		// �����ļ�Ŀ¼
		if (!dir.exists()) {
			dir.mkdirs();
		}
		try {
			// �����ļ�
			if (!file.exists()) {
				file.createNewFile();
			}
			// �ļ��Ƿ��д
			if (file.canWrite()) {
				RandomAccessFile randomAccessFile = new RandomAccessFile(file,
						"rw");
				FileChannel channel = randomAccessFile.getChannel();
				FileLock lock = channel.tryLock();
				lockMap.put(file, lock);
				return (lock == null) || (!lock.isValid());
			}
		} catch (Exception ex) {
		}
		return true;
	}

	/**
	 * �ж��ļ��Ƿ���
	 * 
	 * @param filename
	 * @return true:����;false:û�б���
	 * @throws Exception
	 */
	public static synchronized boolean isLocked(String filename)
			throws Exception {
		return isLocked(new File(filename));
	}

	/**
	 * �����ļ�
	 * 
	 * @param file
	 */
	public static synchronized void unlock(File file) {
		try {
			FileLock fileLock = (FileLock) lockMap.get(file);
			if (fileLock != null) {
				fileLock.release();
				fileLock.channel().close();
				lockMap.remove(file);
			} else {
				System.out.println("Not In The Map");
			}
		} catch (Exception localException) {
		}
	}

	/**
	 * �����ļ�
	 * 
	 * @param filename
	 */
	public static synchronized void unlock(String filename) {
		unlock(new File(filename));
	}
}