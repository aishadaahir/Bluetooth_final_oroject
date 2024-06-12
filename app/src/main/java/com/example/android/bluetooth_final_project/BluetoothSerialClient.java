package com.example.android.bluetooth_final_project;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.view.Display;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

/**
 * BluetoothSerialClient 
 * 블루투스를 이용한 시리얼 통신을 간편하게~ :)
 *
 * www.dev.re.kr 
 * @author ice3x2@gmail.com / Sungbeom Hong.
 *
 */
public class BluetoothSerialClient extends Context {


	static final private String SERIAL_UUID = "00001101-0000-1000-8000-00805F9B34FB";
	static private BluetoothSerialClient sThis = null;

	private BluetoothAdapter mBluetoothAdapter;
	private OnBluetoothEnabledListener mOnBluetoothUpListener;
	private OnScanListener mOnScanListener;
	private BluetoothSocket mBluetoothSocket;
	private UUID mUUID = UUID.fromString(SERIAL_UUID);
	private AtomicBoolean mIsConnection = new AtomicBoolean(false);
	private ExecutorService mReadExecutor;
	private ExecutorService mWriteExecutor;
	private BluetoothStreamingHandler mBluetoothStreamingHandler;
	private Handler mMainHandler = new Handler(Looper.getMainLooper());
	private BluetoothDevice mConnectedDevice = null;
	private InputStream mInputStream;
	private OutputStream mOutputStream;


	/**
	 * BluetoothSerialClient 의 싱글 인스턴스를 가져온다.
	 * @return BluetoothSerialClient 의 인스턴스. 만약 블루투스를 사용할 수 없는 기기라면 null.
	 */
	public static BluetoothSerialClient getInstance() {
		if (sThis == null) {
			sThis = new BluetoothSerialClient();
		}
		if (sThis.mBluetoothAdapter == null) {
			sThis = null;
			return null;
		}


		return sThis;
	}

	private BluetoothSerialClient() {
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		mReadExecutor = Executors.newSingleThreadExecutor();
		mWriteExecutor = Executors.newSingleThreadExecutor();

	}

	@Override
	public AssetManager getAssets() {
		return null;
	}

	@Override
	public Resources getResources() {
		return null;
	}

	@Override
	public PackageManager getPackageManager() {
		return null;
	}

	@Override
	public ContentResolver getContentResolver() {
		return null;
	}

	@Override
	public Looper getMainLooper() {
		return null;
	}

	@Override
	public Context getApplicationContext() {
		return null;
	}

	@Override
	public void setTheme(int resid) {

	}

	@Override
	public Resources.Theme getTheme() {
		return null;
	}

	@Override
	public ClassLoader getClassLoader() {
		return null;
	}

	@Override
	public String getPackageName() {
		return null;
	}

	@Override
	public ApplicationInfo getApplicationInfo() {
		return null;
	}

	@Override
	public String getPackageResourcePath() {
		return null;
	}

	@Override
	public String getPackageCodePath() {
		return null;
	}

	@Override
	public SharedPreferences getSharedPreferences(String name, int mode) {
		return null;
	}

	@Override
	public boolean moveSharedPreferencesFrom(Context sourceContext, String name) {
		return false;
	}

	@Override
	public boolean deleteSharedPreferences(String name) {
		return false;
	}

	@Override
	public FileInputStream openFileInput(String name) throws FileNotFoundException {
		return null;
	}

	@Override
	public FileOutputStream openFileOutput(String name, int mode) throws FileNotFoundException {
		return null;
	}

	@Override
	public boolean deleteFile(String name) {
		return false;
	}

	@Override
	public File getFileStreamPath(String name) {
		return null;
	}

	@Override
	public File getDataDir() {
		return null;
	}

	@Override
	public File getFilesDir() {
		return null;
	}

	@Override
	public File getNoBackupFilesDir() {
		return null;
	}

	@Nullable
	@Override
	public File getExternalFilesDir(@Nullable String type) {
		return null;
	}

	@Override
	public File[] getExternalFilesDirs(String type) {
		return new File[0];
	}

	@Override
	public File getObbDir() {
		return null;
	}

	@Override
	public File[] getObbDirs() {
		return new File[0];
	}

	@Override
	public File getCacheDir() {
		return null;
	}

	@Override
	public File getCodeCacheDir() {
		return null;
	}

	@Nullable
	@Override
	public File getExternalCacheDir() {
		return null;
	}

	@Override
	public File[] getExternalCacheDirs() {
		return new File[0];
	}

	@Override
	public File[] getExternalMediaDirs() {
		return new File[0];
	}

	@Override
	public String[] fileList() {
		return new String[0];
	}

	@Override
	public File getDir(String name, int mode) {
		return null;
	}

	@Override
	public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory) {
		return null;
	}

	@Override
	public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory, @Nullable DatabaseErrorHandler errorHandler) {
		return null;
	}

	@Override
	public boolean moveDatabaseFrom(Context sourceContext, String name) {
		return false;
	}

	@Override
	public boolean deleteDatabase(String name) {
		return false;
	}

	@Override
	public File getDatabasePath(String name) {
		return null;
	}

	@Override
	public String[] databaseList() {
		return new String[0];
	}

	@Override
	public Drawable getWallpaper() {
		return null;
	}

	@Override
	public Drawable peekWallpaper() {
		return null;
	}

	@Override
	public int getWallpaperDesiredMinimumWidth() {
		return 0;
	}

	@Override
	public int getWallpaperDesiredMinimumHeight() {
		return 0;
	}

	@Override
	public void setWallpaper(Bitmap bitmap) throws IOException {

	}

	@Override
	public void setWallpaper(InputStream data) throws IOException {

	}

	@Override
	public void clearWallpaper() throws IOException {

	}

	@Override
	public void startActivity(Intent intent) {

	}

	@Override
	public void startActivity(Intent intent, @Nullable Bundle options) {

	}

	@Override
	public void startActivities(Intent[] intents) {

	}

	@Override
	public void startActivities(Intent[] intents, Bundle options) {

	}

	@Override
	public void startIntentSender(IntentSender intent, @Nullable Intent fillInIntent, int flagsMask, int flagsValues, int extraFlags) throws IntentSender.SendIntentException {

	}

	@Override
	public void startIntentSender(IntentSender intent, @Nullable Intent fillInIntent, int flagsMask, int flagsValues, int extraFlags, @Nullable Bundle options) throws IntentSender.SendIntentException {

	}

	@Override
	public void sendBroadcast(Intent intent) {

	}

	@Override
	public void sendBroadcast(Intent intent, @Nullable String receiverPermission) {

	}

	@Override
	public void sendOrderedBroadcast(Intent intent, @Nullable String receiverPermission) {

	}

	@Override
	public void sendOrderedBroadcast(@NonNull Intent intent, @Nullable String receiverPermission, @Nullable BroadcastReceiver resultReceiver, @Nullable Handler scheduler, int initialCode, @Nullable String initialData, @Nullable Bundle initialExtras) {

	}

	@Override
	public void sendBroadcastAsUser(Intent intent, UserHandle user) {

	}

	@Override
	public void sendBroadcastAsUser(Intent intent, UserHandle user, @Nullable String receiverPermission) {

	}

	@Override
	public void sendOrderedBroadcastAsUser(Intent intent, UserHandle user, @Nullable String receiverPermission, BroadcastReceiver resultReceiver, @Nullable Handler scheduler, int initialCode, @Nullable String initialData, @Nullable Bundle initialExtras) {

	}

	@Override
	public void sendStickyBroadcast(Intent intent) {

	}

	@Override
	public void sendStickyOrderedBroadcast(Intent intent, BroadcastReceiver resultReceiver, @Nullable Handler scheduler, int initialCode, @Nullable String initialData, @Nullable Bundle initialExtras) {

	}

	@Override
	public void removeStickyBroadcast(Intent intent) {

	}

	@Override
	public void sendStickyBroadcastAsUser(Intent intent, UserHandle user) {

	}

	@Override
	public void sendStickyOrderedBroadcastAsUser(Intent intent, UserHandle user, BroadcastReceiver resultReceiver, @Nullable Handler scheduler, int initialCode, @Nullable String initialData, @Nullable Bundle initialExtras) {

	}

	@Override
	public void removeStickyBroadcastAsUser(Intent intent, UserHandle user) {

	}

	@Nullable
	@Override
	public Intent registerReceiver(@Nullable BroadcastReceiver receiver, IntentFilter filter) {
		return null;
	}

	@Nullable
	@Override
	public Intent registerReceiver(@Nullable BroadcastReceiver receiver, IntentFilter filter, int flags) {
		return null;
	}

	@Nullable
	@Override
	public Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter, @Nullable String broadcastPermission, @Nullable Handler scheduler) {
		return null;
	}

	@Nullable
	@Override
	public Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter, @Nullable String broadcastPermission, @Nullable Handler scheduler, int flags) {
		return null;
	}

	@Override
	public void unregisterReceiver(BroadcastReceiver receiver) {

	}

	@Nullable
	@Override
	public ComponentName startService(Intent service) {
		return null;
	}

	@Nullable
	@Override
	public ComponentName startForegroundService(Intent service) {
		return null;
	}

	@Override
	public boolean stopService(Intent service) {
		return false;
	}

	@Override
	public boolean bindService(@NonNull Intent service, @NonNull ServiceConnection conn, int flags) {
		return false;
	}

	@Override
	public void unbindService(@NonNull ServiceConnection conn) {

	}

	@Override
	public boolean startInstrumentation(@NonNull ComponentName className, @Nullable String profileFile, @Nullable Bundle arguments) {
		return false;
	}

	@Override
	public Object getSystemService(@NonNull String name) {
		return null;
	}

	@Nullable
	@Override
	public String getSystemServiceName(@NonNull Class<?> serviceClass) {
		return null;
	}

	@Override
	public int checkPermission(@NonNull String permission, int pid, int uid) {
		return PackageManager.PERMISSION_GRANTED;
	}

	@Override
	public int checkCallingPermission(@NonNull String permission) {
		return PackageManager.PERMISSION_GRANTED;
	}

	@Override
	public int checkCallingOrSelfPermission(@NonNull String permission) {
		return PackageManager.PERMISSION_GRANTED;
	}

	@Override
	public int checkSelfPermission(@NonNull String permission) {
		return PackageManager.PERMISSION_GRANTED;
	}

	@Override
	public void enforcePermission(@NonNull String permission, int pid, int uid, @Nullable String message) {

	}

	@Override
	public void enforceCallingPermission(@NonNull String permission, @Nullable String message) {

	}

	@Override
	public void enforceCallingOrSelfPermission(@NonNull String permission, @Nullable String message) {

	}

	@Override
	public void grantUriPermission(String toPackage, Uri uri, int modeFlags) {

	}

	@Override
	public void revokeUriPermission(Uri uri, int modeFlags) {

	}

	@Override
	public void revokeUriPermission(String toPackage, Uri uri, int modeFlags) {

	}

	@Override
	public int checkUriPermission(Uri uri, int pid, int uid, int modeFlags) {
		return PackageManager.PERMISSION_GRANTED;
	}

	@Override
	public int checkCallingUriPermission(Uri uri, int modeFlags) {
		return PackageManager.PERMISSION_GRANTED;
	}

	@Override
	public int checkCallingOrSelfUriPermission(Uri uri, int modeFlags) {
		return PackageManager.PERMISSION_GRANTED;
	}

	@Override
	public int checkUriPermission(@Nullable Uri uri, @Nullable String readPermission, @Nullable String writePermission, int pid, int uid, int modeFlags) {
		return PackageManager.PERMISSION_GRANTED;
	}

	@Override
	public void enforceUriPermission(Uri uri, int pid, int uid, int modeFlags, String message) {

	}

	@Override
	public void enforceCallingUriPermission(Uri uri, int modeFlags, String message) {

	}

	@Override
	public void enforceCallingOrSelfUriPermission(Uri uri, int modeFlags, String message) {

	}

	@Override
	public void enforceUriPermission(@Nullable Uri uri, @Nullable String readPermission, @Nullable String writePermission, int pid, int uid, int modeFlags, @Nullable String message) {

	}

	@Override
	public Context createPackageContext(String packageName, int flags) throws PackageManager.NameNotFoundException {
		return null;
	}

	@Override
	public Context createContextForSplit(String splitName) throws PackageManager.NameNotFoundException {
		return null;
	}

	@Override
	public Context createConfigurationContext(@NonNull Configuration overrideConfiguration) {
		return null;
	}

	@Override
	public Context createDisplayContext(@NonNull Display display) {
		return null;
	}

	@Override
	public Context createDeviceProtectedStorageContext() {
		return null;
	}

	@Override
	public boolean isDeviceProtectedStorage() {
		return false;
	}

	/**
	 * 연결을 닫고 자원을 해지한다.
	 * 앱 종료시 반드시 호출해 줘야 한다.
	 */
	public void claer() {
		close();
		mReadExecutor.shutdownNow();
		mWriteExecutor.shutdownNow();
		sThis = null;
	}

	/**
	 * 블루투스를 사용 가능한 상태로 만들어준다. <br/>
	 * 만약 기기 내에서 블루투스 사용이 꺼져있다면, 사용자로 하여 블루투스 사용에 관한 선택을 하게 하는 창을 출력한다. 
	 * @param context activity 
	 * @param onBluetoothEnabledListener 블루투스 on/off 에 대한 이벤트. 
	 */
	public void enableBluetooth(Context context, OnBluetoothEnabledListener onBluetoothEnabledListener) {
		if (!mBluetoothAdapter.isEnabled()) {
			mOnBluetoothUpListener = onBluetoothEnabledListener;
			Intent intent = new Intent(context, BluetoothUpActivity.class);
			context.startActivity(intent);
		} else {
			onBluetoothEnabledListener.onBluetoothEnabled(true);
		}
	}


	/**
	 * 블루투스가 사용 가능한 상태인지 확인.
	 * @return false 라면 블루투스가 off 된 상태거나 사용할 수 없다. 
	 */
	public boolean isEnabled() {
		return mBluetoothAdapter.isEnabled();
	}


	public boolean connect(final Context context, final BluetoothDevice device, final BluetoothStreamingHandler bluetoothStreamingHandler) {
		if (!isEnabled()) return false;
		mConnectedDevice = device;
		mBluetoothStreamingHandler = bluetoothStreamingHandler;
		if (isConnection()) {
			mWriteExecutor.execute(new Runnable() {
				@Override
				public void run() {
					try {
						mIsConnection.set(false);
						mBluetoothSocket.close();
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					connect(context, device, bluetoothStreamingHandler);
				}
			});
		} else {
			mIsConnection.set(true);
			connectClient(context);
		}
		return true;
	}


	/**
	 * 과거에 페어링 되었던 블루투스 디바이스 목록을 가져온다.  
	 * @return
	 */
	public Set<BluetoothDevice> getPairedDevices() {
		if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
			// TODO: Consider calling
			//    ActivityCompat#requestPermissions
			// here to request the missing permissions, and then overriding
			//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
			//                                          int[] grantResults)
			// to handle the case where the user grants the permission. See the documentation
			// for ActivityCompat#requestPermissions for more details.
			return null;
		}
		Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
		return pairedDevices;
	}

	/**
	 * 주변의 새 블루투스 디바이스를 스캔한다. 
	 * @param context
	 * @param OnScanListener 블루투스를 스캔 이벤트.  
	 * @return
	 */
	public boolean scanDevices(Context context, OnScanListener OnScanListener) {
		if (!mBluetoothAdapter.isEnabled()) return false;
		if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
			// TODO: Consider calling
			//    ActivityCompat#requestPermissions
			// here to request the missing permissions, and then overriding
			//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
			//                                          int[] grantResults)
			// to handle the case where the user grants the permission. See the documentation
			// for ActivityCompat#requestPermissions for more details.
			return false;
		}
		if (mBluetoothAdapter.isDiscovering()) {
			mBluetoothAdapter.cancelDiscovery();
			try {
				context.unregisterReceiver(mDiscoveryReceiver);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
		}
		mOnScanListener = OnScanListener;
		IntentFilter filterFound = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		filterFound.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
		filterFound.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		context.registerReceiver(mDiscoveryReceiver, filterFound);
		mBluetoothAdapter.startDiscovery();
		return true;
	}

	/**
	 * 스캔을 취소한다.
	 * @param context
	 */
	public void cancelScan(Context context) {
		if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
			// TODO: Consider calling
			//    ActivityCompat#requestPermissions
			// here to request the missing permissions, and then overriding
			//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
			//                                          int[] grantResults)
			// to handle the case where the user grants the permission. See the documentation
			// for ActivityCompat#requestPermissions for more details.
			return;
		}
		if (!mBluetoothAdapter.isEnabled() || !mBluetoothAdapter.isDiscovering()) return;
		mBluetoothAdapter.cancelDiscovery();
		try {
			context.unregisterReceiver(mDiscoveryReceiver);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		if (mOnScanListener != null) mOnScanListener.onFinish();
	}

	/**
	 * 블루투스 디바이스와 연결 되어있는지를 가져온다. 
	 * @return true/false 
	 */
	public boolean isConnection() {
		return mIsConnection.get();
	}

	/**
	 * 연결된 블루투스 디바이스를 가져온다.
	 * @return 만약 연결된 블루투스 디바이스가 없다면 null.
	 */
	public BluetoothDevice getConnectedDevice() {
		return mConnectedDevice;
	}


	private void connectClient(Context context) {
		try {
			if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
				// TODO: Consider calling
				//    ActivityCompat#requestPermissions
				// here to request the missing permissions, and then overriding
				//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
				//                                          int[] grantResults)
				// to handle the case where the user grants the permission. See the documentation
				// for ActivityCompat#requestPermissions for more details.
				return;
			}
			mBluetoothSocket = mConnectedDevice.createRfcommSocketToServiceRecord(mUUID);
		} catch (IOException e) {
			close();
			e.printStackTrace();
			mBluetoothStreamingHandler.onError(e);
			return;
		}
		mWriteExecutor.execute(new Runnable() {
			@Override
			public void run() {
				try {
					if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
						// TODO: Consider calling
						//    ActivityCompat#requestPermissions
						// here to request the missing permissions, and then overriding
						//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
						//                                          int[] grantResults)
						// to handle the case where the user grants the permission. See the documentation
						// for ActivityCompat#requestPermissions for more details.
						return;
					}
					mBluetoothAdapter.cancelDiscovery();
					mBluetoothSocket.connect();
					manageConnectedSocket(mBluetoothSocket);
					callConnectedHandlerEvent();
					mReadExecutor.execute(mReadRunnable);
				} catch (final IOException e) {
					close();
					e.printStackTrace();
					mMainHandler.post(new Runnable() {
						@Override
						public void run() {
							mBluetoothStreamingHandler.onError(e);
						}
					});
					mIsConnection.set(false);
					try {
						mBluetoothSocket.close();
					} catch (Exception ec) {
						ec.printStackTrace();
					}
				}
			}
		});
	}


	private void manageConnectedSocket(BluetoothSocket socket) throws IOException {
		mInputStream = socket.getInputStream();
		mOutputStream = socket.getOutputStream();
	}

	private void callConnectedHandlerEvent() {
		mMainHandler.post(new Runnable() {
			@Override
			public void run() {
				mBluetoothStreamingHandler.onConnected();
			}
		});
	}


	private boolean write(final byte[] buffer) {
		if (!mIsConnection.get()) return false;
		mWriteExecutor.execute(new Runnable() {
			@Override
			public void run() {
				try {
					mOutputStream.write(buffer);
				} catch (Exception e) {
					close();
					e.printStackTrace();
					mBluetoothStreamingHandler.onError(e);
				}
			}
		});
		return true;
	}


	private boolean close() {
		mConnectedDevice = null;
		if (mIsConnection.get()) {
			mIsConnection.set(false);
			try {
				mBluetoothSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			mMainHandler.post(mCloseRunable);
			return true;
		}
		return false;
	}

	private Runnable mCloseRunable = new Runnable() {
		@Override
		public void run() {
			if (mBluetoothStreamingHandler != null) {
				mBluetoothStreamingHandler.onDisconnected();
			}
		}
	};

	private Runnable mReadRunnable = new Runnable() {
		@Override
		public void run() {
			try {
				final byte[] buffer = new byte[256];
				final int readBytes = mInputStream.read(buffer);
				mMainHandler.post(new Runnable() {
					@Override
					public void run() {
						if (mBluetoothStreamingHandler != null) {
							mBluetoothStreamingHandler.onData(buffer, readBytes);
						}
					}
				});
				mReadExecutor.execute(mReadRunnable);
			} catch (Exception e) {
				close();
				e.printStackTrace();
			}
		}
	};


	private BroadcastReceiver mDiscoveryReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (BluetoothDevice.ACTION_FOUND.equals(action)) {
				BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				if (mOnScanListener != null) mOnScanListener.onFoundDevice(device);
			} else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
				if (mOnScanListener != null) mOnScanListener.onFinish();
				try {
					context.unregisterReceiver(mDiscoveryReceiver);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				}
			} else if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
				if (mOnScanListener != null) mOnScanListener.onStart();
			}
		}
	};


	public static class BluetoothUpActivity extends Activity {
		private static int REQUEST_ENABLE_BT = 2;

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			getWindow().getDecorView().postDelayed(new Runnable() {
				@Override
				public void run() {
					upbluetoothDevice();
				}
			}, 100);
		}

		private void upbluetoothDevice() {
			BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
			if (!btAdapter.isEnabled()) {
				Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
				overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
				if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
					// TODO: Consider calling
					//    ActivityCompat#requestPermissions
					// here to request the missing permissions, and then overriding
					//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
					//                                          int[] grantResults)
					// to handle the case where the user grants the permission. See the documentation
					// for ActivityCompat#requestPermissions for more details.
					return;
				}
				startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
	        }
		}
		@Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			super.onActivityResult(requestCode, resultCode, data);
			if(requestCode == REQUEST_ENABLE_BT) {
		        	OnBluetoothEnabledListener onBluetoothEnabledListener = getInstance().mOnBluetoothUpListener;
		            if (resultCode == Activity.RESULT_OK) {
		            	if(onBluetoothEnabledListener != null) 
		            		onBluetoothEnabledListener.onBluetoothEnabled(true);
		            	finish();
		            } else {
		            	if(onBluetoothEnabledListener != null) 
		            		onBluetoothEnabledListener.onBluetoothEnabled(false);
		                finish();
		            }
			}
		}
	}
	// End BluetoothUpActivity
	

	public static interface OnBluetoothEnabledListener {
		public void onBluetoothEnabled(boolean success);
	}
	
	public static interface OnScanListener {
		public void onStart();
		public void onFoundDevice(BluetoothDevice bluetoothDevice);
		public void onFinish();
	}
	
	public abstract static class BluetoothStreamingHandler {
		public abstract void onError(Exception e);
		public abstract void onConnected();
		public abstract void onDisconnected();
		public abstract void onData(byte[] buffer, int length);
		public final boolean close() {
			BluetoothSerialClient btSet = getInstance();
			if(btSet != null) 
				return btSet.close();
			return false;
		}
		public final boolean write(byte[] buffer) {
			BluetoothSerialClient btSet = getInstance();
			if(btSet != null) 
				return btSet.write(buffer);
			return false;
		}
	}
	
}