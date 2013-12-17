package com.common.file;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.HashMap;
import java.util.Map;

/**
 * 锁文件工具类.
 * <p>
 * Copyright: Copyright (c) Jul 20, 2011 11:18:00 AM
 * <p>
 * Company: 北京幻方朗睿技术有限公司
 * <p>
 * Author: lihui
 * <p>
 * Version: 1.0
 * <p>
 */
public class FileLocker {

	/** 被锁文件 */
	private static Map<File, FileLock> lockMap = new HashMap<File, FileLock>();

	/**
	 * 判断文件是否被锁
	 * 
	 * @param file
	 * @return true:被锁;false:没有被锁
	 */
	public static synchronized boolean isLocked(File file) {
		File dir = file.getParentFile();
		// 创建文件目录
		if (!dir.exists()) {
			dir.mkdirs();
		}
		try {
			// 创建文件
			if (!file.exists()) {
				file.createNewFile();
			}
			// 文件是否可写
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
	 * 判断文件是否被锁
	 * 
	 * @param filename
	 * @return true:被锁;false:没有被锁
	 * @throws Exception
	 */
	public static synchronized boolean isLocked(String filename)
			throws Exception {
		return isLocked(new File(filename));
	}

	/**
	 * 解锁文件
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
	 * 解锁文件
	 * 
	 * @param filename
	 */
	public static synchronized void unlock(String filename) {
		unlock(new File(filename));
	}
}