using System.Runtime.InteropServices;
using UnityEngine;
using UnityEngine.UI;

public class NewBehaviourScript : MonoBehaviour
{
	public Button BtnLoad, BtnRead;
	public Text LogText;
	// Use this for initialization
	void Start ()
	{
		var path = "ptgameconfig.json";
		Debug.Log("path:==>>" + path);
		BtnLoad.onClick.AddListener(()=>
		{
			Debug.Log("====>>>读取包内文件。");
			#if !UNITY_IOS
			LogText.text = LoadFile(path);
			#endif
		});
		BtnRead.onClick.AddListener(() =>
		{
			Debug.Log("====>>>读取剩余空间大小。");
			#if UNITY_IOS
			LogText.text = "剩余空间大小:" + _getFreeDiskSpace();
			#else
			LogText.text = "剩余空间大小:" + GetFreeDiskSpace();
			#endif
		});
	}
	
	public static string LoadFile(string path)
	{
		AndroidJavaClass m_AndroidJavaClass = new AndroidJavaClass("com.putao.loadfilesync.FileUtility");
		return m_AndroidJavaClass.CallStatic<string>("loadFile", path);
	}
	
	public static long GetFreeDiskSpace()
	{
		AndroidJavaClass m_AndroidJavaClass = new AndroidJavaClass("com.putao.loadfilesync.FileUtility");
		return m_AndroidJavaClass.CallStatic<long>("getFreeDiskSpace");
	}
	
    [DllImport ("__Internal")]
    private static extern long _getFreeDiskSpace ();
}
